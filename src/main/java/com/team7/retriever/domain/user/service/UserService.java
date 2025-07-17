package com.team7.retriever.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team7.retriever.domain.user.controller.dto.request.GrantRequest;
import com.team7.retriever.domain.user.controller.dto.response.UserInfoResponse;
import com.team7.retriever.domain.user.domain.document.User;
import com.team7.retriever.domain.user.domain.enums.Role;
import com.team7.retriever.global.exception.BadRequestException;
import com.team7.retriever.global.exception.NotFoundException;
import com.team7.retriever.global.exception.UserErrorCode;
import com.team7.retriever.domain.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public List<UserInfoResponse> getAllUsers() {
		return userRepository.findAll()
			.stream()
			.map(UserInfoResponse::from)
			.toList();
	}

	@Transactional
	public String grantRole(String adminId, GrantRequest grantRequest) {
		String employeeId = grantRequest.employeeId();
		Role role = grantRequest.role();

		if (role == Role.ROOT){
			throw new BadRequestException(UserErrorCode.CAN_NOT_GRANT_ROOT_ROLE);
		}

		User user = userRepository.findByEmployeeId(employeeId)
			.orElseThrow(() -> new NotFoundException(UserErrorCode.NOT_FOUND));

		if (user.getId().equals(adminId)) {
			throw new BadRequestException(UserErrorCode.CAN_NOT_CHANGE_MY_ROLE);
		}

		if (user.getRole() == role) {
			throw new BadRequestException(UserErrorCode.ALREADY_GRANTED_ROLE);
		}

		user.updateRole(role);
		userRepository.save(user);

		log.info("employeeId: {} 에게 {} 역할을 부여했습니다.", employeeId, role);

		return user.getName() + "님에게 " + role.getRoleName()
			+ " 권한을 부여했습니다. 변경된 권한을 얻기 위해서는 로그아웃 후 다시 로그인해야 합니다.";
	}
}
