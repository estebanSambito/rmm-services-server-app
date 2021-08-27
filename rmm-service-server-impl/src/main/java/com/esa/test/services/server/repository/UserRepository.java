package com.esa.test.services.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esa.test.services.server.model.UserEntity;

/**
 *
 * @author esalazar
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public Optional<UserEntity> findByUserName(String userName);
}
