package sk.kuchta.eshop.implementation.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.kuchta.eshop.implementation.entity.user.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
