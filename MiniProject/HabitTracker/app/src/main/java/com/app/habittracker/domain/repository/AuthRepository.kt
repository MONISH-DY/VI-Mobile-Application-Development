package com.app.habittracker.domain.repository
 
import com.app.habittracker.data.local.PreferencesManager
import com.app.habittracker.data.local.dao.UserDao
import com.app.habittracker.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val userDao: UserDao
) {
    val currentUserId: Flow<String?> = preferencesManager.currentUserId
    
    suspend fun login(phoneNumber: String, pin: String): Result<Unit> {
        return try {
            val user = userDao.getUserByPhone(phoneNumber)
            if (user != null && user.pin == pin) {
                preferencesManager.saveCurrentUserId(user.id)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Invalid phone number or PIN"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(phoneNumber: String, pin: String, name: String): Result<Unit> {
        return try {
            val existingUser = userDao.getUserByPhone(phoneNumber)
            if (existingUser != null) {
                return Result.failure(Exception("Phone number already registered"))
            }
            
            val userId = java.util.UUID.randomUUID().toString()
            val newUser = UserEntity(
                id = userId,
                phoneNumber = phoneNumber,
                pin = pin,
                name = name
            )
            userDao.insertUser(newUser)
            preferencesManager.saveCurrentUserId(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        preferencesManager.saveCurrentUserId(null)
    }

    suspend fun isUserLoggedIn(): Boolean {
        return preferencesManager.currentUserId.first() != null
    }
}
