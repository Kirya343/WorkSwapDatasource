package org.workswap.datasource.central.model.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;

@Getter
@Entity
@NoArgsConstructor
public class Conversation {

    public Conversation(Set<User> users,
                        Listing listing) {
        for (User user : users) {
            ConversationParticipant participant = new ConversationParticipant(this, user);
            participants.add(participant);
        }
        this.listing = listing;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ConversationParticipant> participants = new HashSet<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    @Setter
    private boolean temporary = true;

    @Setter
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
        return participants.stream()
                .map(ConversationParticipant::getUser)
                .filter(user -> !user.equals(currentUser))
                .findFirst()
                .orElse(null);
    }

    // Получить последнее сообщение
    public Message getLastMessage() {
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }
}

