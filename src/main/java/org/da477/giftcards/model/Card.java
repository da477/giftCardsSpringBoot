package org.da477.giftcards.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Card extends AbstractBaseEntity {

    @Column(name = "number", nullable = false, unique = true)
    private Long number;

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

    @Column(name = "lastupdate", nullable = false, columnDefinition = "timestamp default now()")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM/dd/yyyy hh:mm:ss")
    private Date lastUpdate = new Date();

    @Column(name = "registered")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM/dd/yyyy hh:mm:ss")
    private Date registered;

    @Column(name = "owner_id")
    private String owner_id;

    @Transient
    private Float rest;

    public Card() {

    }

    public Float getRest() {
        return getAmount() - getWithdrawal();
    }

    public boolean isNew() {
        return this.getId() == null || this.getId() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Card card = (Card) o;
        return this.getId() != null && Objects.equals(this.getId(), card.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
