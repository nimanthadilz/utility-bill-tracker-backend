package com.nimantha.utilitybilltracker;

import com.nimantha.utilitybilltracker.models.*;
import com.nimantha.utilitybilltracker.repositories.BillRepository;
import com.nimantha.utilitybilltracker.repositories.PaymentRepository;
import com.nimantha.utilitybilltracker.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UtilityBillTrackerApplicationTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityRepository utilityRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    void storeUsersAndUtilities() {
        User user1 = new User("nimantha", "password", "Nimantha Cooray");

        Utility utility1 = new Utility();
        utility1.setAccountNo("1234");
        utility1.setName("Electricity");
        utility1.setOutstandingAmount(325.75);
        utility1.setUser(user1);

        Bill bill1 = new Bill();
        bill1.setStartDate(LocalDate.of(2024, 7, 22));
        bill1.setEndDate(LocalDate.of(2024, 8, 22));
        bill1.setAmount(100.75);
        bill1.setUtility(utility1);

        Payment payment1 = new Payment();
        payment1.setDateTime(LocalDateTime.now());
        payment1.setAmount(50.75);
        payment1.setBill(bill1);
        payment1.setUtility(utility1);

        userRepository.save(user1);
        utilityRepository.save(utility1);
        billRepository.save(bill1);
        paymentRepository.save(payment1);

//        Optional<Utility> result = utilityRepository.findById(Long.valueOf("1"));
//        assertTrue(result.isPresent());
//        Utility resultUtility = result.get();
//        User user2 = resultUtility.getUser();
//        System.out.println("Name: " + user2.getName());
//        assertEquals(resultUtility.getUser().getName(), utility1.getUser().getName());
    }
}
