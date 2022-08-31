import Manager.SubscriberManager;
import Manager.TopicManager;
import Model.Message;
import Model.Topic;
import lombok.SneakyThrows;

public class PubSubApplication {
    @SneakyThrows
    public static void main(String[] args) {

        //Register Managers
        final TopicManager topicManager = new TopicManager();

        //Create  subscribers
        final SubscriberManager subscriberManagerSubscriber1 = new SubscriberManager("Subscriber1", 10000);
        final SubscriberManager subscriberManagerSubscriber2 = new SubscriberManager("Subscriber2", 10000);
        final SubscriberManager subscriberManagerSubscriber3 = new SubscriberManager("Subscriber3", 10000);


        //Create  Topics
        final Topic topic1 = topicManager.createTopic("Topic1");
        final Topic topic2 = topicManager.createTopic("Topic2");

        //Subscribe Subscriber1 and Subscriber2 to Topic1
        topicManager.subscribe(topic1, subscriberManagerSubscriber1);
        topicManager.subscribe(topic1, subscriberManagerSubscriber2);

        //Subscribe Subscriber3  to Topic2
        topicManager.subscribe(topic2, subscriberManagerSubscriber3);

        //Create Messages
        Message message1 = new Message("Message1");
        Message message2 = new Message("Message2");
        Message message3 = new Message("Message3");

        //Publish message1 and message 2 to topic1 -> which means Subscriber1 and Subscriber2 should get message1 and message 2
        topicManager.publish(topic1, message1);
        topicManager.publish(topic1, message2);

        //Publish message3 to topic2 -> which means Subscriber3 should get message3
        topicManager.publish(topic2, message3);

        //Wait for sometime the main thread to publish another set of messages and finish previous set of messages.
        Thread.sleep(15000);

        //Create  another set of Messages
        Message message4 = new Message("Message4");
        Message message5 = new Message("Message5");

        //Publish message4 to topic1 -> which means Subscriber1 and Subscriber2 should get message4
        topicManager.publish(topic1, message4);

        //Publish message5 to topic2 -> which means Subscriber3 should get message5
        topicManager.publish(topic2, message5);

        //Wait for sometime the main thread to reset the offSet for topic1 and subscriberManagerSubscriber1
        Thread.sleep(15000);
        //Restart publishing message from newOffset  for topic1 and subscriberManagerSubscriber1
        topicManager.resetOffSet(topic1, subscriberManagerSubscriber1, 0);
    }
}
