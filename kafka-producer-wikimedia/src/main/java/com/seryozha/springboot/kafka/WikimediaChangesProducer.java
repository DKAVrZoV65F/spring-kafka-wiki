package com.seryozha.springboot.kafka;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private KafkaTemplate<String, String > kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        String topic = "wikimedia_recent_change";
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        // to read real time stream data from wikimedia, we use event source

        // Создаём обработчик событий
        BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
        EventSource.Builder eventSourceBuilder = new EventSource.Builder(URI.create(url));
        BackgroundEventSource eventSource = new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder).build();

        eventSource.start();
        TimeUnit.MINUTES.sleep(10);
        eventSource.close();
    }
//    opt/kafka/bin/kafka-console-consumer.sh --topic wikimedia_recent_change --from-beginning --bootstrap-server localhost:9092
}
