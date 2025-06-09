package sk.kuchta.eshop.implementation.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(unique = true, nullable = false)
    @Setter
    private String phoneNumber;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    public UserDetail(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public UserDetail(String name, String phoneNumber, UserAccount userAccount) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userAccount = userAccount;
    }
}
