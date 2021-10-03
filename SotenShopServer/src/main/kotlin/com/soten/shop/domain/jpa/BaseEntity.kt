package com.soten.shop.domain.jpa

import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	open var id: Int? = null

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
