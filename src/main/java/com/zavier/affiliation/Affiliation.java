package com.zavier.affiliation;

import com.zavier.Paycheck;

import java.math.BigDecimal;

/**
 * 协会
 */
public interface Affiliation {
    /**
     * 计算应扣除的费用
     * @param pc
     * @return
     */
    BigDecimal calculateDeductions(Paycheck pc);
}
