package businesslogic.userfactory;

import businesslogic.excursionobject.Excursion;

/**
 * Created by Danya on 26.03.2017.
 */
public class Driver extends User {

    Driver(int UID, String name, int money){
        super(UID, name, money);
    }

    Driver(int UID, String name){
        super(UID, name);
    }

    public void addVehicle(String model, int mileage, int capacity, String numbers) {
        vehicle = new Vehicle(model, mileage, capacity, numbers);
    }

    public Vehicle getVehicle(){return vehicle;}
    private Vehicle vehicle;
    private Excursion exc;
}
