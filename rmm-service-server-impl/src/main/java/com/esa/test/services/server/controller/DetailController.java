package com.esa.test.services.server.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esa.test.services.server.exception.ResourceAlreadyExistsException;
import com.esa.test.services.server.exception.ResourceNotFoundException;
import com.esa.test.services.server.model.DetailProductEntity;
import com.esa.test.services.server.model.InvoiceEntity;
import com.esa.test.services.server.model.ProductEntity;
import com.esa.test.services.server.model.UserEntity;
import com.esa.test.services.server.repository.DetailRepository;
import com.esa.test.services.server.repository.DeviceRepository;
import com.esa.test.services.server.repository.InvoiceRepository;
import com.esa.test.services.server.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * 
 * @author esalazar
 *
 */
@RestController
@RequestMapping("/api/v1/detailservice")
public class DetailController {
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DetailRepository detailRepository;
	@Autowired
	private DeviceRepository deviceRepository;
	
	private final String PREFIX = "Bearer ";
	
	private static final Log LOG=LogFactory.getLog(DetailController.class);
	
	@PostMapping()
	public ResponseEntity<InvoiceEntity> addService(@RequestBody DetailProductEntity itemEnviado,@RequestHeader (name="Authorization") String token)
			throws ResourceAlreadyExistsException, ResourceNotFoundException {
		
		Date referenceDate = new Date();
		Calendar ini = Calendar.getInstance();
		ini.setTime(referenceDate);
		ini.add(Calendar.MONTH, -1);
		Calendar end = Calendar.getInstance();
		end.setTime(referenceDate);
		LOG.debug("--->"+token);
		String jwtToken = token.replace(PREFIX, "");
		Claims allClaimsFromToken = getAllClaimsFromToken(jwtToken);
		String subject = allClaimsFromToken.getSubject();
		LOG.info("sub:" + subject);
		LOG.info("ESA -->" + subject);
		String username=subject;
	
		
		List<InvoiceEntity> invoiceList = invoiceRepository.findByCreationDateAndUsername(ini,end,username);
		InvoiceEntity currentInvoice;
		InvoiceEntity save;
		LOG.info("total: " + invoiceList.size());
		if (invoiceList== null || invoiceList.size()==0) {
			LOG.info("not found: ");
			currentInvoice = new InvoiceEntity();
			currentInvoice.setCreationDate(ini);
			Optional<UserEntity> user = userRepository.findByUserName(username);
			currentInvoice.setUser(user.get());
			
			save = invoiceRepository.save(currentInvoice);
			LOG.info("created: " + save.getIdBilling());
			//debe enviar el ID de item y el id de producto
			Optional<ProductEntity> findById = deviceRepository.findById(itemEnviado.getProduct().getIdProd());
			if(!findById.isPresent()) {
				throw new ResourceNotFoundException("Product send not found");
			}
			ProductEntity productEntity = findById.get();
			LOG.info("product: " + productEntity);
			itemEnviado.setProduct(productEntity);
			itemEnviado.setInvoice(save);
			detailRepository.save(itemEnviado);
		}else{
			currentInvoice = invoiceList.get(0);
			LOG.info("found: " +currentInvoice);
			Optional<DetailProductEntity> detailElement = currentInvoice.getProducts().stream().filter(x->itemEnviado.getIdDetail() ==x.getIdDetail()).findFirst();
			if(!detailElement.isPresent()) {
				Optional<ProductEntity> findById = deviceRepository.findById(itemEnviado.getProduct().getIdProd());
				if(!findById.isPresent()) {
					throw new ResourceNotFoundException("Product send not found");
				}
				itemEnviado.setProduct(findById.get());
				itemEnviado.setInvoice(currentInvoice);
				LOG.info("item to save : "+itemEnviado);
				detailRepository.save(itemEnviado);
			}
			save = currentInvoice;

		}

		return ResponseEntity.ok(save);

	}
	
	private Claims getAllClaimsFromToken(String token) {
    	String secretKey = "mySecretKey";
    	Claims body = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
        return body;
    }
	
	@DeleteMapping("/")
	public BodyBuilder deleteService(@RequestBody DetailProductEntity itemEnviado)
			throws ResourceNotFoundException {
		
		Date referenceDate = new Date();
		Calendar ini = Calendar.getInstance();
		ini.setTime(referenceDate);
		ini.add(Calendar.MONTH, -1);
		Calendar end = Calendar.getInstance();
		end.setTime(referenceDate);
		String username="esalazar";
	
		
		List<InvoiceEntity> invoiceList = invoiceRepository.findByCreationDateAndUsername(ini,end,username);
		InvoiceEntity currentInvoice;
		if (invoiceList== null || invoiceList.size()==0) {
			throw new ResourceNotFoundException("Services are empty");
			
		}else{
			currentInvoice = invoiceList.get(0);
		}
		Optional<DetailProductEntity> detailElement = currentInvoice.getProducts().stream()
				.filter(x -> itemEnviado.getIdDetail() == (x.getIdDetail())).findFirst();
		if (detailElement.isPresent()) {
			detailRepository.delete(detailElement.get());
		}
		return ResponseEntity.ok();
		
	}

}
