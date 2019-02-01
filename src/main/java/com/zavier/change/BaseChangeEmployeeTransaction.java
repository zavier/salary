package com.zavier.change;

import com.zavier.employee.Employee;
import com.zavier.GpayrollDatabase;
import com.zavier.Transaction;

public abstract class BaseChangeEmployeeTransaction implements Transaction {
    private int empId;

    public BaseChangeEmployeeTransaction(int empId) {
        this.empId = empId;
    }

    public abstract void change(Employee e);

    @Override
    public void execute() {
        Employee e = GpayrollDatabase.getEmployee(empId);
        if (e != null) {
            change(e);
        }
    }
}
