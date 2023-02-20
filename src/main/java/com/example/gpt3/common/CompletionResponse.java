package com.example.gpt3.common;

import java.util.List;

public class CompletionResponse {

    private List<Choice> choices;
    private String created;
    private String id;
    private String model;
    private String object;
    private String prompt;
    private String search_model;
    private int selected;
    private float temperature;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getSearch_model() {
        return search_model;
    }

    public void setSearch_model(String search_model) {
        this.search_model = search_model;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public static class Choice {

        private String text;
        private float logprobs;
        private String finish_reason;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public float getLogprobs() {
            return logprobs;
        }

        public void setLogprobs(float logprobs) {
            this.logprobs = logprobs;
        }

        public String getFinish_reason() {
            return finish_reason;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }
    }
}

