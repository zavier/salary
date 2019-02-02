package com.zavier.change;

import com.zavier.employee.Employee;

/**
 * 修改雇员姓名
 */
public class ChangeNameTransaction extends BaseChangeEmployeeTransaction {

    private String name;

    public ChangeNameTransaction(int empId, String name) {
        super(empId);
        this.name = name;
    }

    @Override
    public void change(Employee e) {
        e.setName(name);
    }
}
