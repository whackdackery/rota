package com.whackdackery.rota.app.service.user.repository;

import com.whackdackery.rota.app.service.user.domain.SystemRole;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemRoleRepository extends PagingAndSortingRepository<SystemRole, Long> {

}
