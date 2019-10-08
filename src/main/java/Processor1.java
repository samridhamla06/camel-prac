import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

/**
 * @author samridh
 * created on : 08/10/19
 **/
public class Processor1 implements Processor {

    private ProducerTemplate rapProducerTemplate;

    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        String body = (String) in.getBody();
        System.out.println(body);
    }
}
