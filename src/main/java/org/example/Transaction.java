package org.example;

import jakarta.persistence.*; 
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions") 
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double amount;
    private LocalDateTime date;

    public Transaction() {}

    public Transaction(Double amount, LocalDateTime date) {
        this.amount = amount;
        this.date = date;
    }

    public Double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
}
