package com.example.login.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.login.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main3Activity extends AppCompatActivity {
    private TextView textView,textView2;
    private String html,title=null,context=null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:textView.setText(title);
                textView2.setText(context);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textView=findViewById(R.id.text5);
        textView2=findViewById(R.id.xiaoshuo);
        new Thread(){
            @Override
            public void run() {
                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("http://www.qushuba.com/shu502/4885842.html")
                        .build();
                try {
                    Response response=okHttpClient.newCall(request).execute();
                    html=response.body().string();
                    System.out.println(html);
                    Document document= Jsoup.parse(html);
                    Element t=document.getElementsByTag("title").first();
                    title=t.text();
                    Element c=document.select("div#content").first();
                    context=c.text();
                    Message message=handler.obtainMessage();
                    message.what=1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
