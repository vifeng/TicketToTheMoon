package com.vf.tickettothemoon.administrators.domain.dao_repo;

import org.springframework.data.repository.CrudRepository;

import com.vf.tickettothemoon.administrators.domain.model.Cours;


/**
 * CoursRepository
 */
public interface CoursRepository extends CrudRepository<Cours,Long>{

    public Cours findByLabel(String label);
    
}