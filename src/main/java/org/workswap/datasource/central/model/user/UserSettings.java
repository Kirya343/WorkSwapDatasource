package org.workswap.datasource.central.model.user;

import org.workswap.datasource.central.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class UserSettings {

    public UserSettings(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    private String avatarType = "google";

    @Setter
    private String googleAvatar;

    @Setter
    private String uploadedAvatar;
    
    @Setter
    private boolean phoneVisible = true;  // Скрывать или отображать телефон

    @Setter
    private boolean emailVisible = true;  // Скрывать или отображать email

    @Setter
    private boolean telegramConnected = false; // Подключен ли Telegram
}