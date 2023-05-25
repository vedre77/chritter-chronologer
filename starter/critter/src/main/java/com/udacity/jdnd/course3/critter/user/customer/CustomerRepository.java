package com.udacity.jdnd.course3.critter.user.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // derived query (owner has pets, a collection of pet entities)
    Optional<Customer> findByPetsId(Long petId);

     /**
     custom query would look like:
     @Query("SELECT c FROM Customer c JOIN c.pets p WHERE p.id = :petId")
     Optional<Customer> findOwnerByPetId(@Param("petId") Long petId);
     **/
}
