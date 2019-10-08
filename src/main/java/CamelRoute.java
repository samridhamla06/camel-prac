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
    public static final String DIRECT_ROUTE_4 = "direct:hello-4";
    public static final String JMS_QUEUE = "activemq:test1";
    public static final String SEDA_ROUTE_1 = "seda:hello";
    public static final String SEDA_ROUTE_2 = "seda:hello-2";

    public void configure() throws Exception {

        from(DIRECT_ROUTE_1)
                .onException(Exception.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(id + " LOGGING THE ERROR MESSAGE " + exception.getMessage());
                    }
                })
                .end()
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + DIRECT_ROUTE_1);
                    }
                })
                .inOnly(SEDA_ROUTE_1)
                .setHeader("foo", constant("activemq:test11" + "," + DIRECT_ROUTE_3 + "," + SEDA_ROUTE_2))
                .recipientList(header("foo"));

        from(DIRECT_ROUTE_4)
                .onException(Exception.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(id + " LOGGING THE ERROR MESSAGE " + exception.getMessage());
                    }
                })
                .end()
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + DIRECT_ROUTE_1);
                    }
                })
                .inOnly(SEDA_ROUTE_1)
                .to(JMS_QUEUE);

        from(SEDA_ROUTE_1)
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + SEDA_ROUTE_1);
                    }
                })
                .to(DIRECT_ROUTE_2)
                .stop();

        from(SEDA_ROUTE_2 + "2")
                .onException(Exception.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(id + " LOGGING THE ERROR MESSAGE " + exception.getMessage());
                    }
                })
                .end()
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + SEDA_ROUTE_2);
                        throw  new RuntimeException(SEDA_ROUTE_2 + " Failed");
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
                .onException(Exception.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(id + " LOGGING THE ERROR MESSAGE " + exception.getMessage());
                    }
                })
                .end()
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + DIRECT_ROUTE_3);
                        //throw  new RuntimeException(DIRECT_ROUTE_3 + " Failed");
                    }
                })
                .stop();

        from(JMS_QUEUE)
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        long id = Thread.currentThread().getId();
                        System.out.println(id + " " + JMS_QUEUE);
                        throw new RuntimeException(JMS_QUEUE + "FAILED");
                    }
                })
                .stop();




    }
}
