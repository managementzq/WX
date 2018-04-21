package com.nbkj.wchatdemo.controller;

import com.nbkj.wchatdemo.common.WXMsConstant;
import com.nbkj.wchatdemo.model.AccessToken;
import com.nbkj.wchatdemo.service.HttpClientService;
import com.nbkj.wchatdemo.util.WXUtil;
import lombok.extern.java.Log;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
@RestController
@RequestMapping("/api")
@Log
public class ApiController {


    @Autowired
    private HttpClientService httpClientService;


    //getaccTocken
    @GetMapping("/getAccToken")
    public AccessToken getAccessToken() throws IOException {
        AccessToken accessToken = new AccessToken();
        String url = WXMsConstant.ACCESS_TOKEN_API.replace("APPID", WXMsConstant.APPID);
        url = url.replace("APPSECRET", WXMsConstant.APPSECRET);
        JSONObject jsonObject = httpClientService.doGetStr(url);
        if (!ObjectUtils.isEmpty(jsonObject)) {
            try {
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (Exception e) {
                String errcode = jsonObject.getString("errcode");
                String reemsg = jsonObject.getString("errmsg");
                if ("40001".equals(errcode)) {
                    log.info("AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性");
                } else if ("40002".equals(errcode)) {
                    log.info("请确保grant_type字段值为client_credential");
                } else if ("40164".equals(errcode)) {
                    log.info("调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置");
                } else if ("-1".equals(errcode)) {
                    log.info("系统繁忙，此时请开发者稍候再试");
                } else {
                    log.info("请求成功");
                }
            }
        }
        return accessToken;
    }


    //upload file
    @GetMapping("/upload")
    public ResponseEntity<String> upLoadTemporaryFile() throws IOException {
        AccessToken accessToken = this.getAccessToken();
        log.info(accessToken.toString());
        String filePath = "D:\\springboot\\wchat-demo\\src\\main\\resources\\static\\1106024.jpg";
        String videoPath = "D:\\springboot\\wchat-demo\\src\\main\\resources\\static\\1234.mp4";
        String url = WXMsConstant.UPLOAD_FILE_API.replace("ACCESS_TOKEN", accessToken.getToken()).replace("TYPE", "video");
        String media_id = httpClientService.upLoadFile(videoPath, "video", url);
        log.info(media_id);
        return new ResponseEntity<>(media_id, HttpStatus.OK);
    }

    @GetMapping("/createMenu")
    public void createMenu() throws IOException {

        String menu = JSONObject.fromObject(WXUtil.initMenu()).toString();
        AccessToken accessToken = this.getAccessToken();
        int result = 0;
        String url = WXMsConstant.CREATE_MENU_API.replace("ACCESS_TOKEN", accessToken.getToken());
        JSONObject jsonObject = httpClientService.doPostStr(url, menu);
        if (jsonObject != null) {
            result = jsonObject.getInt("errcode");
        }
        log.info(jsonObject.toString());
    }

}
