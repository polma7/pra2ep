package services.smartfeatures;

import micromobility.exceptions.ProceduralException;
import services.exceptions.PMVPhisicalException;

import java.net.ConnectException;

public class ArduinoMicroControllerClass implements ArduinoMicroController {

    private boolean isConnected = false;
    private boolean isDriving = false;

    private boolean connectionEx = false;
    private boolean phisicalEx = false;
    private boolean proceduralEx = false;

    public ArduinoMicroControllerClass() { }

    public ArduinoMicroControllerClass(boolean connectionEx, boolean phisicalEx, boolean proceduralEx) {
        this.connectionEx = connectionEx;
        this.phisicalEx = phisicalEx;
        this.proceduralEx = proceduralEx;
    }

    public boolean isBTConnected() {
        return isConnected;
    }

    public boolean isVehicleDriving() {
        return isDriving;
    }

    @Override
    public void setBTconnection () throws ConnectException {
        if (connectionEx) {
            throw new ConnectException("Error al conectarse");
        }
        isConnected = true;
    }

    @Override
    public void startDriving () throws PMVPhisicalException, ConnectException,
            ProceduralException {
        if (!isConnected) {
            throw new ConnectException("Error al conectarse");
        }
        if (phisicalEx) {
            throw new PMVPhisicalException("Fallo del vehiculo");
        }
        if (proceduralEx) {
            throw new ProceduralException("Error procedural");
        }
        isDriving = true;
    }

    @Override
    public void stopDriving () throws PMVPhisicalException, ConnectException,
            ProceduralException {
        if (!isConnected) {
            throw new ConnectException("Error al conectarse");
        }
        if (!isDriving) {
            throw new ProceduralException("El vehiculo no está en movimiento");
        }
        if (phisicalEx) {
            throw new PMVPhisicalException("Fallo en los frenos");
        }
    }

    @Override
    public void undoBTconnection () {
        if (!isConnected) {
            return;
        }
        isConnected = false;
    }
}
