package businesslogic.userfactory;

/**
 * Created by Danya on 26.03.2017.
 */
public class Vehicle {

    public Vehicle(String model, int mileage, int capacity, String numbers){
        this.model = model;
        this.mileage = mileage;
        this.capacity = capacity;
        this.numbers = numbers;
        isChecked = false;
    }

    public Vehicle(String model, int mileage, int capacity, String numbers, boolean isChecked){
        this.model = model;
        this.mileage = mileage;
        this.capacity = capacity;
        this.numbers = numbers;
        this.isChecked = isChecked;
    }

    public String getModel(){return model;}
    public int getMileage(){return mileage;}
    public int getCapacity(){return capacity;}
    public String getNumbers(){return  numbers;}
    public boolean isChecked(){return isChecked;}

    void setNewMileage(int mileage){this.mileage = mileage;}
    void setisChecked(boolean b){this.isChecked = b;}

    private String model;
    private int mileage;
    private int capacity;
    private String numbers;
    private boolean isChecked;

}
