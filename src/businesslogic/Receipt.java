package businesslogic;

import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;

/**
 * Created by Danya on 26.03.2017.
 */
public class Receipt {
    Receipt(int UID, Organizator from, Driver to, int sum){
        this.UID = UID;
        this.from = from;
        this.to = to;
        this.sum = sum;
    }

    public void printReceipt(){
        System.out.format("UID: %d \nOrganizer: %s \nDriver: %s \n\nPaid %d", UID, from.getLogin(), to.getLogin(), sum);
    }
    int UID;
    Organizator from;
    Driver to;
    int sum;
}
