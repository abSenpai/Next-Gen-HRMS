package com.MyNicProject.hrms.repository;

import com.MyNicProject.hrms.entity.TrainingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRecordRepository extends JpaRepository<TrainingRecord , Long> {

    List<TrainingRecord> findByEmployee_EmployeeId(String employeeId);
}
