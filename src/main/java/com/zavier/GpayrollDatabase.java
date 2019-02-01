package com.zavier;

import com.zavier.employee.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GpayrollDatabase {
    private static PayrollDatabase payrollDatabase = new PayrollDatabase();

    private static Map<Integer, Employee> unionMemberMap = new HashMap<>();

    public static void addEmployee(int empId, Employee e) {
        payrollDatabase.addEmployee(empId, e);
    }

    public static Employee getEmployee(int empId) {
        return payrollDatabase.getEmployee(empId);
    }

    public static void setEmployee(Employee e) {
        payrollDatabase.setEmployee(e);
    }

    public static void deleteEmployee(int empId) {
        payrollDatabase.deleteEmployee(empId);
    }

    public static void addUnionMember(int memberId, Employee e) {
        unionMemberMap.put(memberId, e);
    }

    public static Employee getUnionMember(int memberId) {
        return unionMemberMap.get(memberId);
    }

    public static void removeUnionMember(int memberId) {
        unionMemberMap.remove(memberId);
    }

    public static Set<Integer> getAllEmployeeIds() {
        return payrollDatabase.getAllEmployeeIds();
    }
}
