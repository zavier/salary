package com.zavier.change;

import com.zavier.GpayrollDatabase;
import com.zavier.Transaction;
import com.zavier.employee.Employee;

/**
 * 改变雇员信息抽象类
 */
public abstract class BaseChangeEmployeeTransaction implements Transaction {
    private int empId;

    public BaseChangeEmployeeTransaction(int empId) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = GpayrollDatabase.getEmployee(empId);
        if (e != null) {
            change(e);
        }
    }

    protected abstract void change(Employee e);
}
