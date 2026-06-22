package com.MyNicProject.hrms.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.MyNicProject.hrms.entity.TrainingRecord;
import com.MyNicProject.hrms.repository.TrainingRecordRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TrainingRecordRepository recordRepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Allow CORS pre-flight
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String path = request.getRequestURI();

        // 1. Allow login publicly
        if (path.startsWith("/api/auth/login")) {
            return true;
        }

        // 2. Allow save (certificate upload) publicly as default
        if (path.startsWith("/api/certificates/save")) {
            return true;
        }

        // Enforce JWT for all other endpoints
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return false;
        }

        String token = authHeader.substring(7);
        DecodedJWT decodedJWT = jwtUtil.verifyToken(token);
        if (decodedJWT == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has expired or is invalid");
            return false;
        }

        String tokenEmployeeId = decodedJWT.getClaim("employeeId").asString();
        String tokenRole = decodedJWT.getClaim("role").asString();

        // 3. User provisioning requires ADMIN role
        if (path.startsWith("/api/auth/provision")) {
            if (!"ADMIN".equalsIgnoreCase(tokenRole)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Only ADMIN role can provision users");
                return false;
            }
            return true;
        }

        // 4. View all certificates requires ADMIN role
        if (path.startsWith("/api/certificates/all")) {
            if (!"ADMIN".equalsIgnoreCase(tokenRole)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Only ADMIN role can access all employee certificates");
                return false;
            }
            return true;
        }

        // 5. View specific employee certificates: Admin can view any, User can view only their own
        if (path.startsWith("/api/certificates/employee/")) {
            Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (pathVariables != null && pathVariables.containsKey("employeeId")) {
                String reqEmployeeId = pathVariables.get("employeeId");
                if (!"ADMIN".equalsIgnoreCase(tokenRole) && !tokenEmployeeId.equalsIgnoreCase(reqEmployeeId)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access denied: You can only view your own records");
                    return false;
                }
            }
            return true;
        }

        // 6. Download specific record: Admin can download any, User can download only their own
        if (path.startsWith("/api/certificates/download/")) {
            Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (pathVariables != null && pathVariables.containsKey("recordId")) {
                try {
                    Long recordId = Long.parseLong(pathVariables.get("recordId"));
                    Optional<TrainingRecord> record = recordRepo.findById(recordId);
                    if (record.isPresent()) {
                        String recordEmpId = record.get().getEmployee().getEmployeeId();
                        if (!"ADMIN".equalsIgnoreCase(tokenRole) && !tokenEmployeeId.equalsIgnoreCase(recordEmpId)) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("Access denied: You do not own this certificate");
                            return false;
                        }
                    }
                } catch (NumberFormatException e) {
                    // Let the controller handle bad request formats
                }
            }
            return true;
        }

        return true;
    }
}
