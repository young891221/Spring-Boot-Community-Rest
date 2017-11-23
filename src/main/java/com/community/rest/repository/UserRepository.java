package com.community.rest.repository;

import com.community.rest.domain.User;
import com.community.rest.domain.projection.UserOnlyContainName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UserOnlyContainName.class)
public interface UserRepository extends JpaRepository<User, Long> {
}
