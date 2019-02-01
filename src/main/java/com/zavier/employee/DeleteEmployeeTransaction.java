package com.zavier.employee;

import com.zavier.GpayrollDatabase;
import com.zavier.Transaction;

/**
 * 删除雇员
 */
public class DeleteEmployeeTransaction implements Transaction {

    private int empId;

    public DeleteEmployeeTransaction(int empId) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        GpayrollDatabase.deleteEmployee(empId);
    }
}
