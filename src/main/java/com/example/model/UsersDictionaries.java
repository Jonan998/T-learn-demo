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

    private String isActive;
    private int progress;

    public UsersDictionaries(User user,
                             Dictionary dictionary,
                             String isActive,
                             int progress) {
        this.user = user;
        this.dictionary = dictionary;
        this.isActive = isActive;
        this.progress = progress;
    }
}
