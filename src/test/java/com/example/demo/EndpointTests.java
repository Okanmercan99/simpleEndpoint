package com.example.demo;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.example.demo.module.Mortgage;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class EndpointTests {

	@LocalServerPort
	private int port;

	@Autowired
    private TestRestTemplate restTemplate;
    
    static private final ArrayList<Mortgage> acceptedMortgageList = new ArrayList<Mortgage>();

    @Test
    @Order(1)
    public void applyMortgageTest() throws Exception {

        String url = "http://localhost:" + port + "/api/apply";

        HashMap<String, String> response = new HashMap<String, String>();
        Random random = new Random();

        acceptedMortgageList.clear();
        for (int i = 0; i < 1000; i++) {

            Mortgage request = new Mortgage(random.nextDouble() * 100000, random.nextDouble() * 100000, 
                                         random.nextInt(10) + 1, "Name-" + i, "Surname-" + i);

            response = this.restTemplate.postForObject(url, request, response.getClass());
            
            if ((request.getIncome() * 0.3) > (request.getAmount() / request.getTerm())) {

                acceptedMortgageList.add(request);
                assertThat(response.get("response")).contains("accepted");

            } else {
                assertThat(response.get("response")).contains("rejected");
            }

        }
    }
    
    @Test
    @Order(2)
    public void listMortgageTest() throws Exception {

        String url = "http://localhost:" + port + "/api/list";

        ResponseEntity<List<Mortgage>> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Mortgage>>() {
                });

        List<Mortgage> response = responseEntity.getBody();

        assertThat(response).containsExactlyInAnyOrderElementsOf(acceptedMortgageList);
    }
    
    @Test
    @Order(3)
    public void averageAmount() throws Exception {

        String url = "http://localhost:" + port + "/api/average";

        HashMap<String, Double> response = new HashMap<String, Double>();
        response = this.restTemplate.getForObject(url, response.getClass());

        Double average = 0.0;

        for (Mortgage mortgage : acceptedMortgageList) {
            average = average.doubleValue() + mortgage.getAmount().doubleValue();
        }

        if (acceptedMortgageList.size() > 0) {
            average = average.doubleValue() / acceptedMortgageList.size();
        }

        assertThat(response.get("average")).isEqualTo(average, Assertions.within(0.0001));
    }
}