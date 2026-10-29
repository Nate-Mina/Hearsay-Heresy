package com.example.data

import android.content.Context
import com.example.generator.JokeGeneratorEngine
import com.example.generator.JokeSlot
import com.example.model.GeneratedJoke
import com.example.model.JokeCustomizationParams
import com.example.model.JokeRecipe
import com.example.network.GeminiNetworkClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JokeRepository(context: Context) {
    private val jokeDao: JokeDao = AppDatabase.getInstance(context).jokeDao()

    val allJokes: Flow<List<GeneratedJoke>> = jokeDao.getAllJokes().map { entities ->
        entities.map { it.toDomainModel() }
    }

    val favoriteJokes: Flow<List<GeneratedJoke>> = jokeDao.getFavoriteJokes().map { entities ->
        entities.map { it.toDomainModel() }
    }

    val customRecipes: Flow<List<CustomRecipeEntity>> = jokeDao.getAllCustomRecipes()

    suspend fun saveJoke(joke: GeneratedJoke): Long {
        return jokeDao.insertJoke(joke.toEntity())
    }

    suspend fun toggleFavorite(joke: GeneratedJoke) {
        jokeDao.updateFavorite(joke.id, !joke.isFavorite)
    }

    suspend fun updateJokeContent(
        id: Long,
        statement: String,
        customPunchline: String?,
        whyHearsay: String,
        whyHeresy: String,
        finePrint: String?
    ) {
        jokeDao.updateJokeContent(id, statement, customPunchline, whyHearsay, whyHeresy, finePrint)
    }

    suspend fun deleteJoke(id: Long) {
        jokeDao.deleteJokeById(id)
    }

    suspend fun clearHistory() {
        jokeDao.clearNonFavorites()
    }

    suspend fun saveCustomRecipe(recipe: CustomRecipeEntity) {
        jokeDao.insertCustomRecipe(recipe)
    }

    suspend fun deleteCustomRecipe(id: String) {
        jokeDao.deleteCustomRecipeById(id)
    }

    suspend fun generateJoke(
        recipe: JokeRecipe,
        customInstruction: String? = null,
        customizationParams: JokeCustomizationParams? = null
    ): GeneratedJoke {
        // Check if Gemini AI is available and user requested custom twist or AI generation
        if (GeminiNetworkClient.hasValidApiKey()) {
            val aiResponse = GeminiNetworkClient.generateHeresyWithAI(
                setting = recipe.setting.label,
                dogma = recipe.dogma.label,
                orthodoxDogma = recipe.dogma.orthodoxDogma,
                violationDesc = recipe.dogma.violationDesc,
                absurdTheme = recipe.customAbsurdElement ?: recipe.absurdistType.label,
                finePrintStyle = if (recipe.hasFinePrint) recipe.finePrintCategory.label else null,
                keywords = customizationParams?.keywords?.takeIf { it.isNotBlank() },
                humorCategory = customizationParams?.humorCategory?.label,
                targetAudience = customizationParams?.targetAudience?.label,
                tone = customizationParams?.tone?.label,
                userModificationPrompt = customInstruction
            )

            if (!aiResponse.isNullOrBlank()) {
                val parsedJoke = parseAiJokeResponse(aiResponse, recipe, customizationParams)
                val savedId = saveJoke(parsedJoke)
                return parsedJoke.copy(id = savedId)
            }
        }

        // Fallback or offline combinatorial recipe engine (authentic formulas from PDF + advanced customizations)
        val generated = JokeGeneratorEngine.generateJoke(recipe, customizationParams, customInstruction)
        val savedId = saveJoke(generated)
        return generated.copy(id = savedId)
    }

    suspend fun mutateSlotAndSave(joke: GeneratedJoke, slot: JokeSlot): GeneratedJoke {
        val mutated = JokeGeneratorEngine.mutateSlot(joke, slot)
        jokeDao.updateJoke(mutated.toEntity())
        return mutated
    }

    private fun parseAiJokeResponse(
        text: String,
        recipe: JokeRecipe,
        customParams: JokeCustomizationParams? = null
    ): GeneratedJoke {
        var title = recipe.title
        var statement = ""
        var whyHearsay = ""
        var whyHeresy = ""
        var finePrint: String? = null

        val lines = text.lines()
        for (line in lines) {
            val trimmed = line.trim()
            when {
                trimmed.startsWith("TITLE:", ignoreCase = true) -> {
                    title = trimmed.substringAfter(":").trim()
                }
                trimmed.startsWith("STATEMENT:", ignoreCase = true) -> {
                    statement = trimmed.substringAfter(":").trim()
                }
                trimmed.startsWith("WHY HEARSAY:", ignoreCase = true) -> {
                    whyHearsay = trimmed.substringAfter(":").trim()
                }
                trimmed.startsWith("WHY HERESY:", ignoreCase = true) -> {
                    whyHeresy = trimmed.substringAfter(":").trim()
                }
                trimmed.startsWith("THE FINE PRINT:", ignoreCase = true) -> {
                    val fp = trimmed.substringAfter(":").trim()
                    if (fp.isNotBlank() && !fp.equals("None", ignoreCase = true) && !fp.equals("N/A", ignoreCase = true)) {
                        finePrint = fp
                    }
                }
            }
        }

        // If statement was not parsed cleanly, fallback to full text
        if (statement.isBlank()) {
            statement = "\"${text.take(200)}\""
            whyHearsay = "Unverified transmission chain."
            whyHeresy = "Theological contradiction."
        }

        return GeneratedJoke(
            id = System.currentTimeMillis(),
            title = title,
            statement = statement,
            whyHearsay = whyHearsay,
            whyHeresy = whyHeresy,
            finePrint = finePrint ?: if (recipe.hasFinePrint) "Standard celestial terms and conditions apply." else null,
            recipeTitle = recipe.title,
            hearsaySetting = recipe.setting.label,
            dogmaName = recipe.dogma.label,
            absurdistElement = recipe.customAbsurdElement ?: recipe.absurdistType.label,
            finePrintStyle = if (recipe.hasFinePrint) recipe.finePrintCategory.label else null,
            keywords = customParams?.keywords?.takeIf { it.isNotBlank() },
            humorCategory = customParams?.humorCategory?.label,
            targetAudience = customParams?.targetAudience?.label,
            tone = customParams?.tone?.label
        )
    }
}
