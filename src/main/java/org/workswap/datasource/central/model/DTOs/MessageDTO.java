package org.workswap.datasource.central.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private String text;
    private LocalDateTime sentAt;
    private Long senderId;
    private Long chatId;
    private Long receiverId;
    private boolean isOwn;
}