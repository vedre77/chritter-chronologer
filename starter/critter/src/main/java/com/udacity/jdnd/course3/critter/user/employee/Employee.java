package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.user.BaseUser;
import javax.persistence.*;

@Entity
@DiscriminatorValue("employee")
public class Employee extends BaseUser {
    @Column(name = "title")
    private String title;

    //@ManyToMany(mappedBy = "employees")
    //private List<Schedule> schedules;

    // getters and setters
}
