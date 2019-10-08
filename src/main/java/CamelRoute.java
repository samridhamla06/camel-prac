import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author samridh
 * created on : 08/10/19
 **/
public class CamelRoute extends RouteBuilder{
    public static final String DIRECT_ROUTE_1 = "direct:hello";
    public static final String DIRECT_ROUTE_2 = "direct:hello-2";
    public static final String DIRECT_ROUTE_3 = "direct:hello-3";
    public static final String SEDA_ROUTE_1 = "seda:hello";
    public static final String SEDA_ROUTE_2 = "seda:hello-2";

    public void configure() throws Exception {

        from(DIRECT_ROUTE_1)
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + DIRECT_ROUTE_1);
                    }
                })
                .inOnly(SEDA_ROUTE_1)
                .setHeader("foo", constant(DIRECT_ROUTE_2 + "," + DIRECT_ROUTE_3))
                .recipientList(header("foo"));

        from(SEDA_ROUTE_1)
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + SEDA_ROUTE_1);
                    }
                })
                .to(DIRECT_ROUTE_2)
                .stop();

        from(SEDA_ROUTE_2)
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + SEDA_ROUTE_2);
                    }
                })
                .stop();

        from(DIRECT_ROUTE_2)
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + DIRECT_ROUTE_2);
                    }
                })
                .stop();

        from(DIRECT_ROUTE_3)
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + DIRECT_ROUTE_3);
                    }
                })
                .stop();




    }
}
