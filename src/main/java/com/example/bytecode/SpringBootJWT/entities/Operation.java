package com.example.bytecode.SpringBootJWT.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="operations")
public class Operation  implements Serializable {   //Note that for JPA must implement serializable interface{

   // ID, DATE, VALUE, DESCRIPTION, FK_ACCOUNT1, FK_ACCOUNT2

    @Getter @Setter                 //for lombok
    @NotEmpty @NotBlank @NotNull    //for JSR-303 validation
    @Id                             //for JPA
    @Column(name="ID")              //for JPA
    private String id;

    @Getter @Setter                 //for lombok
    @Column(name="DATE")            //for JPA
    private Date date;

    @Getter @Setter                 //for lombok
    @Column(name="DESCRIPTION")     //for JPA
    private String description;

    @Getter @Setter                 //for lombok
    @Column(name="VALUE")     //for JPA
    private Double value;


    @Getter @Setter                 //for lombok
    @NotNull @NotEmpty @NotBlank    //for JSR-303 validation
    @Column(name="FK_ACCOUNT1")     //for JPA
    private String fkAccount1;

    @Getter @Setter             //for lombok
    @Column(name="FK_ACCOUNT2") //for JPA
    private String fkAccount2;

    @PrePersist
    void getTimeOperation() {
        this.date = new Date();
    }

    public Operation() { }

    public Operation(String id, Date date, String description, Double value, String fkAccount1, String fkAccount2) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.value = value;
        this.fkAccount1 = fkAccount1;
        this.fkAccount2 = fkAccount2;
    }
}
