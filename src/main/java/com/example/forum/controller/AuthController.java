package com.example.forum.controller;

import com.example.forum.entity.AccessToken;
import com.example.forum.entity.GithubUser;
import com.example.forum.provider.GithubProvier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
    @Autowired
    GithubProvier githubProvier;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.sceret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @GetMapping("/callback") 
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setRedirect_uri(redirectUrl);
        accessToken.setCode(code);
        accessToken.setState(state);
        String accessTokenStr = githubProvier.getAccessToken(accessToken);
        GithubUser githubUser = githubProvier.getUser(accessTokenStr);
        if (githubUser!=null){
            //登录成功
            request.getSession().setAttribute("githubUser",githubUser);
            //redirect:跳转到对应的路径（不会解析视图）
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
