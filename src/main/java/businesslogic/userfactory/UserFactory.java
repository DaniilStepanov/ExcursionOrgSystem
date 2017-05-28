package businesslogic.userfactory;

/**
 * Created by Danya on 26.03.2017.
 */
public class UserFactory {

    public static Driver createDriver(String name, int money, boolean isFree, int uid){
        Driver d = new Driver(uid, name, money);
        if (isFree)
            d.setDriverFree();
        else
            d.setDriverBusy();
        return d;
    }

    public static Driver createDriver(String name, int money, int uid){
        return new Driver(uid, name, money);
    }

    public static Driver createDriver(String name, int uid){
        return new Driver(uid, name);
    }

    public static Organizator createOrganizator(String name, int money, int uid){
        return new Organizator(uid, name, money);
    }


    public static Organizator createOrganizator(String name, int uid){
        return new Organizator(uid, name);
    }


    public static User createUser(String name, int uid){
        return new User(uid, name);
    }

    public static User createUser(int uid, String name, int money){
        return new User(uid, name, money);
    }

}
