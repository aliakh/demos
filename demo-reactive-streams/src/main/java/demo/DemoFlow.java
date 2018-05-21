package demo;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class DemoFlow {

    public static void main(String[] args) {
        try (SubmissionPublisher<String> publisher = new SubmissionPublisher<>()) {

            DemoSubscriber subscriber = new DemoSubscriber();
            publisher.subscribe(subscriber);

            List.of("one",
                    "two",
                    "tree")
                    .forEach(publisher::submit);

            while (publisher.hasSubscribers()) {
            }

            System.out.println("no more subscribers left, closing publisher..");
        }
    }

    private static class DemoSubscriber implements Flow.Subscriber<String> {

        private static final int MAX_MESSAGES = 3;

        private Subscription subscription;
        private int receivedMessages = 0;

        @Override
        public void onSubscribe(Subscription subscription) {
            System.out.printf("new subscription added: %s\n", subscription);
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(String message) {
            System.out.printf("new message received: %s\n", message);
            if (++receivedMessages >= MAX_MESSAGES) {
                System.out.printf("%d messages received, cancelling subscription\n", receivedMessages);
                subscription.cancel();
            } else {
                subscription.request(1);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            System.err.printf("error occurred: %s\n", throwable);
        }

        @Override
        public void onComplete() {
            System.out.println("receiving messages completed");
        }
    }
}
