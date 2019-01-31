package com.zavier.affiliation;

import com.zavier.Paycheck;
import com.zavier.affiliation.Affiliation;

import java.math.BigDecimal;

/**
 * 无-协会
 */
public class NoAffiliation implements Affiliation {

    @Override
    public BigDecimal calculateDeductions(Paycheck pc) {
        return BigDecimal.ZERO;
    }
}
