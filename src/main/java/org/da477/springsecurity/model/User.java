package org.da477.springsecurity.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "First_Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2-30 characters")
    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registered = new Date();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;


}
