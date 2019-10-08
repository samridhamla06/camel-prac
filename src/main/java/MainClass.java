import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author samridh
 * created on : 08/10/19
 **/
public class MainClass {

    public static void main(String[] args) {

        CamelContext context = new DefaultCamelContext();

        try {
            ProducerTemplate template = context.createProducerTemplate();
            context.addRoutes(new CamelRoute());
            context.start();
            template.sendBody("direct:hello", "Hello World");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.stop();
        }

    }
}
