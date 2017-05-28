package businesslogic.excursionobject;

import businesslogic.userfactory.Organizator;

import java.util.ArrayList;

/**
 * Created by Danya on 26.03.2017.
 */
public class ExcursionBuilder {
    static public Excursion createExcursion(Organizator org, int uid){
        return new Excursion(org, uid);
    }

    static public Excursion createExcursion(Organizator org){
        return new Excursion(org, 0);
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
