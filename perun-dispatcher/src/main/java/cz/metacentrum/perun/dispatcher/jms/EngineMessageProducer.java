package cz.metacentrum.perun.dispatcher.jms;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.hornetq.api.jms.HornetQJMSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instance of Engine message queue producer for sending messages to Engine.
 * For each Engine own producer (message queue) is created, and stored in EngineMessageProducerPool.
 *
 * @see cz.metacentrum.perun.dispatcher.jms.EngineMessageProducerFactory
 *
 * @author Michal Karm Babacek
 * @author Michal Voců
 * @author David Šarman
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public class EngineMessageProducer {

	private final static Logger log = LoggerFactory.getLogger(EngineMessageProducer.class);

	private Queue queue;
	private Session session;
	private MessageProducer producer;
	private String queueName;

	// this one is to allow for mock objects which extend this class
	public EngineMessageProducer(String queueName) {
		this.queueName = queueName;
	}

	public EngineMessageProducer(String queueName, Session session) {
		this.queueName = queueName;
		this.session = session;
		try {
			// Step 1. Directly instantiate the JMS Queue object.
			queue = HornetQJMSClient.createQueue(queueName);
			if (log.isDebugEnabled()) {
				log.debug("Created queue named as: " + queueName);
			}
			// Step 6. Create a JMS Message Producer
			producer = session.createProducer(queue);
			if (log.isDebugEnabled()) {
				log.debug("Session created: " + session);
			}

		} catch (JMSException e) {
			log.error(e.toString(), e);
		} catch (Exception e) {
			log.error(e.toString(), e);
			// TODO: Restart connection...?
		}
	}

	/**
	 * Send JMS message to the Engine associated with this queue.
	 *
	 * @param text Message content
	 * @throws JMSException when sending fails
	 */
	public void sendMessage(String text) throws JMSException {

		try {
			// Step 7. Create a Text Message
			TextMessage message = session.createTextMessage(text);
			// Step 8. Send...
			producer.send(message);
			log.debug("Sent message (queue:{}): {}", queueName, message.getText());
		} catch (Exception e) {
			log.error(e.toString(), e);
			throw e;
		}
	}

	/**
	 * Get name of the queue for engine.
	 *
	 * @return Name of queue
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * Shutdown before destroying the producer.
	 *
	 */
	public void shutdown() {
		try {
			producer.close();
			session.close();
		} catch (JMSException e) {
			log.error(e.toString(), e);
		}
	}
}
