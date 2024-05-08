package MessageSystem;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import java.io.Serializable;

@MessageDriven(name = "queue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/DLQ"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class MessageReciever implements MessageListener {

    @Override
    public void onMessage(Message unreadmsg) {
          try {
                if (unreadmsg instanceof TextMessage) {
                	
                    System.out.println("unread message: " + ((TextMessage) unreadmsg).getText());
                    
                } else if (unreadmsg instanceof ObjectMessage) {
                    Serializable undefinedmsg = ((ObjectMessage) unreadmsg).getObject();
                    System.out.println("Recevied message in inbox:"+undefinedmsg);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
    }
}