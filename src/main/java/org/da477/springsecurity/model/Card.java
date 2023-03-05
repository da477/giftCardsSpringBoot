package org.da477.springsecurity.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Data
@Table(name = "giftcards")
public class Card {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number", nullable = false, unique = true)
    private long number;

    @Column(name = "amount")
    @Min(value = 0, message = "Amount should be greater than 0")
    private Float amount;

    @Column(name = "withdrawal")
    private Float withdrawal;

    @Column(name = "isprint")
    private boolean isPrint;

    @Column(name = "isgenerated")
    private boolean isGenerated;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "typecard")
    @NotNull
    private TypeCard typeCard;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registered = new Date();

    @Column(name = "lastupdate", nullable = false, columnDefinition = "timestamp default now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate = new Date();

    @Column(name = "owner_id")
    private String owner_id;

    @Transient
    private Float rest;

    public Card() {

    }

    public Float getRest() {
        return getAmount() -  getWithdrawal();
    }

    public boolean isNew() {
        return this.id == null || this.id == 0;
    }
}
