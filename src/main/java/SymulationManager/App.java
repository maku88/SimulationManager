package SymulationManager;

import SymulationManager.manager.SimulationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 */
@Component
public class App 
{
    @Autowired
    private SimulationManager manager;

    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        App p = context.getBean(App.class);
        p.start("dupa");
    }

    private void start(String s) {
        System.out.println("app start " +s );
        manager.registerSimulator("aaa");
        manager.reportFinish("aaa");

    }


}
