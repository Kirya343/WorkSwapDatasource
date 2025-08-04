package org.workswap.datasource.central.model.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;

@Getter
@Entity
@NoArgsConstructor
public class Chat {

    public Chat(Set<User> users,
                        Listing listing) {
        for (User user : users) {
            ChatParticipant participant = new ChatParticipant(this, user);
            participants.add(participant);
        }
        this.listing = listing;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ChatParticipant> participants = new HashSet<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    @Setter
    private boolean temporary = true;

    @Setter
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Message> messages = new ArrayList<>();

    @ManyToOne
    @Setter
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @Transient
    private long unreadCount;

    @Transient
    private String lastMessagePreview;

    @Transient
    private String lastMessageTime;

    @Transient
    private transient User interlocutor; // Временное поле для хранения собеседника

    public User getInterlocutor(User currentUser) {
        Long currentUserId = currentUser.getId();

        return participants.stream()
                .map(ChatParticipant::getUser)
                .filter(user -> !Objects.equals(user.getId(), currentUserId))
                .findFirst()
                .orElse(null);
    }
    
    // Получить последнее сообщение
    public Message getLastMessage() {
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }
}

