package com.hui.tally;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WangluoqingqiuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wangluoqingqiu);

        //启动子线程，发送网络请求
        new Thread(){
            @Override
            public void run() {
                networkRequest();
            }
        }.start();

    }

    //网络请求的代码
    private void networkRequest(){
        HttpURLConnection connection=null;
        try {
            URL url = new URL("https://www.baidu.com");
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);//建立连接的超时时间； 3秒 连接主机的超时时间（单位：毫秒）
            connection.setReadTimeout(3000);// 传递数据的超时时间  3秒 从主机读取数据的超时时间（单位：毫秒）

            //设置请求方式 GET / POST 一定要大写
            connection.setRequestMethod("POST");
            //开启输入输出
            connection.setDoInput(true);//post请求需要setDoOutput(true)，这个默认是false的。
            connection.setDoOutput(false);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code" + responseCode);
            }
            //采用字符串的形式接收返回的json结果
            String result = getStringByStream(connection.getInputStream());
            if (result == null) {
                Looper.prepare();
                Toast.makeText(WangluoqingqiuActivity.this, "网络请求失败！", Toast.LENGTH_SHORT).show();
                Looper.loop();

            }else{
                Looper.prepare();
                Toast.makeText(WangluoqingqiuActivity.this, "网络请求成功！"+result, Toast.LENGTH_LONG).show();
                Looper.loop();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //处理json（本文实例采取的是POST请求的方式，因而采用字符串的形式接收返回的json结果）
    private String getStringByStream(InputStream inputStream){
        Reader reader;
        try {
            reader=new InputStreamReader(inputStream,"UTF-8");
            char[] rawBuffer=new char[512];
            StringBuffer buffer=new StringBuffer();
            int length;
            while ((length=reader.read(rawBuffer))!=-1){
                buffer.append(rawBuffer,0,length);
            }
            return buffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
