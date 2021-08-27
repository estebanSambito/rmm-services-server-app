package com.esa.test.services.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.esa.test.services.server.model.DetailProductEntity;

/**
 *
 * @author esalazar
 */
@Repository
public interface DetailRepository extends CrudRepository<DetailProductEntity, Integer> {

}
