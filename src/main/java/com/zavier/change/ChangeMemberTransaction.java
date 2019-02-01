package com.zavier.change;

import com.zavier.employee.Employee;
import com.zavier.GpayrollDatabase;
import com.zavier.affiliation.Affiliation;
import com.zavier.affiliation.UnionAffiliation;

import java.math.BigDecimal;

public class ChangeMemberTransaction extends BaseChangeAffiliationTransaction {

    private int memberId;
    private BigDecimal dues;

    public ChangeMemberTransaction(int empId, int memberId, BigDecimal dues) {
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
