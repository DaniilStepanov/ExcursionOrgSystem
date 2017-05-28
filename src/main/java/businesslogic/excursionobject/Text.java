package businesslogic.excursionobject;

/**
 * Created by Danya on 26.03.2017.
 */
public class Text implements Description {

    public Text(String label, String content){
        this.label = label;
        this.content = content;
    }

    @Override
    public String getLabel() {
        return ("Text" + label);
    }

    @Override
    public String getContent() {
        return content;
    }

    String label;
    String content;
}
