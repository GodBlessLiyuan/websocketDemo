package org.sang.wschat.controller;

import org.sang.wschat.model.Chat;
import org.sang.wschat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GreetingController {
    @Autowired
    SimpMessagingTemplate messagingTemplate;
//    第一个示例：群聊
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception {
        return message;
    }
//    可能是该函数可以指定发送到
    @MessageMapping("/chat")
    public void chat(Principal principal, Chat chat) {
        String from = principal.getName();
        chat.setFrom(from);
        messagingTemplate.convertAndSendToUser(chat.getTo(),
                "/queue/chat", chat);
    }
}