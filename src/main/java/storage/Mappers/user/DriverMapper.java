package storage.Mappers.user;

import businesslogic.userfactory.Driver;
import businesslogic.userfactory.User;
import businesslogic.userfactory.UserFactory;
import businesslogic.userfactory.Vehicle;
import storage.Gateway;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Danya on 21.05.2017.
 */
public class DriverMapper implements UserMapperInterface<Driver> {

    private static ArrayList<Driver> drivers = new ArrayList<Driver>();
    private Connection connection;
    private UserMapper userMapper;
    private VehicleMapper vehicleMapper;

    public DriverMapper() throws SQLException, IOException {
        Gateway gateway = Gateway.getInstance();
        connection = gateway.getDataSource().getConnection();
        userMapper = new UserMapper();
        vehicleMapper = new VehicleMapper();
    }

    public boolean addDriver(Driver d) throws SQLException {
        String insertSQL = "INSERT INTO USERS(USERS.login, USERS.money) VALUES (?, ?);";
        PreparedStatement insertStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        insertStatement.setString(1, d.getLogin());
        insertStatement.setInt(2, d.getMoney());
        int uid = 1;

        insertStatement.executeUpdate();
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            uid = (int) generatedKeys.getLong(1);
        }

        insertSQL = "INSERT INTO DRIVERS(DRIVERS.id, DRIVERS.isFree) VALUES (?, ?);";
        insertStatement = connection.prepareStatement(insertSQL);
        insertStatement.setInt(1, uid);
        insertStatement.setBoolean(2,false);
        insertStatement.executeUpdate();

        d.setNewUID(uid);
        drivers.add(d);
        return true;
    }

    public Driver findByID(int id) throws SQLException {
        for(int i = 0; i < drivers.size(); ++i){
            if(drivers.get(i).getUID() == id)
                return drivers.get(i);
        }

        User us = userMapper.findByID(id);
        String query = "SELECT * FROM DRIVERS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, us.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        boolean isFree = rs.getBoolean("isFree");
        Driver d = UserFactory.createDriver(us.getLogin(), us.getMoney(), isFree, us.getUID());
        Vehicle v = vehicleMapper.getDriversVehicle(d);
        if (v != null)
            d.addVehicle(v);
        drivers.add(d);
        return d;
    }

    public ArrayList<Driver> findAll() throws SQLException {
        return null;
    }

    public void update(Driver item) throws SQLException {
        userMapper.update(item);

        if(item.getVehicle() != null)
            vehicleMapper.addVehicle(item, item.getVehicle());
    }

    public void update() throws SQLException {
        for (Driver d : drivers){
            update(d);
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void clear() {
        drivers.clear();
    }

    public Driver getByExcursionID(int id) throws SQLException{
        ArrayList<User> users = userMapper.findByExcursionID(id);
        for (int i = 0; i < users.size(); ++i){
            for(int j = 0; j < drivers.size(); ++j){
                if(users.get(i).getUID() == drivers.get(j).getUID())
                    return drivers.get(j);
            }
        }
        for (int i = 0; i < users.size(); ++i){
            if(findByID(users.get(i).getUID()) != null)
                return findByID(users.get(i).getUID());
        }
        return null;
    }

    public Driver findByLogin(String login) throws SQLException {
        for(int i = 0; i < drivers.size(); ++i){
            if(drivers.get(i).getLogin() == login)
                return drivers.get(i);
        }

        User us = userMapper.findByLogin(login);
        String query = "SELECT * FROM DRIVERS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, us.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        boolean isFree = rs.getBoolean("isFree");
        Driver d = UserFactory.createDriver(us.getLogin(), us.getMoney(), isFree, us.getUID());
        Vehicle v = vehicleMapper.getDriversVehicle(d);
        if (v != null)
            d.addVehicle(v);
        drivers.add(d);
        return d;
    }
}
