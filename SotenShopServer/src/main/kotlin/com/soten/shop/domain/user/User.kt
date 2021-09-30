package com.soten.shop.domain.user

import com.soten.shop.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "user")
class User(
	var email: String,
	var password: String,
	var name: String,
	var cardName: String? = null
) : BaseEntity()
