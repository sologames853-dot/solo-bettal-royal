package com.solofirebettelroyal.model

data class User(
    val id: String,
    val username: String,
    val email: String?,
    val loginType: LoginType,
    val role: UserRole = UserRole.USER,
    val level: Int = 1,
    val inventory: List<String> = emptyList()
)

enum class LoginType {
    GOOGLE, FACEBOOK, GUEST
}

enum class UserRole {
    ADMIN, USER
}
