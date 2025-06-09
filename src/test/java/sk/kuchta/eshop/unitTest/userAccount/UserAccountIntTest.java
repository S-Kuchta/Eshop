//package sk.kuchta.eshop.unitTest.userAccount;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.TestPropertySource;
//import sk.kuchta.eshop.api.dto.user.request.UserAccountSaveRequest;
//import sk.kuchta.eshop.api.service.user.UserAccountService;
//import sk.kuchta.eshop.implementation.entity.user.UserAccount;
//import sk.kuchta.eshop.implementation.repository.user.UserAccountRepository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@ExtendWith(MockitoExtension.class)
//@TestPropertySource("classpath:application-test.properties")
//public class UserAccountIntTest {
//
//    @InjectMocks
//    private UserAccountService userAccountService;
//
//    @Autowired
//    private UserAccountRepository userAccountRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void saveUserAccount() {
//        userAccountService.save(new UserAccountSaveRequest("email", "password"));
////        Mockito.verify(userAccountRepository, Mockito.times(1))
////                .save(Mockito.any(UserAccount.class));
//
//        assertEquals(1, userAccountRepository.count());
//
//    }
//
//
//    @Test
//    void save_shouldCreateUserSuccessfully() {
//        UserAccountSaveRequest request = new UserAccountSaveRequest("test@example.com", "password");
//
//        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
//
//        String result = userAccountService.save(request);
//
//        assertEquals("User account created successfully", result);
//        verify(userAccountRepository).save(any(UserAccount.class));
//    }
//}
