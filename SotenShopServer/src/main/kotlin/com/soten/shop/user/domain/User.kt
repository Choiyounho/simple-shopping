package com.soten.shop.user.domain

import com.soten.shop.common.BaseEntity
import javax.persistence.Entity

@Entity(name = "user")
class User(
	val email: String,
	val password: String,
	val name: String,
	var cardName: String? = null
) : BaseEntity()
