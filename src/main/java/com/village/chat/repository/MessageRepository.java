package com.village.chat.repository;

import com.village.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Fetch messages between two users (either direction)
    List<Message> findBySenderUsernameAndReceiverUsernameOrReceiverUsernameAndSenderUsernameOrderByTimestampAsc(
            String sender1, String receiver1, String sender2, String receiver2);
    @Query("SELECT DISTINCT " +
            "CASE WHEN m.senderUsername = :username THEN m.receiverUsername " +
            "     ELSE m.senderUsername " +
            "END " +
            "FROM Message m " +
            "WHERE m.senderUsername = :username OR m.receiverUsername = :username")
    List<String> findConversationPartners(@Param("username") String username);

    void deleteBySenderUsernameOrReceiverUsername(String senderUsername, String receiverUsername);
}
