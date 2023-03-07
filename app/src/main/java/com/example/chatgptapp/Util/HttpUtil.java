package com.example.chatgptapp.Util;

import com.example.chatgptapp.Model.CompletionRequest;
import com.squareup.moshi.Moshi;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    /**
     * 发送请求
     */
    public static void sendOkHttpRequest(String prompt,okhttp3.Callback callback){
        Moshi moshi = new Moshi.Builder().build();
        OkHttpClient client = new OkHttpClient();

        final String API_KEY = "sk...";
        final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

        CompletionRequest completionRequest = new CompletionRequest();
        completionRequest.setPrompt(prompt);

        String reqJson = moshi.adapter(CompletionRequest.class).toJson(completionRequest);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer " + API_KEY)
                .post(RequestBody.create(MEDIA_TYPE_JSON,reqJson))
                .build();

        client.newCall(request).enqueue(callback);
    }
}
