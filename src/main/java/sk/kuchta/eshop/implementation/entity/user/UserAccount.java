package sk.kuchta.eshop.implementation.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Setter
    private String email;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column(nullable = false)
    private String role = "USER";

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAddress> userAddress = new ArrayList<>();

    @Setter
    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private UserDetail userDetail;

    public UserAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserAccount(String email, String password, List<UserAddress> userAddress, UserDetail userDetail) {
        this.email = email;
        this.password = password;
        this.userAddress = userAddress;
        this.userDetail = userDetail;
    }
}
