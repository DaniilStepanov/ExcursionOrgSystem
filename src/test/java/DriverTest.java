import businesslogic.excursionobject.Excursion;
import businesslogic.excursionobject.ExcursionBuilder;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.UserFactory;
import org.junit.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Danya on 25.04.2017.
 */
public class DriverTest {

    @Test
    public void testDriverCreation(){
        Driver d = UserFactory.createDriver("Ashot", 100);
        d.addVehicle("Vaz 2114", 100000, 5, "E777EE78");
        assertNotNull(d.getVehicle());
        assertTrue(d.getVehicle().getModel() == "Vaz 2114");
        assertTrue(d.getVehicle().getCapacity() == 5);
    }

    @Test
    public void testFree() throws  Exception{
        Driver d = UserFactory.createDriver("Ashot", 100);
        Excursion e = ExcursionBuilder.createExcursion(null, "LOL");
        d.addVehicle("Vaz 2114", 100000, 5, "E777EE78");
        d.checkTestVehicle();
        assertTrue(d.getVehicle().isChecked() == true);
        d.setDriverFree();
        assertTrue(d.isFree() == true);
        d.setDriverBusy(e);
        assertTrue(d.isFree() == false);
    }
}
