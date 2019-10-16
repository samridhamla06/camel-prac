import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

import static org.apache.camel.component.jms.JmsComponent.jmsComponentAutoAcknowledge;

/**
 * @author samridh
 * created on : 08/10/19
 **/
public class MainClass {

    public static void main(String[] args) throws Exception {
       CamelContext context = new DefaultCamelContext();

        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            //connectionFactory.setRedeliveryPolicy();
            //connectionFactory.getRedeliveryPolicy().setMaximumRedeliveries(-1);
            context.addComponent("activemq", jmsComponentAutoAcknowledge(connectionFactory));
            context.addRoutes(new CamelRoute());
            ProducerTemplate template = context.createProducerTemplate();
            context.start();
            //template.sendBody(CamelRoute.DIRECT_ROUTE_4, "Hello World");

            System.out.println("badiya");
            Thread.sleep(100000);
            System.out.println("badiya");
            Thread.sleep(10000000);
            System.out.println("badiya");
        } catch (Exception e) {
            System.out.println("FAILED the MAIN camel execution with error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.stop();
        }

    }
}
