package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    PaymentRepository repository;

    @Test
    void shouldFindPaymentsWithinDateRange() {
        LocalDateTime now = LocalDateTime.now();
        
        repository.save(new Payment(100.0, now));
        repository.save(new Payment(200.0, now.minusDays(5)));
        repository.save(new Payment(300.0, now.plusHours(1)));

        List<Payment> results = repository.findByDateBetween(
            now.minusDays(1), 
            now.plusDays(1)
        );

        assertThat(results).hasSize(2);
    }
}
