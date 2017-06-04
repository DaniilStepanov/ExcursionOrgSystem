package businesslogic.excursionobject;

import businesslogic.ErrorCodes;
import businesslogic.Receipt;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import businesslogic.userfactory.User;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

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
        comments = new HashMap<String, String>();
        this.minTourists = 0;
        this.maxTourists = 0;
        this.equipment = "";
        this.status = 0;
        this.departureDate = new java.sql.Date(0);
        canAddComments = false;
    }

    public int setExcursionInfo(int minTourists, int maxTourists,
                                String equipment, java.sql.Date departureDate, int status){
        this.minTourists = minTourists;
        this.maxTourists = maxTourists;
        this.equipment = equipment;
        this.departureDate = departureDate;
        if (minTourists > maxTourists)
            return 1;
        Date d = new Date();
        if (departureDate.before(d))
            return 2;
        this.status = status;
        return 0;
    }

    public int beginExcursion(){
        if (users.size() > maxTourists)
            return ErrorCodes.maxTourists;
        if (users.size() < minTourists)
            return ErrorCodes.minTourists;
        if (driver.isAgree() == false)
            return ErrorCodes.driverIsNotAgree;
        org.payToDriver(driver, driver.getGivenPrice());
        status = 2;
        return ErrorCodes.success;
    }

    public int endExcursion(){
        status = 3;
        driver.setDriverFree();
        canAddComments = true;
        return ErrorCodes.success;
    }

    public int addComment(String userName, String comment){
        if (canAddComments)
            comments.put(userName, comment);
        else return ErrorCodes.cantAddComm;
        return ErrorCodes.success;
    }

    public int setDriver(Driver d){
        driver = d;
        return ErrorCodes.success;
    }

    public void addExcursionObject(ExcursionObject obj){
        objects.add(obj);
    }

    public String printExcursionInStr(){
        String res = "";
        res += "Excursion number " + UID + "\n";
        res += "Organizator is " + org.getLogin() + "\nDriver is ";
        if(driver == null)
            res += "No driver\n";
        else
            if(driver.isAgree())
                res += driver.getLogin() + " (agree)\n";
            else
                res += driver.getLogin() +" (not agree)\n";
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

    public int addUser(User u){
        if(status > 1)
            return ErrorCodes.late;
        for (User us : users){
            if( u.getLogin().equals(us.getLogin()))
                return ErrorCodes.userAlreadyInExcursion;
        }
        System.out.println();
        System.out.println();
        if (users.size() == maxTourists)
            return ErrorCodes.maxTourists;
        users.add(u);
        return ErrorCodes.success;
    }

    public void delUser(User u){
        if(users.contains(u))
            users.remove(u);
    }


    public int getMinTourists() { return minTourists; }

    public int getMaxTourists() { return maxTourists; }

    public String getEquipment() { return equipment; }

    public java.sql.Date getDepartureDate() { return departureDate; }

    public int getStatus() { return status; }
    public String getStringStatus() {
        switch (status){
            case 0:
                return "Setting";
            case 1:
                return "Ready";
            case 2:
                return "In progress";
            case 3:
                return "End";
            default:
                return "";
        }
    }

    public void setPay(boolean b, Receipt receipt){isPay = b; rec = receipt;}
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

    //Update
    private int minTourists;
    private int maxTourists;
    private String equipment;
    private int status;
    private java.sql.Date departureDate;
    private boolean canAddComments;
    private Map<String, String> comments;

}
