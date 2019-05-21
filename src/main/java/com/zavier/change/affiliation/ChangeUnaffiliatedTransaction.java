package com.zavier.change.affiliation;

import com.zavier.GpayrollDatabase;
import com.zavier.affiliation.Affiliation;
import com.zavier.affiliation.NoAffiliation;
import com.zavier.affiliation.UnionAffiliation;
import com.zavier.employee.Employee;

/**
 * 修改为无工会
 */
public class ChangeUnaffiliatedTransaction extends BaseChangeAffiliationTransaction {

    public ChangeUnaffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    protected void recordMembership(Employee e) {
        Affiliation af = e.getAffiliation();
        if (af instanceof UnionAffiliation) {
            UnionAffiliation uf = (UnionAffiliation) af;
            int memberId = uf.getMemberId();
            GpayrollDatabase.removeUnionMember(memberId);
        }
    }

    @Override
    protected Affiliation getAffiliation() {
        return new NoAffiliation();
    }
}
