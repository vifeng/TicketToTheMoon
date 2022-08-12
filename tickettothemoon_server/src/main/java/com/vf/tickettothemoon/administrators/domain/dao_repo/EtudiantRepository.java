package com.vf.tickettothemoon.administrators.domain.dao_repo;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vf.tickettothemoon.administrators.domain.model.Cours;
import com.vf.tickettothemoon.administrators.domain.model.Etudiant;



/**
 * EtudiantRepository
 */
public interface EtudiantRepository extends CrudRepository<Etudiant,Long> {
    public Iterable<Etudiant> findByAddressePersonnelle(String adresse);

    public Iterable<Etudiant> findByNomOrPrenom(String nom, String prenom);

    @Query("select e from Etudiant e where :cours member of e.cours")
    public Set<Etudiant> findByCours(@Param("cours") Cours cours); 
}