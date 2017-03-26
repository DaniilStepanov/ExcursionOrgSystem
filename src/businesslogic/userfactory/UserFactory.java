package businesslogic.userfactory;

/**
 * Created by Danya on 26.03.2017.
 */
public class UserFactory {
    public static Driver createDriver(String name, int money){
        ++UID;
        return new Driver(UID, name, money);
    }

    public static Driver createDriver(String name){
        ++UID;
        return new Driver(UID, name);
    }

    public static Organizator createOrganizator(String name, int money){
        ++UID;
        return new Organizator(UID, name, money);
    }

    public static Organizator createOrganizator(String name){
        ++UID;
        return new Organizator(UID, name);
    }

    public static User createUser(String name, int money){
        ++UID;
        return new User(UID, name, money);
    }

    public static User createUser(String name){
        ++UID;
        return new User(UID, name);
    }

    private static int UID = -1;
}
