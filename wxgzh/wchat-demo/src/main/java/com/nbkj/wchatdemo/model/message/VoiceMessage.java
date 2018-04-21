package com.nbkj.wchatdemo.model.message;

import lombok.Data;

/**
 * @author :hsj
 * @description
 * @date :2018/4/21
 */
@Data
public class VoiceMessage extends BaseMessage {
    private Voice voice;
}
