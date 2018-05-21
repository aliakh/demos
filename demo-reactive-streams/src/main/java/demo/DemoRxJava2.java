package demo;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import org.reactivestreams.Subscription;

public class DemoRxJava2 {

    public static void main(String[] args) {
        Flowable.just("one",
                "two",
                "tree")
                .subscribe(new DemoSubscriber());
    }

    private static class DemoSubscriber implements FlowableSubscriber<String> {

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
        public void onNext(String messages) {
            System.out.printf("message received: %s \n", messages);
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
