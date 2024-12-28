package services.smartfeatures;

import micromobility.JourneyRealizeHandler;
import services.ServerClass;

import java.net.ConnectException;

public class UnboundedBTSignalClass implements UnbondedBTSignal {
    private final JourneyRealizeHandler handler;
    private final int BROADCAST_INTERVAL = 5000;

    private boolean connectEx = false;

    public UnboundedBTSignalClass (JourneyRealizeHandler handler) {
        this.handler = handler;
    }

    public UnboundedBTSignalClass (JourneyRealizeHandler handler, boolean connectEx) {
        this.handler = handler;
        this.connectEx = connectEx;
    }
    public void BTbroadcast () throws ConnectException {
        ServerClass server = handler.getServer();
        while (true) {
            try {
                Thread.sleep(5000);
                handler.broadcastStationID();
            } catch (Exception e) {
                throw new RuntimeException(new ConnectException("Error en la conexión"));
            }
        }
    }
}