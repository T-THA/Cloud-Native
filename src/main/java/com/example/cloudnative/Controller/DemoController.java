package com.example.cloudnative.Controller;

import com.google.common.util.concurrent.RateLimiter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
public class DemoController {

    private final RateLimiter rateLimiter = RateLimiter.create(100.0);

    @GetMapping("/api/text")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getText(){
        if(!rateLimiter.tryAcquire(1))
            throw new HttpStatusCodeException(HttpStatus.TOO_MANY_REQUESTS) {
            };
        return "{\"name\":\"云原生斗地主\",\"number\":\"nju17\"}";
    }

    @GetMapping( "/api/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getJson() {
        if(!rateLimiter.tryAcquire(1)) {
            throw new HttpStatusCodeException(HttpStatus.TOO_MANY_REQUESTS) {};
        }
        JSONObject json = new JSONObject();
        try {
            json.put("name", "云原生斗地主");
            json.put("number", "nju17");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String ret = json.toString();
        return ret;
    }

}
