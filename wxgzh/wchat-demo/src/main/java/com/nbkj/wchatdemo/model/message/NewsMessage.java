package com.nbkj.wchatdemo.model.message;

import lombok.Data;

import java.util.List;

/**
 * //图文消息
 *
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
@Data
public class NewsMessage extends BaseMessage {
    private int ArticleCount;
    private List<News> Articles;
}
