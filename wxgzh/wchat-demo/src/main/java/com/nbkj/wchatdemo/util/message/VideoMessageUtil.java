package com.nbkj.wchatdemo.util.message;

import com.nbkj.wchatdemo.common.WXMsConstant;
import com.nbkj.wchatdemo.model.message.Video;
import com.nbkj.wchatdemo.model.message.VideoMessage;
import com.thoughtworks.xstream.XStream;

import java.util.Date;

/**
 * @author :hsj
 * @description
 * @date :2018/4/21
 */
public class VideoMessageUtil implements BaseMessageUtil<VideoMessage> {
    @Override
    public String messageToxml(VideoMessage videoMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", videoMessage.getClass());
        return xstream.toXML(videoMessage);
    }

    @Override
    public String initMessage(String FromUserName, String ToUserName) {
        Video video = new Video();
        video.setDescription("这是一个视频！");
        video.setTitle("视屏");
        video.setMediaId("qgsLtsVsdmqBPiTd8CYXTA32HONeb7jw_zduhrxk2eA80CHqahr_ySRk-5DNXINu");
        VideoMessage videoMessage = new VideoMessage();
        videoMessage.setVideo(video);
        videoMessage.setFromUserName(FromUserName);
        videoMessage.setCreateTime(new Date().getTime());
        videoMessage.setToUserName(ToUserName);
        videoMessage.setMsgType(WXMsConstant.REQ_MESSAGE_TYPE_VIDEO);
        return messageToxml(videoMessage);
    }
}
