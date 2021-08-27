package com.esa.test.services.server.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.esa.test.services.server.model.DetailProductEntity;

/**
 *
 * @author esalazar
 */
@Repository
public interface DetailRepository extends CrudRepository<DetailProductEntity, Integer> {
	@Modifying
	@Query("delete from DetailProductEntity t where t.idDetail = ?1")
	void deleteQuery(int id);
}
