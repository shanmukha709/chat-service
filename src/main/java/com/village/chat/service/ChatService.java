package com.village.chat.service;

import com.village.chat.dto.MessageDTO;

import java.util.List;

public interface ChatService {

    // Save a new message
    MessageDTO sendMessage(MessageDTO messageDTO);

    // Get chat history between two users
    List<MessageDTO> getChatHistory(String user1, String user2);

    List<String> getConversationUsers(String username);

    void deleteMessagesOfUser(String username);

}
