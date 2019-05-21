package com.zavier;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 服务费用
 */
public class ServiceCharge {
    private LocalDate date;
    private BigDecimal charge;

    public ServiceCharge(LocalDate date, BigDecimal charge) {
        this.date = date;
        this.charge = charge;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return charge;
    }
}
