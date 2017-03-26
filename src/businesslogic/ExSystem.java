package businesslogic;

import businesslogic.excursionobject.Description;
import businesslogic.excursionobject.ExcursionBuilder;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.*;

/**
 * Created by Danya on 26.03.2017.
 */
public class ExSystem {

   public static void main(String [] args){
       Driver d = UserFactory.createDriver("Toha", 100);
       d.addVehicle("Vaz 2110", 100000, 5, "Y291YY78");
       Organizator org = UserFactory.createOrganizator("Danya", 100);
       org.createExcursion();
       ExcursionObject eo = ExcursionBuilder.createExcursionObject();
       eo.addText("Lol1", "lol2");
       ExcursionObject eo2 = ExcursionBuilder.createExcursionObject();
       eo2.addText("Lol3", "Lol4");
       org.getExcursion().addExcursionObject(eo);
       org.getExcursion().addExcursionObject(eo2);
       org.setDriver(d);
       org.payToDriver(d, 5);
       org.getExcursion().printExcursion();
   }
}
