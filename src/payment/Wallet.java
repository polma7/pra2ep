package payment;

import payment.exceptions.InvalidAmountException;
import payment.exceptions.NotEnoughWalletException;

import java.math.BigDecimal;

public class Wallet {
    private BigDecimal amount;

    public Wallet(BigDecimal amount){
        this.amount = amount;
    }

    public void addAmount(BigDecimal toAdd){
        if(toAdd.compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidAmountException("Amount to add cannot be negative");
        }
        this.amount = this.amount.add(toAdd);
    }

    public void subtractAmount(BigDecimal toSub){
        if(toSub.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Amount to subtract cannot be negative");
        }if(this.amount.compareTo(toSub) >= 0) {
            this.amount = this.amount.subtract(toSub);
        } else {
            throw new NotEnoughWalletException("There is not enough money in the wallet");
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

