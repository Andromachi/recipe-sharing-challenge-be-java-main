package net.azeti.challenge.recipe.authentication.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue
    public Integer id;
    @Column(unique = true)
    public String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}
