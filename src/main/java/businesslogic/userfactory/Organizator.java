package businesslogic.userfactory;

import businesslogic.ErrorCodes;
import businesslogic.PaySystem;
import businesslogic.Receipt;
import businesslogic.excursionobject.Excursion;
import businesslogic.excursionobject.ExcursionBuilder;
import com.mysql.fabric.jdbc.ErrorReportingExceptionInterceptor;
import com.sun.org.apache.xpath.internal.operations.Or;
import gui.controllers.ErrorViewController;

import java.util.ArrayList;

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
        excursion.endExcursion();
    }

    public int sendOfferToDriver(Driver d, int price){
        if(this.excursion.getDriver() != null && this.excursion.getDriver().isAgree())
            return ErrorCodes.excursionAlreadyHaveADriver;
        if(this.getMoney() < price)
            return ErrorCodes.wrongMoney;
        if (!d.isFree())
            return ErrorCodes.driverIsBusy;
        if (d.getVehicle() == null)
            return ErrorCodes.driverWIthoutVehicle;
        if (!d.getVehicle().isChecked())
            return ErrorCodes.driversVehicle;
        if (d.getVehicle().getCapacity() < excursion.getMaxTourists())
            return ErrorCodes.capacity;
        d.setGivenPrice(price);
        d.setExc(excursion);
        excursion.setDriver(d);
        return ErrorCodes.success;
    }

    public int setDriver(Driver d){
        if (!d.isAgree())
            return ErrorCodes.driverIsNotAgree;
        excursion.setDriver(d);
        return ErrorCodes.success;
    }

    public void setExcursion(Excursion e){
        e.setOrg(this);
        excursion = e;
    }

    public void payToDriver(Driver d, int sum){
        if(d.getLogin().equals(excursion.getDriver().getLogin()) &&
                !excursion.isPaid()){
            Receipt rec = PaySystem.pay(excursion, sum);
            excursion.setPay(true, rec);
        }
    }

    public int beginExcursion(){
        if (excursion == null)
            return ErrorCodes.excursionIsNull;
        int status = excursion.beginExcursion();
        return status;
    }

    public void createExcursion(String name){
        excursion = ExcursionBuilder.createExcursion(this, name);
    }

    private Excursion excursion;
}
