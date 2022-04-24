## To Start Endpoint [EndpointController](src/main/java/com/example/demo/controller/EndpointController.java)
`mvn -e clean package && java -jar target/demo-0.0.1-SNAPSHOT.jar`

## Apply for mortgage
Send POST request to url: http://localhost:8080/api/apply with body(example): 
```
{
    "amount" : 300.4,
    "income" : 2000.4,
    "term" : 1,
    "name" : "Okan",
    "surname" : "Mercan"
}
```

## List approved Mortgages
Send GET request to url: http://localhost:8080/api/list. Response be like:
```
[
    {
        "amount": 300.0,
        "income": 2000.0,
        "term": 1,
        "name": "Okan234",
        "surname": "Mercan"
    },
    {
        "amount": 300.0,
        "income": 2000.0,
        "term": 1,
        "name": "Okan",
        "surname": "Mercan"
    }
]
```

## Get average amount
Send GET request to url: http://localhost:8080/api/average. Response be like:
```
{
    "average:": 300.0
}
```

## Unit Tests [EndpointTests](src/test/java/com/example/demo/EndpointTests.java)
Creates and sends 1000 mortgage requests and checks whether implementation is correct.
