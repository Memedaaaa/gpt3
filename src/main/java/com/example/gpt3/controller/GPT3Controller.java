package com.example.gpt3.controller;

import com.example.gpt3.common.CompletionResponse;
import com.google.gson.Gson;
import com.xiaoleilu.hutool.http.HttpRequest;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import okhttp3.*;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private static final String API_BASE_URL = "https://api.openai.com";
    private static final String API_COMPLETIONS_ENDPOINT = "/v1/completions";
    @RequestMapping("/send")
    public String send(@RequestParam String request, HttpServletRequest httpServletRequest
            , @RequestHeader("User-Agent") String userAgent){
        logger.info("请求报文：{}",request);
        logger.info("客户端IP：{}",httpServletRequest.getRemoteAddr());
        logger.info("客户端端口：{}",httpServletRequest.getRemotePort());
        logger.info("客户端主机：{}",httpServletRequest.getRemoteHost());
        logger.info("客户端浏览器信息：{}",httpServletRequest.getHeader("User-Agent"));
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
                .header("Authorization", "Bearer " +Authorization)
                .body(json)
                .execute().body());
        logger.info("响应报文：{}",responseJson.toString());
        String response = String.valueOf(new JSONObject(new JSONArray(responseJson.get("choices")).get(0)).get("text"));
        return response;
    }
    @PostMapping("/gpt3")
    public CompletionResponse generateText(@org.springframework.web.bind.annotation.RequestBody String prompt, HttpServletRequest httpServletRequest) throws IOException {
        logger.info("客户端IP：{}", httpServletRequest.getRemoteAddr());
        logger.info("客户端端口：{}", httpServletRequest.getRemotePort());
        logger.info("客户端主机：{}", httpServletRequest.getRemoteHost());
        logger.info("客户端浏览器信息：{}", httpServletRequest.getHeader("User-Agent"));
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("model", model);
        requestData.put("prompt", prompt);
        requestData.put("temperature", temperature);
        requestData.put("max_tokens", max_tokens);
        requestData.put("top_p", top_p);
        requestData.put("frequency_penalty", frequency_penalty);
        requestData.put("presence_penalty", presence_penalty);
        Gson gson = new Gson();
        logger.info("请求报文：{}", gson.toJson(requestData));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(requestData));

        // 构造 API 请求
        Request request = new Request.Builder()
                .url(API_BASE_URL + API_COMPLETIONS_ENDPOINT)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Authorization)
                .post(requestBody)
                .build();

        OkHttpClient httpClient = new OkHttpClient();
        String responseData = null;
        // 发送 API 请求
        Response response = httpClient.newCall(request).execute();
        try (ResponseBody responseBody = response.body()) {
            responseData = responseBody.string();
            logger.info("响应报文：{}", responseData);
        } catch (IOException e) {
            // 处理异常
            logger.error(e.getMessage());
        }
        String responseBody = responseData;

        // 解析 API 响应
        CompletionResponse completionResponse = gson.fromJson(responseBody, CompletionResponse.class);
//        String generatedText = completionResponse.getChoices().get(0).getText();
        return completionResponse;
    }

}
