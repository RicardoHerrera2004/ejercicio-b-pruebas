package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Spring Data JPA crea la query SQL automáticamente basándose en el nombre
    List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end);
}