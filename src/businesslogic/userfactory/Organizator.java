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

    public void createExcursion(){
        excursion = ExcursionBuilder.createExcursion(this);
    }

    public void setDriver(Driver d){
        excursion.setDriver(d);
    }

    public void payToDriver(Driver d, int sum){
        Receipt rec = PaySystem.pay(this, d, sum);
        excursion.setPay(true, rec);
    }

    private Excursion excursion;
}
