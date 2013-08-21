package SymulationManager;

import SymulationManager.manager.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import pl.mobiid.server.ejb.cache.interfaces.ScenarioUpdateCacheRemote;

/**
 * Hello world!
 *
 */
@Component
public class ManagerApp
{
    @Autowired
    private Manager manager;


    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ManagerApp p = context.getBean(ManagerApp.class);
        p.start(args);
    }

    private void start(String[] args ) {
        System.out.println(args[1]);
        manager.setup(args[0],Integer.parseInt(args[1]),args[2]);
        while(true){}
    }


}
