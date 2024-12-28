package payment;

import payment.exceptions.NotEnoughWalletException;

import java.math.BigDecimal;

public class Wallet {
    BigDecimal amount;

    public Wallet(BigDecimal amount){
        this.amount = amount;
    }

    public void addAmount(BigDecimal toAdd){
        this.amount = this.amount.add(toAdd);
    }

    public void subtractAmount(BigDecimal toSub){
        if(this.amount.compareTo(toSub) >= 0) {
            this.amount = this.amount.subtract(toSub);
        } else {
            throw new NotEnoughWalletException("There is not enough money in the wallet");
        }
    }
}

