package org.example;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments") // Nombre seguro en plural
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDateTime date;

    public Payment() {}

    public Payment(Double amount, LocalDateTime date) {
        this.amount = amount;
        this.date = date;
    }

    public Double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
}
