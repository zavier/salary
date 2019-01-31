package com.zavier.classification;

import com.zavier.Paycheck;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时薪结薪
 */
public class HourlyClassification extends PaymentClassification {

    private BigDecimal hourlyRate;
    private List<TimeCard> timeCardList;

    public HourlyClassification(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
        timeCardList = new ArrayList<>();
    }

    public void addTimeCard(TimeCard timeCard) {
        timeCardList.add(timeCard);
    }

    public TimeCard getTimeCard(LocalDate date) {
        for (TimeCard timeCard : timeCardList) {
            if (timeCard.getDate().equals(date)) {
                return timeCard;
            }
        }
        return null;
    }

    public BigDecimal getRate() {
        return hourlyRate;
    }

    @Override
    public BigDecimal calculatePay(Paycheck pc) {

        BigDecimal totalPay = BigDecimal.ZERO;
        LocalDate payPeriod = pc.getPayDate();
        Map<LocalDate, TimeCard> timeCardMap = new HashMap<>();
        for (TimeCard timeCard : timeCardList) {
            if (isInPayPeriod(timeCard.getDate(), pc)) {
                totalPay = totalPay.add(calculatePayForTimeCard(timeCard));
            }
        }
        return totalPay;
    }

    public BigDecimal calculatePayForTimeCard(TimeCard tc) {
        double hours = tc.getHours();
        double overtime = Math.max(0.0, hours - 8.0);
        double straightTime = hours - overtime;
        return hourlyRate.multiply(BigDecimal.valueOf(straightTime))
                .add(hourlyRate.multiply(BigDecimal.valueOf(overtime)).multiply(new BigDecimal("1.5")));
    }
}
