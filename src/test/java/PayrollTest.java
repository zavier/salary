import com.zavier.*;
import com.zavier.classification.CommissionedClassification;
import com.zavier.classification.HourlyClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.classification.SalariedClassification;
import com.zavier.payschedule.BiWeeklySchedule;
import com.zavier.payschedule.MonthlySchedule;
import com.zavier.payschedule.PaymentSchedule;
import com.zavier.payschedule.WeeklySchedule;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PayrollTest {
    @Test
    public void testAddSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertEquals("Bob", e.getName());
        Assert.assertEquals("Home", e.getAddress());

        PaymentClassification pc = e.getPaymentClassfication();
        Assert.assertTrue(pc instanceof SalariedClassification);
//        Assert.assertEquals(1000.00, pc.calculatePay(), 0.0001);

        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof MonthlySchedule);

        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);

    }

    @Test
    public void testAddHourlyEmployee() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob1", "Home1", 50);
        t.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertEquals("Bob1", e.getName());
        Assert.assertEquals("Home1", e.getAddress());

        PaymentClassification pc = e.getPaymentClassfication();
        Assert.assertTrue(pc instanceof HourlyClassification);

        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof WeeklySchedule);

        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testAddCommissionedEmployee() {
        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob2", "Home2", 2000,  0.2);
        t.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertEquals("Bob2", e.getName());
        Assert.assertEquals("Home2", e.getAddress());

        PaymentClassification pc = e.getPaymentClassfication();
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
            "Lance", "Home", 2500, 3.2);
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
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate date = LocalDate.of(2001, 10, 31);
        TimeCardTransaction tct = new TimeCardTransaction(date,8.0, empId);
        tct.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        HourlyClassification hc = (HourlyClassification) e.getPaymentClassfication();
        Assert.assertNotNull(hc);
        TimeCard tc = hc.getTimeCard(date);
        Assert.assertNotNull(tc);
        Assert.assertEquals(8.0, tc.getHours(), 0.0001);

    }

    @Test
    public void testSalesReceiptTransaction() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill", "Home", 2000, 0.2);
        t.execute();

        LocalDate date = LocalDate.of(2011, 5, 7);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date, 8000, empId);
        srt.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        CommissionedClassification cc = (CommissionedClassification) e.getPaymentClassfication();
        SalesReceipt sr = cc.getSalesReceipt(date);
        Assert.assertNotNull(sr);
        Assert.assertEquals(8000, sr.getAmount(), 0.0001);
    }

    @Test
    public void testAddServiceCharge() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        int memberId = 86;
        UnionAffiliation af = new UnionAffiliation(memberId, 12.5);
        e.setAffilication(af);

        GpayrollDatabase.addUnionMember(memberId, e);
        LocalDate date = LocalDate.of(2001, 11, 1);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, date, 12.95);
        sct.execute();

        ServiceCharge sc = af.getServiceCharge(date);
        Assert.assertNotNull(sc);
        Assert.assertEquals(12.95, sc.getAmount(), 0.0001);

    }

    @Test
    public void testChangeNameTransaction() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
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
            "Lance", "Home", 2500, 3.2);
        t.execute();

        ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, 27.52);
        cht.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getPaymentClassfication();
        Assert.assertNotNull(pc);
        HourlyClassification hc = (HourlyClassification) pc;
        Assert.assertEquals(27.52, hc.getRate(), 0.0001);

        PaymentSchedule ps = e.getPaymentSchedule();
        WeeklySchedule ws = (WeeklySchedule) ps;
        Assert.assertNotNull(ws);

    }

    @Test
    public void testChangeSalariedTransaction() {
        int empId = 3;
        AddHourlyEmployee t = new AddHourlyEmployee(empId,
            "Lance", "Home", 31.2);
        t.execute();

        ChangeSalariedTransaction cht = new ChangeSalariedTransaction(empId, 3000);
        cht.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getPaymentClassfication();
        Assert.assertNotNull(pc);
        SalariedClassification sc = (SalariedClassification) pc;
//        Assert.assertEquals(3000, sc.calculatePay(), 0.0001);

        PaymentSchedule ps = e.getPaymentSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        Assert.assertNotNull(ms);
    }

    @Test
    public void testChangeCommissionedTransaction() {
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home",
            2900);
        t.execute();

        ChangeCommissionedTransaction cht = new ChangeCommissionedTransaction(empId, 3000, 0.4);
        cht.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);

        PaymentClassification pc = e.getPaymentClassfication();
        Assert.assertNotNull(pc);
        CommissionedClassification cc = (CommissionedClassification) pc;
