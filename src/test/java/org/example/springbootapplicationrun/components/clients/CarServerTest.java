package org.example.springbootapplicationrun.components.clients;

import org.example.springbootapplicationrun.models.Car;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class CarServerTest {

    @Autowired
    private CarServer carServer;

    @Test
    void contextLoads() {
        JSONArray carsJson = new JSONArray();
        Car car = new Car();

        car.setTitle("Image Title");
        car.setImage("Image Image");
        car.setLink("Image Link");
        car.setPrice("£££");
        car.setDistance("Image Distance");

        JSONObject jsonObject = car.getJSONInfo();
        carsJson.put(jsonObject);


    }

    @Test
    void regexTest(){
        Car car = new Car();
        car.setLink("https://www.facebook.com/marketplace/item/822157459240222/?ref=category_feed&referral_code=undefined&referral_story_type=listing&tracking=%7B%22qid%22%3A%22-3990715795629091513%22%2C%22mf_story_key%22%3A%229517738204966771%22%2C%22commerce_rank_obj%22%3A%22%7B%5C%22target_id%5C%22%3A9517738204966771%2C%5C%22target_type%5C%22%3A0%2C%5C%22primary_position%5C%22%3A4%2C%5C%22ranking_signature%5C%22%3A3643287424232062976%2C%5C%22commerce_channel%5C%22%3A504%2C%5C%22value%5C%22%3A1.1379693960123e-5%2C%5C%22candidate_retrieval_source_map%5C%22%3A%7B%5C%229517738204966771%5C%22%3A111%7D%7D%22%2C%22ftmd_400706%22%3A%22111112l%22%7D");

        }
    }

}
