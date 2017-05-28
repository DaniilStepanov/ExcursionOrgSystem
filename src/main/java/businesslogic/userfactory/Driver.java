package businesslogic.userfactory;

import businesslogic.excursionobject.Excursion;

/**
 * Created by Danya on 26.03.2017.
 */
public class Driver extends User {

    Driver(int UID, String name, int money){
        super(UID, name, money);
        isFree = true;
    }

    Driver(int UID, String name){
        super(UID, name);
    }

    public void addVehicle(String model, int mileage, int capacity, String numbers) {
        vehicle = new Vehicle(model, mileage, capacity, numbers);
        vehicle.setisChecked(false);
    }

    public void addVehicle(Vehicle v){
        this.vehicle = v;
    }

    public boolean checkVehicle(){
        // CHECK VEHICLE
        // External query
        vehicle.setisChecked(true);

        if(vehicle.isChecked()){
            return true;
        }
        else{
            vehicle = null;
            return false;
        }
    }
    public Vehicle getVehicle(){return vehicle;}
    public void setDriverFree(){isFree = true;}
    public void setDriverBusy(){isFree = false;}
    public boolean isFree(){return isFree;}

    private Vehicle vehicle;
    private boolean isFree;
}
