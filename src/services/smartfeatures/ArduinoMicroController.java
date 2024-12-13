package services.smartfeatures;

import micromobility.exceptions.ProceduralException;
import services.exceptions.PMVPhisicalException;

import java.net.ConnectException;

public interface ArduinoMicroController {
    public void setBTconnection () throws ConnectException;
    public void startDriving () throws PMVPhisicalException, ConnectException,
            ProceduralException;
    public void stopDriving () throws PMVPhisicalException, ConnectException,
            ProceduralException;
    public void undoBTconnection ();
}
