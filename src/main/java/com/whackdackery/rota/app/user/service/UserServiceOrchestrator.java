package com.whackdackery.rota.app.user.service;

import com.whackdackery.rota.app.common.service.ServiceOrchestrator;
import com.whackdackery.rota.app.user.model.User;
import com.whackdackery.rota.app.user.model.dto.UserGetDto;
import com.whackdackery.rota.app.user.model.dto.UserPostDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceOrchestrator extends ServiceOrchestrator<User, UserGetDto, UserPostDto, UserGetService> {

}
