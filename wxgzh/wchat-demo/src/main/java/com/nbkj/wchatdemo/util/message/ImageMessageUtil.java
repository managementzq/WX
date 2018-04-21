package com.nbkj.wchatdemo.util.message;

import com.nbkj.wchatdemo.common.WXMsConstant;
import com.nbkj.wchatdemo.model.message.Image;
import com.nbkj.wchatdemo.model.message.ImageMessage;
import com.thoughtworks.xstream.XStream;

import java.util.Date;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
public class ImageMessageUtil implements BaseMessageUtil<ImageMessage> {
    @Override
    public String messageToxml(ImageMessage imageMessage) {

        XStream xstream = new XStream();
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }


    @Override
    public String initMessage(String fromUserName, String toUserName) {
        String message = null;
        Image image = new Image();
        image.setMediaId("QYBMPmRMdmt4msaVAuthae0qJ6QGKyP5ABK_bG-8KwS4RT-G08hv2LrS-ev5WwHb");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(fromUserName);
        imageMessage.setToUserName(toUserName);
        imageMessage.setMsgType(WXMsConstant.REQ_MESSAGE_TYPE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        message = messageToxml(imageMessage);
        return message;
    }
}
