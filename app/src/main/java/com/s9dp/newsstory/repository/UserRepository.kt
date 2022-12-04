package com.s9dp.newsstory.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.s9dp.newsstory.UserPreferences
import com.s9dp.newsstory.data.protodatastore.DataStoreSetting
import com.s9dp.newsstory.data.protodatastore.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore : DataStore<UserPreferences> by dataStore(
        fileName = "newsStory",
        serializer = DataStoreSetting
    )

    suspend fun writeToLocal(isLogged : Boolean, fullName : String, email : String) = context.dataStore.updateData { user->
        user.toBuilder()
            .setIsLogged(isLogged)
            .setFullName(fullName)
            .setEmail(email)
            .build()
    }

    val readToLocal : Flow<User> = context.dataStore.data
        .catch {
            if(this is Exception){
                Log.d("main", "${this.message}")
            }
        }.map {
            val  user = User(it.isLogged, it.fullName, it.email)
            user
        }
}