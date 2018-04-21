package com.nbkj.wchatdemo.util.message;


import com.nbkj.wchatdemo.model.message.MessageText;
import com.thoughtworks.xstream.XStream;

import java.util.Date;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
public class TextMessageUtil implements BaseMessageUtil<MessageText> {
    /**
     * 将发送消息封装成对应的xml格式
     */
    public String messageToxml(MessageText message) {
        XStream xstream = new XStream();
        xstream.alias("xml", message.getClass());
        return xstream.toXML(message);
    }

    /**
     * 封装发送消息对象,封装时，需要将调换发送者和接收者的关系
     *
     * @param FromUserName
     * @param ToUserName
     */
    public String initMessage(String ToUserName , String FromUserName) {
        MessageText text = new MessageText();
        text.setToUserName(ToUserName);
        text.setFromUserName(FromUserName);
        text.setContent("你好我是胡少君");
        text.setCreateTime(new Date().getTime());
        text.setMsgType("text");
        return messageToxml(text);
    }
}
