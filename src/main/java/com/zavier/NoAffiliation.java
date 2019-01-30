package com.zavier;

public class NoAffiliation implements Affiliation{

    @Override
    public double calculateDeductions(Paycheck pc) {
        return 0;
    }
}
