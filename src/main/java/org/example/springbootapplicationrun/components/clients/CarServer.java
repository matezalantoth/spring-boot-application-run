package org.example.springbootapplicationrun.components.clients;

import org.example.springbootapplicationrun.models.Car;
import org.example.springbootapplicationrun.models.Token;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Component
public class CarServer {

    private Token token;

    //account email: cars-client@thesoftwareadvisor.co.uk
//account password: carsMATE2023!
    public void sendCarsToServer(List<Car> carsInfo) {

        JSONArray carsFinal = new JSONArray();
        carsInfo.forEach(newCar -> {
            JSONObject carFinal = newCar.getJSONInfo();
            carsFinal.put(carFinal);
        });

        WebClient webClient = WebClient.builder()
                .baseUrl("https://cars-api.thesoftwareadvisor.co.uk")
                .build();


        Mono<String> response = webClient.post()
                .uri("/api/cars")
                .headers(httpHeaders -> {
                    httpHeaders.set("Authorization", "Bearer " + getToken().getAuthenticationToken());
                    httpHeaders.set("Content-Type", "application/json");
                })
                .body(BodyInserters.fromValue(carsFinal.toString()))
                .retrieve()
                .bodyToMono(String.class)
                        .timeout(Duration.ofMinutes(1));

        response.block();
        response.subscribe(e -> {
            System.out.println("------------------------------RESPONSE----------------------------------");
            System.out.println(e);
            System.out.println("------------------------------END----------------------------------");

        });

    }

    public void updateCar(Car car){

        JSONObject carInfo = car.getJSONInfo();

        WebClient webClient = WebClient.builder()
                .baseUrl("https://cars-api.thesoftwareadvisor.co.uk")
                .build();


        Mono<String> response = webClient.post()
                .uri("/api/cars/" + car.getMarketplaceId())
                .headers(httpHeaders -> {
                    httpHeaders.set("Authorization", "Bearer " + getToken().getAuthenticationToken());
                    httpHeaders.set("Content-Type", "application/json");
                })
                .body(BodyInserters.fromValue(carInfo.toString()))
                .retrieve()
                .bodyToMono(String.class);
        response.block();
        response.subscribe(e -> {
            System.out.println("------------------------------RESPONSE----------------------------------");
            System.out.println(e);
            System.out.println("------------------------------END----------------------------------");

        });

    }

    protected Token getToken() {
        if (token != null) {
            return token;
        }
        fetchToken();
        return token;
    }

    protected void fetchToken() {
        token = new Token();
        token.setAuthenticationToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNmYyZmQ4YWU5NDA3YjkzY2EwNGRkZGFkZDgxM2VjNjM5NjJhMjYyNTY3OWY0MGZkNmJmNGI4N2FmMDRhNDZiNTdiN2RiZTc4YzgyM2QzMWEiLCJpYXQiOjE2ODUxMDYzMTYuNTQwMTkyLCJuYmYiOjE2ODUxMDYzMTYuNTQwMTk1LCJleHAiOjE3MDEwMDM5MTYuNTI4ODYsInN1YiI6IjkiLCJzY29wZXMiOltdfQ.hafhPTqJ69T9j9kShn_79LAYneJxzJiZrEz-j3-AkypsSYae86BYsTxWvQT9W6EDYSXokh2PLIO-pY9QBVQVg-ZV7uNUcCqcflAFkGEoezjqhCf_df8El6UN2O_HNQBm402PdyRVUccEROxlET8YEFI2DyD0Aa9rqrisQZi56LlU2uBfLt6lSjHHH9ZHEe6HlfZHcU0sUIb2EL6eIxfuqjYM-sRs1rK_fLNb12Qkqxcgk45MEzUc8klB5A5WnEUILPmvHKc5e_GA_ZCfzI9fBGbdbA4pPNEcUTmz1WnA27mOoJA88ZAf-nZ6a9iMYvxhd2W4Tm2mgj-MV24iD7zo5XlaZ4v-Dm-FB7Bgr7VjsMcDTl8TyBlqPjQmvBxvwcietVdbHN1nEfP48YRst2ORVQrY5wTI6MbYmh4vfb9xmBMHVMf1Q9Bs3Rd6nuNIZ-wUCQup7zGDhX5xZXYt99hk0TCtxZFj7Y8k1PH7pdYKDrACaECPvWygY7TwKGke4QLM2iIE_UV9kY2n4C6K5dAcMZ6CWmZJoZGGU-trX603GY5Vh-Y23jl7H66PjkN30kiezCVCiCApK_Y8NvW6K4IDHKl8icX4jpvrIGXrfaQx9Hm-ykOe_l8m1IRNXUeRyNJM6Rcoc_Ms5SzKy45N0ThGUu83xtq06j6BXPXlcMLekIM");

    }

}
