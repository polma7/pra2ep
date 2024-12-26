package services;

import data.*;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import micromobility.*;
import micromobility.exceptions.InvalidPairingArgsException;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.PairingNotFoundException;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerClass implements Server{
    private ArrayList<Station> stations;
    private HashMap<Station, List<PMVehicle>> vehicles;
    private ArrayList<JourneyService> services;
    private ArrayList<JourneyService> activeServices;

    public ServerClass(){

    }

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        for (Map.Entry<Station, List<PMVehicle>> entry : vehicles.entrySet()) {
            Station station = entry.getKey();
            for(PMVehicle vehicle : entry.getValue()) {
                PMVehicle veh = vehicle;

                if (veh.getVehicleID().equals(vhID)) {
                    if (!veh.getState().equals(PMVState.Available)) {
                        throw new PMVNotAvailException("The vehicle is not available");
                    }
                }
            }
        }
    }

    @Override
    public void registerPairing(Driver user, PMVehicle veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException, InvalidGeographicCoordinateException {
        for (Map.Entry<Station, List<PMVehicle>> entry : vehicles.entrySet()) {
            Station station = entry.getKey();
            for(PMVehicle vehicle : entry.getValue()) {
                if (vehicle.getVehicleID().equals(veh.getVehicleID()) && station.getId().equals(st)) {
                    setPairing(user,veh, st, loc, date);
                    blockVehicle(vehicle);
                }
            }
        }
    }

    @Override
    public void stopPairing(Driver user, PMVehicle veh, StationID st, GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException, InvalidGeographicCoordinateException, PairingNotFoundException {
        for(JourneyService service: activeServices){
            if(service.getDriver().getUserAccount().equals(user.getUserAccount()) && service.getVehicle().getVehicleID().equals(veh.getVehicleID())){
                for (Map.Entry<Station, List<PMVehicle>> entry : vehicles.entrySet()) {
                    Station station = entry.getKey();
                    for(PMVehicle vehicle : entry.getValue()) {
                        if (vehicle.getVehicleID().equals(veh.getVehicleID()) && station.getId().equals(st)) {
                            freeVehicle(vehicle);
                        }
                    }
                }
                service.setServiceFinish(loc, date, avSp, dist, dur, imp);
                unPairRegisterService(service);
                registerLocation(veh.getVehicleID(), st);
            }
        }
    }

    @Override
    public void setPairing(Driver user, PMVehicle veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidGeographicCoordinateException {
        JourneyService s = new JourneyService();
        s.setServiceInit(st, loc, veh, user, date);
    }

    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        for(JourneyService service : services){
            if(service.getServiceID().equals(s.getServiceID())){
                activeServices.remove(service);
                services.add(service);
            }
        }
        throw new PairingNotFoundException("The service with id " + s.getServiceID() + " does not exist");
    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        PMVehicle tomove = null;
        for (Map.Entry<Station, List<PMVehicle>> entry : vehicles.entrySet()) {
            Station station = entry.getKey();
            for(PMVehicle vehicle : entry.getValue()) {

                if (vehicle.getVehicleID().equals(veh)) {
                    vehicles.get(station).remove(vehicle);
                    tomove = vehicle;
                    break;
                }
            }
        }
        if(tomove != null) {
            for (Map.Entry<Station, List<PMVehicle>> entry : vehicles.entrySet()) {
                Station station = entry.getKey();
                if (station.getId().equals(st)) {
                    vehicles.get(station).add(tomove);
                }
            }
        }
    }

    public Station getStation(StationID stID){
        for(Station st : stations){
            if(st.getId().equals(stID)){
                return st;
            }
        }
        return null;
    }

    private void blockVehicle(PMVehicle vehicle){
        vehicle.setNotAvailb();
    }

    private void freeVehicle(PMVehicle vehicle){
        vehicle.setAvailb();
    }
}
