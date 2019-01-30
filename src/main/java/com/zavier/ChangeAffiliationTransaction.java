package com.zavier;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {

    public ChangeAffiliationTransaction(int empId) {
        super(empId);
    }

    protected abstract void recordMemebership(Employee e);
    protected abstract Affiliation getAffilication();

    @Override
    public void change(Employee e) {
        recordMemebership(e);
        e.setAffilication(getAffilication());
    }
}
