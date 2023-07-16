package com.example.bankserver.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private double amount;

    //    @Field(type = FieldType.Nested)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id", referencedColumnName = "id")
    private Account senderAccount;

    //    @Field(type = FieldType.Nested)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_account_id", referencedColumnName = "id")
    private Account recipientAccount;

    //    @Field(type = FieldType.Date, format = {}, pattern = "dd.MM.uuuu HH:mm:ss", name = "created_date_time")
    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDateTime;

    public Transaction(Account senderAccount, Account recipientAccount, double amount, LocalDateTime now) {
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.amount = amount;
//        this.createdDateTime = now;
    }
}
