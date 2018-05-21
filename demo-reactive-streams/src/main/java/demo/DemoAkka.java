package demo;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.AsPublisher;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

public class DemoAkka {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        Materializer materializer = ActorMaterializer.create(system);

        Publisher<String> publisher = Source.from(
                List.of("one",
                        "two",
                        "tree")
        ).runWith(Sink.asPublisher(AsPublisher.WITH_FANOUT), materializer);

        Subscriber<String> subscriber = new DemoSubscriber();
        publisher.subscribe(subscriber);
    }

    private static class DemoSubscriber implements Subscriber<String> {

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
