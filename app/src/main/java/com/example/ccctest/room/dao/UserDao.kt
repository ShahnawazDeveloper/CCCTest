package com.example.ccctest.room.dao

import androidx.room.*
import com.example.ccctest.room.entity.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Maybe<Long>

    @Query("SELECT * FROM users where email = :email")
    fun findByEmail(email: String): Single<User?>

    @Query("SELECT * FROM users where user_id = :id")
    fun findUserById(id: Long): Maybe<User>

    @Query("SELECT * FROM users")
    fun getAll(): Maybe<List<User>>

    @Update
    fun update(users: User): Maybe<Int>

    @Query("SELECT * FROM users WHERE email = :userEmail AND password = :userPassword LIMIT 1")
    fun findUserByEmailAndPassword(
        userEmail: String,
        userPassword: String
    ): Single<User?>//Maybe<User?>

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM users")
    fun deleteAllUsers()


}