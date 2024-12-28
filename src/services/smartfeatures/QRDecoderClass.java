package services.smartfeatures;

import java.util.Random;
import data.VehicleID;
import data.exceptions.vehicleid.InvalidVehicleIDFormatException;
import data.exceptions.vehicleid.NullOrEmptyVehicleIDException;
import micromobility.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

public class QRDecoderClass implements QRDecoder{

    @Override
    public VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException {
        if (QRImg == null) {
            throw new CorruptedImgException("La imagen esta corrompida");
        }

        String decodedID = generateRandomID();    /* Id  vàlido de ejemplo */

        try {
            return new VehicleID(decodedID);
        } catch (NullOrEmptyVehicleIDException | InvalidVehicleIDFormatException e) {
            return null;
        }
    }

    /* Genera un id de vehiculo con formato valido */
    private static String generateRandomID() {
        Random r = new Random();
        StringBuilder sb =  new StringBuilder();

        for (int i=0; i<3; i++) {
            sb.append((char) (r.nextInt(26) + (r.nextBoolean() ? 'A' : 'a')));
        }

        sb.append('-');

        for (int i=0; i<3; i++) {
            sb.append(r.nextInt(10));
        }

        sb.append('-');

        for (int i=0; i<2; i++) {
            if (r.nextBoolean()) {
                sb.append((char) (r.nextInt(26) + (r.nextBoolean() ? 'A' : 'a')));
            } else {
                sb.append(r.nextInt(10));
            }
        }
        return sb.toString();
    }
}
