package com.zavier;

public class DeleteEmployeeTransaction implements Transaction {

    private int itsEmpid;

    public DeleteEmployeeTransaction(int itsEmpid) {
        this.itsEmpid = itsEmpid;
    }

    @Override
    public void execute() {
        GpayrollDatabase.deleteEmployee(itsEmpid);
    }
}
