package services.smartfeatures;

import data.VehicleID;

import java.awt.image.BufferedImage;

public interface QRDecoder {
    VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException;
}
