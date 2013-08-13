package SymulationManager;

import SymulationManager.manager.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

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
        p.start("dupa");
    }

    private void start(String s) {
        System.out.println("app start " + s);
        manager.setup("D:\\magisterka\\ProxyServer\\SymulationManager\\simulationPlan",1);
        while(true){}
    }


}
