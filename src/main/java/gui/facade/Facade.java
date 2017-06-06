package gui.facade;

import businesslogic.ErrorCodes;
import businesslogic.ExSystem;
import businesslogic.excursionobject.Excursion;
import businesslogic.excursionobject.ExcursionBuilder;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import businesslogic.userfactory.User;
import businesslogic.userfactory.Vehicle;
import com.sun.org.apache.xpath.internal.operations.Or;
import storage.Repository;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Danya on 28.05.2017.
 */
public class Facade {

    public Facade(){
        rep = new Repository();
    }

    public void addUser(String name, int money) throws Exception {
        rep.addUser(name, money);
    }

    public void addDriver(String name, int money) throws Exception {
        rep.addDriver(name, money);
    }

    public void addOrg(String name, int money) throws Exception {
        rep.addOrganizator(name, money);
    }

    public int getMoney(String user) throws Exception{
        User u = getUser(user);
        System.out.println("Get money for user " + u.getLogin());
        System.out.println("Money = " + u.getMoney());
        return u.getMoney();
    }

    private User getUser(String name) throws Exception {
        return rep.getUser(name);
    }

    private Driver getDriver(String name) throws Exception {
        return rep.getDriver(name);
    }

    private Organizator getOrg(String name) throws Exception {
        return rep.getOrganizator(name);
    }

    private Excursion getExc(String name) throws Exception {
        return rep.getExcursion(name);
//        for (Excursion e : excs){
//            if(e.getName().equals(name)){
//                return e;
//            }
//        }
//        return null;
    }

    public String getDriversVehicleInfo(String login) throws Exception {
        Driver d = getDriver(login);
        String res = "";
        if (d.getVehicle() == null)
            return "You dont have a vehicle";
        else {
            res += "Model: " + d.getVehicle().getModel() + "\n";
            res += "Capacity: " + d.getVehicle().getCapacity() + "\n";
            res += "Numbers: " + d.getVehicle().getNumbers() + "\n";
            res += "IsChecked: " + d.getVehicle().isChecked() + "\n";
        }
        return res;
    }

    public boolean authenticate(String name, String password) throws Exception{
        if (rep.getUser(name) != null)
            return true;
        else
            return false;
    }

    public boolean isDriver(String name) throws Exception {
        if(rep.getDriver(name) != null)
            return true;
        return false;
    }

    public boolean isOrg(String name) throws Exception {
        if(rep.getOrganizator(name) != null)
            return true;
        return false;
    }

    public ArrayList<String> getExcursionList() throws Exception{
        ArrayList<String> result = new ArrayList<String>();
        excs = rep.getAllExcursions();
        for (Excursion e : excs){
            result.add(e.getName());
        }
        return result;
    }

