package com.MyNicProject.hrms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "training_module")
public class TrainingModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @Column(name = "training_type", nullable = false)
    private String trainingType;

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public String getModuleName() { return moduleName; }
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    public String getTrainingType() { return trainingType; }
    public void setTrainingType(String trainingType) { this.trainingType = trainingType; }
}