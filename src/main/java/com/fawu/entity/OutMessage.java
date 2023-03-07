package com.fawu.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElementWrapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class OutMessage {
    /**
     * 开发者微信号
     */
    @JacksonXmlProperty(localName = "FromUserName")
    protected String fromUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    @JacksonXmlProperty(localName = "ToUserName")
    protected String toUserName;

    /**
     * 消息创建时间
     */
    @JacksonXmlProperty(localName = "CreateTime")
    protected Long createTime;

    /**
     * 消息类型
     * text 文本消息
     * image 图片消息
     * voice 语音消息
     * video 视频消息
     * music 音乐消息
     */
    @JacksonXmlProperty(localName = "MsgType")
    protected String msgType;

    /**
     * 文本内容
     */
    @JacksonXmlProperty(localName = "Content")
    private String content;

    /**
     * 图片的媒体id列表
     */
    @XmlElementWrapper(name = "Image") // 表示MediaId属性内嵌于<Image>标签
    @JacksonXmlProperty(localName = "MediaId")
    private String[] mediaId;
}
