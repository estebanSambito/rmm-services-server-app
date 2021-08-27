package com.esa.test.services.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.esa.test.services.server.model.ProductEntity;

/**
 *
 * @author esalazar
 */
@Repository
public interface DeviceRepository extends CrudRepository<ProductEntity, Integer> {

	 List<ProductEntity> findByType(String type);
}
