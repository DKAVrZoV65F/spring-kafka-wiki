package com.seryozha.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wiki_event")
@Getter
@Setter
public class WikiEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private String eventType;
    private String comment;
    private Long timestamp;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private WikiPage page;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private WikiUser user;
}
