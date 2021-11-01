package com.panoramichotel.api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "guest")
public class GuestInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

}
