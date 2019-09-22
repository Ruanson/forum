package com.example.forum.provider;

import com.alibaba.fastjson.JSON;
import com.example.forum.entity.AccessToken;
import com.example.forum.entity.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvier {
    public static final MediaType mediaType =MediaType.parse("application/json; charset=utf-8");
    public String getAccessToken(AccessToken accessToken){
        OkHttpClient client = new OkHttpClient();
        String json=JSON.toJSONString(accessToken);
        System.out.println(json);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] strings = string.split("&");
            String tokenStr=strings[0].split("=")[1];
            return tokenStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        System.out.println("令牌"+accessToken);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(""+string);
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return githubUser;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
