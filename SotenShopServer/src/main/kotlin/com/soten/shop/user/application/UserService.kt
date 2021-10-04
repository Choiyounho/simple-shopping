package com.soten.shop.user.application

import com.soten.shop.user.domain.User
import com.soten.shop.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService (
    private val userRepository: UserRepository
) {

    fun updateFcmToken(userId: Long): User {
        return find(userId).run {
            userRepository.save(this)
        } ?: throw IllegalStateException("사용자 정보 없음")
    }

    private fun find(userId: Long) = userRepository.findByIdOrNull(userId)
}
