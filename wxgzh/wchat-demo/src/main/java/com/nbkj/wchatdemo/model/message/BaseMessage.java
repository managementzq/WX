package com.nbkj.wchatdemo.model.message;

import lombok.Data;
import lombok.ToString;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
@Data
@ToString
public class BaseMessage {
   protected String ToUserName;
   protected String FromUserName;
   protected long CreateTime;
   protected String MsgType;
}
