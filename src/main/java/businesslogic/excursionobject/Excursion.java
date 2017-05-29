package businesslogic.excursionobject;

import businesslogic.Receipt;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import businesslogic.userfactory.User;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;

/**
 * Created by Danya on 26.03.2017.
 */
public class Excursion {

    Excursion(Organizator org, int UID, String name){
        this.org = org;
        this.UID = UID;
        objects = new ArrayList<ExcursionObject>();
        users = new ArrayList<User>();
        isPay = false;
        this.name = name;
        if(org != null)
            org.setExcursion(this);
    }

    public void setDriver(Driver d){
        if (d.getVehicle() == null)
            throw new RuntimeException("Driver without vehicle!");
        else{
            if(d.getVehicle().isChecked() && d.isFree() && !isPay){
                if (driver != null)
                    driver.setDriverFree();
                driver = d;
                d.setDriverBusy(this);
            }
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
    public String printExcursionInStr(){
        String res = "";
        res += "Excursion number " + UID + "\n";
        res += "Organizator is " + org.getLogin() + "\nDriver is ";
        if(driver == null)
            res += "No driver\n";
        else
            res += driver.getLogin() + "\n";
        res += "Excursion objects:\n";
        int ind = 1;
        for (ExcursionObject eo: objects){
            res += ind + ". " + eo.printDescriptionInString();
            ++ind;
        }
        res += "\n\n";
        res += "List of participants:\n";
        for (int i = 0; i < users.size(); ++i){
            res += i + ". " + users.get(i).getLogin() + "\n";
        }
        res += "\n\n";
        if (isPay) {
            res += "Excursion is paid \n";
            res += rec.printReceiptInString();
        }
        else {
            res+= "Excursion still not paid";
        }
        return res;
    }

    public ArrayList<ExcursionObject> getExsursionObjects(){return objects;}
    public void addUser(User u){
        if(!users.contains(u))
            users.add(u);
    }

    public void delUser(User u){
        if(users.contains(u))
            users.remove(u);
    }

    public void setPay(boolean b, Receipt receipt){isPay = b; rec = receipt;}
    public void setPaid(boolean b){isPay = b;}
    public void setNewUID(int UID) { this.UID = UID;}
    public void setOrg(Organizator o) {org = o;}

    public Driver getDriver(){return driver; }
    public Receipt getReceipt(){return rec;}
    public Organizator getOrg(){return org;}
    public ArrayList<User> getUsers(){return users;}
    public int getUID() {return UID;}
    public boolean isPaid() {return isPay;}
    public String getName() {return name;}

    //objects
    private ArrayList<ExcursionObject> objects;
    private int UID;
    private boolean isPay;
    private ArrayList<User> users;
    private Organizator org;
    private Driver driver;
    private Receipt rec;
    private String name;
}
