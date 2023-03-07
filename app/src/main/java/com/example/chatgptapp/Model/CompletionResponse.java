package com.example.chatgptapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//解析返回的json内容
public class CompletionResponse {
    String id;
    String object;
    String created;
    String model;
    @SerializedName("choices")
    public List<Choices> choicesList;
    Usage usage;
}
