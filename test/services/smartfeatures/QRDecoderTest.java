package services.smartfeatures;

import data.VehicleID;
import micromobility.exceptions.CorruptedImgException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class QRDecoderTest {
    private QRDecoder qrDecoder;

    @BeforeEach
    void setup() {
        qrDecoder = new QRDecoderClass();
    }

    @Test
    void testGetVehicleIDFromValidImage() {
        BufferedImage validImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        try {
            VehicleID vehicleID = qrDecoder.getVehicleID(validImage);
            assertNotNull(vehicleID);
        } catch (CorruptedImgException e) {
            fail("No se deberia de lanzar la excepción");
        }
    }

    @Test
    void testGetVehicleIDFromNonValidImage() {
        BufferedImage nonValidImage = null;

        assertThrows(CorruptedImgException.class, () -> {
            qrDecoder.getVehicleID(nonValidImage);
        }, "Se debería lanzar una CorruptedImgException");
    }
}
