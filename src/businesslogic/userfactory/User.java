package businesslogic.userfactory;

/**
 * Created by Danya on 26.03.2017.
 */
public class User {

    User(){
        UID = -1;
        login = "";
        money = 0;
    }

    User(int UID, String login){
        this.UID = UID;
        this.login = login;
        money = 0;
    }
    User(int UID, String login, int money){
        this.UID = UID;
        this.login = login;
        this.money = money;
    }
    public int getUID(){return UID;}
    public String getLogin(){return login;}
    public void setMoney(int money){this.money = money;}
    public void subMoney(int amount){
        money = money - amount;
    }
    public void addMoney(int amount){
        money = money + amount;
    }

    private int UID;
    private String login;
    private int money;
}
