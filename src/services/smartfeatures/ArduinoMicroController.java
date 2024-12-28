package services.smartfeatures;

import micromobility.exceptions.ProceduralException;
import services.exceptions.PMVPhisicalException;

import java.net.ConnectException;

public interface ArduinoMicroController {
    void setBTconnection () throws ConnectException;
    void startDriving () throws PMVPhisicalException, ConnectException,
            ProceduralException;
    void stopDriving () throws PMVPhisicalException, ConnectException,
            ProceduralException;
    void undoBTconnection ();
}
