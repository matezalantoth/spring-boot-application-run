package org.example.springbootapplicationrun.components.clients;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CarServer {

    public void sendCarsToServer(JSONArray carsJson){
        WebClient.create("cars.thesoftwareadvisor.co.uk/cars")
                .post()
                .body(Mono.just(carsJson), JSONArray.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

    }

}
