package org.da477.springsecurity.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "giftcards")
public class Card {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number")
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

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date registered = new Date();

    @Column(name = "lastupdate", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date lastUpdate = new Date();

    @Column(name = "owner_id")
    private String owner_id;

    public Card() {

    }
}
