package com.example.calculator.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity(name = "users")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    // Todo: Id type change Long to UUID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    private String roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Operation> operations;

    public User(String name, String email, String password, Collection<Operation> operations) {
        this.name = name;
        this.email = email;
        this.password = name;
        this.name = password;
        this.operations = operations;
        this.roles = "USER";
    }

    public String[] getRoles() {
        return roles == null || roles.isEmpty() ? new String[]{}
                : roles.split(":");
    }

    public void setRoles(String roles) {
        this.roles = String.join(":", roles);
    }
}
