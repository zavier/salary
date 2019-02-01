package com.zavier;

import com.zavier.employee.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PayrollDatabase {
    private Map<Integer, Employee> itsEmployees;

    public PayrollDatabase() {
        itsEmployees = new HashMap<>();
    }

    public Employee getEmployee(int empId) {
        return itsEmployees.get(empId);
    }

    public void addEmployee(int empId, Employee e) {
        itsEmployees.put(empId, e);
    }

    public void setEmployee(Employee e) {
        Integer id = e.getId();
        if (id != null) {
            itsEmployees.put(id, e);
        }
    }

    public void deleteEmployee(int empId) {
        itsEmployees.remove(empId);
    }

    public Set<Integer> getAllEmployeeIds() {
        return itsEmployees.keySet();
    }
}
