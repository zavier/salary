package com.zavier;

import com.zavier.affiliation.Affiliation;
import com.zavier.affiliation.UnionAffiliation;
import com.zavier.employee.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceChargeTransaction implements Transaction {

    private int memberId;
    private LocalDate date;
    private BigDecimal charge;

    public ServiceChargeTransaction(int memberId, LocalDate date, BigDecimal charge) {
        this.memberId = memberId;
        this.date = date;
        this.charge = charge;
    }

    @Override
    public void execute() {
        Employee e = GpayrollDatabase.getUnionMember(memberId);
        Affiliation af = e.getAffiliation();
        if (af instanceof UnionAffiliation) {
            ((UnionAffiliation)af).addServiceCharge(date, charge);
        }
    }
}
