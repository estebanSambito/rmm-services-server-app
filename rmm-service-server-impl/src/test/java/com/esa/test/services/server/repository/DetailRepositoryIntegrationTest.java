package com.esa.test.services.server.repository;

import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.esa.test.services.server.controller.DetailController;
import com.esa.test.services.server.model.DetailProductEntity;
import com.esa.test.services.server.model.InvoiceEntity;
import com.esa.test.services.server.model.ProductEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql({ "/initialData.sql" })
public class DetailRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private UserRepository userRepository;
	
	private static final Log LOG=LogFactory.getLog(DetailController.class);
	

	@Test
	public void testCreateDetailsExistingInvoiceSucceed() {

		Date referenceDate = new Date();
		Calendar ini = Calendar.getInstance();
		ini.setTime(referenceDate);
		ini.add(Calendar.MONTH, -1);
		Calendar end = Calendar.getInstance();
		end.setTime(referenceDate);
		String username="esalazar";
		List<InvoiceEntity> resultInvoiceList = invoiceRepository.findByCreationDateAndUsername(ini, end,username);

		if (!resultInvoiceList.isEmpty()) {
			InvoiceEntity invoiceEntity = resultInvoiceList.get(0);
			List<DetailProductEntity> products = new ArrayList<DetailProductEntity>();
			DetailProductEntity detailProductEntity = new DetailProductEntity();
			ProductEntity productEntity = new ProductEntity();
			productEntity.setIdProd(1);
			detailProductEntity.setInvoice(invoiceEntity);
			detailProductEntity.setProduct(productEntity);
			products.add(detailProductEntity);
			invoiceEntity.setProducts(products);
			
			InvoiceEntity save = invoiceRepository.save(invoiceEntity);
			assertThat("Is present", save.getIdBilling()!= 0);
		}

	}
	
	@Test
	public void testGetTotalInvoiceSucceed() {
		Date referenceDate = new Date();
		Calendar ini = Calendar.getInstance();
		ini.setTime(referenceDate);
		ini.add(Calendar.MONTH, -1);
		Calendar end = Calendar.getInstance();
		end.setTime(referenceDate);
		String username="esalazar";
		List<InvoiceEntity> resultInvoiceList = invoiceRepository.findByCreationDateAndUsername(ini, end, username);
		LOG.debug("size:"+resultInvoiceList.size());
		if (!resultInvoiceList.isEmpty()) {
			InvoiceEntity invoiceEntity = resultInvoiceList.get(0);
			Optional<BigDecimal> total = invoiceEntity.getProducts().stream().map(x -> x.getProduct().getCost())
			.reduce((a, b) -> a.add(b));
			
			LOG.debug(total);
			invoiceEntity.setTotal(total.get());
			invoiceRepository.save(invoiceEntity);
		}
	
	}
}
