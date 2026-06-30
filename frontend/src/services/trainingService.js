// LocalStorage-based Mock Training and Authentication Service
// Simulates the Spring Boot backend completely in the browser.

const INITIAL_RECORDS = [
  {
    recordId: 1,
    employeeName: "Bikas Mallik",
    employeeId: "TE-030316",
    department: "Agriculture",
    trainingModule: "Cyber Security Awareness",
    trainingType: "Refresher",
    status: "Completed",
    issueDate: "2026-01-12",
    instructor: "NIC Security Cell",
    certificateNumber: "NIC-CS-2026-0891",
    certificateFile: "bikas_mallik_security.pdf",
    remarks: "Successfully completed Cyber Security training with distinction."
  },
  {
    recordId: 2,
    employeeName: "Asok Ranjan Chaudhuri",
    employeeId: "TE-064617",
    department: "Agriculture (Horticulture)",
    trainingModule: "Data Privacy Compliance",
    trainingType: "Induction",
    status: "In Progress",
    issueDate: "2026-05-10",
    instructor: "Finance IT Team",
    certificateNumber: "Pending",
    certificateFile: "john_doe_privacy.pdf",
    remarks: "Currently attending online modules."
  },
  {
    recordId: 3,
    employeeName: "Niren Majumder",
    employeeId: "TE-062411",
    department: "Finance Department",
    trainingModule: "e-Procurement & GeM",
    trainingType: "Specialization",
    status: "Completed",
    issueDate: "2026-03-24",
    instructor: "Ministry of Commerce",
    certificateNumber: "GEM-EP-2026-302",
    certificateFile: "niren_majumder_gem.pdf",
    remarks: "Acquired certification for official government purchases."
  }
];

const INITIAL_USERS = [
  {
    employeeId: "EMP001",
    password: "password",
    employeeName: "Test Admin",
    role: "ADMIN",
    canLogin: true
  },
  {
    employeeId: "TE-030316",
    password: "password",
    employeeName: "Bikas Mallik",
    role: "USER",
    canLogin: true
  },
  {
    employeeId: "TE-064617",
    password: "password",
    employeeName: "Asok Ranjan Chaudhuri",
    role: "USER",
    canLogin: true
  },
  {
    employeeId: "TE-062411",
    password: "password",
    employeeName: "Niren Majumder",
    role: "USER",
    canLogin: true
  }
];

// Helper to initialize data in localStorage
const initializeStorage = () => {
  if (!localStorage.getItem("mock_records")) {
    localStorage.setItem("mock_records", JSON.stringify(INITIAL_RECORDS));
  }
  if (!localStorage.getItem("mock_users")) {
    localStorage.setItem("mock_users", JSON.stringify(INITIAL_USERS));
  }
};

initializeStorage();

// Read operations helper
const getStoredRecords = () => {
  initializeStorage();
  const records = JSON.parse(localStorage.getItem("mock_records") || "[]");
  // Map records to include both nested and flat structures to be 100% compatible with the components
  return records.map(rec => ({
    ...rec,
    instructorName: rec.instructor || rec.instructorName,
    employee: {
      employeeId: rec.employeeId,
      employeeName: rec.employeeName,
      department: {
        departmentName: rec.department
      }
    },
    module: {
      moduleName: rec.trainingModule,
      trainingType: rec.trainingType
    }
  }));
};

const getStoredUsers = () => {
  initializeStorage();
  return JSON.parse(localStorage.getItem("mock_users") || "[]");
};

// Auth helper exports (identical signature to original service)
export const getAuthToken = () => localStorage.getItem("token");
export const getAuthUser = () => {
  const user = localStorage.getItem("user");
  return user ? JSON.parse(user) : null;
};
export const setAuthData = (token, user) => {
  localStorage.setItem("token", token);
  localStorage.setItem("user", JSON.stringify(user));
};
export const clearAuthData = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("user");
};

// Service calls mimicking backend API latency and structure
const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

export const fetchAllRecords = async () => {
  await delay(300); // Simulate network call
  return getStoredRecords();
};

