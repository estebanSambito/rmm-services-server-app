package com.esa.test.services.server.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esa.test.services.server.exception.ResourceNotFoundException;
import com.esa.test.services.server.model.UserEntity;
import com.esa.test.services.server.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author esalazar
 *
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
	@Autowired
	private UserRepository usuarioRepository;

	@GetMapping()
	public String hello() {
		return "esa";
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserEntity> loginUsuario(@RequestBody UserEntity usuarioEnviado) throws ResourceNotFoundException {
		UserEntity usuario = usuarioRepository.findByUserName(usuarioEnviado.getUserName())
				.orElseThrow(() -> new ResourceNotFoundException("User not found[" + usuarioEnviado.getUserName()+"]"));
		if (usuarioEnviado.getPassword()!= null && 
				usuarioEnviado.getPassword().equals(usuario.getPassword())){
			String token = getJWTToken(usuario);
//			String token = "BEARER asfasfad";
			
			UserEntity uResponse = new UserEntity();
			uResponse.setUserName(usuarioEnviado.getUserName());
			uResponse.setToken(token);
			uResponse.setAutenticado(true);
			return ResponseEntity.ok(uResponse);
		}else {
			throw new ResourceNotFoundException("Authentication error[" + usuarioEnviado.getUserName()+"]");
		}
				
	}
	
	private String getJWTToken(UserEntity username) {
		
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER"+","+"CLIENT");
		//TODO Permisos para acceder al API en base a ROLES
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username.getUserName())
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1200000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
