package com.esa.test.services.server;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.esa.test.services.server.controller.UserController;
import com.esa.test.services.server.model.UserEntity;
import com.esa.test.services.server.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
 @ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
class UserControllerIntegrationTest {

	@Autowired
	   private MockMvc mvc;

//	   @MockBean
//	   private UsuarioController usuarioController;
	
	   @MockBean
	   private UserRepository userRepository;

	
	   private String baseUrl="http://localhost:8080";
	   private String apiPath="/api/v1/";
	
	   @Test
	   public void shoud_return_success_loginUsuario() throws Exception {
	       UserEntity usuario = new UserEntity();
	       usuario.setUserName("esalazar");
	       usuario.setPassword("esalazar");
	       
	       UserEntity usuarioEsperado = new UserEntity();
	       usuarioEsperado.setUserName("esalazar");
	       usuarioEsperado.setPassword("esalazar");
	       usuarioEsperado.setAutenticado(true);
	       
	       Optional<UserEntity> userResp = Optional.of(usuarioEsperado);
	       //ResponseEntity<UserEntity> usuarioResp = ResponseEntity.ok(usuarioEsperado);

	       given(userRepository.findByUserName(usuario.getUserName())).willReturn(userResp);
	       
	       RequestBuilder request = MockMvcRequestBuilders.post(baseUrl+apiPath+"/login")
	    		   .content(asJsonString(usuario))
	               .contentType(APPLICATION_JSON);
	       
	       mvc.perform(request)
	    		   .andExpect(status().isOk())
	               .andExpect(jsonPath("autenticado", is(usuarioEsperado.isAutenticado())))
	               .andExpect(jsonPath("token", not("")))
	               .andDo(document("login"));;
	   }
	   
	   @Test
	   public void shoud_return_user_not_found_loginUsuario() throws Exception {
	       UserEntity usuario = new UserEntity();
	       usuario.setUserName("esalazar");
	       usuario.setPassword("12313");
	       
	       RequestBuilder request = MockMvcRequestBuilders.post(baseUrl+apiPath+"/login")
	    		   .content(asJsonString(usuario))
	               .contentType(APPLICATION_JSON);
	       
	       mvc.perform(request)
	    		   .andExpect(status().isNotFound())
	    		   .andExpect(jsonPath("message", is("User not found[" + usuario.getUserName()+"]")));
	       }
	   
	   @Test
	   public void shoud_return_invalidCredentials_loginUsuario() throws Exception {
		   UserEntity usuario = new UserEntity();
	       usuario.setUserName("esalazar");
	       usuario.setPassword("esalazar");
	       
	       UserEntity usuarioEsperado = new UserEntity();
	       usuarioEsperado.setUserName("esalazar");
	       usuarioEsperado.setPassword("123456");
	       usuarioEsperado.setAutenticado(true);
	       
	       Optional<UserEntity> userResp = Optional.of(usuarioEsperado);
	       //ResponseEntity<UserEntity> usuarioResp = ResponseEntity.ok(usuarioEsperado);

	       given(userRepository.findByUserName(usuario.getUserName())).willReturn(userResp);
	 
	       RequestBuilder request = MockMvcRequestBuilders.post(baseUrl+apiPath+"/login")
	    		   .content(asJsonString(usuario))
	               .contentType(APPLICATION_JSON);
	       
	       mvc.perform(request)
	    		   .andExpect(status().isNotFound())
	    		   .andExpect(jsonPath("message", is("Authentication error[" + usuario.getUserName()+"]")));
	       }
	   
	   public static String asJsonString(final Object obj) {
		    try {
		      return new ObjectMapper().writeValueAsString(obj);
		    } catch (Exception e) {
		      throw new RuntimeException(e);
		    }
		  }

}
