package com.example.util

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.model.GeneratedJoke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

enum class OratorPersona(
    val title: String,
    val subtitle: String,
    val emoji: String,
    val basePitch: Float,
    val baseRate: Float,
    val introPhrase: String,
    val conclusionPhrase: String
) {
    GRAND_INQUISITOR(
        title = "Grand Inquisitor",
        subtitle = "Solemn, dramatic & ecclesiastical",
        emoji = "⚖️",
        basePitch = 0.82f,
        baseRate = 0.88f,
        introPhrase = "Hear ye, flock of the realm! An urgent inquiry into grave theological absurdity.",
        conclusionPhrase = "Thus stands the canonical indictment. Let the heretic beware."
    ),
    TOWN_CRIER(
        title = "Town Crier",
        subtitle = "Loud, animated & proclamatory",
        emoji = "📢",
        basePitch = 1.08f,
        baseRate = 1.05f,
        introPhrase = "Oyez! Oyez! Fresh gossip hot from the village square!",
        conclusionPhrase = "Spread the rumor far and wide across the cobblestones!"
    ),
    VILLAGE_GOSSIP(
        title = "Village Gossip",
        subtitle = "Fast-paced, conspiratorial & whispering",
        emoji = "🗣️",
        basePitch = 1.22f,
        baseRate = 1.18f,
        introPhrase = "Listen close, you won't believe what Sister Beatrice just whispered to the hedge trimmer!",
        conclusionPhrase = "Now don't tell anyone I told you this!"
    ),
    DEADPAN_BUREAUCRAT(
        title = "Deadpan Bureaucrat",
        subtitle = "Monotone, legalistic & matter-of-fact",
        emoji = "📋",
        basePitch = 0.95f,
        baseRate = 0.94f,
        introPhrase = "Regarding incident report 404, filed under unauthorized doctrinal anomalies.",
        conclusionPhrase = "Failure to comply with paragraph 9 will result in immediate ticket forfeiture."
    )
}

enum class VocalSection(val label: String, val icon: String) {
    INTRO("Proclamation", "📢"),
    RUMOR("The Heretical Rumor", "📜"),
    HEARSAY("Why it's Hearsay", "🗣️"),
    HERESY("Why it's Heresy", "⚖️"),
    FINE_PRINT("The Petty Fine Print", "🏛️"),
    CONCLUSION("The Canonical Seal", "🔏")
}

data class VocalOratorState(
    val isSpeaking: Boolean = false,
    val isPaused: Boolean = false,
    val isInitialized: Boolean = false,
    val activeSection: VocalSection? = null,
    val currentUtteranceId: String? = null,
    val progress: Float = 0f,
    val persona: OratorPersona = OratorPersona.GRAND_INQUISITOR,
    val speedMultiplier: Float = 1.0f,
    val pitchMultiplier: Float = 1.0f,
    val autoPlayOnGenerate: Boolean = false,
    val spokenTextSnippet: String = ""
)

