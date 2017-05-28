package businesslogic;

import businesslogic.excursionobject.Excursion;
import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

/**
 * Created by Danya on 26.03.2017.
 */
public class PaySystem {
    public static Receipt pay(Excursion e, int sum){
        e.getOrg().subMoney(sum);
        e.getDriver().addMoney(sum);
        Receipt r = new Receipt(0, e, sum);
        recps.add(r);
        return r;
    }

    public static Receipt getReceipt(Excursion e){
        for(Receipt rec : recps){
            if(rec.getExc() == e)
                return rec;
        }
        return null;
    }
    private static ArrayList<Receipt> recps = new ArrayList<Receipt>();
}

