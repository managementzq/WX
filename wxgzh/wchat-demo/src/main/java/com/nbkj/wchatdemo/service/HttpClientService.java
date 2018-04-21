package com.nbkj.wchatdemo.service;

import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
public interface HttpClientService {
    JSONObject doGetStr(String url) throws IOException;

    JSONObject doPostStr(String url, String outStr) throws IOException;

    String upLoadFile(String filePath, String type, String url) throws IOException;
}
