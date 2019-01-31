package com.zavier.classification;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售凭条
 */
public class SalesReceipt {
    private BigDecimal amount;
    private LocalDate date;

    public SalesReceipt(BigDecimal amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
