package com.vf.tickettothemoon.administrators.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/** 
 * Démonstration des règles de nommage dans les implémentations de JPA.
 */
@Entity
public class FormationUniversitaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intituleDeLaFormation;

}
