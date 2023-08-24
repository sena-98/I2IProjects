package org.example;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class SecondActor extends AbstractActor {
    // Define messages
    public static class HelloFromSecondActor {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(HelloFromSecondActor.class, this::onHelloFromSecondActor)
                .build();
    }

    private void onHelloFromSecondActor(HelloFromSecondActor message) {
        System.out.println("Message from FirstActor: Hi from Actor 1");
        // Send the response back to the first actor
        getSender().tell("Hi from Actor 2 ", getSelf());
    }

    public static Props props() {
        return Props.create(SecondActor.class);
    }
}
