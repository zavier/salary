package com.zavier.pay;

import com.zavier.GpayrollDatabase;
import com.zavier.Transaction;
import com.zavier.employee.Employee;
import com.zavier.pay.Paycheck;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 进行支付（如果是支付日期）
 */
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
                    // 生成计算薪水的相关信息
                    Paycheck pc = new Paycheck(e.getPayPeriodStartDate(date), date);
                    paycheckMap.put(empId, pc);

                    e.payday(pc);
                }
            }
        }
    }
}
