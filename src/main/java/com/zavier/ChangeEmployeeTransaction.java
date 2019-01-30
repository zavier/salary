package com.zavier;

public abstract class ChangeEmployeeTransaction implements Transaction{
    private int empId;

    public ChangeEmployeeTransaction(int empId) {
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
