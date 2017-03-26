package businesslogic;

import businesslogic.userfactory.Driver;
import businesslogic.userfactory.Organizator;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Danya on 26.03.2017.
 */
public class PaySystem {
    public static Receipt pay(Organizator org, Driver d, int sum){
        org.subMoney(sum);
        d.addMoney(sum);
        ++UID;
        return new Receipt(UID, org, d ,sum);
    }
    static int UID = -1;
}

