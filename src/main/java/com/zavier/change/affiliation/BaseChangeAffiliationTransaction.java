package com.zavier.change.affiliation;

import com.zavier.GpayrollDatabase;
import com.zavier.affiliation.Affiliation;
import com.zavier.change.BaseChangeEmployeeTransaction;
import com.zavier.employee.Employee;

/**
 * 修改参加的工会
 */
public abstract class BaseChangeAffiliationTransaction extends BaseChangeEmployeeTransaction {

    public BaseChangeAffiliationTransaction(int empId) {
        super(empId);
    }

    @Override
    public void change(Employee e) {
        recordMembership(e);
        e.setAffiliation(getAffiliation());
        GpayrollDatabase.addEmployee(e.getId(), e);
    }

    /**
     * 记录工会会员与雇员关系
     *
     * @param e
     */
    protected abstract void recordMembership(Employee e);

    /**
     * 获取新加入的工会，用来绑定成员
     *
     * @return
     */
    protected abstract Affiliation getAffiliation();
}
