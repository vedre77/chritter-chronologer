package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer owner;

    private LocalDate birthDate;

    private String notes;

    public Long getId() {
        return id;
    }

    public PetType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Customer getOwner() {
        return owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
