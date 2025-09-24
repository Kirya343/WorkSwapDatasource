package org.workswap.datasource.central.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.workswap.common.enums.UserStatus;
import org.workswap.common.enums.UserType;
import org.workswap.datasource.admin.model.Task;
import org.workswap.datasource.central.model.chat.ChatParticipant;
import org.workswap.datasource.central.model.listingModels.Location;
import org.workswap.datasource.central.model.user.Role;
import org.workswap.datasource.central.model.user.UserSettings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public User(String name,
                String email,
                String sub,
                String avatarUrl,
                Set<Role> roles,
                boolean termsAccepted) {
        this.type = UserType.STANDARD;
        this.name = name;
        this.email = email;
        this.sub = sub;
        this.avatarUrl = avatarUrl;
        this.roles = roles;
        this.termsAccepted = termsAccepted;
        this.termsAcceptanceDate = LocalDateTime.now();

        this.settings = new UserSettings(this);
    }

    public User(UserStatus status, UserType type, Set<Role> roles) {
        this.status = status;
        this.type = type;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = true, unique = true)
    private String sub; // Уникальный идентификатор от Google

    @Setter
    @Column(nullable = true, unique = true)
    private String name;

    @Column(nullable = true, unique = true)
    private String email;

    @Setter
    private String bio;

    @Setter
    private String avatarUrl;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_languages", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> languages = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Listing> listings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatParticipant> chatParticipants = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "favorite_listing",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "listing_id")
    )
    private Set<Listing> favoriteListings = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type = UserType.TEMP;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.PENDING;

    @Setter
    private Double rating = 0.0; // Средний рейтинг пользователя

    @Setter
    private String phone;

    @Setter
    private Integer completedJobs;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Setter
    private boolean termsAccepted = false; // Приняты ли условия использования

    @Setter
    private LocalDateTime termsAcceptanceDate = LocalDateTime.now();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Review> profileReviews;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Setter
    @Transient
    private List<Task> tasks;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private UserSettings settings;
}
