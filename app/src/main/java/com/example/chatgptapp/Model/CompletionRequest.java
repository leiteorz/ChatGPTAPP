package com.example.chatgptapp.Model;

public class CompletionRequest {
    String model = "text-davinci-003";  //可选参数,语言模型
    String prompt;  //请求
    Integer max_tokens = 200;    //最大分词数
    float temperature = 0.5f;   //该值越大每次返回的结果越随机
    Integer top_p = 1;  //与temperature类似
    Integer n = 1;  //表示对prompt生成多少条结果
    Boolean stream = false; //是否回流部分结果
    Boolean logprobs;
    String stop;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
