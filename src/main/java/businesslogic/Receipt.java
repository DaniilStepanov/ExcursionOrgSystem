package businesslogic;

import businesslogic.excursionobject.Excursion;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Danya on 26.03.2017.
 */
public class Receipt {
    Receipt(int UID, Excursion e, int sum){
        this.UID = UID;
        this.e = e;
        this.sum = sum;
    }

    public void printReceipt(){
        System.out.format("UID: %d \nOrganizer: %s \nDriver: %s \n\nPaid %d \n", UID, e.getOrg().getLogin(), e.getDriver().getLogin(), sum);
    }

    public int getUID(){
        return UID;
    }
    public Excursion getExc(){ return e; }
    public int getSum() { return sum;}

    public void setUID(int uid){
        UID = uid;
    }

    int UID;
    Excursion e;
    int sum;
}
