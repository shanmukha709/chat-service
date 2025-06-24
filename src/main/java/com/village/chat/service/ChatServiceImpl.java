package com.village.chat.service;

import com.village.chat.dto.MessageDTO;
import com.village.chat.entity.Message;
import com.village.chat.repository.MessageRepository;
import com.village.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public MessageDTO sendMessage(MessageDTO messageDTO) {
        // Convert DTO to Entity
        Message message = new Message();
        message.setSenderUsername(messageDTO.getSenderUsername());
        message.setReceiverUsername(messageDTO.getReceiverUsername());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(LocalDateTime.now());

        // Save to DB
        Message savedMessage = messageRepository.save(message);

        // Convert back to DTO and return
        return mapToDTO(savedMessage);
    }

    @Override
    public List<MessageDTO> getChatHistory(String user1, String user2) {
        List<Message> messages = messageRepository
                .findBySenderUsernameAndReceiverUsernameOrReceiverUsernameAndSenderUsernameOrderByTimestampAsc(
                        user1, user2, user1, user2);

        return messages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Convert Entity to DTO
    private MessageDTO mapToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setSenderUsername(message.getSenderUsername());
        dto.setReceiverUsername(message.getReceiverUsername());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }

    @Override
    public List<String> getConversationUsers(String username) {
        return messageRepository.findConversationPartners(username);
    }

    @Override
    public void deleteMessagesOfUser(String username) {
        messageRepository.deleteBySenderUsernameOrReceiverUsername(username, username);
    }
}
