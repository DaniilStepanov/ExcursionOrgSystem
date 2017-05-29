import businesslogic.excursionobject.*;
import businesslogic.userfactory.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Danya on 25.04.2017.
 */
public class ExcursionTest {

    @Test
    public void testCreation(){
        Organizator org = UserFactory.createOrganizator("Daniil", 100, 0);
        assertNull(org.getExcursion());
        ExcursionBuilder.createExcursion(org, 0, "LOL");
        assertNotNull(org.getExcursion());
        Text t1 = new Text("Temple", "1831");
        Text t2 = new Text("Waterfall", "70 m");
        ExcursionObject eo = ExcursionBuilder.createExcursionObject();
        eo.addText(t1);
        ExcursionObject eo2 = ExcursionBuilder.createExcursionObject();
        eo2.addText(t2);
        Excursion e = org.getExcursion();
        e.addExcursionObject(eo);
        e.addExcursionObject(eo2);
        assertNotNull(eo.getDescription());
        assertNotNull(e.getExsursionObjects());
    }

    @Test
    public void testAddingUsers(){
        Organizator org = UserFactory.createOrganizator("Daniil", 100, 0);
        ExcursionBuilder.createExcursion(org, 0, "LOL");
        Excursion e = org.getExcursion();
        assertNotNull(e.getUsers());
        assertTrue(e.getUsers().size()==0);
        e.addUser(UserFactory.createUser("User", 0));
        assertNotNull(e.getUsers());
        for (User u : e.getUsers()){
            assertTrue(u.getLogin()=="User");
            assertTrue(u.getMoney() == 0);
        }
    }

    @Test (expected = Exception.class)
    public void testDriverWithoutVehicleSetting(){
        Driver d = UserFactory.createDriver("Driver", 0);
        Organizator org = UserFactory.createOrganizator("Daniil", 1);
        ExcursionBuilder.createExcursion(org, 0, "LOL");
        org.setDriver(d);
    }

    @Test
    public void testDriverSetting(){
        Driver d = UserFactory.createDriver("Driver", 100, 0);
        d.addVehicle("Vaz 2114", 100000, 5, "E777EE78");
        Organizator org = UserFactory.createOrganizator("Daniil", 100, 1);
        ExcursionBuilder.createExcursion(org, 0, "LOL");
        assertFalse(org.setDriver(d));
        d.checkVehicle();
        assertTrue(org.setDriver(d));
        Organizator org2 = UserFactory.createOrganizator("Volodya", 2);
        ExcursionBuilder.createExcursion(org2, 2, "LOL");
        assertFalse(org2.setDriver(d));
        org.getExcursion().getDriver().setDriverFree();
        org.endExcursion();
        assertTrue(org2.setDriver(d));
    }

    @Test
    public void testDeletion(){
        Organizator org = UserFactory.createOrganizator("Danya", 0);
        ExcursionBuilder.createExcursion(org, 0, "LOL");
        ExcursionObject eo = ExcursionBuilder.createExcursionObject();
        eo.addText("Flowers", "1000");
        ExcursionObject eo2 = ExcursionBuilder.createExcursionObject();
        eo2.addText("Cars", "BMW");
        Excursion e = org.getExcursion();
        e.addExcursionObject(eo);
        e.addExcursionObject(eo2);
        assertNotNull(org.getExcursion());
        org.endExcursion();
        assertNull(org.getExcursion());
    }
}
