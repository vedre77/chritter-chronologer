package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.BaseUser;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("customer")
public class Customer extends BaseUser {
    @Column(name = "telephone")
    private String telephone;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    public String getTelephone() {
        return telephone;
    }

    public String getNotes() {
        return notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPets(Pet pet) {
        pet.setOwner(this);
        pets.add(pet);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "pets=" + pets +
                "} " + super.toString();
    }
}
