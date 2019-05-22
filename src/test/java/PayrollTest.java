import com.zavier.*;
import com.zavier.affiliation.Affiliation;
import com.zavier.affiliation.UnionAffiliation;
import com.zavier.change.ChangeNameTransaction;
import com.zavier.change.affiliation.ChangeMemberTransaction;
import com.zavier.change.classification.ChangeCommissionedTransaction;
import com.zavier.change.classification.ChangeHourlyTransaction;
import com.zavier.change.classification.ChangeSalariedTransaction;
import com.zavier.change.paymethod.ChangeDirectTransaction;
import com.zavier.change.paymethod.ChangeHoldTransaction;
import com.zavier.change.paymethod.ChangeMailTransaction;
import com.zavier.classification.*;
import com.zavier.employee.*;
import com.zavier.pay.Paycheck;
import com.zavier.pay.PaydayTransaction;
import com.zavier.paymethod.DirectMethod;
import com.zavier.paymethod.HoldMethod;
import com.zavier.paymethod.MailMethod;
import com.zavier.paymethod.PaymentMethod;
import com.zavier.payschedule.BiWeeklySchedule;
import com.zavier.payschedule.MonthlySchedule;
import com.zavier.payschedule.PaymentSchedule;
import com.zavier.payschedule.WeeklySchedule;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PayrollTest {
    @Test
    public void testAddSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", new BigDecimal("1000"));
        t.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertEquals("Bob", e.getName());
        Assert.assertEquals("Home", e.getAddress());

        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertTrue(pc instanceof SalariedClassification);

        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof MonthlySchedule);

        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);

    }

    @Test
    public void testAddHourlyEmployee() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob1", "Home1", new BigDecimal("50"));
        t.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertEquals("Bob1", e.getName());
        Assert.assertEquals("Home1", e.getAddress());

        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertTrue(pc instanceof HourlyClassification);

        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof WeeklySchedule);

        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testAddCommissionedEmployee() {
        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob2", "Home2", new BigDecimal("2000"),  new BigDecimal("0.2"));
        t.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertEquals("Bob2", e.getName());
        Assert.assertEquals("Home2", e.getAddress());

        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertTrue(pc instanceof CommissionedClassification);

        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof BiWeeklySchedule);

        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testDeleteEmployee() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId,
            "Lance", "Home", new BigDecimal("2500"), new BigDecimal("3.2"));
        t.execute();
        {
            Employee e = GpayrollDatabase.getEmployee(empId);
            Assert.assertNotNull(e);
        }
        DeleteEmployeeTransaction dt = new DeleteEmployeeTransaction(empId);
        dt.execute();
        {
            Employee e = GpayrollDatabase.getEmployee(empId);
            Assert.assertNull(e);
        }
    }

    @Test
    public void testTimeCardTransaction() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        LocalDate date = LocalDate.of(2001, 10, 31);
        TimeCardTransaction tct = new TimeCardTransaction(date,8.0, empId);
        tct.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        HourlyClassification hc = (HourlyClassification) e.getPaymentClassification();
        Assert.assertNotNull(hc);
        TimeCard tc = hc.getTimeCard(date);
        Assert.assertNotNull(tc);
        Assert.assertEquals(8.0, tc.getHours(), 0.0001);

    }

    @Test
    public void testSalesReceiptTransaction() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill", "Home", new BigDecimal("2000"), new BigDecimal("0.2"));
        t.execute();

        LocalDate date = LocalDate.of(2011, 5, 7);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date, new BigDecimal("8000"), empId);
        srt.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        CommissionedClassification cc = (CommissionedClassification) e.getPaymentClassification();
        SalesReceipt sr = cc.getSalesReceipt(date);
        Assert.assertNotNull(sr);
        Assert.assertEquals(new BigDecimal("8000"), sr.getAmount());
    }

    @Test
    public void testAddServiceCharge() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        int memberId = 86;
        UnionAffiliation af = new UnionAffiliation(memberId, new BigDecimal("12.5"));
        e.setAffiliation(af);

        GpayrollDatabase.addUnionMember(memberId, e);
        LocalDate date = LocalDate.of(2001, 11, 1);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, date, new BigDecimal("12.95"));
        sct.execute();

        ServiceCharge sc = af.getServiceCharge(date);
        Assert.assertNotNull(sc);
        Assert.assertEquals(new BigDecimal("12.95"), sc.getAmount());

    }

    @Test
    public void testChangeNameTransaction() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Bob");
        cnt.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        Assert.assertEquals("Bob", e.getName());
    }

    @Test
    public void testChangeHourlyTransaction() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId,
            "Lance", "Home", new BigDecimal("2500"), new BigDecimal("3.2"));
        t.execute();

        ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, new BigDecimal("27.52"));
        cht.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
        HourlyClassification hc = (HourlyClassification) pc;
        Assert.assertEquals(new BigDecimal("27.52"), hc.getRate());

        PaymentSchedule ps = e.getPaymentSchedule();
        WeeklySchedule ws = (WeeklySchedule) ps;
        Assert.assertNotNull(ws);

    }

    @Test
    public void testChangeSalariedTransaction() {
        int empId = 3;
        AddHourlyEmployee t = new AddHourlyEmployee(empId,
            "Lance", "Home", new BigDecimal("31.2"));
        t.execute();

        ChangeSalariedTransaction cht = new ChangeSalariedTransaction(empId, new BigDecimal("3000"));
        cht.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
//        SalariedClassification sc = (SalariedClassification) pc;
//        Assert.assertEquals(3000, sc.calculatePay(), 0.0001);

        PaymentSchedule ps = e.getPaymentSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        Assert.assertNotNull(ms);
    }

    @Test
    public void testChangeCommissionedTransaction() {
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home",
                new BigDecimal("2900"));
        t.execute();

        ChangeCommissionedTransaction cht = new ChangeCommissionedTransaction(empId, new BigDecimal("3000"),
                new BigDecimal("0.4"));
        cht.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
        CommissionedClassification cc = (CommissionedClassification) pc;
