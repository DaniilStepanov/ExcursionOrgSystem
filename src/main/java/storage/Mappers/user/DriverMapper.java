package storage.Mappers.user;

import businesslogic.excursionobject.Excursion;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.User;
import businesslogic.userfactory.UserFactory;
import businesslogic.userfactory.Vehicle;
import storage.Gateway;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

        insertSQL = "INSERT INTO DRIVERS(DRIVERS.id, DRIVERS.isFree," +
                "DRIVERS.givenPrice, DRIVERS.isAgree) VALUES (?, ?, ?, ?);";
        insertStatement = connection.prepareStatement(insertSQL);
        insertStatement.setInt(1, uid);
        insertStatement.setBoolean(2,false);
        insertStatement.setInt(3, -1);
        insertStatement.setBoolean(4,false);
        insertStatement.executeUpdate();

        d.setNewUID(uid);
        drivers.add(d);
        return true;
    }

    public Driver findByID(int id) throws SQLException {
//        for (Driver d : drivers){
//            if(d.getUID() == id){
//                return d;
//            }
//        }
        User us = userMapper.findByID(id);
        String query = "SELECT * FROM DRIVERS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, us.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        boolean isFree = rs.getBoolean("isFree");
        int givenPrice = rs.getInt("givenPrice");
        boolean isAgree = rs.getBoolean("isAgree");
        Driver d = UserFactory.createDriver(us.getLogin(), us.getMoney(), isFree, us.getUID());
        d.setGivenPrice(givenPrice);
        if (isAgree)
            d.agree();
        else
            d.disagree();
        Vehicle v = vehicleMapper.getDriversVehicle(d);
        if (v != null)
            d.addVehicle(v);
        drivers.add(d);
        return d;
    }

    public ArrayList<Driver> findAll() throws SQLException {
        ArrayList<Driver> res = new ArrayList<Driver>();
        String query = "SELECT * FROM drivers;";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            int uid = rs.getInt("id");
            Driver d = findByID(uid);
            if( d != null)
                res.add(d);
        }
        return res;
    }

    public void update(Driver item) throws SQLException {
        for (int i = 0; i < drivers.size(); ++i){
            if( drivers.get(i).getLogin() == item.getLogin()){
                drivers.set(i, item);
                break;
            }
        }
        userMapper.update(item);
        if(item.getExcursion() != null)
            userMapper.updateExcursion(item.getExcursion().getUID(), item.getUID());
        else
            userMapper.updateExcursion(-1, item.getUID());

        String query = "UPDATE drivers SET drivers.isFree = ?, drivers.givenPrice = ?," +
                "drivers.isAgree = ? WHERE drivers.id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setBoolean(1, item.isFree());
        st.setInt(2, item.getGivenPrice());
        st.setBoolean(3, item.isAgree());
        st.setInt(4, item.getUID());
        st.executeUpdate();
        if(item.getVehicle() != null){
            vehicleMapper.addVehicle(item, item.getVehicle());
        }
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
            for (Driver d : drivers){
                if(users.get(i).getUID() == d.getUID())
                    return d;
            }
        }
        for (int i = 0; i < users.size(); ++i){
            if(findByID(users.get(i).getUID()) != null)
                return findByID(users.get(i).getUID());
        }
        return null;
    }

    public Driver findByLogin(String login) throws SQLException {
//        for (Driver d : drivers){
//            if(d.getLogin().equals(login))
//                return d;
//        }
        User us = userMapper.findByLogin(login);
        String query = "SELECT * FROM DRIVERS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, us.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        boolean isFree = rs.getBoolean("isFree");
        int givenPrice = rs.getInt("givenPrice");
        boolean isAgree = rs.getBoolean("isAgree");
        Driver d = UserFactory.createDriver(us.getLogin(), us.getMoney(), isFree, us.getUID());
        d.setGivenPrice(givenPrice);
        d.setAgree(isAgree);
        Vehicle v = vehicleMapper.getDriversVehicle(d);
        if (v != null)
            d.addVehicle(v);
        drivers.add(d);
        return d;
    }
}
