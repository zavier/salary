package com.zavier.change.affiliation;

import com.zavier.GpayrollDatabase;
import com.zavier.affiliation.Affiliation;
import com.zavier.affiliation.UnionAffiliation;
import com.zavier.employee.Employee;

import java.math.BigDecimal;

/**
 * 修改工会中的成员信息（即有新成员加入协会等）
 */
public class ChangeMemberTransaction extends BaseChangeAffiliationTransaction {

    private int memberId;
    private BigDecimal dues;

    public ChangeMemberTransaction(int empId, int memberId, BigDecimal dues) {
        super(empId);
        this.memberId = memberId;
        this.dues = dues;
    }

    @Override
    protected void recordMembership(Employee e) {
        GpayrollDatabase.addUnionMember(memberId, e);
    }

    @Override
    protected Affiliation getAffiliation() {
        return new UnionAffiliation(memberId, dues);
    }
}
