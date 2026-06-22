package com.MyNicProject.hrms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "role")
    private String role; // "ADMIN" or "USER"

    @Column(name = "can_login")
    private Boolean canLogin = false;

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getCanLogin() { return canLogin; }
    public void setCanLogin(Boolean canLogin) { this.canLogin = canLogin; }
}