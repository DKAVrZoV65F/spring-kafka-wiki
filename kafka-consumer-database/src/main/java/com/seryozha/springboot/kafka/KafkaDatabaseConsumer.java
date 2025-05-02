package com.seryozha.springboot.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seryozha.springboot.entity.WikiEvent;
import com.seryozha.springboot.entity.WikiPage;
import com.seryozha.springboot.entity.WikiUser;
import com.seryozha.springboot.repository.WikiEventRepository;
import com.seryozha.springboot.repository.WikiPageRepository;
import com.seryozha.springboot.repository.WikiUserRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private final WikiEventRepository eventRepository;
    private final WikiPageRepository pageRepository;
    private final WikiUserRepository userRepository;
    private final ObjectMapper objectMapper;

    public KafkaDatabaseConsumer(WikiEventRepository eventRepository, WikiPageRepository pageRepository, WikiUserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "wikimedia_recent_change", groupId = "myGroup")
    @Transactional
    public void consume(String eventMessage) {
        try {
            LOGGER.info("Event message received -> {}", eventMessage);

            JsonNode rootNode = objectMapper.readTree(eventMessage);
            
            // Извлечение данных из JSON
            String pageTitle = rootNode.path("title").asText();
            String pageUrl = rootNode.path("title_url").asText();
            String username = rootNode.path("user").asText();
            String eventType = rootNode.path("type").asText();
            String comment = rootNode.path("comment").asText();
            Long eventId = rootNode.path("id").asLong();
            Long timestamp = rootNode.path("timestamp").asLong();
            String serverUrl = rootNode.path("server_url").asText();

            // Проверка, существует ли такая страница
            WikiPage page = pageRepository.findByTitle(pageTitle);
            if (page == null) {
                page = new WikiPage();
                page.setTitle(pageTitle);
                page.setTitleUrl(pageUrl);
                page.setServerUrl(serverUrl);
                pageRepository.save(page);
            }

            // Проверка, существует ли такой пользователь
            WikiUser user = userRepository.findByUsername(username);
            if (user == null) {
                user = new WikiUser();
                user.setUsername(username);
                userRepository.save(user);
            }

            // Создание нового события
            WikiEvent wikiEvent = new WikiEvent();
            wikiEvent.setEventId(eventId);
            wikiEvent.setEventType(eventType);
            wikiEvent.setComment(comment);
            wikiEvent.setTimestamp(timestamp);
            wikiEvent.setPage(page);
            wikiEvent.setUser(user);

            // Сохранение события в базу
            eventRepository.save(wikiEvent);
        } catch (Exception e) {
            LOGGER.error("Error while processing event message", e);
        }
    }
}