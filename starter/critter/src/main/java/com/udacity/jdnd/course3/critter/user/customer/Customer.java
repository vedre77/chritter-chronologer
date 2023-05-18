package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.user.BaseUser;
import javax.persistence.*;

@Entity
@DiscriminatorValue("customer")
public class Customer extends BaseUser {
    @Column(name = "telephone")
    private String telephone;

    @Column(name = "notes")
    private String notes;

    //@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    //private List<Pet> pets;

    // getters and setters


    public String getTelephone() {
        return telephone;
    }

    public String getNotes() {
        return notes;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
