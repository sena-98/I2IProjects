package org.example;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.pattern.PatternsCS;
import akka.util.Timeout;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class FirstActor extends AbstractActor {
    // Define messages
    public static class SayHelloToSecondActor {
        private final ActorRef replyTo;

        public SayHelloToSecondActor(ActorRef replyTo) {
            this.replyTo = replyTo;
        }

        public ActorRef getReplyTo() {
            return replyTo;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SayHelloToSecondActor.class, this::onSayHelloToSecondActor)
                .build();
    }

    private void onSayHelloToSecondActor(SayHelloToSecondActor message) {
        // Ask the second actor and wait for the response
        Timeout timeout = Timeout.create(Duration.ofSeconds(5));
        CompletionStage<Object> responseFuture = PatternsCS.ask(message.getReplyTo(), new SecondActor.HelloFromSecondActor(), timeout);

        // Handle the response
        PatternsCS.pipe(responseFuture, getContext().dispatcher()).to(getSelf());
    }

    public static Props props() {
        return Props.create(FirstActor.class);
    }
}

