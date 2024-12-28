package micromobility;

import com.sun.jdi.PathSearchingVirtualMachine;
import data.UserAccount;
import payment.Wallet;

public class Driver {
    private String name;
    private String email;
    private String telNumber;
    private Wallet wallet;
    private UserAccount userAccount;
    private PMVehicle tripVehicle;
    private char payMethod = 'w';

    public Driver(String telNumber, Wallet wallet, UserAccount account){
        this.name = account.getUsername();
        this.email = account.getMail();
        this.telNumber = telNumber;
        this.wallet = wallet;
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
    public char getPayMethod(){
        return this.payMethod;
    }
    public Wallet getWallet(){
        return this.wallet;
    }
}
