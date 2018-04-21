package com.nbkj.wchatdemo.util.message;

import com.nbkj.wchatdemo.common.WXMsConstant;
import com.nbkj.wchatdemo.model.message.Music;
import com.nbkj.wchatdemo.model.message.MusicMessage;
import com.thoughtworks.xstream.XStream;

import java.util.Date;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
public class MusicMessageUtil implements BaseMessageUtil<MusicMessage> {
    @Override
    public String messageToxml(MusicMessage musicMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    @Override
    public String initMessage(String fromUserName, String toUserName) {
        String message = null;
        Music music = new Music();
        music.setThumbMediaId("QYBMPmRMdmt4msaVAuthae0qJ6QGKyP5ABK_bG-8KwS4RT-G08hv2LrS-ev5WwHb");
        music.setTitle("夜空中最亮的星");
        music.setDescription("夜空中最亮的星");
        music.setMusicUrl("http://up.mcyt.net/?down/46425.mp3");
        music.setHQMusicUrl("http://up.mcyt.net/?down/46425.mp3");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(fromUserName);
        musicMessage.setToUserName(toUserName);
        musicMessage.setMsgType(WXMsConstant.RESP_MESSAGE_TYPE_MUSIC);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setMusic(music);
        message = messageToxml(musicMessage);
        return message;
    }
}
