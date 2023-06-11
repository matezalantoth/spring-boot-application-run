//package org.example.springbootapplicationrun.components;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomSpringEventPublisher {
//    @Autowired
//    private ApplicationEventPublisher applicationEventPublisher;
//
//    public void publishCustomEvent(JSONObject data) {
//        System.out.println("Publishing custom event. ");
//        CustomSpringEvent customSpringEvent = new CustomSpringEvent(data);
//        applicationEventPublisher.publishEvent(customSpringEvent);
//    }
//}