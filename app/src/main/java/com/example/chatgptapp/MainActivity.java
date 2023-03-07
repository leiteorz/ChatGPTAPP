package com.example.chatgptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.chatgptapp.Controller.MsgAdapter;
import com.example.chatgptapp.Model.CompletionResponse;
import com.example.chatgptapp.Model.Msg;
import com.example.chatgptapp.Util.HttpUtil;
import com.example.chatgptapp.Util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "leiteorz";

    private List<Msg> msgList = new ArrayList<>();
    private MsgAdapter adapter;

    //ui
    private EditText inputText; //输入框
    private Button sendBtn; //发送按钮
    private RecyclerView msgRecyclerView;

    public static final int UPDATA_TEXT = 1;    //发送消息,更新UI

    String text;    //发送的消息内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 初始化ui
         */
        inputText = (EditText) findViewById(R.id.input_text);
        sendBtn = (Button) findViewById(R.id.send_btn);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        /**
         * 设置recyclerView
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        /**
         * 点击发送信息按钮
         */
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框的内容
                String content = inputText.getText().toString();
                Msg msg = new Msg(content,Msg.TYPE_SENT);
                Log.d(TAG,msg.getContent());
                msgList.add(msg);
                //刷新显示
                adapter.notifyItemInserted(msgList.size() - 1);
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
                inputText.setText("");
                //发送网络请求
                try {
                    completion(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 处理ui改变
     */
    private Handler handler = new Handler(){
       public void handleMessage(Message msg){
          switch (msg.what){
              case UPDATA_TEXT:
                  //去除前面的回车
                  text = text.substring(2);

                  Msg reply = new Msg(text,Msg.TYPE_RECEIVED);
                  msgList.add(reply);
                  //刷新显示
                  adapter.notifyItemInserted(msgList.size() - 1);
                  msgRecyclerView.scrollToPosition(msgList.size() - 1);
                  break;
              default:
                  break;
          }
       }
    };

    /**
     * 发送网络请求
     */
    public void completion(String prompt) throws IOException{
        HttpUtil.sendOkHttpRequest(prompt, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //将返回的json数据解析成CompletionResponse类
                final String responseText = response.body().string();
                CompletionResponse res = Utility.handleResponse(responseText);
                Log.d(TAG,responseText);
                Log.d(TAG,res.choicesList.get(0).getText());

                text = res.choicesList.get(0).getText();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATA_TEXT;
                        handler.sendMessage(message);
                    }
                });
            }
        });
    }
}