class VocalOratorEngine(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VocalOratorState())
    val state: StateFlow<VocalOratorState> = _state.asStateFlow()

    private var currentPlaylist: List<Pair<VocalSection, String>> = emptyList()
    private var currentIndex = 0

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e("VocalOratorEngine", "Failed to init TTS: ${e.message}")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.getDefault())
            }
            setupUtteranceListener()
            _state.update { it.copy(isInitialized = true) }
        } else {
            Log.e("VocalOratorEngine", "TTS init failed with status: $status")
        }
    }

    private fun setupUtteranceListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                scope.launch {
                    val section = VocalSection.entries.find { it.name == utteranceId }
                    val currentItem = currentPlaylist.getOrNull(currentIndex)
                    val text = currentItem?.second ?: ""
                    val progressVal = if (currentPlaylist.isNotEmpty()) {
                        (currentIndex + 1).toFloat() / currentPlaylist.size.toFloat()
                    } else 0f

                    _state.update {
                        it.copy(
                            isSpeaking = true,
                            isPaused = false,
                            activeSection = section ?: currentItem?.first,
                            currentUtteranceId = utteranceId,
                            progress = progressVal,
                            spokenTextSnippet = text
                        )
                    }
                }
            }

            override fun onDone(utteranceId: String?) {
                scope.launch {
                    currentIndex++
                    if (currentIndex < currentPlaylist.size) {
                        speakNextItem()
                    } else {
                        _state.update {
                            it.copy(
                                isSpeaking = false,
                                isPaused = false,
                                activeSection = null,
                                progress = 1f,
                                spokenTextSnippet = "Proclamation complete."
                            )
                        }
                    }
                }
            }

            override fun onError(utteranceId: String?) {
                scope.launch {
                    _state.update {
                        it.copy(
                            isSpeaking = false,
                            isPaused = false,
                            activeSection = null
                        )
                    }
                }
            }
        })
    }

    fun deliverJoke(joke: GeneratedJoke, targetPersona: OratorPersona? = null) {
        val selectedPersona = targetPersona ?: _state.value.persona
        stop()

        val statementText = joke.customPunchline?.takeIf { it.isNotBlank() } ?: joke.statement
        // Clean quotation marks for cleaner speech
        val cleanedStatement = statementText.trim('"').trim()

        val playlist = mutableListOf<Pair<VocalSection, String>>()

        // 1. Proclamation Intro
        playlist.add(VocalSection.INTRO to selectedPersona.introPhrase)

        // 2. The Heretical Rumor with dramatic timing
        playlist.add(VocalSection.RUMOR to cleanedStatement)

        // 3. Why it's Hearsay breakdown
        playlist.add(VocalSection.HEARSAY to "Why is this classified as hearsay? ${joke.whyHearsay}")

        // 4. Why it's Heresy breakdown
        playlist.add(VocalSection.HERESY to "And why is it grave heresy? ${joke.whyHeresy}")

        // 5. Petty Fine Print if present
        if (!joke.finePrint.isNullOrBlank()) {
            val fpIntro = when (selectedPersona) {
                OratorPersona.DEADPAN_BUREAUCRAT -> "Mandatory administrative footnote:"
                OratorPersona.VILLAGE_GOSSIP -> "Oh, and you have to hear the fine print!"
                OratorPersona.TOWN_CRIER -> "Take heed of the royal fine print clause!"
                OratorPersona.GRAND_INQUISITOR -> "Observe the canonical fine print condition:"
            }
            playlist.add(VocalSection.FINE_PRINT to "$fpIntro ${joke.finePrint}")
        }

        // 6. Canonical Seal
        playlist.add(VocalSection.CONCLUSION to selectedPersona.conclusionPhrase)

        currentPlaylist = playlist
        currentIndex = 0

        _state.update {
            it.copy(
                persona = selectedPersona,
                isSpeaking = true,
                isPaused = false,
                progress = 0f
            )
        }

        speakNextItem()
    }

    private fun speakNextItem() {
        val ttsInstance = tts ?: return
        if (currentIndex !in currentPlaylist.indices) {
            _state.update { it.copy(isSpeaking = false, activeSection = null) }
            return
        }

        val (section, text) = currentPlaylist[currentIndex]
        val currentPersona = _state.value.persona
        val speed = currentPersona.baseRate * _state.value.speedMultiplier
        val pitch = currentPersona.basePitch * _state.value.pitchMultiplier

        ttsInstance.setSpeechRate(speed)
        ttsInstance.setPitch(pitch)

        val params = Bundle()
        ttsInstance.speak(text, TextToSpeech.QUEUE_FLUSH, params, section.name)
    }

    fun stop() {
        tts?.stop()
        currentIndex = 0
        currentPlaylist = emptyList()
        _state.update {
            it.copy(
                isSpeaking = false,
                isPaused = false,
                activeSection = null,
                progress = 0f,
                spokenTextSnippet = ""
            )
        }
    }

    fun setPersona(persona: OratorPersona) {
        _state.update { it.copy(persona = persona) }
    }

    fun setSpeedMultiplier(multiplier: Float) {
        _state.update { it.copy(speedMultiplier = multiplier.coerceIn(0.7f, 1.6f)) }
    }

    fun setPitchMultiplier(multiplier: Float) {
        _state.update { it.copy(pitchMultiplier = multiplier.coerceIn(0.7f, 1.5f)) }
    }

    fun setAutoPlay(enabled: Boolean) {
        _state.update { it.copy(autoPlayOnGenerate = enabled) }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