//        Assert.assertEquals(3000, cc.calculatePay(), 0.0001);
        Assert.assertEquals(0.4, cc.getCommissionRate(), 0.0001);

        PaymentSchedule ps = e.getPaymentSchedule();
        BiWeeklySchedule ms = (BiWeeklySchedule) ps;
        Assert.assertNotNull(ms);
    }

    @Test
    public void testChangeDirectTransaction() {
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home",
            2900);
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
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home", 2900);
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
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Lance", "Home", 2900);
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
    public void tesetChangeMemberTransaction() {
        int empId = 2;
        int memberId = 7734;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 99.42);
        cmt.execute();

        Employee e = GpayrollDatabase.getEmployee(empId);
        Affiliation af = e.getAffiliation();
        Assert.assertTrue(af instanceof UnionAffiliation);
        UnionAffiliation uf = (UnionAffiliation) af;
        Assert.assertEquals(99.42, uf.getDues(), 0.0001);
        Employee member = GpayrollDatabase.getUnionMember(memberId);
        Assert.assertTrue(e == member);
    }

    @Test
    public void testPaySingleSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
            1000);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertTrue(pc.getPayDate() == payDate);
        Assert.assertEquals(1000, pc.getGrossPay(), 0.0001);
//        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(0.0, pc.getDeductions(), 0.0001);
        Assert.assertEquals(1000, pc.getNetPay(), 0.0001);
    }

    @Test
    public void testPaySingleSalariedEmployeeOnWrongDate() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
            1000);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 29);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeNoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate date = LocalDate.of(2001, 11, 9);
        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();
        validatePaycheck(pt, empId, date, 0.0);
    }

    private void validatePaycheck(PaydayTransaction pt, int empId, LocalDate date, double pay) {
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(date, pc.getPayPeriodEndDate());
        Assert.assertEquals(pay, pc.getGrossPay(), 0.0001);
//        Assert.assertEquals("Hold", pc.geField("Disposition"));
        Assert.assertEquals(0.0, pc.getDeductions(), 0.0001);
        Assert.assertEquals(pay, pc.getNetPay(), 0.0001);
    }

    @Test
    public void testPaySingleHourlyEmployeeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate date = LocalDate.of(2001, 11, 9);
        TimeCardTransaction tc = new TimeCardTransaction(date, 2.0, empId);
        tc.execute();

        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();

        validatePaycheck(pt, empId, date, 30.5);
    }

    @Test
    public void testPaySingleHourlyEmployeeOvertimeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate date = LocalDate.of(2001, 11, 9);
        TimeCardTransaction tc = new TimeCardTransaction(date, 9.0, empId);
        tc.execute();

        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();
        validatePaycheck(pt, empId, date, (8 + 1.5) * 15.25);
    }

    @Test
    public void testPaysingleHourlyEmployeeOnWrongDate() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate date = LocalDate.of(2001, 11, 8);
        TimeCardTransaction tc = new TimeCardTransaction(date, 9.0, empId);
        tc.execute();

        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeTwoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate date = LocalDate.of(2001, 11, 9);
        TimeCardTransaction tc = new TimeCardTransaction(date, 2.0, empId);
        tc.execute();
        TimeCardTransaction tc1 = new TimeCardTransaction(LocalDate.of(2001, 11, 8),
            5.0, empId);
        tc1.execute();

        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();
        validatePaycheck(pt, empId, date, 7 * 15.25);
    }

    @Test
    public void testPaySingHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        LocalDate deteInPreviousPayPeriod = LocalDate.of(2001, 11, 2);
        TimeCardTransaction tc = new TimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        TimeCardTransaction tc2 = new TimeCardTransaction(deteInPreviousPayPeriod,
            5.0, empId);
        tc2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheck(pt, empId, payDate, 2 * 15.25);
    }

    private void emptyRunningFirstWeek(LocalDate date) {
//        LocalDate date = LocalDate.of(2001, 11, 9);
        PaydayTransaction pt = new PaydayTransaction(date);
        pt.execute();
    }

    @Test
    public void testPaySingleCommissionEmployeeNoSalesReceipts() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill", "Home", 1200, 0.23);
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2001, 11, 9);
        emptyRunningFirstWeek(firstWeekDate);
        // 因为每隔一周运行一次，所以再运行一次
        LocalDate date1 = LocalDate.of(2001, 11, 16);
        PaydayTransaction pt1 = new PaydayTransaction(date1);
        pt1.execute();
        validatePaycheck(pt1, empId, date1, 1200);
    }

    @Test
    public void testPaySingleCommissionEmployeeOneSalesReceipt() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
            "Home", 1200, 0.23);
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2001, 11, 9);
        emptyRunningFirstWeek(firstWeekDate);


        LocalDate date1 = LocalDate.of(2001, 11, 9);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date1, 5000,
            empId);
        srt.execute();

        PaydayTransaction pt = new PaydayTransaction(date1);
        pt.execute();

        validatePaycheck(pt, empId, date1, 1200 + 5000 * 0.23);
    }


    @Test
    public void testPaysingleCommissionEmployeeOnWrongDate() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
            "Home", 1200, 0.23);
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2001, 11, 9);
        emptyRunningFirstWeek(firstWeekDate);

        LocalDate date1 = LocalDate.of(2001, 11, 10);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date1, 5000,
            empId);
        srt.execute();

        PaydayTransaction pt = new PaydayTransaction(date1);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPaySingleCommissionEmployeeTwoSalesReceipts() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
            "Home", 1200, 0.23);
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2001, 11, 2);
        emptyRunningFirstWeek(firstWeekDate);

        LocalDate date1 = LocalDate.of(2001, 11, 9);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date1, 5000,
            empId);
        srt.execute();
        SalesReceiptTransaction srt1 = new SalesReceiptTransaction(
            LocalDate.of(2001, 11, 8), 3000,
            empId);
        srt1.execute();

        PaydayTransaction pt = new PaydayTransaction(date1);
        pt.execute();
        validatePaycheck(pt, empId, date1, 1200 + 8000 * 0.23);
    }

    @Test
    public void testPaySingCommissionEmployeeWithSalesReceiptsSpanningTwoPayPeriods() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
            "Home", 1200, 0.23);
        t.execute();

        LocalDate firstWeekDate = LocalDate.of(2001, 11, 2);
        emptyRunningFirstWeek(firstWeekDate);

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        LocalDate deteInPreviousPayPeriod = LocalDate.of(2001, 10, 26);
        SalesReceiptTransaction srt1 = new SalesReceiptTransaction(payDate, 2500, empId);
        srt1.execute();
        SalesReceiptTransaction srt2 = new SalesReceiptTransaction(deteInPreviousPayPeriod,
            1800, empId);
        srt2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheck(pt, empId, payDate, 1200 + 2500 * 0.23);
    }

    @Test
    public void testHourlyUnionMemberServiceCharge() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();

        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId,
            memberId, 9.42);
        cmt.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId,
            payDate, 19.42);
        sct.execute();

        TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertEquals(pc.getPayPeriodEndDate(), payDate);
        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), 0.0001);
