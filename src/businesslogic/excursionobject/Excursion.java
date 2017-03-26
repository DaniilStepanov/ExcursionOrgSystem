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
        objects = new ArrayList<ExcursionObject>();
        isPay = false;
    }
    public void setDriver(Driver d){
        if (d.getVehicle() == null)
            throw new RuntimeException("Driver without vehicle!");
        else
            driver = d;
    }
    public void addExcursionObject(ExcursionObject obj){
        objects.add(obj);
    }
    public void printExcursion(){
        System.out.format("Excursion number %d\n", UID);
        System.out.format("Organizator is %s \nDriver is %s\n", org.getLogin(), driver.getLogin());
        System.out.format("Description:\n");
        for (ExcursionObject eo: objects){
            System.out.println("Excursion object");
            eo.printDescription();
            System.out.format("\n\n");
        }
        if (isPay) {
            System.out.println("Excursion is paid");
            rec.printReceipt();
        }
        else {
            System.out.println("Excursion still not paid");
        }
    }
    public ArrayList<ExcursionObject> getExsursionObjects(){return objects;}

    public void setPay(boolean b, Receipt receipt){isPay = b; rec = receipt;}

    public Receipt getReceipt(){return rec;}
    //objects
    private ArrayList<ExcursionObject> objects;
    private int UID;
    private boolean isPay;
    private ArrayList<User> users;
    private Organizator org;
    private Driver driver;
    private Receipt rec;
}
