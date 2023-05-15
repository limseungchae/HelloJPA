package model;

// deptid, dname, mgrid, locid

import lombok.Data;

import javax.persistence.*;

@Entity
@Table (name = "departments")
@Data
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    @JoinColumn(name = "employees_deptid")
    private String deptid;

    @Column(name = "DEPARTMENT_NAME")
    private String dname;

    @Column(name = "MANAGER_ID")
    private String mgrid;

    @Column(name = "LOCATION_ID")
    private String locid;
}
