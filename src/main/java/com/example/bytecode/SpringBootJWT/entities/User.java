package com.example.bytecode.SpringBootJWT.entities;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name="users")
public class User implements Serializable{   //Note that for JPA must implement serializable interface

    //ID, USERNAME, PASSWORD, PERMISSION

    @Getter @Setter                 //for lombok
    @NotEmpty @NotBlank @NotNull    //for JSR-303 validation
    @Id                             //for JPA
    @Column(name="ID")              //for JPA
    private String id;

    @Getter @Setter                 //for lombok
    @NotEmpty @NotBlank @NotNull    //for JSR-303 validation
    @Column(name="USERNAME")        //for JPA
    private String username;

    @Getter @Setter                 //for lombok
    @NotEmpty @NotBlank @NotNull    //for JSR-303 validation
    @Column(name="PASSWORD")        //for JPA
    private String password;

    @Getter @Setter                 //for lombok
    @NotEmpty @NotBlank @NotNull    //for JSR-303 validation
    @Column(name="PERMISSION")      //for JPA
    private String permission;

    public User() { }

    public User(String id, String username, String password, String permission) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.permission = permission;
    }
}


