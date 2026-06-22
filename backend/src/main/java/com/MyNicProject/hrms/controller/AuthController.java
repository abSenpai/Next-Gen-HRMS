package com.MyNicProject.hrms.controller;

import com.MyNicProject.hrms.config.JwtUtil;
import com.MyNicProject.hrms.entity.Employee;
import com.MyNicProject.hrms.repository.EmployeeRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public static class LoginRequest {
        public String employeeId;
        public String password;
    }

    public static class ProvisionRequest {
        public String employeeId;
        public String password;
        public String role;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req.employeeId == null || req.employeeId.isBlank() || req.password == null || req.password.isBlank()) {
            return ResponseEntity.badRequest().body("Employee ID and password are required");
        }

        Optional<Employee> empOpt = employeeRepo.findByEmployeeId(req.employeeId);
        if (empOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Employee ID or password");
        }

        Employee employee = empOpt.get();
        if (employee.getCanLogin() == null || !employee.getCanLogin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login not provisioned for this employee");
        }

        if (employee.getPasswordHash() == null || !BCrypt.checkpw(req.password, employee.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Employee ID or password");
        }

        String token = jwtUtil.generateToken(employee.getEmployeeId(), employee.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("employeeId", employee.getEmployeeId());
        response.put("employeeName", employee.getEmployeeName());
        response.put("role", employee.getRole());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/provision")
    public ResponseEntity<?> provision(@RequestBody ProvisionRequest req) {
        if (req.employeeId == null || req.employeeId.isBlank() || req.password == null || req.password.isBlank() || req.role == null || req.role.isBlank()) {
            return ResponseEntity.badRequest().body("Employee ID, password, and role are required");
        }

        if (!"ADMIN".equalsIgnoreCase(req.role) && !"USER".equalsIgnoreCase(req.role)) {
            return ResponseEntity.badRequest().body("Role must be either ADMIN or USER");
        }

        Optional<Employee> empOpt = employeeRepo.findByEmployeeId(req.employeeId);
        if (empOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee does not exist. Please create the employee first.");
        }

        Employee employee = empOpt.get();
        String hashedPassword = BCrypt.hashpw(req.password, BCrypt.gensalt(12));
        employee.setPasswordHash(hashedPassword);
        employee.setRole(req.role.toUpperCase());
        employee.setCanLogin(true);

        employeeRepo.save(employee);

        return ResponseEntity.ok("Employee login credentials provisioned successfully");
    }
}
