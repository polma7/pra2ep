package services.smartfeatures;

import data.VehicleID;
import micromobility.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

public interface QRDecoder {
    VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException;
}
