package com.logic.springboot.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WikimediaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String wikimediaEventData;

}