    public ArrayList<String> getExcursionDescription(String excName) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (Excursion e : excs){
            if(e.getName().equals(excName)){
                result.add(e.getName());
                result.add("Free " + (e.getMaxTourists() - e.getUsers().size()) + " places\n");
                result.add("Equipment: " + e.getEquipment());
                result.add("Date: " + e.getDepartureDate());
                result.add("Status: " + e.getStringStatus() + "\n\n");
                ArrayList<ExcursionObject> eos = e.getExsursionObjects();
                for( ExcursionObject eo : eos){
                    result.add(eo.getDescription(0).getLabel());
                }
            }
        }
        return result;
    }

    public String getExcustionDescriptionForOrg(String login) throws Exception {
        Organizator o = getOrg(login);
        if (o.getExcursion() != null){
            return o.getExcursion().printExcursionInStr();
        }
        else
            return "None";
    }

    public String getDriversExcursion(String name) throws Exception{
        Driver d = getDriver(name);
        if(d.getExcursion() == null)
            return "";
        else
            return d.getExcursion().getName();
    }

    public String getOrganizatorExcursion(String name) throws Exception {
        Organizator o = getOrg(name);
        if(o.getExcursion() == null)
            return "None";
        else
            return o.getExcursion().getName();
    }

    public void addVechicleToDriver(String login, String model,
                                    int mileage, int capacity,
                                    String numbers) throws Exception{
        Driver d = getDriver(login);
        d.addVehicle(model, mileage, capacity, numbers);
        rep.updateDrivers();
    }

    public boolean checkVehicle(String login) throws Exception {
        Driver d = getDriver(login);
        boolean res = d.checkVehicle();
        rep.update();
        return res;
    }

    public ArrayList<String> getDriversList(String name) throws Exception {
        Organizator o = getOrg(name);
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<Driver> drivers = rep.getAllDrivers();
        for (Driver d : drivers){
                if(o.getExcursion() != null &&
                        o.getExcursion().getDriver() != null &&
                        o.getExcursion().getDriver().getLogin().equals(d.getLogin())){
                    if (d.isAgree() == false)
                        res.add(d.getLogin() + "(Sent offer)");
                    else
                        res.add(d.getLogin() + "(Your driver)");
                }
                else if(d.isFree())
                    res.add(d.getLogin() + "(Free)");
                else
                    res.add(d.getLogin() + "(Busy)");
        }
        return res;
    }

    public void payToDriver(String org, String driver, int am) throws Exception {
        Organizator o = getOrg(org);
        Driver d = getDriver(driver);
        o.payToDriver(d, am);
        rep.update();
    }

    public int addExcursionObject(String org, String text) throws Exception {
        Organizator o = getOrg(org);
        if(o.getExcursion() == null)
            return ErrorCodes.excursionIsNull;
        ExcursionObject eo = ExcursionBuilder.createExcursionObject();
        eo.addText(text, "");
        o.getExcursion().addExcursionObject(eo);
        rep.update();
        return ErrorCodes.success;
    }

    public void setDriverToExc(String org, String driver) throws Exception {
        Organizator o = getOrg(org);
        Driver d = getDriver(driver);
        o.getExcursion().setDriver(d);
        rep.updateDrivers();
    }

    public int sendOfferToDriver(String org, String driver,
                                  int price) throws Exception {
        Organizator o = getOrg(org);
        Driver d = getDriver(driver);
        int status = o.sendOfferToDriver(d, price);
        rep.update();
        return status;
    }

    public String getDriverOffers(String driver) throws Exception {
        Driver d = getDriver(driver);
        int price = d.getGivenPrice();
        String res = "";
        if (price == -1)
            res = "No offers";
        else{
            res += "You have an offer to excursion ";
            res += d.getExcursion().getName();
            res += "\nPrice: ";
            res += d.getGivenPrice();
        }
        return res;
    }

    public void setDriverAgree(String driver) throws Exception{
        Driver d = getDriver(driver);
        System.out.println("Set driver " + d.getLogin() + " agree");
        System.out.println(d.isAgree());
        d.agree();
        System.out.println(d.isAgree());
        rep.updateDriver(d);
    }

    public void setDriverdisagree(String driver) throws Exception{
        Driver d = getDriver(driver);
        d.disagree();
        rep.update();
    }

    public int addExcursion(String org, String name, String minTourists,
                             String maxTourists, String equipment,
                             LocalDate departureDate) throws Exception{
        Organizator o = getOrg(org);
        o.createExcursion(name);
        Date d = java.sql.Date.valueOf(departureDate);
        java.util.Date now = new java.util.Date();
        if (now.compareTo(d) == 1)
            return ErrorCodes.dateError;
        try {
            int min = Integer.parseInt(minTourists);
            int max = Integer.parseInt(maxTourists);
            if (max < min)
                return ErrorCodes.minMaxTour;
            o.getExcursion().setExcursionInfo(min, max, equipment, d, 0);
            rep.update();
            return ErrorCodes.success;
        }
        catch (NumberFormatException e){
            return ErrorCodes.fieldString;
        }
    }

    public void getInfoToExcursion(String org) throws Exception {

    }

    public int addUserToExcursion(String user, String excName) throws Exception {
        User u = getUser(user);
        Excursion e = getExc(excName);
        int status = e.addUser(u);
        rep.update();
        return status;
    }

    public void delUserFromExcursion(String user, String excName) throws Exception {
        User u = getUser(user);
        Excursion e = getExc(excName);
        e.delUser(u);
        rep.delUserFromExc(user);
    }

    public ArrayList<String> getUserExcursions(String user) throws Exception {
        ArrayList<String> res = new ArrayList<String>();
        User u = getUser(user);
        for (Excursion e : excs){
            for (User us : e.getUsers()){
                if (u.getLogin().equals(us.getLogin()))
                    res.add(e.getName());
            }
        }
        return res;
    }

    public void updateOrg(String org) throws Exception {
        //update();
        Organizator o = getOrg(org);
        if (o.getExcursion().getDriver() != null){
            Excursion e = o.getExcursion();
            System.out.println("num of users =" + e.getUsers().size());
            Driver d = o.getExcursion().getDriver();
            Driver newDriver = getDriver(d.getLogin());
            if(newDriver.isAgree()){
                newDriver.setDriverBusy(e);
                e.setDriver(newDriver);
            }
        }
    }

    public int beginExcursion(String org) throws Exception {
        Organizator o = getOrg(org);
        int status = o.beginExcursion();
        rep.updateDriver(o.getExcursion().getDriver());
        rep.updateOrganizator(o);
        return status;
    }

    public void update() throws Exception {
        rep.update();
    }

    public int endExcursion(String org) throws Exception {
        System.out.println("END excursion\n");
        Organizator o = getOrg(org);
        Driver d = o.getExcursion().getDriver();
        o.endExcursion();
        rep.updateDriver(d);
        rep.updateOrganizator(o);
        return 0;
    }

    private Repository rep;
    private ArrayList<Excursion> excs = new ArrayList<Excursion>();
}
