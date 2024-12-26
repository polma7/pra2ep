package micromobility;

import com.sun.jdi.PathSearchingVirtualMachine;
import data.UserAccount;

public class Driver {
    private String name;
    private String email;
    private String telNumber;
    private String bankAccount;
    private UserAccount userAccount;
    PMVehicle tripVehicle;

    public Driver(String telNumber, String bankAccount, UserAccount account){
        this.name = account.getUsername();
        this.email = account.getMail();
        this.telNumber = telNumber;
        this.bankAccount = bankAccount;
        this.userAccount = account;
    }

    public void setTripVehicle(PMVehicle vehicle){
        this.tripVehicle = vehicle;
    }

    public void unsetTripVehicle(){
        this.tripVehicle = null;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
