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
class TransactionRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    TransactionRepository repository;

    @Test
    void shouldFindTransactionsWithinDateRange() {
        // 1. ARRANGE
        LocalDateTime now = LocalDateTime.now();
        
        Transaction t1 = new Transaction(100.0, now);
        Transaction t2 = new Transaction(200.0, now.minusDays(5));
        Transaction t3 = new Transaction(300.0, now.plusHours(1));

        repository.saveAll(List.of(t1, t2, t3));

        List<Transaction> results = repository.findByDateBetween(
            now.minusDays(1), 
            now.plusDays(1)
        );

        assertThat(results).hasSize(2);
    }
}
