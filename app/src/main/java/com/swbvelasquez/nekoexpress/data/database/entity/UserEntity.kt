package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.swbvelasquez.nekoexpress.domain.model.UserModel

@Entity(
    tableName = "UserTable",
    indices = [Index(name = "user_alternative_pk_idx",value = ["email"])],
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="userId") val userId:Long,
    @ColumnInfo(name="email") val email:String,
    @ColumnInfo(name="phone") val phone:String,
    @ColumnInfo(name="firstName") val firstName:String,
    @ColumnInfo(name="lastName") val lastName:String,
    @ColumnInfo(name="image") val image:String,
    @ColumnInfo(name="registerDate") val registerDate:Long
)

fun UserModel.toUserEntity() = UserEntity(userId=userId,email=email,phone=phone,firstName=firstName,lastName=lastName,image=image,registerDate=registerDate)