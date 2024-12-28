package payment;

import data.ServiceID;
import micromobility.Driver;

import java.math.BigDecimal;

public class WalletPayment extends Payment{
    private Driver driver;
    private ServiceID service;
    private char payMethod;
    private BigDecimal importe;

    public WalletPayment(Driver driver, ServiceID service, char payMethod, BigDecimal importe) {
        super(driver, service, payMethod, importe);
        this.driver = driver;
        this.service = service;
        this.payMethod = payMethod;
        this.importe = importe;
    }

    public void deductImport(){
        this.driver.getWallet().subtractAmount(importe);
    }
}
