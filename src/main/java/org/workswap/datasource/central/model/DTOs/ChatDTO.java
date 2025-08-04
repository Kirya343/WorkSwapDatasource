package org.workswap.datasource.central.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Новый DTO класс
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private String interlocutorName;
    private String interlocutorAvatar;
    private long unreadCount;
    private String lastMessagePreview;
    private LocalDateTime lastMessageTime;
    private String formattedLastMessageTime;
    private ListingDTO listing;
    private boolean hasNewMessage;
    private boolean temporary;
}
