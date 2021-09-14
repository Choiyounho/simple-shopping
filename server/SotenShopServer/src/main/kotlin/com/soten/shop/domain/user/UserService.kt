package com.soten.shop.domain.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {

    fun updateFcmToken(userId: Long) =
        userRepository.findByIdOrNull(userId)?.run {
            userRepository.save(this)
        } ?: throw IllegalStateException("사용자 정보 없음")

    fun find(userId: Long) = userRepository.findByIdOrNull(userId)

}
