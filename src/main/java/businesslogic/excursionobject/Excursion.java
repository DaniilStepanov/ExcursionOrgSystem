package businesslogic.excursionobject;

import businesslogic.Receipt;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import businesslogic.userfactory.User;

import java.util.ArrayList;

/**
 * Created by Danya on 26.03.2017.
 */
public class Excursion {

    Excursion(Organizator org, int UID){
        this.org = org;
        this.UID = UID;
        org.setExcursion(this);
        objects = new ArrayList<ExcursionObject>();
        users = new ArrayList<User>();
        isPay = false;
    }

    public void setDriver(Driver d){
        if (d.getVehicle() == null)
            throw new RuntimeException("Driver without vehicle!");
        else{
            driver = d;
            d.setDriverBusy();
        }
    }
    public void addExcursionObject(ExcursionObject obj){
        objects.add(obj);
    }
    public void printExcursion(){
        System.out.format("Excursion number %d\n", UID);
        System.out.format("Organizator is %s \nDriver is %s\n", org.getLogin(), driver.getLogin());
        System.out.format("Description:\n");
        for (ExcursionObject eo: objects){
            System.out.println("Excursion objects:");
            eo.printDescription();
            System.out.format("\n\n");
        }
        System.out.println("List of participants:");
        for (int i = 0; i < users.size(); ++i){
            System.out.format("%d. %s\n", i, users.get(i).getLogin());
        }
        System.out.format("\n\n");
        if (isPay) {
            System.out.println("Excursion is paid");
            rec.printReceipt();
        }
        else {
            System.out.println("Excursion still not paid");
        }
    }

    public ArrayList<ExcursionObject> getExsursionObjects(){return objects;}
    public void addUser(User u){users.add(u);}

    public void setPay(boolean b, Receipt receipt){isPay = b; rec = receipt;}
    public void setNewUID(int UID) { this.UID = UID;}

    public Driver getDriver(){return driver; }
    public Receipt getReceipt(){return rec;}
    public Organizator getOrg(){return org;}
    public ArrayList<User> getUsers(){return users;}
    public int getUID() {return UID;}
    public boolean isPaid() {return isPay;}

    //objects
    private ArrayList<ExcursionObject> objects;
    private int UID;
    private boolean isPay;
    private ArrayList<User> users;
    private Organizator org;
    private Driver driver;
    private Receipt rec;
}