export const fetchEmployeeRecords = async (employeeId) => {
  await delay(250); // Simulate network call
  const records = getStoredRecords();
  return records.filter(rec => rec.employeeId.toLowerCase() === employeeId.toLowerCase());
};

export const saveTrainingRecord = async (formData) => {
  await delay(500); // Simulate network call
  
  if (!formData.employeeName || !formData.employeeId || !formData.department || !formData.trainingModule || !formData.trainingType) {
    throw new Error("Missing required training fields");
  }

  const records = JSON.parse(localStorage.getItem("mock_records") || "[]");

  // Determine certificate file name representation
  let fileName = "";
  if (formData.certificateFile) {
    if (typeof formData.certificateFile === "object" && formData.certificateFile.name) {
      fileName = formData.certificateFile.name;
    } else {
      fileName = String(formData.certificateFile);
    }
  }

  const newRecord = {
    recordId: Date.now(),
    employeeName: formData.employeeName,
    employeeId: formData.employeeId,
    department: formData.department,
    trainingModule: formData.trainingModule,
    trainingType: formData.trainingType,
    status: formData.status || "In Progress",
    issueDate: formData.issueDate || new Date().toISOString().split("T")[0],
    instructor: formData.instructor || "Self-paced",
    certificateNumber: formData.certificateNumber || "Pending",
    certificateFile: fileName,
    remarks: formData.remarks || ""
  };

  records.unshift(newRecord); // Add to the top
  localStorage.setItem("mock_records", JSON.stringify(records));

  // If this employee doesn't exist in our users system yet, make them known so they can be provisioned if desired
  const users = getStoredUsers();
  const exists = users.some(u => u.employeeId.toLowerCase() === formData.employeeId.toLowerCase());
  if (!exists) {
    users.push({
      employeeId: formData.employeeId,
      employeeName: formData.employeeName,
      role: "USER",
      canLogin: false // Must be provisioned first
    });
    localStorage.setItem("mock_users", JSON.stringify(users));
  }

  return "Training record saved successfully";
};

export const loginUser = async (employeeId, password) => {
  await delay(400); // Simulate network call
  
  if (!employeeId || !password) {
    throw new Error("Employee ID and password are required");
  }

  const users = getStoredUsers();
  const user = users.find(u => u.employeeId.toLowerCase() === employeeId.toLowerCase());
  
  if (!user) {
    throw new Error("Invalid Employee ID or password");
  }

  if (!user.canLogin) {
    throw new Error("Login not provisioned for this employee");
  }

  if (user.password !== password) {
    throw new Error("Invalid Employee ID or password");
  }

  const responseData = {
    token: `mock-jwt-token-${user.employeeId}-${Date.now()}`,
    employeeId: user.employeeId,
    employeeName: user.employeeName,
    role: user.role
  };

  setAuthData(responseData.token, {
    employeeId: responseData.employeeId,
    employeeName: responseData.employeeName,
    role: responseData.role
  });

  return responseData;
};

export const provisionUser = async (employeeId, password, role) => {
  await delay(400); // Simulate network call

  if (!employeeId || !password || !role) {
    throw new Error("Employee ID, password, and role are required");
  }

  if (role.toUpperCase() !== "ADMIN" && role.toUpperCase() !== "USER") {
    throw new Error("Role must be either ADMIN or USER");
  }

  const users = getStoredUsers();
  const userIndex = users.findIndex(u => u.employeeId.toLowerCase() === employeeId.toLowerCase());

  if (userIndex === -1) {
    throw new Error("Employee does not exist. Please create the employee first.");
  }

  // Provision credentials
  users[userIndex].password = password;
  users[userIndex].role = role.toUpperCase();
  users[userIndex].canLogin = true;

  localStorage.setItem("mock_users", JSON.stringify(users));
  return "Employee login credentials provisioned successfully";
};

export const deleteTrainingRecord = async (recordId) => {
  // Execute instantly to ensure zero lag, while persisting in localStorage
  const records = JSON.parse(localStorage.getItem("mock_records") || "[]");
  const updated = records.filter(rec => Number(rec.recordId) !== Number(recordId));
  localStorage.setItem("mock_records", JSON.stringify(updated));
  return "Record deleted successfully";
};
