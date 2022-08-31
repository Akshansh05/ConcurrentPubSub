package Manager;

import Handler.TopicHandler;
import Model.Interface.ISubscriber;
import Model.Message;
import Model.Topic;
import Model.TopicSubscriber;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TopicManager {
    private final Map<String, TopicHandler> topicProcessors;

    public TopicManager() {
        topicProcessors = new HashMap<>();
    }

    public Topic createTopic(@NonNull final String topicName) {
        Topic topic = new Topic(UUID.randomUUID().toString(), topicName);
        TopicHandler topicHandler = new TopicHandler(topic);
        topicProcessors.put(topic.getTopicId(), topicHandler);
        return topic;
    }

    public void subscribe(@NonNull final Topic topic, @NonNull final ISubscriber isubscriber) {
        topic.addSubscriber(new TopicSubscriber(isubscriber));
        System.out.println("Topic " + topic.getTopicName() + " successfully subscribed to  " + isubscriber.getId());
    }

    public void publish(@NonNull final Topic topic, @NonNull final Message message) {
        topic.addMessage(message);
        System.out.println("Message " + message.getMessage() + " successfully added to Topic " + topic.getTopicName());
        new Thread(() -> topicProcessors.get(topic.getTopicId()).publish()).start();
    }

    public void resetOffSet(@NonNull final Topic topic, @NonNull final ISubscriber iSubscriber, @NonNull final Integer newOffSet) {

        for (TopicSubscriber topicSubscriber : topic.getTopicSubscribers()) {
            if (topicSubscriber.getISubscriber().equals(iSubscriber)) {
                topicSubscriber.getOffset().set(newOffSet);
                System.out.println("new offSet is set for  Topic " + topic.getTopicName() + " Subscriber " + topicSubscriber.getISubscriber().getId() + " to  " + newOffSet);
                new Thread(() -> topicProcessors.get(topic.getTopicId()).startSubscriberHandler(topicSubscriber)).start();
                break;
            }
        }
    }
}
