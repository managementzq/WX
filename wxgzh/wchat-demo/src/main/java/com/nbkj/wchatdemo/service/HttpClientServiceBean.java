package com.nbkj.wchatdemo.service;

import net.sf.json.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
@Service
public class HttpClientServiceBean implements HttpClientService {

    @Autowired
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    private RequestConfig requestConfig;


    //get 请求
    @Override
    public JSONObject doGetStr(String url) throws IOException {
        // httpClient
        CloseableHttpClient httpClient = this.buildHttpClient();
        // response
        CloseableHttpResponse response = null;
        // 创建get请求
        HttpGet getMethod = new HttpGet(url);
        // 结果字符串builder
        StringBuilder resultBuilder = new StringBuilder();
        // HTTP响应输入流
        InputStream inputStream = null;
        try {
            // 执行get请求
            response = httpClient.execute(getMethod);
            // 响应实体
            HttpEntity responseEntity = response.getEntity();
            if (!ObjectUtils.isEmpty(responseEntity)) {
                inputStream = responseEntity.getContent();
                // 转换为字节输入流
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Consts.UTF_8));
                String responseBody;
                // 将响结果读入结果字符串
                while ((responseBody = reader.readLine()) != null) {
                    resultBuilder.append(responseBody);
                }
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (!ObjectUtils.isEmpty(response)) {
                response.close();
            }
            // 关闭输入流，释放资源
            if (!ObjectUtils.isEmpty(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return JSONObject.fromObject(resultBuilder.toString());
    }


    //post请求
    @Override
    public JSONObject doPostStr(String url,String outStr) throws IOException {
        // httpClient
        CloseableHttpClient httpClient = this.buildHttpClient();
        // response
        CloseableHttpResponse response = null;
        // 创建post请求
        HttpPost postMethod = new HttpPost(url);
        postMethod.setEntity(new StringEntity(outStr,"UTF-8"));
        // 结果字符串builder
        StringBuilder resultBuilder = new StringBuilder();
        // HTTP响应输入流
        InputStream inputStream = null;
        try {
            // 执行post请求
            response = httpClient.execute(postMethod);
            // 响应实体
            HttpEntity responseEntity = response.getEntity();
            if (!ObjectUtils.isEmpty(responseEntity)) {
                inputStream = responseEntity.getContent();
                // 转换为字节输入流
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Consts.UTF_8));
                String responseBody;
                // 将响结果读入结果字符串
                while ((responseBody = reader.readLine()) != null) {
                    resultBuilder.append(responseBody);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } finally {
            if (!ObjectUtils.isEmpty(response)) {
                response.close();
            }
            // 关闭输入流，释放资源
            if (!ObjectUtils.isEmpty(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return JSONObject.fromObject(resultBuilder.toString());
    }

    @Override
    public String upLoadFile(String filePath, String type, String url) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在或者不是文件");
        }

        //create url
        URL urlObj = new URL(url);

        //create connection
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlObj.openConnection();

        //request method
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);

        //set request header info
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Charset", "UTF-8");

        //set  border
        String BOUNDARY = "------------" + System.currentTimeMillis();
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if (!"image".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }


    private CloseableHttpClient buildHttpClient() {
        // 创建httpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 设置连接管理器
        httpClientBuilder.setConnectionManager(this.httpClientConnectionManager);
        // 设置请求参数
        httpClientBuilder.setDefaultRequestConfig(this.requestConfig);
        // 创建CloseableHttpClient
        return httpClientBuilder.build();
    }
}
