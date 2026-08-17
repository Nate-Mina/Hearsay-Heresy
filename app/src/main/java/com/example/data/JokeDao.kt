package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes ORDER BY timestamp DESC")
    fun getAllJokes(): Flow<List<JokeEntity>>

    @Query("SELECT * FROM jokes WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteJokes(): Flow<List<JokeEntity>>

    @Query("SELECT * FROM jokes WHERE id = :id LIMIT 1")
    suspend fun getJokeById(id: Long): JokeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: JokeEntity): Long

    @Update
    suspend fun updateJoke(joke: JokeEntity)

    @Query("UPDATE jokes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE jokes SET statement = :statement, customPunchline = :customPunchline, whyHearsay = :whyHearsay, whyHeresy = :whyHeresy, finePrint = :finePrint WHERE id = :id")
    suspend fun updateJokeContent(
        id: Long,
        statement: String,
        customPunchline: String?,
        whyHearsay: String,
        whyHeresy: String,
        finePrint: String?
    )

    @Query("DELETE FROM jokes WHERE id = :id")
    suspend fun deleteJokeById(id: Long)

    @Query("DELETE FROM jokes WHERE isFavorite = 0")
    suspend fun clearNonFavorites()

    // Custom Recipes
    @Query("SELECT * FROM custom_recipes ORDER BY title ASC")
    fun getAllCustomRecipes(): Flow<List<CustomRecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomRecipe(recipe: CustomRecipeEntity)

    @Query("DELETE FROM custom_recipes WHERE id = :id")
    suspend fun deleteCustomRecipeById(id: String)
}
