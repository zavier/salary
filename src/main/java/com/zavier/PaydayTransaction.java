package com.zavier;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PaydayTransaction implements Transaction {

    private LocalDate date;

    private Map<Integer, Paycheck> paycheckMap = new HashMap<>();

    public PaydayTransaction(LocalDate date) {
        this.date = date;
    }

    public Paycheck getPaycheck(int empId) {
        return paycheckMap.get(empId);
    }

    @Override
    public void execute() {
        Set<Integer> empIds = GpayrollDatabase.getAllEmployeeIds();
        for (Integer empId : empIds) {
            Employee e = GpayrollDatabase.getEmployee(empId);
            if (e != null) {
                if (e.isPayDate(date)) {
                    Paycheck pc = new Paycheck(e.getPayPeriodStartDate(date), date);
                    paycheckMap.put(empId, pc);
                    e.payday(pc);
                }
            }
        }
    }
}
