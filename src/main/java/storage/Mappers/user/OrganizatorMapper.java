package storage.Mappers.user;

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
        System.out.println(id);
        String query = "SELECT * FROM ORGANIZATORS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        Organizator o = UserFactory.createOrganizator(u.getLogin(), u.getMoney(), id);
        orgs.add(o);
        return o;
    }

    public ArrayList<Organizator> findAll() throws SQLException {
        return null;
    }

    public void update(Organizator item) throws SQLException {
        userMapper.update(item);
        excursionMapper.update(item.getExcursion());
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
            if(orgs.get(i).getLogin() == login)
                return orgs.get(i);
        }

        User u = userMapper.findByLogin(login);
        System.out.println(u.getUID());
        String query = "SELECT * FROM ORGANIZATORS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, u.getUID());
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        Organizator o = UserFactory.createOrganizator(u.getLogin(), u.getMoney(), u.getUID());
        orgs.add(o);
        return o;
    }
}
