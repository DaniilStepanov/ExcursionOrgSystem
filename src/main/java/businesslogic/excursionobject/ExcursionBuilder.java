package businesslogic.excursionobject;

import businesslogic.userfactory.Organizator;

import java.util.ArrayList;

/**
 * Created by Danya on 26.03.2017.
 */
public class ExcursionBuilder {
    static public Excursion createExcursion(Organizator org, int uid, String name){
        if( org != null && org.getExcursion() != null
                && org.getExcursion().getDriver() != null){
            org.getExcursion().getDriver().setDriverFree();
        }
        return new Excursion(org, uid, name);
    }

    static public Excursion createExcursion(Organizator org, String name){
        if( org != null && org.getExcursion() != null
                && org.getExcursion().getDriver() != null){
            org.getExcursion().getDriver().setDriverFree();
        }
        return new Excursion(org, 0, name);
    }

    static public ExcursionObject createExcursionObject(int uid){
        return new ExcursionObject(uid);
    }

    static public ExcursionObject createExcursionObject(ArrayList<Description> descs, int uid){
        return new ExcursionObject(descs, uid);
    }


    static public ExcursionObject createExcursionObject(){
        return new ExcursionObject();
    }

}
