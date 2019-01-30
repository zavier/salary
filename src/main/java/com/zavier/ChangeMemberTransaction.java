package com.zavier;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {

    private int memberId;
    private double dues;

    public ChangeMemberTransaction(int empId, int memberId, double dues) {
        super(empId);
        this.memberId = memberId;
        this.dues = dues;
    }

    @Override
    protected void recordMemebership(Employee e) {
        GpayrollDatabase.addUnionMember(memberId, e);
    }

    @Override
    protected Affiliation getAffilication() {
        return new UnionAffiliation(memberId, dues);
    }
}
