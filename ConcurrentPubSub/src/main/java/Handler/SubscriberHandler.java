package Handler;

import Model.Message;
import Model.Topic;
import Model.TopicSubscriber;
import lombok.NonNull;
import lombok.SneakyThrows;

public class SubscriberHandler implements Runnable {

    private final Topic topic;
    private final TopicSubscriber topicSubscriber;

    public SubscriberHandler(@NonNull Topic topic, @NonNull TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int curOffSet = topicSubscriber.getOffset().get();
                if (curOffSet >= topic.getMessages().size()) {
                    topicSubscriber.wait();
                } else {
                    Message message = topic.getMessages().get(curOffSet);
                    topicSubscriber.getISubscriber().consume(message);
                    topicSubscriber.getOffset().compareAndSet(curOffSet, curOffSet + 1);
                }

            } while (true);
        }
    }

    public synchronized void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }
}
