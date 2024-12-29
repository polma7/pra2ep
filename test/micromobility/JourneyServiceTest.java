package micromobility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.JobOriginatingUserName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JourneyServiceTest {
    JourneyService service1;
    JourneyService service2;

    @BeforeEach
    public void setup(){
        service1 = new JourneyService();
        service2 = new JourneyService();
        service1.setEndDate();
        service2.setEndDate();
    }

    @Test
    public void testSetIdGeneratesValidId() {
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        service1.setId();
        String generatedId = service1.getServiceID().getId(); // Método getId() según tu clase ServiceID

        String regexPattern = "^[A-Z]{2}-\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";
        assertTrue(generatedId.matches(regexPattern),
                "Generated ID does not match the expected pattern: " + generatedId);
    }

    @Test
    public void testSetIdGeneratesUniqueIds() {

        service1.setId();
        service2.setId();

        String id1 = service1.getServiceID().getId();
        String id2 = service2.getServiceID().getId();

        assertNotEquals(id1, id2, "Generated IDs should be unique but were the same: " + id1);
    }
}
