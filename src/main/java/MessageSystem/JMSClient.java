package MessageSystem;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

@Startup
@Singleton
public class JMSClient {
    
    @Resource(mappedName = "java:/jms/queue/DLQ")
    private Queue inbox;
    
    @Inject
    private JMSContext context;
    
    public void sendMessage(String msg) {
        JMSProducer producer = context.createProducer();
        producer.send(inbox, msg);
        System.out.println("Message sent: " + msg);
    }
    
    public String getMessage() {
        JMSConsumer consumer = context.createConsumer(inbox);
        String msg = consumer.receiveBody(String.class);
        System.out.println("Message received: " + msg);
        return msg;
    }

}