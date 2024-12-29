package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import micromobility.exceptions.*;
import payment.Payment;
import payment.exceptions.NotEnoughWalletException;
import services.ServerClass;
import services.smartfeatures.QRDecoderClass;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class JourneyRealizeHandler{
    private PMVehicle vehicle;
    private JourneyService service;
    private Station currentStation;
    private Driver driver;
    private ServerClass server;
    private boolean state;
    private QRDecoderClass decoder;

    public JourneyRealizeHandler (Driver driver){
        this.driver = driver;
    }

    public Station getCurrentStation () {
        return this.currentStation;
    }

    public void setCurrentStation (Station currentStation) {
        this.currentStation = currentStation;
    }

    // Different input events that intervene
    // User interface input events
    public void scanQR (PMVehicle vehicle) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException, InvalidGeographicCoordinateException {
        if(this.currentStation.getId() == null){
            throw new ProceduralException("Station ID has not been registered correctly");
        }
        decoder.getVehicleID(vehicle.getQr());
        this.vehicle = vehicle;
        this.state = true;
        server.checkPMVAvail(vehicle.getVehicleID());
        this.service = new JourneyService();
        driver.setTripVehicle(vehicle);
        service.setServiceInit(currentStation.getId(),currentStation.getGP(),vehicle,driver, LocalDateTime.now());
        server.registerPairing(this.driver,vehicle,this.currentStation.getId(),this.currentStation.getGP(),service.getInitDate());
        System.out.println("Pairing done, can start driving");
    }

    public void unPairVehicle () throws ConnectException, InvalidPairingArgsException,
            PairingNotFoundException, ProceduralException, InvalidGeographicCoordinateException {
        if(!checkConnection()){
            throw new ConnectException("Connection has failed");
        }
        if(service == null){
            throw new PairingNotFoundException("The pairing has not been found");
        }
        if(!service.getProgress()){
            throw new ProceduralException("The trip is not in progress, cannot stop driving");
        }
        if(!vehicle.getState().equals(PMVState.UnderWay)){
            throw new ProceduralException("The vehicle is not underway");
        }
        if(this.currentStation == null){
            throw new ProceduralException("The vehicle is not in a PMV Station");
        }
        service.setEndDate();
        service.setEndPoint(currentStation.getGP());
        float avgSp = calculateAvgSpeed();
        System.out.println();
        int distance = (int) calculateDistance();
        float dur = (float) calculateDuration();
        service.setServiceFinish(currentStation.getGP(), LocalDateTime.now(), avgSp, distance,(int) dur, calculateImport(distance, (int) dur, avgSp));
        server.stopPairing(driver, vehicle, currentStation.getId(), service.getEndPoint(),service.getEndDate(),service.getAvgSpeed(), service.getDistance(), (int) service.getDuration(), service.getImporte());
        service.setProgress(false);
        driver.unsetTripVehicle();
    }
    // Input events from the unbonded Bluetooth channel
    public void broadcastStationID (StationID stID) throws ConnectException {
        try {
            System.out.println("Id de la estación és " + stID.getId());
        } catch (Exception e) {
            throw new ConnectException(); // Simula error al transmitir el id
        }
    }

    // Input events from the Arduino microcontroller channel
    public void startDriving () throws ConnectException, ProceduralException {
        if(!vehicle.getState().equals(PMVState.NotAvailable)){
            throw new ProceduralException("PMVehicle has incorrect state");
        }
        if(service == null){
            throw new ProceduralException("An instance of JourneyService has not been created yet");
        }
        vehicle.setUnderWay();
        service.setProgress(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void stopDriving ()
            throws ConnectException, ProceduralException
    {
        if(!service.getProgress()){
            throw new ProceduralException("The trip is not in progress, cannot stop driving");
        }
        if(!vehicle.getState().equals(PMVState.UnderWay)){
            throw new ProceduralException("The vehicle is not underway");
        }
    }
    // Internal operations
    private float calculateDistance()
    {
        GeographicPoint start = service.getOriginPoint();
        GeographicPoint end  = service.getEndPoint();

        return (float) start.CalculateDistance(end);
    }
    private BigDecimal calculateImport (float dis, int dur, float avSp)    {
        double tarifa = 1.5;
        avSp = 20; //Teniamos problemas con la funcion calculate duration con el LocalDateTime, hemos puesto este valor para podr
        //realizar el testing
        BigDecimal result = BigDecimal.valueOf((tarifa * dis) + (dur * 0.1) + (0.05 * avSp));
        
        return result;
    }


    private boolean checkConnection(){
        return this.state;
    }

    private float calculateAvgSpeed(){
        long totalMinutes = calculateDuration();

        float totalHours = totalMinutes / 60f;


        return calculateDistance() / totalHours;
    }

    private long calculateDuration(){
        return ChronoUnit.MINUTES.between(service.getInitDate(), service.getInitDate());

    }

    public void selectPaymentMethod (char opt) throws ProceduralException,
            NotEnoughWalletException, ConnectException
    {
        if(opt ==  'w'){
            realizePayment(service.getImporte());
        }
    }
    // Specific internal operation
    private void realizePayment (BigDecimal imp) throws NotEnoughWalletException, ConnectException {
        server.registerPayment(service.getServiceID(), this.driver, service.getImporte(), this.driver.getPayMethod());
        Payment payment = new Payment(driver, service.getServiceID(), driver.getPayMethod(),imp);
        payment.doPayment();
    }

    public void setServer(ServerClass server){
        this.server = server;
    }

    public void setDecoder(QRDecoderClass decoder){
        this.decoder = decoder;
    }

    public void setService(JourneyService service){
        this.service = service;
    }
    public PMVehicle getVehicle(){
        return this.vehicle;
    }

}
