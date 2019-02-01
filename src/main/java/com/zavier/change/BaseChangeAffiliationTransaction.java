package com.zavier.change;

import com.zavier.employee.Employee;
import com.zavier.GpayrollDatabase;
import com.zavier.affiliation.Affiliation;

public abstract class BaseChangeAffiliationTransaction extends BaseChangeEmployeeTransaction {

    public BaseChangeAffiliationTransaction(int empId) {
        super(empId);
    }

    protected abstract void recordMemebership(Employee e);
    protected abstract Affiliation getAffilication();

    @Override
    public void change(Employee e) {
        recordMemebership(e);
        e.setAffilication(getAffilication());
        GpayrollDatabase.addEmployee(e.getId(), e);
    }
}
