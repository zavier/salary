package com.zavier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GpayrollDatabase {
    private static PayrollDatabase payrollDatabase = new PayrollDatabase();

    private static Map<Integer, Employee> unionMemberMap = new HashMap<>();

    public static void addEmployee(int empid, Employee e) {
        payrollDatabase.addEmployee(empid, e);
    }

    public static Employee getEmployee(int empid) {
        return payrollDatabase.getEmployee(empid);
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
