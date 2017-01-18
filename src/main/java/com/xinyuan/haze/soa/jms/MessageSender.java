package com.xinyuan.haze.soa.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;


/**
 * Created by sofar on 15-12-4.
 */
@Component
public class MessageSender {

    @Autowired(required = false)
    private JmsTemplate jmsTemplate;

    @Autowired(required = false)
    ActiveMQConnectionFactory connectionFactory;

    public void sendMessage(final JmsMessage jmsMessage) {
        Destination destination = new ActiveMQTopic("aaa");
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("key", jmsMessage.getKey());
                message.setString("content", jmsMessage.getContent());
                return message;
            }
        });
    }

    public void receiveMessage() {
        connectionFactory.setClientID("aaaaa");
        final Session session;
        try {
            Connection connection = connectionFactory.createTopicConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic("aaa");
            MessageConsumer consumer = session.createDurableSubscriber(topic, "my-sub-name");
            //consumer.receive()
            System.out.println(consumer.receive());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
