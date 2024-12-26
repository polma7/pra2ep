package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import micromobility.Driver;
import micromobility.JourneyService;
import micromobility.PMVehicle;
import micromobility.exceptions.InvalidPairingArgsException;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.PairingNotFoundException;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public interface Server {
    // To be invoked by the use case controller
    void checkPMVAvail(VehicleID vhID)
            throws PMVNotAvailException, ConnectException;
    void registerPairing(Driver user, PMVehicle veh, StationID st,
                         GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException, InvalidGeographicCoordinateException;

    void stopPairing(Driver user, PMVehicle veh, StationID st,
                     GeographicPoint loc, LocalDateTime date, float avSp, float dist,
                     int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException, InvalidGeographicCoordinateException, PairingNotFoundException;
    // Internal operations
    void setPairing(Driver user, PMVehicle veh, StationID st,
                    GeographicPoint loc, LocalDateTime
                            date) throws InvalidGeographicCoordinateException;
    void unPairRegisterService(JourneyService s)
            throws PairingNotFoundException;
    void registerLocation(VehicleID veh, StationID st);
}
