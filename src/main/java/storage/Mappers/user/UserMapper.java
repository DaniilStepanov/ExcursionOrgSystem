package storage.Mappers.user;

import businesslogic.userfactory.User;
import businesslogic.userfactory.UserFactory;
import storage.Gateway;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Danya on 21.05.2017.
 */
public class UserMapper implements UserMapperInterface<User> {

    private static ArrayList<User> users = new ArrayList<User>();
    private Connection connection;

    public UserMapper() throws SQLException, IOException {

        Gateway gateway = Gateway.getInstance();
        connection = gateway.getDataSource().getConnection();
    }

    public ArrayList<User> findByExcursionID(int id) throws SQLException{
        String query = "SELECT * FROM USERS WHERE excursionID = ?";
        PreparedStatement select = connection.prepareStatement(query);
        select.setInt(1, id);
        ResultSet rs = select.executeQuery();
        ArrayList<User> users = new ArrayList<User>();
        while(rs.next()){
            int uid = rs.getInt("id");
            String login = rs.getString("login");
            int money = rs.getInt("money");
            User u = UserFactory.createUser(uid, login, money);
            users.add(u);
        }
        return users;
    }

    public void updateExcursion(int excID, int uid) throws SQLException{
        String query = "UPDATE users SET " +
                "users.excursionID = ? " +
                "WHERE users.id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        if(excID == -1)
            st.setNull(1, Types.INTEGER);
        else
            st.setInt(1, excID);
        st.setInt(2, uid);
        st.execute();
    }

    public boolean addUser(User user) throws SQLException {
        String insertSQL = "INSERT INTO USERS(USERS.login, USERS.money) VALUES (?, ?);";
        PreparedStatement insertStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        insertStatement.setString(1, user.getLogin());
        insertStatement.setInt(2, user.getMoney());
        insertStatement.executeUpdate();
        int uid = 1;

        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            uid = (int) generatedKeys.getLong(1);
        }
        user.setNewUID(uid);
        users.add(user);
        return true;
    }


    public User findByID(int id) throws SQLException {
        for(int i = 0; i < users.size(); ++i){
            if(users.get(i).getUID() == id)
                return users.get(i);
        }

        String query = "SELECT * FROM USERS WHERE id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) return null;
        int uid = rs.getInt("id");
        String login = rs.getString("login");
        int money = rs.getInt("money");
        User u = UserFactory.createUser(uid, login, money);
        users.add(u);
        return u;
    }

    public ArrayList<User> findAll() throws SQLException {
        ArrayList<User> res = new ArrayList<User>();
        String query = "SELECT * FROM USERS;";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            int uid = rs.getInt("id");
            String login = rs.getString("login");
            int money = rs.getInt("money");
            int excId = rs.getInt("excursionID");
            User u = UserFactory.createUser(uid, login, money);
            res.add(u);
        }
        return res;
    }

    public void update(User item) throws SQLException {
        String query = "UPDATE users SET users.money = ? WHERE users.login = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, item.getMoney());
        st.setString(2, item.getLogin());
        st.executeUpdate();
    }

    public void update() throws SQLException {
        for (User u : users){
            update(u);
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void clear() {
        users.clear();
    }

    public int getExcursionID(User u) throws SQLException {
        String query = "SELECT excursionID FROM USERS WHERE login = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, u.getLogin());
        ResultSet rs = st.executeQuery();
        if(!rs.next()) return -1;

        int uid = rs.getInt("excursionID");
        return uid;
    }

    public User findByLogin(String login) throws SQLException {
        for(int i = 0; i < users.size(); ++i) {
            if (users.get(i).getLogin().equals(login))
                return users.get(i);
        }

        String query = "SELECT * FROM USERS WHERE login = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, login);
        ResultSet rs = st.executeQuery();
        if(!rs.next()) return null;

        int uid = rs.getInt("id");
        String newlogin = rs.getString("login");
        int money = rs.getInt("money");
        User u = UserFactory.createUser(uid, newlogin, money);
        users.add(u);
        return u;
    }
}
