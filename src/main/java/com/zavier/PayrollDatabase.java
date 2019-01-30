package com.zavier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PayrollDatabase {
    private Map<Integer, Employee> itsEmployees;

    public PayrollDatabase() {
        itsEmployees = new HashMap<>();
    }

    public Employee getEmployee(int empid) {
        return itsEmployees.get(empid);
    }

    public void addEmployee(int empid, Employee e) {
        itsEmployees.put(empid, e);
    }

    public void deleteEmployee(int empid) {
        itsEmployees.remove(empid);
    }

    public Set<Integer> getAllEmployeeIds() {
        return itsEmployees.keySet();
    }
}
