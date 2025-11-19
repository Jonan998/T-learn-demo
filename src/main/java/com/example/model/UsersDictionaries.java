package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_dictionaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDictionaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "progress")
    private Integer progress;

    public UsersDictionaries(User user,
                             Dictionary dictionary,
                             Boolean isActive,
                             Integer progress) {
        this.user = user;
        this.dictionary = dictionary;
        this.isActive = isActive;
        this.progress = progress;
    }
}