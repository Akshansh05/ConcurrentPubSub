package Model;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class Topic {

    private final String topicId;
    private final String topicName;
    private final List<TopicSubscriber> topicSubscribers;
    private final List<Message> messages;

    public Topic(@NonNull final String topicId, @NonNull final String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.topicSubscribers = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public synchronized void addMessage(@NonNull final Message message) {
        messages.add(message);
    }

    public void addSubscriber(@NonNull final TopicSubscriber topicSubscriber) {
        topicSubscribers.add(topicSubscriber);
    }

}
