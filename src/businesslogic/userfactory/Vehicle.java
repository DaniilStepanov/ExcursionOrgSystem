package businesslogic.userfactory;

/**
 * Created by Danya on 26.03.2017.
 */
public class Vehicle {

    Vehicle(String model, int mileage, int capacity, String numbers){
        this.model = model;
        this.mileage = mileage;
        this.capacity = capacity;
        this.numbers = numbers;
        isChecked = false;
    }

    public String getModel(){return model;}
    public int getMileage(){return mileage;}
    public int getCapacity(){return capacity;}
    public String getNumbers(){return  numbers;}
    public boolean isChecked(){return isChecked;}

    void checkVehicle(){
        // CHECK VEHICLE
        isChecked = true;
    }

    void setNewMileage(int mileage){this.mileage = mileage;}

    private String model;
    private int mileage;
    private int capacity;
    private String numbers;
    private boolean isChecked;

}
