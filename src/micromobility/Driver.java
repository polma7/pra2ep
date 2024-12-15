package micromobility;

import com.sun.jdi.PathSearchingVirtualMachine;
import data.UserAccount;

public class Driver {
    private String name;
    private String email;
    private String telNumber;
    private PaymentMethods methods;
    private String bankAccount;
    private UserAccount userAccount;
    PMVehicle tripVehicle;

    public Driver(String name, String email, String telNumber, PaymentMethods methods, String bankAccount, UserAccount account){
        this.name = account.getUsername();
        this.email = account.getMail();
        this.telNumber = telNumber;
        this.methods = methods;
        this.bankAccount = bankAccount;
        this.userAccount = account;
    }

    public void setTripVehicle(PMVehicle vehicle){
        this.tripVehicle = vehicle;
    }

    public void unsetTripVehicle(){
        this.tripVehicle = null;
    }
}
