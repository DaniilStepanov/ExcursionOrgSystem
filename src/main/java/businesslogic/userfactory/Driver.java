package businesslogic.userfactory;

import businesslogic.excursionobject.Excursion;
import service.CheckVehicle;

/**
 * Created by Danya on 26.03.2017.
 */
public class Driver extends User {

    Driver(int UID, String name, int money){
        super(UID, name, money);
        isFree = true;
        exc = null;
        givenPrice = -1;
        isAgree = false;
    }

    Driver(int UID, String name){
        super(UID, name);
        isFree = true;
        exc = null;
        givenPrice = -1;
        isAgree = false;
    }

    public void addVehicle(String model, int mileage, int capacity, String numbers) {
        vehicle = new Vehicle(model, mileage, capacity, numbers);
        vehicle.setisChecked(false);
    }

    public void addVehicle(Vehicle v){
        this.vehicle = v;
    }

    public boolean checkVehicle() throws Exception{
        // CHECK VEHICLE
        // External query
        CheckVehicle service = new CheckVehicle();
        if(vehicle != null){
            boolean check = service.check(vehicle);
            if(check)
                vehicle.setisChecked(true);
        }
        return false;
    }

    public boolean checkTestVehicle() throws Exception{
        // CHECK VEHICLE
        vehicle.setisChecked(true);
        if(vehicle.isChecked()){
            return true;
        }
        else{
            vehicle = null;
            return false;
        }
    }

    public void agree(){
        isAgree = true;
        isFree = false;
    }

    public void disagree(){
        givenPrice = -1;
        isAgree = false;
        exc = null;
    }

    public Vehicle getVehicle(){ return vehicle; }
    public Excursion getExcursion() { return exc; }
    public int getGivenPrice() { return givenPrice; }
    public void setDriverFree(){isFree = true; exc = null; isAgree = false; givenPrice = -1;}
    public void setDriverBusy(Excursion e){isFree = false; exc = e;}
    public void setGivenPrice(int price) { givenPrice = price; }
    public void setExc(Excursion e) { exc = e;}
    public void setAgree(boolean b) { isAgree = b;}
    public boolean isFree(){return isFree;}
    public boolean isAgree() {return isAgree;}

    private Vehicle vehicle;
    private boolean isFree;
    private Excursion exc;

    private int givenPrice;
    private boolean isAgree;
}
