package com.esa.test.services.server.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esa.test.services.server.exception.ResourceAlreadyExistsException;
import com.esa.test.services.server.exception.ResourceNotFoundException;
import com.esa.test.services.server.model.ProductEntity;
import com.esa.test.services.server.model.enums.ProductTypes;
import com.esa.test.services.server.repository.DeviceRepository;

/**
 * 
 * @author esalazar
 *
 */
@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {
	@Autowired
	private DeviceRepository deviceRepository;
	
	@GetMapping()
	public ResponseEntity<List<ProductEntity>> getAllDevices()
			throws ResourceNotFoundException {
		List<ProductEntity>  devices = deviceRepository.findByType(ProductTypes.DEVICE.getName());
		return ResponseEntity.ok(devices);
		
	}

	@PostMapping()
	public ResponseEntity<ProductEntity> addDevice(@RequestBody ProductEntity productoEnviado)
			throws ResourceAlreadyExistsException {
		Optional<ProductEntity> producto = deviceRepository.findById(productoEnviado.getIdProd());

		if (producto.isPresent()) {
			throw new ResourceAlreadyExistsException("Product already registred [" + productoEnviado.getName() + "]");
		}
		productoEnviado.setType(ProductTypes.DEVICE.getName());

		ProductEntity uResponse = deviceRepository.save(productoEnviado);

		return ResponseEntity.ok(uResponse);

	}
	
	@PutMapping("/")
	public ResponseEntity<ProductEntity> updateDevice(@RequestBody ProductEntity productoEnviado)
			throws ResourceNotFoundException {
		
		ProductEntity productoEncontrado = deviceRepository.findById(productoEnviado.getIdProd())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found[" + productoEnviado+"]"));
		
		productoEncontrado.setCost(productoEnviado.getCost());
		productoEncontrado.setName(productoEnviado.getName());
		ProductEntity uResponse = deviceRepository.save(productoEnviado);

		return ResponseEntity.ok(uResponse);

	}
	@DeleteMapping("/")
	public ResponseEntity<ProductEntity> deleteDevice(@RequestBody ProductEntity productoEnviado)
			throws ResourceNotFoundException {
		
		ProductEntity productoEncontrado = deviceRepository.findById(productoEnviado.getIdProd())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found[" + productoEnviado+"]"));
		
		deviceRepository.delete(productoEncontrado);
		
		return ResponseEntity.ok(productoEncontrado);
		
	}

}
