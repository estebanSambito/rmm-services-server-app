package com.esa.test.services.server;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.esa.test.services.server.controller.UsuarioController;
import com.esa.test.services.server.model.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
//@AutoConfigureMockMvc
class RmmServicesServerAppApplicationTests {

	@Autowired
	   private MockMvc mvc;

	   @MockBean
	   private UsuarioController usuarioController;
	   
	   private String baseUrl="http://localhost:8080";
	   private String apiPath="/api/v1/";
	
	   @Test
	   public void loginUsuario() throws Exception {
	       UserEntity usuario = new UserEntity();
	       usuario.setUserName("esalazar");
	       usuario.setPassword("esalazar");
	       
	       UserEntity usuarioEsperado = new UserEntity();
	       usuarioEsperado.setUserName("esalazar");
	       usuarioEsperado.setPassword("esalazar");
	       usuarioEsperado.setAutenticado(true);
	       ResponseEntity<UserEntity> usuarioResp = ResponseEntity.ok(usuarioEsperado);

	       given(usuarioController.loginUsuario(usuario)).willReturn(usuarioResp);

	       mvc.perform(post(baseUrl + apiPath + "login")
	    		   .content(asJsonString(usuario))
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk())
	               .andExpect(jsonPath("autenticado", is(usuarioEsperado.isAutenticado())));
	   }
	   
	   public static String asJsonString(final Object obj) {
		    try {
		      return new ObjectMapper().writeValueAsString(obj);
		    } catch (Exception e) {
		      throw new RuntimeException(e);
		    }
		  }

}
