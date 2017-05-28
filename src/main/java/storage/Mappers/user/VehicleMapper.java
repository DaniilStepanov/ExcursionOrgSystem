package storage.Mappers.user;

import businesslogic.userfactory.Driver;
import businesslogic.userfactory.UserFactory;
import businesslogic.userfactory.Vehicle;
import storage.Gateway;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Danya on 21.05.2017.
 */
public class VehicleMapper {
    private Connection connection;
    private ArrayList<Vehicle> vcls;

    public VehicleMapper() throws SQLException, IOException {
        Gateway gateway = Gateway.getInstance();
        connection = gateway.getDataSource().getConnection();
        vcls = new ArrayList<Vehicle>();
    }

    public void addVehicle(Driver d, Vehicle v) throws SQLException {
        if (!vcls.contains(v)) {
            String insertSQL = "INSERT INTO VEHICLES(VEHICLES.driversID, " +
                    "VEHICLES.model, VEHICLES.mileage," +
                    "VEHICLES.capacity, VEHICLES.numbers, VEHICLES.isChecked)" +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
            insertStatement.setInt(1, d.getUID());
            insertStatement.setString(2, v.getModel());
            insertStatement.setInt(3, v.getMileage());
            insertStatement.setInt(4, v.getCapacity());
            insertStatement.setString(5, v.getNumbers());
            insertStatement.setBoolean(6, v.isChecked());
            insertStatement.executeUpdate();
        }
    }


    public void setCheck(boolean check, String numbers) throws SQLException {
        String query = "UPDATE VEHICLES SET VEHICLES.isChecked = ? " +
                "WHERE VEHICLES.numbers = ?";
        PreparedStatement updateStatement = connection.prepareStatement(query);
        updateStatement.setBoolean(1, check);
        updateStatement.setString(2, numbers);
        updateStatement.executeUpdate();
    }

    public Vehicle getDriversVehicle(Driver d) throws SQLException {
        String query = "SELECT * FROM vehicles WHERE driversID = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, d.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        String model = rs.getString("model");
        int mileage = rs.getInt("mileage");
        int capacity = rs.getInt("capacity");
        String numbers = rs.getString("numbers");
        boolean isChecked = rs.getBoolean("isChecked");
        Vehicle v = new Vehicle(model, mileage, capacity, numbers, isChecked);
        vcls.add(v);
        return v;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }


}
