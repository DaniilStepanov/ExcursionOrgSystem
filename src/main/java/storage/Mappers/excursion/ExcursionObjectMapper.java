package storage.Mappers.excursion;

import businesslogic.excursionobject.ExcursionBuilder;
import businesslogic.excursionobject.ExcursionObject;
import storage.Gateway;
import storage.MapperInterface;
import sun.misc.IOUtils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Danya on 22.05.2017.
 */
public class ExcursionObjectMapper implements MapperInterface<ExcursionObject> {

    private static ArrayList<ExcursionObject> objs = new ArrayList<ExcursionObject>();
    private Connection connection;

    public ExcursionObjectMapper() throws SQLException, IOException {
        Gateway gateway = Gateway.getInstance();
        connection = gateway.getDataSource().getConnection();
    }

    public ArrayList<ExcursionObject> findByExcursionID(int id) throws SQLException{
        String query = "SELECT * FROM EXCURSIONOBJECTS WHERE excID = ?";
        PreparedStatement select = connection.prepareStatement(query);
        select.setInt(1, id);
        ResultSet rs = select.executeQuery();
        ArrayList<ExcursionObject> objects = new ArrayList<ExcursionObject>();
        while(rs.next()){
            int uid = rs.getInt("uid");
            String desciption = rs.getString("description");
            Blob pict = rs.getBlob("pict");
            ExcursionObject eo = ExcursionBuilder.createExcursionObject(uid);
            eo.addText(desciption, "");
            //eo.addPhoto()
            objects.add(eo);
        }
        return objects;
    }

    public ExcursionObject findByLabel(String label) throws SQLException {
        for(int i = 0; i < objs.size(); ++i){
            if(objs.get(i).getDescription(0).getLabel().equals(label))
                return objs.get(i);
        }

        String query = "SELECT * FROM EXCURSIONOBJECTS WHERE description = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, label);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        int uid = rs.getInt("uid");
        int excId = rs.getInt("excID");
        String description = rs.getString("description");
        Blob blob = rs.getBlob("pict");
        ExcursionObject eo = ExcursionBuilder.createExcursionObject(uid);
        if(description != null)
            eo.addText(description, "");
        if (blob != null){
            File f = new File("desc.png");
            InputStream in = blob.getBinaryStream();
            OutputStream out = null;
            try {
                out = new FileOutputStream(f);
            } catch (FileNotFoundException e) {}
            byte[] buff = new byte[4096];  // how much of the blob to read/write at a time
            int len = 0;
            try {
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
                eo.addPhoto("photo", f);
            } catch (IOException e) {}
        }
        objs.add(eo);
        return eo;
    }

    public ExcursionObject findByID(int id) throws SQLException {
        for(int i = 0; i < objs.size(); ++i){
            if(objs.get(i).getObjUID() == id)
                return objs.get(i);
        }

        String query = "SELECT * FROM EXCURSIONOBJECTS WHERE uid = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        int excId = rs.getInt("excID");
        String description = rs.getString("description");
        Blob blob = rs.getBlob("pict");
        ExcursionObject eo = ExcursionBuilder.createExcursionObject(id);
        if(description != null)
            eo.addText(description, "");
        if (blob != null){
            File f = new File("desc.png");
            InputStream in = blob.getBinaryStream();
            OutputStream out = null;
            try {
                out = new FileOutputStream(f);
            } catch (FileNotFoundException e) {}
            byte[] buff = new byte[4096];  // how much of the blob to read/write at a time
            int len = 0;
            try {
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
                eo.addPhoto("photo", f);
            } catch (IOException e) {}
        }
        objs.add(eo);
        return eo;
    }

    public ArrayList<ExcursionObject> findAll() throws SQLException {
        ArrayList<ExcursionObject> objs = new ArrayList<ExcursionObject>();
        String query = "SELECT * FROM EXCURSIONOBJECTS;";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()){
            int uid = rs.getInt("uid");
            int excId = rs.getInt("excID");
            String description = rs.getString("description");
            Blob blob = rs.getBlob("pict");
            ExcursionObject eo = ExcursionBuilder.createExcursionObject(uid);
            if(description != null)
                eo.addText(description, "");
            objs.add(eo);
        }
        return objs;
    }

    public void updateExcursion(int excID, int uid) throws SQLException{
        String query = "UPDATE excursionobjects SET " +
                "excursionobjects.excID = ? " +
                "WHERE excursionobjects.uid = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, excID);
        st.setInt(2, uid);
        st.execute();
    }

    public void update(ExcursionObject item) throws SQLException {
        if (findByID(item.getObjUID()) == null){
            String query = "INSERT INTO EXCURSIONOBJECTS(EXCURSIONOBJECTS.uid," +
                    "excursionobjects.description, excursionobjects.pict)" +
                    "VALUES (?, ?, ?);";
            PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, item.getObjUID());
            st.setString(2, item.getDescription(0).getLabel());
            st.setBlob(3, (Blob) null);
            st.execute();
            int uid = 0;
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                uid = (int) generatedKeys.getLong(1);
            }
            item.setNewUID(uid);
        }
        else {
            String query = "UPDATE EXCURSIONOBJECTS SET " +
                    "EXCURSIONOBJECTS.description = ?, " +
                    "EXCURSIONOBJECTS.pict = ?" +
                    "WHERE EXCURSIONOBJECTS.uid = ?;";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, item.getDescription(0).getLabel());
            st.setBlob(2, (Blob) null);
            st.setInt(3, item.getObjUID());
            st.execute();
        }

    }

    public void updateWithExcursion(ExcursionObject item, int excID) throws SQLException {
        if (findByID(item.getObjUID()) == null){
            String query = "INSERT INTO EXCURSIONOBJECTS(EXCURSIONOBJECTS.uid," +
                    "excursionobjects.description, excursionobjects.pict, " +
                    "excursionobjects.excID)" +
                    "VALUES (?, ?, ?, ?);";
            PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, item.getObjUID());
            st.setString(2, item.getDescription(0).getLabel());
            st.setBlob(3, (Blob) null);
            st.setInt(4, excID);
            st.execute();
            int uid = 0;
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                uid = (int) generatedKeys.getLong(1);
            }
            item.setNewUID(uid);
        }
        else {
//            String query = "UPDATE EXCURSIONOBJECTS SET " +
//                    "EXCURSIONOBJECTS.description = ?, " +
//                    "EXCURSIONOBJECTS.pict = ?," +
//                    "EXCURSIONOBJECTS.excID = ?" +
//                    "WHERE EXCURSIONOBJECTS.uid = ?;";
//            PreparedStatement st = connection.prepareStatement(query);
//            st.setString(1, item.getDescription(0).getLabel());
//            st.setBlob(2, (Blob) null);
//            st.setInt(3, excID);
//            st.setInt(4, item.getObjUID());
//            st.execute();
//            int uid = 0;
//            ResultSet generatedKeys = st.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                uid = (int) generatedKeys.getLong(1);
//            }
//            item.setNewUID(uid);
        }

    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void clear() {
        objs.clear();
    }
}