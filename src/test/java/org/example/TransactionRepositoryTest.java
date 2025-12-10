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

@DataJpaTest
@Testcontainers // 1. Activa Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 2. Le dice a Spring: "No uses H2, usa el contenedor que te doy abajo"
class TransactionRepositoryTest {

    // 3. Define el contenedor de Postgres (Esto se descargará solo en GitHub Codespaces)
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    TransactionRepository repository;

    @Test
    void shouldFindTransactionsWithinDateRange() {
        // ARRANGE: Preparamos datos
        LocalDateTime now = LocalDateTime.now();

        Transaction t1 = new Transaction(100.0, now);
        Transaction t2 = new Transaction(200.0, now.minusDays(5)); // Fuera de fecha
        Transaction t3 = new Transaction(300.0, now.plusHours(1));

        repository.saveAll(List.of(t1, t2, t3));

        // ACT: Ejecutamos la búsqueda
        List<Transaction> results = repository.findByDateBetween(
                now.minusDays(1),
                now.plusDays(1)
        );

        // ASSERT: Verificamos
        assertThat(results).hasSize(2); // Solo t1 y t3 deberían salir
    }
}