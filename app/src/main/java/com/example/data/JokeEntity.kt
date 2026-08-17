package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.GeneratedJoke

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val statement: String,
    val whyHearsay: String,
    val whyHeresy: String,
    val finePrint: String?,
    val recipeTitle: String,
    val hearsaySetting: String,
    val dogmaName: String,
    val absurdistElement: String,
    val finePrintStyle: String?,
    val customPunchline: String? = null,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

fun JokeEntity.toDomainModel(): GeneratedJoke = GeneratedJoke(
    id = id,
    title = title,
    statement = statement,
    whyHearsay = whyHearsay,
    whyHeresy = whyHeresy,
    finePrint = finePrint,
    recipeTitle = recipeTitle,
    hearsaySetting = hearsaySetting,
    dogmaName = dogmaName,
    absurdistElement = absurdistElement,
    finePrintStyle = finePrintStyle,
    customPunchline = customPunchline,
    isFavorite = isFavorite,
    timestamp = timestamp
)

fun GeneratedJoke.toEntity(): JokeEntity = JokeEntity(
    id = id,
    title = title,
    statement = statement,
    whyHearsay = whyHearsay,
    whyHeresy = whyHeresy,
    finePrint = finePrint,
    recipeTitle = recipeTitle,
    hearsaySetting = hearsaySetting,
    dogmaName = dogmaName,
    absurdistElement = absurdistElement,
    finePrintStyle = finePrintStyle,
    customPunchline = customPunchline,
    isFavorite = isFavorite,
    timestamp = timestamp
)

@Entity(tableName = "custom_recipes")
data class CustomRecipeEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val setting: String,
    val dogma: String,
    val absurdistType: String,
    val hasFinePrint: Boolean,
    val finePrintCategory: String,
    val customHearsayChain: String?,
    val customAbsurdElement: String?,
    val customOrthodoxyTarget: String?,
    val customFinePrintCondition: String?,
    val isPreset: Boolean = false
)
