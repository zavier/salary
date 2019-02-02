package com.zavier.employee;


import com.zavier.GpayrollDatabase;
import com.zavier.Transaction;
import com.zavier.affiliation.NoAffiliation;
import com.zavier.classification.BasePaymentClassification;
import com.zavier.paymethod.HoldMethod;
import com.zavier.paymethod.PaymentMethod;
import com.zavier.payschedule.PaymentSchedule;

/**
 * 增加雇员基类
 */
public abstract class BaseAddEmployeeTransaction implements Transaction {

    private int empId;
    private String name;
    private String address;

    public BaseAddEmployeeTransaction(int empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }

    @Override
    public void execute() {
        BasePaymentClassification pc = getClassification();
        PaymentSchedule ps = getSchedule();
        PaymentMethod pm = new HoldMethod();
        Employee e = new Employee(empId, name, address);
        e.setPaymentClassification(pc);
        e.setPaymentSchedule(ps);
        e.setPaymentMethod(pm);
        e.setAffiliation(new NoAffiliation());
        GpayrollDatabase.addEmployee(empId, e);
    }

    /**
     * 获取支付调度方式
     * @return
     */
    public abstract PaymentSchedule getSchedule();

    /**
     * 获取计薪方式
     * @return
     */
    public abstract BasePaymentClassification getClassification();
}
