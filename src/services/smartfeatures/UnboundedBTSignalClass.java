package services.smartfeatures;

import micromobility.JourneyRealizeHandler;

import java.net.ConnectException;

public class UnboundedBTSignalClass implements UnbondedBTSignal {
    private final JourneyRealizeHandler handler;
    private final int BROADCAST_INTERVAL = 1000;

    private boolean connectEx = false;

    public UnboundedBTSignalClass (JourneyRealizeHandler handler) {
        this.handler = handler;
    }

    public UnboundedBTSignalClass (JourneyRealizeHandler handler, boolean connectEx) {
        this.handler = handler;
        this.connectEx = connectEx;
    }

    @Override
    public void BTbroadcast () throws ConnectException, InterruptedException {
        if (connectEx) {
            throw new ConnectException();
        }

        // Se hace un numero finito de iteraciones para simplificar la implementacion
        for (int i=0; i<10; i++) {
            handler.broadcastStationID(handler.getCurrentStation().getId());
            Thread.sleep(BROADCAST_INTERVAL);
        }
    }
}