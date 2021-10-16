package com.soten.shop.user.application

import com.soten.shop.user.domain.User
import com.soten.shop.user.domain.UserRepository
import com.soten.shop.user.dto.CardNameUpdateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
	private val userRepository: UserRepository
) {

	fun updateCardName(cardNameUpdateRequest: CardNameUpdateRequest): User {
		val userId = cardNameUpdateRequest.userId
		val user = userRepository.findById(userId)
		user.updateCardName(cardNameUpdateRequest.cardName)
		return user
	}

}
