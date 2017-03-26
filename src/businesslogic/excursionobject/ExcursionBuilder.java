package businesslogic.excursionobject;

import businesslogic.userfactory.Organizator;

import java.util.ArrayList;

/**
 * Created by Danya on 26.03.2017.
 */
public class ExcursionBuilder {
    static public Excursion createExcursion(Organizator org){
        ++UID;
        return new Excursion(org, UID);
    }
    static public ExcursionObject createExcursionObject(ArrayList<Description> descs){
        return new ExcursionObject(descs);
    }
    static public ExcursionObject createExcursionObject(){
        return new ExcursionObject();
    }

    static private int UID = -1;
}
