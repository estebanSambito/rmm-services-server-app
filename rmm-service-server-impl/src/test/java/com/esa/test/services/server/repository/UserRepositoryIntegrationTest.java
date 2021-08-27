package com.esa.test.services.server.repository;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.esa.test.services.server.model.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql({"/initialData.sql"})
public class UserRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testLoginSucceed() {
//		UserEntity entity = new UserEntity();
//		entity.setUserName("esalazar");
//		entity.setPassword("12312");
//		userRepository.save(entity);
		
		
		Optional<UserEntity> findByUserName = userRepository.findByUserName("esalazar");
		assertThat("Is present", findByUserName.isPresent());
	}
}
