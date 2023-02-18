package com.example.gpt3.controller;

import com.xiaoleilu.hutool.http.HttpRequest;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/openai")
@RestController
public class GPT3Controller {
    private static final Logger logger = LoggerFactory.getLogger(GPT3Controller.class);
    @Value("${gpt.Authorization}")
    private String Authorization;
    @Value("${gpt.model}")
    private String model;
    @Value("${gpt.temperature}")
    private Integer temperature;
    @Value("${gpt.max_tokens}")
    private Integer max_tokens;
    @Value("${gpt.top_p}")
    private Integer top_p;
    @Value("${gpt.frequency_penalty}")
    private Integer frequency_penalty;
    @Value("${gpt.presence_penalty}")
    private Integer presence_penalty;
    @RequestMapping("/send")
    public String send(@RequestParam String request){
        logger.info("请求报文：{}",request);
        JSONObject json = new JSONObject();
        json.put("model",model);
        json.put("prompt",request);
        json.put("temperature",temperature);
        json.put("max_tokens",max_tokens);
        json.put("top_p",top_p);
        json.put("frequency_penalty",frequency_penalty);
        json.put("presence_penalty",presence_penalty);
        JSONObject responseJson = new JSONObject(HttpRequest.post("https://api.openai.com/v1/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", Authorization)
                .body(json)
                .execute().body());
        logger.info("响应报文：{}",responseJson.toString());
        String response = String.valueOf(new JSONObject(new JSONArray(responseJson.get("choices")).get(0)).get("text"));
        return response;
    }

}
