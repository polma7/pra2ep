package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import micromobility.JourneyService;
import micromobility.PMVState;
import micromobility.PMVehicle;
import micromobility.Station;
import micromobility.exceptions.InvalidPairingArgsException;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.PairingNotFoundException;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerClass implements Server{
    private ArrayList<Station> stations;
    private HashMap<Station, PMVehicle> vehicles;

    public ServerClass(){

    }

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        for (Map.Entry<Station, PMVehicle> entry : vehicles.entrySet()) {
            Station station = entry.getKey();
            PMVehicle vehicle = entry.getValue();

            if (vehicle.getVehicleID().equals(vhID)) {
                if(!vehicle.getState().equals(PMVState.Available)){
                    throw new PMVNotAvailException("The vehicle is not available");
                }
            }
        }
    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException {
        for (Map.Entry<Station, PMVehicle> entry : vehicles.entrySet()) {
            Station station = entry.getKey();
            PMVehicle vehicle = entry.getValue();

            if (vehicle.getVehicleID().equals(veh) && station.getId().equals(st)) {
                //Register pairing
            }
        }
    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException {

    }

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {

    }

    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {

    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {

    }

    public Station getStation(StationID stID){
        for(Station st : stations){
            if(st.getId().equals(stID)){
                return st;
            }
        }
        return null;
    }
}
