package com.vf.tickettothemoon.administrators.domain.dao_repo;

import org.springframework.data.repository.CrudRepository;

import com.vf.tickettothemoon.administrators.domain.model.Prof;


public interface ProfRepository extends CrudRepository<Prof,Long> {
    
}