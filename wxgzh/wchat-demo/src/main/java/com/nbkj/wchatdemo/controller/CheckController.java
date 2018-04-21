package com.nbkj.wchatdemo.controller;

import com.nbkj.wchatdemo.util.WXCheckUtil;
import com.nbkj.wchatdemo.util.message.*;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
@RestController
@RequestMapping("/wx")
@Log
public class CheckController {
    //增加日志

    //验证是否来自微信服务器的消息
    @GetMapping("/check")
    @ResponseBody
    public String checkSignature(@RequestParam(name = "signature", required = false) String signature,
                                 @RequestParam(name = "nonce", required = false) String nonce,
                                 @RequestParam(name = "timestamp", required = false) String timestamp,
                                 @RequestParam(name = "echostr", required = false) String echostr) {
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WXCheckUtil.checkSignature(signature, timestamp, nonce)) {
            log.info("接入成功");
            return echostr;
        }
        log.info("接入失败");
        return "";
    }


    //微信公众消息的接收与回复
    @PostMapping("/check")
    public void demo(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        //将微信请求xml转为map格式，获取所需的参数
        Map<String, String> map = MessageUtil.xmlToMap(request);
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        String MsgType = map.get("MsgType");
        String Content = map.get("Content");
        log.info(Content);

        String message = null;
        //处理文本类型，实现输入1，回复相应的封装的内容
        if ("text".equals(MsgType)) {
            if ("1".equals(Content)) {
                TextMessageUtil textMessage = new TextMessageUtil();
                message = textMessage.initMessage(FromUserName, ToUserName);
            } else if ("2".equals(Content)) {
                NewsMessageUtil newsMessageUtil = new NewsMessageUtil();
                message = newsMessageUtil.initMessage(FromUserName, ToUserName);
            } else if ("3".equals(Content)) {
                ImageMessageUtil imageMessageUtil = new ImageMessageUtil();
                message = imageMessageUtil.initMessage(FromUserName, ToUserName);
            } else if ("4".equals(Content)) {
                MusicMessageUtil musicMessageUtil = new MusicMessageUtil();
                message = musicMessageUtil.initMessage(FromUserName, ToUserName);
            }else if("5".equals(Content)){
                VideoMessageUtil videoMessageUtil=new VideoMessageUtil();
                message = videoMessageUtil.initMessage(FromUserName, ToUserName);
            }
        }

        try {
            out = response.getWriter();
            response.getWriter().write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }
}


