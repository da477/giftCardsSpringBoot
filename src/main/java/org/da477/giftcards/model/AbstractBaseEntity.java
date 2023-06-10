package org.da477.giftcards.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class AbstractBaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "MM/dd/yyyy hh:mm:ss")
    private Date registered = new Date();

}
