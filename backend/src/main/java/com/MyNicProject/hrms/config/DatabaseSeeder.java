package com.MyNicProject.hrms.config;

import com.MyNicProject.hrms.entity.Employee;
import com.MyNicProject.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public void run(String... args) throws Exception {
        Optional<Employee> adminOpt = employeeRepo.findByEmployeeId("EMP001");
        if (adminOpt.isEmpty()) {
            Employee admin = new Employee();
            admin.setEmployeeId("EMP001");
            admin.setEmployeeName("Test Admin");
            // BCrypt hash for "password"
            admin.setPasswordHash("$2a$12$grexblRtXxncwy2vuJkGtuJagA0OH2PaAiZ1g1jlOPrQEV0bzMLRa");
            admin.setRole("ADMIN");
            admin.setCanLogin(true);
            employeeRepo.save(admin);
            System.out.println("=================================================");
            System.out.println("  SEEDED DEFAULT ADMIN USER: EMP001 / password  ");
            System.out.println("=================================================");
        }
    }
}
