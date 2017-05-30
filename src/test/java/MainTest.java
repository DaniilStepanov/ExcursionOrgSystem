import businesslogic.excursionobject.Excursion;
import businesslogic.excursionobject.ExcursionBuilder;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import businesslogic.userfactory.User;
import businesslogic.userfactory.UserFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Danya on 25.04.2017.
 */
public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void receiptTest() throws Exception{
        Driver d = UserFactory.createDriver("Ashot", 100, 0);
        d.addVehicle("Vaz 2114", 100000, 5, "E777EE78");
        d.checkTestVehicle();
        Organizator org = UserFactory.createOrganizator("Daniil", 100, 1);
        ExcursionBuilder.createExcursion(org, 0, "LOL");
        ExcursionObject eo = ExcursionBuilder.createExcursionObject();
        eo.addText(" Temple", "");
        ExcursionObject eo2 = ExcursionBuilder.createExcursionObject();
        eo2.addText(" Waterfall", "");
        Excursion e = org.getExcursion();
        e.addExcursionObject(eo);
        e.addExcursionObject(eo2);
        org.setDriver(d);
        int oldMoney = d.getMoney();
        org.payToDriver(d, 5);
        User u1 = UserFactory.createUser("User 1", 2);
        User u2 = UserFactory.createUser("User 2", 3);
        e.addUser(u1);
        e.addUser(u2);
        e.getReceipt().printReceipt();
        assertNotNull(outContent.toString());
        String content = outContent.toString();
        assertTrue(content.contains("UID: 1"));
        assertTrue(content.contains("Organizer: Daniil"));
        assertTrue(content.contains("Paid 5"));
    }


    @Test
    public void workingScenary() throws Exception{
        Driver d = UserFactory.createDriver("Ashot", 100, 0);
        d.addVehicle("Vaz 2114", 100000, 5, "E777EE78");
        d.checkTestVehicle();
        Organizator org = UserFactory.createOrganizator("Daniil", 100, 1);
        ExcursionBuilder.createExcursion(org, 0, "LOL");
        ExcursionObject eo = ExcursionBuilder.createExcursionObject();
        eo.addText(" Temple", "");
        ExcursionObject eo2 = ExcursionBuilder.createExcursionObject();
        eo2.addText(" Waterfall", "");
        Excursion e = org.getExcursion();
        e.addExcursionObject(eo);
        e.addExcursionObject(eo2);
        assertNull(e.getDriver());
        org.setDriver(d);
        int oldMoney = d.getMoney();
        org.payToDriver(d, 5);
        //Checking money transition
        assertTrue(d.getMoney() == oldMoney + 5);
        int oldOrgMoney = org.getMoney();
        assertTrue(org.getMoney() == oldMoney - 5);

        User u1 = UserFactory.createUser("User 1", 2);
        User u2 = UserFactory.createUser("User 2",3);
        e.addUser(u1);
        e.addUser(u2);
        ArrayList<User> users = new ArrayList<User>();
        users.add(u1);
        users.add(u2);
        assertArrayEquals(e.getUsers().toArray(), users.toArray());
        assertNotNull(e.getExsursionObjects());
        assertTrue(org.getExcursion() == e);
        assertTrue(e.getDriver().getLogin() == "Ashot");
    }

}
