package com.nbkj.wchatdemo.util.message;

import com.nbkj.wchatdemo.common.WXMsConstant;
import com.nbkj.wchatdemo.model.message.News;
import com.nbkj.wchatdemo.model.message.NewsMessage;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
public class NewsMessageUtil implements BaseMessageUtil<NewsMessage> {
    @Override
    public String messageToxml(NewsMessage newsMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new News().getClass());
        return xstream.toXML(newsMessage);
    }

    @Override
    public String initMessage(String fromUserName, String toUserName) {
        String message = null;
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("胡少君");
        news.setDescription("黄鹤楼客服哈死定了开发和卢卡斯电话费拉萨的解放路卡三等奖路口附近路口沙发斯蒂芬阿道夫撒多");
        news.setPicUrl("http://res.ent.ifeng.com/dci_2011/1028/ori_4eaa1f50342c9.jpeg");
        news.setUrl("www.baidu.com");

        newsList.add(news);

        newsMessage.setToUserName(toUserName);
        newsMessage.setFromUserName(fromUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(WXMsConstant.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = messageToxml(newsMessage);
        return message;
    }
}
