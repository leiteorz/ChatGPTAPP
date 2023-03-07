package com.example.chatgptapp.Model;

//返回的json内容中的choices
public class Choices {
    String text;    //回复内容
    int index;
    String logprobs;
    String finish_reason;

    public String getText() {
        return text;
    }
}