//        Assert.assertEquals(3000, cc.calculatePay(), 0.0001);
        Assert.assertEquals(new BigDecimal("0.4"), cc.getCommissionRate());

        PaymentSchedule ps = e.getPaymentSchedule();
        BiWeeklySchedule ms = (BiWeeklySchedule) ps;
        Assert.assertNotNull(ms);
    }

    @Test
    public void testChangeDirectTransaction() {
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home",
                new BigDecimal("2900"));
        t.execute();

        ChangeDirectTransaction cdt = new ChangeDirectTransaction(empId, "bank1", "11232");
        cdt.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentMethod method = e.getPaymentMethod();
        Assert.assertNotNull(method);
        Assert.assertTrue(method instanceof DirectMethod);
        DirectMethod dm = (DirectMethod) method;
        Assert.assertEquals("bank1", dm.getBank());
        Assert.assertEquals("11232", dm.getAccount());
    }

    @Test
    public void testChangeHoldTransaction() {
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home", new BigDecimal("2900"));
        t.execute();

        ChangeHoldTransaction cht = new ChangeHoldTransaction(empId);
        cht.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentMethod method = e.getPaymentMethod();
        Assert.assertNotNull(method);
        Assert.assertTrue(method instanceof HoldMethod);
    }

    @Test
    public void testChangeMailTransaction() {
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home", new BigDecimal("2900"));
        t.execute();

        ChangeMailTransaction cht = new ChangeMailTransaction(empId, "Home1");
        cht.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentMethod method = e.getPaymentMethod();
        Assert.assertNotNull(method);
        Assert.assertTrue(method instanceof MailMethod);
        MailMethod mm = (MailMethod) method;
        Assert.assertEquals("Home1", mm.getAddress());
    }

    @Test
    public void testChangeMemberTransaction() {
        int empId = 2;
        int memberId = 7734;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, new BigDecimal("99.42"));
        cmt.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Affiliation af = e.getAffiliation();
        Assert.assertTrue(af instanceof UnionAffiliation);
        UnionAffiliation uf = (UnionAffiliation) af;
        Assert.assertEquals(new BigDecimal("99.42"), uf.getDues());
        Employee member = GpayrollDatabase.getUnionMember(memberId);
        Assert.assertSame(e, member);
    }

    /**
     * 月薪员工，月底结算薪水
     */
    @Test
    public void testPaySingleSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
                new BigDecimal("1000"));
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 1, 31);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertEquals(pc.getPayDate(), payDate);
        Assert.assertEquals(new BigDecimal("1000"), pc.getGrossPay());
        Assert.assertEquals(BigDecimal.ZERO, pc.getDeductions());
        Assert.assertEquals(new BigDecimal("1000"), pc.getNetPay());
    }

    /**
     * 月薪员工，未到月底时没有需要支付的信息
     */
    @Test
    public void testPaySingleSalariedEmployeeOnWrongDate() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
                new BigDecimal("1000"));
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 1, 29);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    /**
     * 时薪员工，在发薪日（周五）发薪，但是没有工作时长时薪水为0
     */
    @Test
    public void testPaySingleHourlyEmployeeNoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();
        // 日期时周五，在周五进行支付
        LocalDate date = LocalDate.of(2019, 5, 24);
        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();
        validatePaycheck(pt, empId, date, BigDecimal.ZERO);
    }

    private void validatePaycheck(PaydayTransaction pt, int empId, LocalDate date, BigDecimal pay) {
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(date, pc.getPayPeriodEndDate());
        Assert.assertEquals(pay.compareTo(pc.getGrossPay()), 0);
        Assert.assertEquals(BigDecimal.ZERO, pc.getDeductions());
        Assert.assertEquals(0, pay.compareTo(pc.getNetPay()));
    }

    /**
     * 时薪员工，只有一条工时记录卡记录，在周五进行发薪
     */
    @Test
    public void testPaySingleHourlyEmployeeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        LocalDate date = LocalDate.of(2019, 5, 24);
        TimeCardTransaction tc = new TimeCardTransaction(date, 2.0, empId);
        tc.execute();

        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();

        validatePaycheck(pt, empId, date, new BigDecimal("30.5"));
    }

    /**
     * 时薪员工工作时长超过8小时，进行发薪
     */
    @Test
    public void testPaySingleHourlyEmployeeOvertimeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        LocalDate date = LocalDate.of(2019, 5, 24);
        TimeCardTransaction tc = new TimeCardTransaction(date, 9.0, empId);
        tc.execute();

        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();

        BigDecimal res = new BigDecimal("8").add(new BigDecimal("1.5")).multiply(new BigDecimal("15.25"));
        validatePaycheck(pt, empId, date, res);
    }

    /**
     * 时薪员工，在非发薪日允许发薪程序，不发放薪水
     */
    @Test
    public void testPaySingleHourlyEmployeeOnWrongDate() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        // 非周五
        LocalDate date = LocalDate.of(2019, 5, 23);
        TimeCardTransaction tc = new TimeCardTransaction(date, 9.0, empId);
        tc.execute();

        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    /**
     * 时薪员工，有两条记录，进行发薪
     */
    @Test
    public void testPaySingleHourlyEmployeeTwoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        LocalDate date = LocalDate.of(2019, 5, 24);
        TimeCardTransaction tc = new TimeCardTransaction(date, 2.0, empId);
        tc.execute();
        TimeCardTransaction tc1 = new TimeCardTransaction(LocalDate.of(2019, 5, 24),
                5.0, empId);
        tc1.execute();

        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();
        validatePaycheck(pt, empId, date, new BigDecimal("7").multiply(new BigDecimal("15.25")));
    }

    /**
     * 时薪员工，只计算发放发薪周期内的薪水
     */
    @Test
    public void testPaySingHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.25"));
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 5, 24);
        LocalDate dateInPreviousPayPeriod = LocalDate.of(2019, 5, 17);
        TimeCardTransaction tc = new TimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        TimeCardTransaction tc2 = new TimeCardTransaction(dateInPreviousPayPeriod,
                5.0, empId);
        tc2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheck(pt, empId, payDate, new BigDecimal("2").multiply(new BigDecimal("15.25")));
    }

    private void emptyRunningFirstWeek(LocalDate date) {
        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();
    }

    /**
     * 销售雇员，没有销售记录时只发放基本薪水
     */
    @Test
    public void testPaySingleCommissionEmployeeNoSalesReceipts() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill", "Home",
                new BigDecimal("1200"), new BigDecimal("0.23"));
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2019, 5, 17);
        emptyRunningFirstWeek(firstWeekDate);
        // 因为每隔一周运行一次，所以再运行一次记录数据
        LocalDate date1 = LocalDate.of(2019, 5, 24);
        PaydayTransaction pt1 = new PaydayTransaction(date1);
        pt1.execute();
        validatePaycheck(pt1, empId, date1, new BigDecimal("1200"));
    }

    /**
     * 只有一条销售记录的销售雇员发放薪水
     */
    @Test
    public void testPaySingleCommissionEmployeeOneSalesReceipt() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
                "Home", new BigDecimal("1200"), new BigDecimal("0.23"));
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2019, 5, 17);
        emptyRunningFirstWeek(firstWeekDate);


        LocalDate date1 = LocalDate.of(2019, 5, 24);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date1, new BigDecimal("5000"),
                empId);
        srt.execute();

        PaydayTransaction pt = new PaydayTransaction(date1);
        pt.execute();
        BigDecimal res = new BigDecimal("1200").add(new BigDecimal("5000").multiply(new BigDecimal("0.23")));
        validatePaycheck(pt, empId, date1, res);
    }

    /**
     * 销售雇员，非发薪日运行程序不发放薪水
     */
    @Test
    public void testPaySingleCommissionEmployeeOnWrongDate() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
            "Home", new BigDecimal("1200"), new BigDecimal("0.23"));
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2019, 5, 24);
        emptyRunningFirstWeek(firstWeekDate);

        LocalDate date1 = LocalDate.of(2019, 5, 25);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date1, new BigDecimal("5000"),
            empId);
        srt.execute();

        PaydayTransaction pt = new PaydayTransaction(date1);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    /**
     * 销售员工有两条发薪记录发薪
     */
    @Test
    public void testPaySingleCommissionEmployeeTwoSalesReceipts() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
                "Home", new BigDecimal("1200"), new BigDecimal("0.23"));
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2019, 5, 17);
        emptyRunningFirstWeek(firstWeekDate);

        LocalDate date1 = LocalDate.of(2019, 5, 24);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date1, new BigDecimal("5000"), empId);
        srt.execute();

        SalesReceiptTransaction srt1 = new SalesReceiptTransaction(
                LocalDate.of(2019, 5, 23), new BigDecimal("3000"), empId);
        srt1.execute();

        PaydayTransaction pt = new PaydayTransaction(date1);
        pt.execute();

        BigDecimal res = new BigDecimal("1200").add(new BigDecimal("8000").multiply(new BigDecimal("0.23")));
        validatePaycheck(pt, empId, date1, res);
    }

    /**
     * 销售雇员，有两张销售凭条，结算时只结算在有效范围内的
     */
    @Test
    public void testPaySingCommissionEmployeeWithSalesReceiptsSpanningTwoPayPeriods() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
                "Home", new BigDecimal("1200"), new BigDecimal("0.23"));
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2019, 5, 17);
        emptyRunningFirstWeek(firstWeekDate);

        LocalDate payDate = LocalDate.of(2019, 5, 24);
        SalesReceiptTransaction srt1 = new SalesReceiptTransaction(payDate, new BigDecimal("2500"), empId);
        srt1.execute();

        LocalDate dateInPreviousPayPeriod = LocalDate.of(2019, 5, 10);
        SalesReceiptTransaction srt2 = new SalesReceiptTransaction(dateInPreviousPayPeriod,
                new BigDecimal("1800"), empId);
        srt2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        BigDecimal res = new BigDecimal("1200").add(new BigDecimal("2500").multiply(new BigDecimal("0.23")));
        validatePaycheck(pt, empId, payDate, res);
    }

    /**
     * 时薪雇员正确结算费用，扣除服务费
     */
    @Test
    public void testHourlyUnionMemberServiceCharge() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.24"));
        t.execute();

        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId,
                memberId, new BigDecimal("9.42"));
        cmt.execute();

        LocalDate payDate = LocalDate.of(2019, 5, 24);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId,
                payDate, new BigDecimal("19.42"));
        sct.execute();

        TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertEquals(pc.getPayPeriodEndDate(), payDate);
        Assert.assertEquals(new BigDecimal("8").multiply(new BigDecimal("15.24")).compareTo(pc.getGrossPay()), 0);
        Assert.assertEquals(new BigDecimal("9.42").add(new BigDecimal("19.42")).compareTo(pc.getDeductions()), 0);
        Assert.assertEquals(BigDecimal.valueOf((8*15.24)-(9.42+19.42)).compareTo(pc.getNetPay()), 0);
    }

    @Test
    public void testServiceChargesSpanningMultiplePayPeriods() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", new BigDecimal("15.24"));
        t.execute();

        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId,
            memberId, new BigDecimal("9.42"));
        cmt.execute();


        // 结算日期
        LocalDate payDate = LocalDate.of(2019, 5, 17);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId,
            payDate, new BigDecimal("19.42"));
        sct.execute();

        // 不在此次结算周期内的服务费
        LocalDate earlyDate = LocalDate.of(2019, 5, 10);
        ServiceChargeTransaction sctEarly = new ServiceChargeTransaction(memberId,
            earlyDate, new BigDecimal("100"));
        sctEarly.execute();

        // 不在此次结算周期内的服务费
        LocalDate lateDate = LocalDate.of(2019, 5, 24);
        ServiceChargeTransaction sctLate = new ServiceChargeTransaction(memberId,
            lateDate, new BigDecimal("200"));
        sctLate.execute();

        TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertEquals(pc.getPayPeriodEndDate(), payDate);
        Assert.assertSame(new BigDecimal("8").multiply(new BigDecimal("15.24")).compareTo(pc.getGrossPay()), 0);
        Assert.assertEquals(new BigDecimal("9.42").add(new BigDecimal("19.42")), pc.getDeductions());
        Assert.assertSame(BigDecimal.valueOf((8 * 15.24) - (9.42 + 19.42)).compareTo(pc.getNetPay()), 0);

    }
}
