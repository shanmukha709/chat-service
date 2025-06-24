package com.village.chat.controller;

import com.village.chat.dto.MessageDTO;
import com.village.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = {
        "https://our-circle-ui.onrender.com",
        "http://localhost:3000"
})
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Send a new message
    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO savedMessage = chatService.sendMessage(messageDTO);
        return ResponseEntity.ok(savedMessage);
    }

    // Get chat history between two users
    @GetMapping("/history/{user1}/{user2}")
    public ResponseEntity<List<MessageDTO>> getChatHistory(
            @PathVariable String user1,
            @PathVariable String user2) {

        List<MessageDTO> chatHistory = chatService.getChatHistory(user1, user2);
        return ResponseEntity.ok(chatHistory);
    }

    @GetMapping("/conversations/{username}")
    public ResponseEntity<List<String>> getConversations(@PathVariable String username) {
        List<String> conversations = chatService.getConversationUsers(username);
        return ResponseEntity.ok(conversations);
    }

    @DeleteMapping("/delete/user/{username}")
    public ResponseEntity<Void> deleteChatsOfUser(@PathVariable String username) {
        chatService.deleteMessagesOfUser(username);
        return ResponseEntity.ok().build();
    }

}
