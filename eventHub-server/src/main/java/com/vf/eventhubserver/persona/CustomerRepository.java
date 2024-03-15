package com.vf.eventhubserver.persona;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phone);

    Optional<Customer> findByUsername(String username);

    boolean existsByEmailAndPhoneNumber(String email, String phoneNumber);

}
