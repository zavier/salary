package com.zavier;

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
