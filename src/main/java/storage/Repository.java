package storage;

import businesslogic.excursionobject.Excursion;
import businesslogic.excursionobject.ExcursionBuilder;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import businesslogic.userfactory.User;
import businesslogic.userfactory.UserFactory;
import storage.Mappers.excursion.ExcursionMapper;
import storage.Mappers.excursion.ExcursionObjectMapper;
import storage.Mappers.user.DriverMapper;
import storage.Mappers.user.OrganizatorMapper;
import storage.Mappers.user.UserMapper;
import storage.Mappers.user.VehicleMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Danya on 21.05.2017.
 */
public class Repository {
    private static UserMapper userMapper;
    private static OrganizatorMapper organizatorMapper;
    private static ExcursionObjectMapper excursionObjectMapper;
    private static DriverMapper driverMapper;
    private static ExcursionMapper excursionMapper;
    private static VehicleMapper vehicleMapper;


    public Repository() {
        try {
            if (userMapper == null) userMapper = new UserMapper();
            if (organizatorMapper == null) organizatorMapper = new OrganizatorMapper();
            if (excursionObjectMapper == null) excursionObjectMapper = new ExcursionObjectMapper();
            if (driverMapper == null) driverMapper = new DriverMapper();
            if (excursionMapper == null) excursionMapper = new ExcursionMapper();
            if (vehicleMapper == null) vehicleMapper = new VehicleMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(String name, int money) throws SQLException{
        User u = UserFactory.createUser(0, name, money);
        try {
            userMapper.addUser(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Driver addDriver(String name, int money) throws SQLException {
        Driver d = UserFactory.createDriver(name, money, 0);
        try {
            driverMapper.addDriver(d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return d;
    }

    public Organizator addOrganizator(String name, int money) throws SQLException{
        Organizator o = UserFactory.createOrganizator(name, money, 0);
        try {
            organizatorMapper.addOrganizator(o);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return o;
    }

    public Organizator getOrganizator(String login) throws SQLException{
        Organizator o = organizatorMapper.findByLogin(login);
        return o;
    }

    public User getUser(String login) throws SQLException{
        User u = userMapper.findByLogin(login);
        return u;
    }

    public Driver getDriver(String login) throws SQLException{
        Driver driver = driverMapper.findByLogin(login);
        if (driver == null)
            return null;
        int excId = userMapper.getExcursionID(driver);
        if(excId == -1 || excId == 0){
            driver.setDriverFree();
            return driver;
        }
        if (driver.isAgree())
            driver.setDriverBusy(excursionMapper.findByID(excId));
        else
            driver.setExc(excursionMapper.findByID(excId));
        return driver;
    }

    public Excursion getExcursion(String name) throws SQLException {
        Organizator o = organizatorMapper.getByExcursionName(name);
        return o.getExcursion();
    }

    public void update() throws SQLException {
        userMapper.update();
        driverMapper.update();
        organizatorMapper.update();
    }

    public void updateDrivers() throws SQLException {
        driverMapper.update();
    }

    public void updateDriver(Driver d) throws SQLException {
        driverMapper.update(d);
    }

    public void updateOrganizator(Organizator o) throws SQLException {
        organizatorMapper.update(o);
    }

    public ExcursionObject addExcursionObject(String label, String text) throws SQLException{
        ExcursionObject eo = ExcursionBuilder.createExcursionObject();
        eo.addText(label, text);
        excursionObjectMapper.update(eo);
        return eo;
    }

    public void delUserFromExc(String name) throws SQLException{
        User u = getUser(name);
        userMapper.updateExcursion(-1, u.getUID());
    }

    public ArrayList<Excursion> getAllExcursions() throws SQLException{
        return organizatorMapper.getAllExcursions();
    }

    public ArrayList<Driver> getAllDrivers() throws SQLException {
        return driverMapper.findAll();
    }

    public ArrayList<ExcursionObject> getAllObjects() throws SQLException {
        return excursionObjectMapper.findAll();
    }
}
