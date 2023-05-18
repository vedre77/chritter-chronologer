package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.user.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Transactional
    public Customer saveCustomer(Customer customer) {
        baseUserRepository.save(customer);
        return customer;
    }
}
