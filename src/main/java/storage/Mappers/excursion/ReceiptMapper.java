package storage.Mappers.excursion;

import businesslogic.PaySystem;
import businesslogic.Receipt;
import businesslogic.excursionobject.Excursion;
import storage.Gateway;
import storage.MapperInterface;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Danya on 22.05.2017.
 */
public class ReceiptMapper {
    private static ArrayList<Receipt> recps;
    private Connection connection;

    public ReceiptMapper() throws SQLException, IOException {
        Gateway gateway = Gateway.getInstance();
        recps = new ArrayList<Receipt>();
        connection = gateway.getDataSource().getConnection();
    }

    public Receipt findByExc(Excursion e) throws SQLException {
//        for (Receipt rec : recps){
//            if(rec.getExc().equals(e))
//                return rec;
//        }
        String query = "SELECT * FROM receipts WHERE excID = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, e.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        int sum = rs.getInt("sum");
        int uid = rs.getInt("uid");
        Receipt r = PaySystem.getCreatedReceipt(uid, e, sum);
        //recps.add(r);
        return r;
    }

    public ArrayList<Receipt> findAll() throws SQLException {
        return null;
    }

    public void update(Receipt item) throws SQLException {
        if (recps.contains(item))
            return;
        String query = "INSERT INTO receipts(receipts.uid," +
                "receipts.sum, receipts.excID)" +
                "VALUES (?, ?, ?);";
        PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, item.getUID());
        st.setInt(2, item.getSum());
        st.setInt(3, item.getExc().getUID());
        st.execute();
        int uid = 0;
        ResultSet generatedKeys = st.getGeneratedKeys();
        if (generatedKeys.next()) {
            uid = (int) generatedKeys.getLong(1);
        }
        item.setUID(uid);
        recps.add(PaySystem.getReceipt(item.getExc()));
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void clear() {
        recps.clear();
    }
}
