package com.MyNicProject.hrms.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "training_record")
public class TrainingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;


    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private TrainingModule module;


    @Column(name = "instructor_name")
    private String instructorName;

    @Column(name = "status", nullable = false)
    private String status = "In Progress";

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;


    @Column(name = "certificate_number", unique = true)
    private String certificateNumber;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    public Long getRecordId() {return recordId;}
    public void setRecordId(Long recordId) {this.recordId = recordId;}

    public TrainingModule getModule() {return module;}
    public void setModule(TrainingModule module) {this.module = module;}

    public Employee getEmployee() {return employee;}
    public void setEmployee(Employee employee) {this.employee = employee;}

    public String getInstructorName() {return instructorName;}
    public void setInstructorName(String instructorName) {this.instructorName = instructorName;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public LocalDate getIssueDate() {return issueDate;}
    public void setIssueDate(LocalDate issueDate) {this.issueDate = issueDate;}

    public String getRemarks() {return remarks;}
    public void setRemarks(String remarks) {this.remarks = remarks;}

    public String getCertificateNumber() {return certificateNumber;}
    public void setCertificateNumber(String certificateNumber) {this.certificateNumber = certificateNumber;}

    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public String getFileType() {return fileType;}
    public void setFileType(String fileType) {this.fileType = fileType;}

    public String getFilePath() {return filePath;}
    public void setFilePath(String filePath) {this.filePath = filePath;}

    public LocalDateTime getUploadedAt() {return uploadedAt;}
    public void setUploadedAt(LocalDateTime uploadedAt) {this.uploadedAt = uploadedAt;}


}
