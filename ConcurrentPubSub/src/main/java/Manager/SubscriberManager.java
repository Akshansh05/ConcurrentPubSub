package Manager;

import Model.Interface.ISubscriber;
import Model.Message;
import lombok.NonNull;

public class SubscriberManager implements ISubscriber {

    private final String subscriberId;
    private final int sleepingTimeInMillis;

    public SubscriberManager(@NonNull final String subscriberId, final int sleepingTimeInMillis) {
        this.subscriberId = subscriberId;
        this.sleepingTimeInMillis = sleepingTimeInMillis;
    }

    @Override
    public String getId() {
        return subscriberId;
    }

    @Override
    public void consume(Message message) throws InterruptedException {
        System.out.println("Subscriber " + subscriberId + " Started consuming Message " + message.getMessage());
        Thread.sleep(sleepingTimeInMillis);
        System.out.println("Subscriber " + subscriberId + " done consuming Message " + message.getMessage());

    }
}
