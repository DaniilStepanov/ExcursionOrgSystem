package storage.Mappers.user;

import businesslogic.excursionobject.Excursion;
import businesslogic.userfactory.Organizator;
import businesslogic.userfactory.User;
import businesslogic.userfactory.UserFactory;
import businesslogic.userfactory.Vehicle;
import storage.Gateway;
import storage.Mappers.excursion.ExcursionMapper;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Danya on 21.05.2017.
 */
public class OrganizatorMapper implements UserMapperInterface<Organizator> {
    private static ArrayList<Organizator> orgs = new ArrayList<Organizator>();
    private Connection connection;
    private UserMapper userMapper;
    private ExcursionMapper excursionMapper;

    public OrganizatorMapper() throws SQLException, IOException {
        Gateway gateway = Gateway.getInstance();
        connection = gateway.getDataSource().getConnection();
        userMapper = new UserMapper();
        excursionMapper = new ExcursionMapper();
    }

    public boolean addOrganizator(Organizator org) throws SQLException {
        String insertSQL = "INSERT INTO USERS(USERS.login, USERS.money) VALUES (?, ?);";
        PreparedStatement insertStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        insertStatement.setString(1, org.getLogin());
        insertStatement.setInt(2, org.getMoney());
        int uid = 1;

        insertStatement.executeUpdate();
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            uid = (int) generatedKeys.getLong(1);
        }

        insertSQL = "INSERT INTO ORGANIZATORS(ORGANIZATORS.id) VALUES (?);";
        insertStatement = connection.prepareStatement(insertSQL);
        insertStatement.setInt(1, uid);
        insertStatement.executeUpdate();
        org.setNewUID(uid);
        orgs.add(org);
        return true;
    }

    public ArrayList<Excursion> getAllExcursions() throws SQLException{
        ArrayList<Excursion> result = new ArrayList<Excursion>();
        String query = "SELECT * FROM organizators;";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            int id = rs.getInt("id");
            Organizator o = findByID(id);
            result.add(o.getExcursion());
        }
        return result;
    }

    public Organizator getByExcursionId(int id) throws SQLException{
        ArrayList<User> users = userMapper.findByExcursionID(id);
        for (int i = 0; i < users.size(); ++i){
            for(int j = 0; j < orgs.size(); ++j){
                if(users.get(i).getUID() == orgs.get(j).getUID())
                    return orgs.get(j);
            }
        }
        for (int i = 0; i < users.size(); ++i){
            if(findByID(users.get(i).getUID()) != null)
                return findByID(users.get(i).getUID());
        }
        return null;
    }

    public Organizator findByID(int id) throws SQLException {
        for(int i = 0; i < orgs.size(); ++i){
            if(orgs.get(i).getUID() == id)
                return orgs.get(i);
        }

        User u = userMapper.findByID(id);

        String query = "SELECT * FROM ORGANIZATORS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, u.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        int orgID = rs.getInt("id");
        if(u.getUID() != orgID)
            return null;
        Organizator o = UserFactory.createOrganizator(u.getLogin(), u.getMoney(), u.getUID());
        int excID = userMapper.getExcursionID(o);
        Excursion e = excursionMapper.findByID(excID);
        o.setExcursion(e);
        orgs.add(o);
        return o;
    }

    public ArrayList<Organizator> findAll() throws SQLException {
        return null;
    }

    public void update(Organizator item) throws SQLException {
        userMapper.update(item);
        if(item.getExcursion() != null){
            excursionMapper.update(item.getExcursion());
        }
    }

    public void update() throws SQLException {
        for(Organizator org : orgs){
            update(org);
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void clear() {
        orgs.clear();
    }

    public Organizator findByLogin(String login) throws SQLException {
        for(int i = 0; i < orgs.size(); ++i){
            if(orgs.get(i).getLogin().equals(login))
                return orgs.get(i);
        }

        User u = userMapper.findByLogin(login);
        String query = "SELECT * FROM ORGANIZATORS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, u.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        int orgID = rs.getInt("id");
        if(u.getUID() != orgID)
            return null;
        Organizator o = UserFactory.createOrganizator(u.getLogin(), u.getMoney(), u.getUID());
        int excID = userMapper.getExcursionID(o);
        Excursion e = excursionMapper.findByID(excID);
        o.setExcursion(e);
        orgs.add(o);
        return o;
    }
}
