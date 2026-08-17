package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CustomRecipeEntity
import com.example.data.JokeRepository
import com.example.generator.JokeGeneratorEngine
import com.example.generator.JokeSlot
import com.example.model.AbsurdistType
import com.example.model.DogmaCategory
import com.example.model.FinePrintCategory
import com.example.model.GeneratedJoke
import com.example.model.HearsaySetting
import com.example.model.JokeRecipe
import com.example.util.OratorPersona
import com.example.util.ShareHelper
import com.example.util.VocalOratorEngine
import com.example.util.VocalOratorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

enum class AppTab(val label: String, val iconName: String) {
    GENERATOR("Joke Scriptorium", "Create"),
    RECIPE_LAB("Recipe Lab", "Tune"),
    RELIQUARY("Saved Heresies", "Favorites"),
    RECIPE_BOOK("Guidebook", "Book")
}

data class JokeUiState(
    val currentTab: AppTab = AppTab.GENERATOR,
    val activeRecipe: JokeRecipe = JokeGeneratorEngine.PRESET_RECIPES.first(),
    val activeJoke: GeneratedJoke? = null,
    val isGenerating: Boolean = false,
    val allJokes: List<GeneratedJoke> = emptyList(),
    val favoriteJokes: List<GeneratedJoke> = emptyList(),
    val customSavedRecipes: List<JokeRecipe> = emptyList(),
    val presetRecipes: List<JokeRecipe> = JokeGeneratorEngine.PRESET_RECIPES,
    val showSlotCustomizerDialog: Boolean = false,
    val showShareDialog: Boolean = false,
    val showRecipeSaveDialog: Boolean = false,
    val showVocalDialog: Boolean = false,
    val vocalState: VocalOratorState = VocalOratorState(),
    val selectedShareFormat: ShareHelper.ShareFormat = ShareHelper.ShareFormat.FULL_INQUEST,
    val searchQuery: String = "",
    val dogmaFilter: DogmaCategory? = null,
    val customTwistInput: String = "",
    val statusMessage: String? = null
)

class JokeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = JokeRepository(application)
    private val vocalOratorEngine = VocalOratorEngine(application)

    private val _uiState = MutableStateFlow(JokeUiState())
    val uiState: StateFlow<JokeUiState> = _uiState.asStateFlow()

    init {
        // Initial auto-generation with first PDF preset
        val initialJoke = JokeGeneratorEngine.generateJoke(JokeGeneratorEngine.PRESET_RECIPES.first())
        _uiState.update { it.copy(activeJoke = initialJoke) }

        // Observe TTS state
        viewModelScope.launch {
            vocalOratorEngine.state.collect { vState ->
                _uiState.update { it.copy(vocalState = vState) }
            }
        }

        // Observe jokes from Room DB
        viewModelScope.launch {
            repository.allJokes.collect { jokes ->
                _uiState.update { it.copy(allJokes = jokes) }
            }
        }

        viewModelScope.launch {
            repository.favoriteJokes.collect { favs ->
                _uiState.update { it.copy(favoriteJokes = favs) }
            }
        }

        viewModelScope.launch {
            repository.customRecipes.collect { customEntities ->
                val recipes = customEntities.map { entity ->
                    JokeRecipe(
                        id = entity.id,
                        title = entity.title,
                        description = entity.description,
                        setting = HearsaySetting.entries.find { it.name == entity.setting } ?: HearsaySetting.MEDIEVAL_VILLAGE,
                        dogma = DogmaCategory.entries.find { it.name == entity.dogma } ?: DogmaCategory.HOLY_TRINITY,
                        absurdistType = AbsurdistType.entries.find { it.name == entity.absurdistType } ?: AbsurdistType.JUNK_FOOD,
                        hasFinePrint = entity.hasFinePrint,
                        finePrintCategory = FinePrintCategory.entries.find { it.name == entity.finePrintCategory } ?: FinePrintCategory.IT_HELPDESK,
                        customHearsayChain = entity.customHearsayChain,
                        customAbsurdElement = entity.customAbsurdElement,
                        customOrthodoxyTarget = entity.customOrthodoxyTarget,
                        customFinePrintCondition = entity.customFinePrintCondition,
                        isPreset = false
                    )
                }
                _uiState.update { it.copy(customSavedRecipes = recipes) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        vocalOratorEngine.shutdown()
    }

    fun setTab(tab: AppTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun selectRecipe(recipe: JokeRecipe) {
        _uiState.update { it.copy(activeRecipe = recipe) }
        generateCurrentJoke()
    }

    fun updateSetting(setting: HearsaySetting) {
        _uiState.update {
            val updated = it.activeRecipe.copy(setting = setting)
            it.copy(activeRecipe = updated)
        }
    }

    fun updateDogma(dogma: DogmaCategory) {
        _uiState.update {
            val updated = it.activeRecipe.copy(dogma = dogma)
            it.copy(activeRecipe = updated)
        }
    }

    fun updateAbsurdistType(absurdistType: AbsurdistType) {
        _uiState.update {
            val updated = it.activeRecipe.copy(absurdistType = absurdistType)
            it.copy(activeRecipe = updated)
        }
    }

    fun updateFinePrint(hasFinePrint: Boolean, category: FinePrintCategory = _uiState.value.activeRecipe.finePrintCategory) {
        _uiState.update {
            val updated = it.activeRecipe.copy(
                hasFinePrint = hasFinePrint,
                finePrintCategory = category
            )
            it.copy(activeRecipe = updated)
        }
    }

    fun updateCustomRecipeFields(
        title: String? = null,
        customChain: String? = null,
        customAbsurd: String? = null,
        customDoctrine: String? = null,
        customFinePrint: String? = null
    ) {
        _uiState.update {
            val current = it.activeRecipe
            val updated = current.copy(
                title = title ?: current.title,
                customHearsayChain = customChain ?: current.customHearsayChain,
                customAbsurdElement = customAbsurd ?: current.customAbsurdElement,
                customOrthodoxyTarget = customDoctrine ?: current.customOrthodoxyTarget,
                customFinePrintCondition = customFinePrint ?: current.customFinePrintCondition
            )
            it.copy(activeRecipe = updated)
        }
    }

    fun setCustomTwistInput(input: String) {
        _uiState.update { it.copy(customTwistInput = input) }
    }

    fun injectRandomChaosTwist() {
        val twist = JokeGeneratorEngine.getRandomChaosTwist()
        _uiState.update {
            it.copy(
                customTwistInput = twist,
                statusMessage = "Injected chaos twist: \"$twist\""
            )
        }
    }

    fun randomizeStageSetting() {
        val setting = JokeGeneratorEngine.getRandomSetting()
        updateSetting(setting)
        _uiState.update { it.copy(statusMessage = "Randomized Setting to ${setting.label}") }
    }

    fun randomizeStageDogma() {
        val dogma = JokeGeneratorEngine.getRandomDogma()
        updateDogma(dogma)
        _uiState.update { it.copy(statusMessage = "Randomized Dogma to ${dogma.label}") }
    }

    fun randomizeStageAbsurd() {
        val absurd = JokeGeneratorEngine.getRandomAbsurdistType()
        updateAbsurdistType(absurd)
        _uiState.update { it.copy(statusMessage = "Randomized Relic to ${absurd.label}") }
    }

    fun randomizeStageFinePrint() {
        val fp = JokeGeneratorEngine.getRandomFinePrintCategory()
        updateFinePrint(hasFinePrint = true, category = fp)
        _uiState.update { it.copy(statusMessage = "Randomized Fine Print to ${fp.label}") }
    }

    fun randomizeCustomChain() {
        val chain = JokeGeneratorEngine.getRandomCustomChain()
        updateCustomRecipeFields(customChain = chain)
        _uiState.update { it.copy(statusMessage = "Randomized Hearsay Gossip Chain") }
    }

    fun randomizeCustomAbsurd() {
        val absurd = JokeGeneratorEngine.getRandomCustomAbsurd()
        updateCustomRecipeFields(customAbsurd = absurd)
        _uiState.update { it.copy(statusMessage = "Randomized Mundane Absurdity") }
    }

    fun randomizeCustomDoctrine() {
        val doctrine = JokeGeneratorEngine.getRandomCustomDoctrine()
        updateCustomRecipeFields(customDoctrine = doctrine)
        _uiState.update { it.copy(statusMessage = "Randomized Orthodoxy Target") }
    }

    fun randomizeCustomFinePrint() {
        val fp = JokeGeneratorEngine.getRandomCustomFinePrint()
        updateCustomRecipeFields(customFinePrint = fp)
        _uiState.update { it.copy(statusMessage = "Randomized HOA Fine Print Clause") }
    }

    fun randomizeChaoticCustomRecipe() {
        val chaoticRecipe = JokeGeneratorEngine.generateChaoticCustomRecipe()
        _uiState.update { it.copy(activeRecipe = chaoticRecipe) }
        generateCurrentJoke()
    }

    fun generateCurrentJoke() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGenerating = true) }
            val customTwist = _uiState.value.customTwistInput.takeIf { it.isNotBlank() }
            val joke = repository.generateJoke(_uiState.value.activeRecipe, customTwist)
            _uiState.update {
                it.copy(
                    activeJoke = joke,
                    isGenerating = false,
                    statusMessage = "Transmitted fresh heresy to parchment!"
                )
            }

            if (_uiState.value.vocalState.autoPlayOnGenerate) {
                vocalOratorEngine.deliverJoke(joke)
            }
        }
    }

    fun mutateSlot(slot: JokeSlot) {
        val currentJoke = _uiState.value.activeJoke ?: return
        viewModelScope.launch {
            val mutated = repository.mutateSlotAndSave(currentJoke, slot)
            _uiState.update {
                it.copy(
                    activeJoke = mutated,
                    statusMessage = "Mutated ${slot.label}!"
                )
            }
            if (_uiState.value.vocalState.isSpeaking) {
                vocalOratorEngine.deliverJoke(mutated)
            }
        }
    }

    fun updateJokeContent(
        statement: String,
        punchline: String?,
        whyHearsay: String,
        whyHeresy: String,
        finePrint: String?
    ) {
        val currentJoke = _uiState.value.activeJoke ?: return
        val updated = currentJoke.copy(
            statement = statement,
            customPunchline = punchline,
            whyHearsay = whyHearsay,
            whyHeresy = whyHeresy,
            finePrint = finePrint
        )

        viewModelScope.launch {
            repository.updateJokeContent(
                currentJoke.id,
                statement,
                punchline,
                whyHearsay,
                whyHeresy,
                finePrint
            )
            _uiState.update {
                it.copy(
                    activeJoke = updated,
                    showSlotCustomizerDialog = false,
                    statusMessage = "Custom punchlines & slots sealed!"
                )
            }
        }
    }

    fun toggleFavorite(joke: GeneratedJoke) {
        viewModelScope.launch {
            repository.toggleFavorite(joke)
            if (_uiState.value.activeJoke?.id == joke.id) {
                _uiState.update {
                    it.copy(activeJoke = joke.copy(isFavorite = !joke.isFavorite))
                }
            }
        }
    }

    fun deleteJoke(id: Long) {
        viewModelScope.launch {
            repository.deleteJoke(id)
            if (_uiState.value.activeJoke?.id == id) {
                _uiState.update {
                    it.copy(activeJoke = it.allJokes.firstOrNull { j -> j.id != id })
                }
            }
        }
    }

    fun saveActiveAsCustomRecipe(title: String, description: String) {
        val recipe = _uiState.value.activeRecipe
        val entity = CustomRecipeEntity(
            id = UUID.randomUUID().toString(),
            title = title.ifBlank { "Custom ${recipe.dogma.label} Recipe" },
            description = description.ifBlank { "Custom recipe created in Recipe Lab" },
            setting = recipe.setting.name,
            dogma = recipe.dogma.name,
            absurdistType = recipe.absurdistType.name,
            hasFinePrint = recipe.hasFinePrint,
            finePrintCategory = recipe.finePrintCategory.name,
            customHearsayChain = recipe.customHearsayChain,
            customAbsurdElement = recipe.customAbsurdElement,
            customOrthodoxyTarget = recipe.customOrthodoxyTarget,
            customFinePrintCondition = recipe.customFinePrintCondition,
            isPreset = false
        )

        viewModelScope.launch {
            repository.saveCustomRecipe(entity)
            _uiState.update {
                it.copy(
                    showRecipeSaveDialog = false,
                    statusMessage = "Saved recipe '$title' to Reliquary!"
                )
            }
        }
    }

    fun deleteCustomRecipe(id: String) {
        viewModelScope.launch {
            repository.deleteCustomRecipe(id)
        }
    }

    fun randomizeRecipe() {
        val randomSetting = HearsaySetting.entries.random()
        val randomDogma = DogmaCategory.entries.random()
        val randomAbsurd = AbsurdistType.entries.random()
        val randomFinePrint = FinePrintCategory.entries.random()
        val hasFp = kotlin.random.Random.nextBoolean()

        val randomized = JokeRecipe(
            id = "custom_random_${System.currentTimeMillis()}",
            title = "The ${randomDogma.label} Remix",
            description = "Randomized comedic convergence of ${randomSetting.label} and ${randomAbsurd.label}.",
            setting = randomSetting,
            dogma = randomDogma,
            absurdistType = randomAbsurd,
            hasFinePrint = hasFp,
            finePrintCategory = randomFinePrint,
            isPreset = false
        )

        _uiState.update { it.copy(activeRecipe = randomized) }
        generateCurrentJoke()
    }

    fun setActiveJoke(joke: GeneratedJoke) {
        _uiState.update { it.copy(activeJoke = joke) }
    }

    fun setShowSlotCustomizer(show: Boolean) {
        _uiState.update { it.copy(showSlotCustomizerDialog = show) }
    }

    fun setShowShareDialog(show: Boolean) {
        _uiState.update { it.copy(showShareDialog = show) }
    }

    fun setShowRecipeSaveDialog(show: Boolean) {
        _uiState.update { it.copy(showRecipeSaveDialog = show) }
    }

    fun setShowVocalDialog(show: Boolean) {
        _uiState.update { it.copy(showVocalDialog = show) }
    }

    fun deliverJokeVocally(joke: GeneratedJoke? = null, persona: OratorPersona? = null) {
        val targetJoke = joke ?: _uiState.value.activeJoke ?: return
        vocalOratorEngine.deliverJoke(targetJoke, persona)
    }

    fun stopVocalDelivery() {
        vocalOratorEngine.stop()
    }

    fun setOratorPersona(persona: OratorPersona) {
        vocalOratorEngine.setPersona(persona)
    }

    fun setVocalSpeed(speed: Float) {
        vocalOratorEngine.setSpeedMultiplier(speed)
    }

    fun setVocalPitch(pitch: Float) {
        vocalOratorEngine.setPitchMultiplier(pitch)
    }

    fun setVocalAutoPlay(autoPlay: Boolean) {
        vocalOratorEngine.setAutoPlay(autoPlay)
    }

    fun setShareFormat(format: ShareHelper.ShareFormat) {
        _uiState.update { it.copy(selectedShareFormat = format) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setDogmaFilter(dogma: DogmaCategory?) {
        _uiState.update { it.copy(dogmaFilter = dogma) }
    }

    fun clearStatusMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
