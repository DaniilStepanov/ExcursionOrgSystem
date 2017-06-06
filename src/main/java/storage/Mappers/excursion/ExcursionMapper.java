package storage.Mappers.excursion;

import businesslogic.Receipt;
import businesslogic.excursionobject.Excursion;
import businesslogic.excursionobject.ExcursionBuilder;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import businesslogic.userfactory.User;
import com.sun.org.apache.xpath.internal.SourceTree;
import storage.Gateway;
import storage.MapperInterface;
import storage.Mappers.user.DriverMapper;
import storage.Mappers.user.OrganizatorMapper;
import storage.Mappers.user.UserMapper;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Danya on 22.05.2017.
 */
public class ExcursionMapper implements MapperInterface<Excursion> {

    private static ArrayList<Excursion> excursions = new ArrayList<Excursion>();
    private Connection connection;
    ExcursionObjectMapper excursionObjectMapper;
    DriverMapper driverMapper;
    UserMapper userMapper;
    ReceiptMapper receiptMapper;

    public ExcursionMapper() throws SQLException, IOException {
        Gateway gateway = Gateway.getInstance();
        connection = gateway.getDataSource().getConnection();
        excursionObjectMapper = new ExcursionObjectMapper();
        driverMapper = new DriverMapper();
        userMapper = new UserMapper();
        receiptMapper = new ReceiptMapper();
    }

    public Excursion findByID(int id) throws SQLException {
//        for(int i = 0; i < excursions.size(); ++i)
//            if(excursions.get(i).getUID() == id)
//                return excursions.get(i);
        String query = "SELECT * FROM excursions WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if(!rs.next()) return null;

        boolean isPaid = rs.getBoolean("isPaid");
        String name = rs.getString("name");
        int minTourists = rs.getInt("minTourists");
        int maxTourists = rs.getInt("maxTourists");
        String equipment = rs.getString("equipment");
        int status = rs.getInt("status");
        Date date = rs.getDate("departureDate");
        boolean canAddComments = rs.getBoolean("canAddComments");
        String comments = rs.getString("comments");

        Excursion e = ExcursionBuilder.createExcursion(null, id, name);
        ArrayList<ExcursionObject> objs = excursionObjectMapper.findByExcursionID(id);
        for (ExcursionObject eo : objs){
            e.addExcursionObject(eo);
        }
        //Set info
        e.setExcursionInfo(minTourists, maxTourists, equipment, date, status);
        //Set Driver
        Driver d = driverMapper.getByExcursionID(id);
        if (d != null)
            e.setDriver(d);
        //Get users
        ArrayList<User> users = userMapper.findByExcursionID(id);
        if ( d == null)
            for (User u : users)
                e.getUsers().add(u);
        else
            for (User u : users)
                if(!d.getLogin().equals(u.getLogin()))
                    e.getUsers().add(u);
        //if is Paid getReceipt
        if(isPaid){
            Receipt r = receiptMapper.findByExc(e);
            e.setPay(true, r);
        }
        excursions.add(e);
        return e;
    }

    public ArrayList<Excursion> findAll() throws SQLException {
        return null;
    }

    public void update(Excursion item) throws SQLException {
        //if (findByID(item.getUID()) == null){
        if(!excursions.contains(item)) {
            String query = "INSERT INTO excursions(excursions.id," +
                    "excursions.isPaid, excursions.name, excursions.minTourists," +
                    "excursions.maxTourists, excursions.equipment, excursions.departureDate," +
                    "excursions.canAddComments)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, item.getUID());
            st.setBoolean(2, item.isPaid());
            st.setString(3, item.getName());
            st.setInt(4, item.getMinTourists());
            st.setInt(5, item.getMaxTourists());
            st.setString(6, item.getEquipment());
            st.setDate(7, item.getDepartureDate());
            st.setBoolean(8, false);
            st.execute();
            int uid = 1;
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                uid = (int) generatedKeys.getLong(1);
            }
            item.setNewUID(uid);
            userMapper.updateExcursion(item.getUID(), item.getOrg().getUID());
            if(item.getDriver() != null)
                userMapper.updateExcursion(item.getUID(), item.getDriver().getUID());
            if(item.getReceipt() != null)
                receiptMapper.update(item.getReceipt());
            for (int i = 0; i < item.getUsers().size(); ++i)
                userMapper.updateExcursion(item.getUID(), item.getUsers().get(i).getUID());
            for (int i = 0; i < item.getExsursionObjects().size(); ++i)
                excursionObjectMapper.updateExcursion(item.getUID(),
                        item.getExsursionObjects().get(i).getObjUID());
            excursions.add(item);
        }
        else {
            String query = "UPDATE EXCURSIONS SET " +
                    "`isPaid` = ?, `minTourists` = ?, `maxTourists` = ?, `equipment` = ?, `departureDate` = ?, `canAddComments` = ?, `status` = ? WHERE `id` = ?;";
            PreparedStatement st = connection.prepareStatement(query);
            st.setBoolean(1, item.isPaid());
            st.setInt(2, item.getMinTourists());
            st.setInt(3, item.getMaxTourists());
            st.setString(4, item.getEquipment());
            st.setDate(5, item.getDepartureDate());
            st.setBoolean(6, true);
            st.setInt(7, item.getStatus());
            st.setInt(8, item.getUID());
            st.execute();
            if (item.getStatus() != 3){
                userMapper.updateExcursion(item.getUID(), item.getOrg().getUID());
                if(item.getDriver() != null)
                    userMapper.updateExcursion(item.getUID(), item.getDriver().getUID());
                if(item.getReceipt() != null)
                    receiptMapper.update(item.getReceipt());
                for (int i = 0; i < item.getUsers().size(); ++i){
                    userMapper.updateExcursion(item.getUID(), item.getUsers().get(i).getUID());
                }
                for (int i = 0; i < item.getExsursionObjects().size(); ++i){
                    excursionObjectMapper.updateWithExcursion(item.getExsursionObjects().get(i),
                            item.getUID());
                }
            }
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void update() throws SQLException {
        for (Excursion e : excursions){
            update(e);
        }
    }
    public void clear() {
        excursions.clear();
    }
}
