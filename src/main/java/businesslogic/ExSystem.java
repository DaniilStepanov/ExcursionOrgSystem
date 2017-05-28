package businesslogic;

import businesslogic.excursionobject.Excursion;
import businesslogic.excursionobject.ExcursionBuilder;
import businesslogic.excursionobject.ExcursionObject;
import businesslogic.userfactory.*;
import gui.facade.Facade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import storage.Gateway;
import storage.Mappers.excursion.ExcursionMapper;
import storage.Mappers.excursion.ExcursionObjectMapper;
import storage.Mappers.user.DriverMapper;
import storage.Mappers.user.OrganizatorMapper;
import storage.Mappers.user.UserMapper;
import storage.Mappers.user.VehicleMapper;
import storage.Repository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by Danya on 26.03.2017.
 */
public class ExSystem extends Application{

    public static Facade facade = new Facade();

    @Override
    public void start(Stage stage) throws Exception{
        //Init DB
        Gateway g = new Gateway();
        g.dropAll();

        //Start gui
        String fxmlFile = "/BeginView.fxml";
        FXMLLoader loader = new FXMLLoader();
        AnchorPane root = (AnchorPane) loader.load(ExSystem.class.getClass().getResourceAsStream(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


//    public static void main(String [] args) throws IOException, SQLException {
//
//        Gateway g = new Gateway();
//        g.dropAll();
//
//        Repository r = new Repository();
//        Organizator org = r.addOrganizator("ProOrg", 1000);
//        Driver d = r.addDriver("VinDiesel", 1000);
//        d.addVehicle("VAZ HUYAZ", 100, 4, "L777OL78");
//        d.checkVehicle();
//        d.setDriverFree();
//        org.createExcursion();
//        Excursion e = org.getExcursion();
//
//        ExcursionObject eo = r.addExcursionObject(" Polyana", "");
//        ExcursionObject eo2 = r.addExcursionObject(" Memorial", "");
//        e.addExcursionObject(eo);
//        e.addExcursionObject(eo2);
//        org.setDriver(d);
//        org.payToDriver(d, 5);
//        r.addUser("Us1", 100);
//        r.addUser("Us2", 100);
//        User u1 = r.getUser("Us1");
//        User u2 = r.getUser("Us2");
//        e.addUser(u1);
//        e.addUser(u2);
//        e.printExcursion();
//
//
//        r.update();
//
//
////        OrganizatorMapper om = new OrganizatorMapper();
////        UserMapper um = new UserMapper();
////        DriverMapper dm = new DriverMapper();
////        ExcursionObjectMapper eom = new ExcursionObjectMapper();
////        ExcursionMapper em = new ExcursionMapper();
////        VehicleMapper vm = new VehicleMapper();
////        Organizator o = UserFactory.createOrganizator("ProOrg", 150, 0);
////        Driver d = UserFactory.createDriver("Murik", 200, 0);
////        d.addVehicle("Vaz 2114", 100000, 5, "O001OO78");
////        ExcursionObject eo = ExcursionBuilder.createExcursionObject();
////        eo.addText(" Temple", "");
////        ExcursionObject eo2 = ExcursionBuilder.createExcursionObject();
////        eo2.addText(" Waterfall", "");
////        Excursion e = ExcursionBuilder.createExcursion(o, 0);
////
////
////        om.addOrganizator(o);
////        dm.addDriver(d);
////        vm.addVehicle(d, d.getVehicle()); // ADD VEHICLE MUST BE AUTOMATIC
////        eom.update(eo);
////        eom.update(eo2);
////        e.addExcursionObject(eo);
////        e.addExcursionObject(eo2);
////        e.setDriver(d);
////        em.update(e);
////
////        System.out.println(o.getLogin());
////        System.out.println(o.getUID());
////
//
//
//
////        Driver d = UserFactory.createDriver("Ashot", 100);
////        d.addVehicle("Vaz 2114", 100000, 5, "E777EE78");
////        d.checkVehicle();
////
////        Organizator org = UserFactory.createOrganizator("Daniil", 100);
////        org.createExcursion();
////        ExcursionObject eo = ExcursionBuilder.createExcursionObject();
////        eo.addText(" Temple", "");
////        ExcursionObject eo2 = ExcursionBuilder.createExcursionObject();
////        eo2.addText(" Waterfall", "");
////        Excursion e = org.getExcursion();
////        e.addExcursionObject(eo);
////        e.addExcursionObject(eo2);
////        org.setDriver(d);
////        org.payToDriver(d, 5);
////        User u1 = UserFactory.createUser("User 1");
////        User u2 = UserFactory.createUser("User 2");
////        e.addUser(u1);
////        e.addUser(u2);
////        org.getExcursion().printExcursion();
////        org.endExcursion();
//    }
}
