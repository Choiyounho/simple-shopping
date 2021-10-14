package com.soten.shop.auth.interceptor

import com.soten.shop.user.domain.UserRepository
import com.soten.shop.common.ShopException
import com.soten.shop.user.domain.User
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SignUpService @Autowired constructor(
	private val userRepository: UserRepository
) {

	fun signup(signUpRequest: SignUpRequest) {
		validateRequest(signUpRequest)
		checkDuplicates(signUpRequest.email)
		registerUser(signUpRequest)
	}

	private fun validateRequest(signUpRequest: SignUpRequest) {
		validateEmail(signUpRequest.email)
		validateName(signUpRequest.name)
		validatePassword(signUpRequest.password)
	}

	private fun validateEmail(email: String) {
		val isNotValidEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
			.toRegex(RegexOption.IGNORE_CASE)
			.matches(email)
			.not()

		if (isNotValidEmail) {
			throw ShopException("이메일 형식이 올바르지 않습니다.")
		}
	}

	private fun validateName(name: String) {
		if (name.trim().length !in 2..20)
			throw ShopException("이름은 2자 이상 20자 이하여야 합니다.")
	}

	private fun validatePassword(password: String) {
		if (password.trim().length !in 8..20)
			throw ShopException("비밀번호는 공백을 제외하고 8자 이상 20자 이하여야 합니다.")
	}

	private fun checkDuplicates(email: String) =
		userRepository.findByEmail(email)?.let {
			throw ShopException("이미 사용 중인 이메일입니다.")
		}

	private fun registerUser(signUpRequest: SignUpRequest) =
		with(signUpRequest) {
			val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
			val user = User(email, hashedPassword, name)
			userRepository.save(user)
		}

}
