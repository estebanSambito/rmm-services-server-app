package com.esa.test.services.server.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.esa.test.services.server.model.InvoiceEntity;

/**
 *
 * @author esalazar
 */
@Repository
public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Integer> {

	@Query("select e from InvoiceEntity e where e.creationDate between ?1 and ?2 and e.user.userName=?3 ")
	 List<InvoiceEntity> findByCreationDateAndUsername(Calendar lastMonth, Calendar actualMonth, String username);
}
