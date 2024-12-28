package payment;

import data.ServiceID;
import micromobility.Driver;

import java.awt.dnd.DragGestureEvent;
import java.math.BigDecimal;

public class Payment {
    private Driver driver;
    private ServiceID service;
    private char payMethod;
    private BigDecimal importe;

    public Payment(Driver driver, ServiceID service, char payMethod, BigDecimal importe){
        this.driver = driver;
        this.service = service;
        this.payMethod = payMethod;
        this.importe = importe;
    }

    public void deductImportFromWallet(){
        WalletPayment payment = new WalletPayment(this.driver, this.service, this.payMethod, this.importe);
        payment.deductImport();
    }
}
