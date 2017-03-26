package businesslogic.excursionobject;

import sun.security.krb5.internal.crypto.Des;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Danya on 26.03.2017.
 */
public class ExcursionObject {
    ExcursionObject(){
        di = new ArrayList<Description>();
    }
    ExcursionObject(ArrayList<Description> di){
        this.di = di;
    }
    public String getInfoFromExtSource(){
        /* */
        return "";
    }

    public void printDescription(){
        for (Description des: di){
            System.out.println(des.getLabel());
            //Get Content?
        }
    }
    public void addText(String label, String text){
        di.add(new Text(label, text));
    }
    public void addPhoto(String label, File file){
        di.add(new Photo(label, file));
    }
    public ArrayList<Description> getDescription(){return di;}
    ArrayList<Description> di;

}
