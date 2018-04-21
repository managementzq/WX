package com.nbkj.wchatdemo.util.message;

import com.nbkj.wchatdemo.common.WXMsConstant;
import com.nbkj.wchatdemo.model.message.Voice;
import com.nbkj.wchatdemo.model.message.VoiceMessage;
import com.thoughtworks.xstream.XStream;

import java.util.Date;

/**
 * @author :hsj
 * @description
 * @date :2018/4/21
 */
public class VoiceMessageUtil implements BaseMessageUtil<VoiceMessage> {
    @Override
    public String messageToxml(VoiceMessage voiceMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", voiceMessage.getClass());
        return xStream.toXML(voiceMessage);
    }

    @Override
    public String initMessage(String FromUserName, String ToUserName) {
        String message = null;
        Voice voice = new Voice();
        voice.setMediaId("QYBMPmRMdmt4msaVAuthae0qJ6QGKyP5ABK_bG-8KwS4RT-G08hv2LrS-ev5WwHb");
        VoiceMessage voiceMessage = new VoiceMessage();
        voiceMessage.setFromUserName(FromUserName);
        voiceMessage.setToUserName(ToUserName);
        voiceMessage.setMsgType(WXMsConstant.REQ_MESSAGE_TYPE_VOICE);
        voiceMessage.setCreateTime(new Date().getTime());
        voiceMessage.setVoice(voice);
        message = messageToxml(voiceMessage);
        return message;
    }
}
