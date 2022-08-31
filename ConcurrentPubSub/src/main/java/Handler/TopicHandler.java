package Handler;

import Model.Topic;
import Model.TopicSubscriber;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private final Topic topic;
    private final Map<String, SubscriberHandler> subscriberHandles;

    public TopicHandler(@NonNull Topic topic) {
        this.topic = topic;
        subscriberHandles = new HashMap<>();
    }

    public void publish() {
        for (TopicSubscriber topicSubscriber : topic.getTopicSubscribers()) {
            startSubscriberHandler(topicSubscriber);
        }
    }

    public void startSubscriberHandler(@NonNull final TopicSubscriber topicSubscriber) {
        if (!subscriberHandles.containsKey(topicSubscriber.getISubscriber().getId())) {
            final SubscriberHandler subscriberHandler = new SubscriberHandler(topic, topicSubscriber);
            subscriberHandles.put(topicSubscriber.getISubscriber().getId(), subscriberHandler);
            new Thread(subscriberHandler).start();
        } else {
            final SubscriberHandler subscriberHandler = subscriberHandles.get(topicSubscriber.getISubscriber().getId());
            subscriberHandler.wakeUpIfNeeded();
        }

    }
}
