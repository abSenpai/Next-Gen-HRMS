const API_BASE_URL = "http://localhost:8080/api/certificates";
const AUTH_BASE_URL = "http://localhost:8080/api/auth";

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

const getAuthHeaders = () => {
  const token = getAuthToken();
  return token ? { "Authorization": `Bearer ${token}` } : {};
};

export const fetchAllRecords = async () => {
  const response = await fetch(`${API_BASE_URL}/all`, {
    headers: {
      ...getAuthHeaders()
    }
  });

  if (!response.ok) {
    throw new Error("Failed to fetch records");
  }

  return response.json();
};

export const fetchEmployeeRecords = async (employeeId) => {
  const response = await fetch(`${API_BASE_URL}/employee/${employeeId}`, {
    headers: {
      ...getAuthHeaders()
    }
  });

  if (!response.ok) {
    throw new Error("Failed to fetch records");
  }

  return response.json();
};

export const saveTrainingRecord = async (formData) => {
  const payload = new FormData();

  Object.keys(formData).forEach((key) => {
    payload.append(key, formData[key]);
  });

  const response = await fetch(`${API_BASE_URL}/save`, {
    method: "POST",
    body: payload,
    headers: {
      ...getAuthHeaders()
    }
  });

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.text();
};

export const loginUser = async (employeeId, password) => {
  const response = await fetch(`${AUTH_BASE_URL}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ employeeId, password })
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Login failed");
  }

  const data = await response.json();
  setAuthData(data.token, {
    employeeId: data.employeeId,
    employeeName: data.employeeName,
    role: data.role
  });

  return data;
};

export const provisionUser = async (employeeId, password, role) => {
  const response = await fetch(`${AUTH_BASE_URL}/provision`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      ...getAuthHeaders()
    },
    body: JSON.stringify({ employeeId, password, role })
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Provisioning failed");
  }

  return response.text();
};

