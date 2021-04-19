package com.whackdackery.rota.app.service.user.repository;

import com.whackdackery.rota.app.service.user.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
