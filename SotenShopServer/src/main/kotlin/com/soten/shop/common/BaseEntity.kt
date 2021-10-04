package com.soten.shop.common

import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	open var id: Long? = null

	open var createdAt: LocalDateTime? = null
	open var updatedAt: LocalDateTime? = null

	@PrePersist
	fun prePersist() {
		createdAt = LocalDateTime.now()
		updatedAt = LocalDateTime.now()
	}

	@PreUpdate
	fun preUpdate() {
		updatedAt = LocalDateTime.now()
	}

}
