package com.zavier;

public class MailMethod implements PaymentMethod {
    private String address;

    public MailMethod(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public void pay(Paycheck pc) {

    }
}