//        Assert.assertEquals("Hold", pc.getField("Dispositon"));
        Assert.assertEquals(9.42 + 19.42, pc.getDeductions(), 0.0001);
        Assert.assertEquals((8*15.24)-(9.42+19.42), pc.getNetPay(), 0.0001);
    }

    @Test
    public void testServiceChargesSpanningMultiplePayPeriods() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();

        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId,
            memberId, 9.42);
        cmt.execute();

        LocalDate earlyDate = LocalDate.of(2001, 11, 2);
        LocalDate payDate = LocalDate.of(2001, 11, 9);
        LocalDate lateDate = LocalDate.of(2001, 11, 16);

        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId,
            payDate, 19.42);
        sct.execute();
        ServiceChargeTransaction sctEarly = new ServiceChargeTransaction(memberId,
            earlyDate, 100);
        sctEarly.execute();
        ServiceChargeTransaction sctLate = new ServiceChargeTransaction(memberId,
            lateDate, 200);
        sctLate.execute();

        TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertEquals(pc.getPayPeriodEndDate(), payDate);
        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), 0.0001);
//        Assert.assertEquals("Hold", pc.geField("Disposition"));
        Assert.assertEquals(9.42 + 19.42, pc.getDeductions(), 0.0001);
        Assert.assertEquals((8 * 15.24) - (9.42 + 19.42), pc.getNetPay(), 0.0001);

    }
}
