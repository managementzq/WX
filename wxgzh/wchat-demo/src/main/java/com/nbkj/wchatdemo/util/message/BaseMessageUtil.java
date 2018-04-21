package com.nbkj.wchatdemo.util.message;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
public interface BaseMessageUtil<T> {
    /**
     * 将回复的信息对象转xml格式给微信
     */
    public String messageToxml(T t);


    /**
     * 回复的信息封装
     */
    public abstract String initMessage(String FromUserName, String ToUserName);
}
