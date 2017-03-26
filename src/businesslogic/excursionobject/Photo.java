package businesslogic.excursionobject;


import java.io.File;

/**
 * Created by Danya on 26.03.2017.
 */
public class Photo implements Description {

    Photo(String label, File pict){
        this.label = label;
        this.pict = pict;
    }

    @Override
    public String getContent() {
        return pict.toString();
    }

    @Override
    public String getLabel() {
        return "Photo";
    }

    String label;
    File pict;
}
