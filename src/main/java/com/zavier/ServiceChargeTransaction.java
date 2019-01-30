package com.zavier;

import java.time.LocalDate;

public class ServiceChargeTransaction implements Transaction {

    private int memberId;
    private LocalDate date;
    private double charge;

    public ServiceChargeTransaction(int memberId, LocalDate date, double charge) {
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
