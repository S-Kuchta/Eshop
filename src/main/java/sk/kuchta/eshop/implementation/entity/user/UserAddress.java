package sk.kuchta.eshop.implementation.entity.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.kuchta.eshop.api.exception.ResourceNotFoundException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Setter
    private String street;

    @Column(nullable = false)
    @Setter
    private String city;

    @Column(nullable = false)
    @Setter
    private String country;

    @Column(nullable = false)
    @Setter
    private int zip;

    @Column(nullable = false)
    @Setter
    private boolean billingAddress;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    public UserAddress(String street, String city, String country, int zip, boolean billingAddress, UserAccount userAccount) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.billingAddress = billingAddress;
        this.userAccount = userAccount;
    }

    public UserAddress(String street, String city, String country, int zip, boolean billingAddress) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.billingAddress = billingAddress;
    }
}
