package com.example.demo.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.module.Mortgage;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
@CrossOrigin("*")
public class EndpointController {

    private static final HashSet<Mortgage> approvedMortgages = new HashSet<>();
    
    
    @PostMapping("/apply")
    public HashMap<String, String> applyMortgage(@RequestBody Mortgage request) {

        log.info("Recieved Mortgage request.");

        HashMap<String, String> response = new HashMap<String, String>();
        

        if (request.validateMortgage()) {
            
            log.info("Mortgage is accepted.");

            approvedMortgages.add(request);
            
            response.put("response", "Mortgage is accepted.");

        } else {

            log.info("Mortgage is rejected.");
            
            response.put("response", "Mortgage is rejected.");

        }
       
        return response;
    }

    @GetMapping("/list")
    public ArrayList<Mortgage> listMortgages() {

        log.info("List request received.");

        ArrayList<Mortgage> response = new ArrayList<>();

        for (Mortgage mortgage : approvedMortgages) {

            response.add(mortgage);

        }

        return response;
    }

    @GetMapping("/average")
    public HashMap<String, Double> averageAmount() {

        log.info("Average request received.");

        HashMap<String, Double> response = new HashMap<String, Double>();
        Double sum = 0.0;

        for (Mortgage mortgage : approvedMortgages) {
            sum = sum.doubleValue() + mortgage.getAmount().doubleValue();
        }
        
        if (approvedMortgages.size() > 0) {
            sum = sum.doubleValue() / approvedMortgages.size();
        }
        
        response.put("average", sum);
        return response;
    }
    
}