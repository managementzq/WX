package com.nbkj.wchatdemo.model.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
@Data
@ToString
@NoArgsConstructor
public class MessageText extends BaseMessage {
    private String Content;//文本消息内容

    private String MsgId;//消息id，64位整型
    
    public MessageText(String toUserName, String fromUserName,
                       long createTime, String msgType, String content, String msgId) {
        super();
        ToUserName = toUserName;
        FromUserName = fromUserName;
        CreateTime = createTime;
        MsgType = msgType;
        Content = content;
        MsgId = msgId;
    }
}
