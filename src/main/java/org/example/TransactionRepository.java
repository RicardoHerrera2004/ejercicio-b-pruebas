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

// OJO AQUÍ: Agregamos la propiedad 'ddl-auto=create-drop'
@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryTest {
    
    // ... el resto del código déjalo igual ...
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    TransactionRepository repository;

    @Test
    void shouldFindTransactionsWithinDateRange() {
        // ... tu test ...
        LocalDateTime now = LocalDateTime.now();
        repository.saveAll(List.of(
            new Transaction(100.0, now),
            new Transaction(200.0, now.minusDays(5)),
            new Transaction(300.0, now.plusHours(1))
        ));

        List<Transaction> results = repository.findByDateBetween(now.minusDays(1), now.plusDays(1));
        assertThat(results).hasSize(2);
    }
}
