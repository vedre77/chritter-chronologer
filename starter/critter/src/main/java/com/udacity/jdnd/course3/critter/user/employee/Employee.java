package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.user.BaseUser;
import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@DiscriminatorValue("employee")
public class Employee extends BaseUser {
    @Column(name = "title")
    private String title;

    @ElementCollection(targetClass = DayOfWeek.class)
    private Set<DayOfWeek> daysAvailable;

    //@ManyToMany(mappedBy = "employees")
    //private List<Schedule> schedules;

    // getters and setters

    public String getTitle() {
        return title;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
