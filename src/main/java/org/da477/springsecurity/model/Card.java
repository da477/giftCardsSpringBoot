package org.da477.springsecurity.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "giftcards")
public class Card {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number", nullable = false, unique = true)
    private long number;

    @Column(name = "amount")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Card card = (Card) o;
        return id != null && Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



}
