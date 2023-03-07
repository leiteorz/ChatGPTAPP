package com.example.chatgptapp.Util;

import com.example.chatgptapp.Model.CompletionResponse;
import com.google.gson.Gson;

import okhttp3.Response;

public class Utility {
    /**
     * 解析和处理返回的数据
     */
    public static CompletionResponse handleResponse(String response){
        try{
            Gson gson = new Gson();
            CompletionResponse res = gson.fromJson(response,CompletionResponse.class);
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
