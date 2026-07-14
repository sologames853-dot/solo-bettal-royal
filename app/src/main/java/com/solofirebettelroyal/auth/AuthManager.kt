package com.solofirebettelroyal.auth

import com.solofirebettelroyal.model.LoginType
import com.solofirebettelroyal.model.User
import com.solofirebettelroyal.model.UserRole

object AuthManager {
    private var currentUser: User? = null

    fun login(type: LoginType, username: String): User {
        // Mocking login logic without Firebase for now
        val role = if (username.lowercase() == "admin") UserRole.ADMIN else UserRole.USER
        val user = User(
            id = "user_${System.currentTimeMillis()}",
            username = username,
            email = if (type != LoginType.GUEST) "$username@example.com" else null,
            loginType = type,
            role = role
        )
        currentUser = user
        return user
    }

    fun getCurrentUser(): User? = currentUser

    fun isAdmin(): Boolean = currentUser?.role == UserRole.ADMIN

    fun logout() {
        currentUser = null
    }
}
