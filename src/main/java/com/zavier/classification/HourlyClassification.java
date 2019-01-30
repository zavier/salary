package com.zavier.classification;

import com.zavier.Paycheck;
import com.zavier.TimeCard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HourlyClassification extends PaymentClassification {

    private double hourlyRate;
    private List<TimeCard> timeCardList;

    public HourlyClassification(double hourlyRate) {
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

    public double getRate() {
        return hourlyRate;
    }

    @Override
    public double calculatePay(Paycheck pc) {

        double totalPay = 0;
        LocalDate payPeriod = pc.getPayDate();
        Map<LocalDate, TimeCard> timeCardMap = new HashMap<>();
        for (TimeCard timeCard : timeCardList) {
            if (isInPayPeriod(timeCard.getDate(), pc)) {
                totalPay += calculatePayForTimeCard(timeCard);
            }
        }
        return totalPay;
    }

    public double calculatePayForTimeCard(TimeCard tc) {
        double hours = tc.getHours();
        double overtime = Math.max(0.0, hours - 8.0);
        double straightTime = hours - overtime;
        return straightTime * hourlyRate + overtime * hourlyRate * 1.5;
    }
}
