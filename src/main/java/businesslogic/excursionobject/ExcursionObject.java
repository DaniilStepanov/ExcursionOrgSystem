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
    ExcursionObject(int objUID){
        di = new ArrayList<Description>();
        this.ObjUID = objUID;
    }
    ExcursionObject(ArrayList<Description> di, int objUID){
        this.di = di;
        this.ObjUID = objUID;
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

    public String printDescriptionInString(){
        String res ="";
        for (Description des: di){
            res += des.getLabel().substring(0, des.getLabel().indexOf(".")) + "\n";
        }
        return res;
    }

    public void setNewUID(int uid) {
        ObjUID = uid;
    }
    public int getObjUID(){ return ObjUID; }
    public Description getDescription(int index){
        return di.get(index);
    }

    public void addText(String label, String text){
        di.add(new Text(label, text));
    }

    public void addText(Text t){
        di.add(t);
    }

    public void addPhoto(String label, File file){
        di.add(new Photo(label, file));
    }
    public ArrayList<Description> getDescription(){return di;}
    ArrayList<Description> di;
    int ObjUID;
}
