package gui.facade;

import storage.Repository;

/**
 * Created by Danya on 28.05.2017.
 */
public class Facade {

    public Facade(){
        rep = new Repository();
    }

    public void addUser(String name, int money) throws Exception {
        rep.addUser(name, money);
        System.out.println("User " + name + "was added");
    }

    private Repository rep;
}
