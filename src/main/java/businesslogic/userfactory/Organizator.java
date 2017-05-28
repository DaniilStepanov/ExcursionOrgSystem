package businesslogic.userfactory;

import businesslogic.PaySystem;
import businesslogic.Receipt;
import businesslogic.excursionobject.Excursion;
import businesslogic.excursionobject.ExcursionBuilder;

/**
 * Created by Danya on 26.03.2017.
 */
public class Organizator extends User {

    Organizator(int UID, String name){
        super(UID, name);
    }

    Organizator(int UID, String name, int money){
        super(UID, name, money);
    }

    public Excursion getExcursion(){
        if (excursion == null)
            new RuntimeException("Didnt create an excursion");
        return excursion;}

    public void endExcursion(){
        if(excursion.getDriver() != null) {
            excursion.getDriver().setDriverFree();
        }
        excursion = null;
    }

    public boolean setDriver(Driver d){
        if(d.isFree() && d.getVehicle().isChecked()){
            excursion.setDriver(d);
            return true;
        }
        else
            return false;
    }
    public void setExcursion(Excursion e){
        excursion = e;
    }

    public void payToDriver(Driver d, int sum){
        Receipt rec = PaySystem.pay(excursion, sum);
        excursion.setPay(true, rec);
    }

    public void createExcursion(){
        excursion = ExcursionBuilder.createExcursion(this);
    }

    private Excursion excursion;
}
