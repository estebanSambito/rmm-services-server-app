package com.esa.test.services.server.repository;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.esa.test.services.server.model.InvoiceEntity;
import com.esa.test.services.server.model.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql({"/initialData.sql"})
public class InvoiceRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testCreateInvoiceSucceed() {
		InvoiceEntity wInvoice = new InvoiceEntity();
		wInvoice.setCreationDate(Calendar.getInstance());
		
		Optional<UserEntity> findById = userRepository.findById(1);
		if(findById.isPresent()){
			wInvoice.setUser(findById.get());
			InvoiceEntity save = invoiceRepository.save(wInvoice);
			assertThat("Is present", save.getIdBilling()!= 0);
			
		}
	
	}
}
