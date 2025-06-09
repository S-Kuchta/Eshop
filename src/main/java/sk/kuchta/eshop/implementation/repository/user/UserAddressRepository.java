package sk.kuchta.eshop.implementation.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.kuchta.eshop.implementation.entity.user.UserAddress;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findAllByUserAccountId(Long userAccountId);
}
