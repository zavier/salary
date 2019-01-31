package com.zavier;

import com.zavier.affiliation.Affiliation;
import com.zavier.affiliation.NoAffiliation;
import com.zavier.affiliation.UnionAffiliation;

public class ChangeUnffiliatedTransaction extends ChangeAffiliationTransaction {

    public ChangeUnffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    protected void recordMemebership(Employee e) {
        Affiliation af = e.getAffiliation();
        if (af instanceof UnionAffiliation) {
            UnionAffiliation uf = (UnionAffiliation) af;
            int memberId = uf.getMemberId();
            GpayrollDatabase.removeUnionMember(memberId);
        }
    }

    @Override
    protected Affiliation getAffilication() {
        return new NoAffiliation();
    }
}
