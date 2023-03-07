package com.fawu.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author K.faWu
 * @program starter-dome
 * @date 2023-02-27 15:11
 **/

@Slf4j
@RestController
@RequestMapping
public class TestController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;

    @Autowired
    private WxMpMessageRouter wxMpMessageRouter;

    @GetMapping("test")
    public void testAutowire() {
        System.out.println(wxMpService);
        System.out.println(wxMpConfigStorage);
    }

    @GetMapping("message")
    public String configAccess(String signature, String timestamp, String nonce, String echostr) {
        // 校验签名
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 校验成功原样返回echostr
            return echostr;
        }
        // 校验失败
        return null;
    }


    @PostMapping(value = "message", produces = "application/xml; charset=UTF-8")
    public String handleMessage(@RequestBody String requestBody,
                                @RequestParam("signature") String signature,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce) {
        log.info("handleMessage调用");
        // 校验消息是否来自微信
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        // 解析消息体，封装为对象
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
        WxMpXmlOutMessage outMessage;
        try {
            // 将消息路由给对应的处理器，获取响应
            outMessage = wxMpMessageRouter.route(inMessage);
        } catch (Exception e) {
            log.error("微信消息路由异常", e);
            outMessage = null;
        }
        // 将响应消息转换为xml格式返回
        return outMessage == null ? "" : outMessage.toXml();
    }

    @GetMapping("createMenu")
    public String createMenu() throws WxErrorException {
        // 创建菜单对象
        WxMenu menu = new WxMenu();
        // 创建按钮1
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.MenuButtonType.CLICK);
        button1.setName("今日歌曲");
        button1.setKey("V1001_TODAY_MUSIC");
        // 创建按钮2
        WxMenuButton button2 = new WxMenuButton();
        button2.setName("菜单");
        // 创建按钮2的子按钮1
        WxMenuButton button21 = new WxMenuButton();
        button21.setType(WxConsts.MenuButtonType.VIEW);
        button21.setName("搜索");
        button21.setUrl("https://www.baidu.com/");
        // 创建按钮2的子按钮2
        WxMenuButton button22 = new WxMenuButton();
        button22.setType(WxConsts.MenuButtonType.VIEW);
        button22.setName("视频");
        button22.setUrl("https://v.qq.com/");
        // 创建按钮2的子按钮3
        WxMenuButton button23 = new WxMenuButton();
        button23.setType(WxConsts.MenuButtonType.CLICK);
        button23.setName("赞一下我们");
        button23.setKey("V1001_GOOD");
        // 将子按钮添加到按钮2
        button2.getSubButtons().add(button21);
        button2.getSubButtons().add(button22);
        button2.getSubButtons().add(button23);
        // 将按钮1和你按钮2添加到菜单
        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        System.out.println(menu.toJson());
        // 创建按钮
        return wxMpService.getMenuService().menuCreate(menu);
    }


}
