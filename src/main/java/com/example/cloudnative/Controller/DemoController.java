package com.example.cloudnative.Controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/api/text")
    @ResponseStatus(HttpStatus.OK)
    public String getText() {
        return "Hello World!";
    }

    @GetMapping( "/api/json")
    @ResponseStatus(HttpStatus.OK)
    public String getJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", "TTHA");
            json.put("age", 20);
            json.put("msg", "hello");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String ret = json.toString();
        return ret;
    }
}
