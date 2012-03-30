package com.projectsexception.mz.htmlapi.model;

public class CurrencyValue {
    
    public enum Currency {
        EUR,
        USD,
        UNKNOW;
    }
    
    private int value;    
    private Currency currency;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
