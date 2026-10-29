package com.example.network

import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GeminiRequest(
    @Json(name = "contents") val contents: List<GeminiContent>,
    @Json(name = "systemInstruction") val systemInstruction: GeminiContent? = null
)

@JsonClass(generateAdapter = true)
data class GeminiContent(
    @Json(name = "parts") val parts: List<GeminiPart>
)

@JsonClass(generateAdapter = true)
data class GeminiPart(
    @Json(name = "text") val text: String
)

@JsonClass(generateAdapter = true)
data class GeminiResponse(
    @Json(name = "candidates") val candidates: List<GeminiCandidate>?
)

@JsonClass(generateAdapter = true)
data class GeminiCandidate(
    @Json(name = "content") val content: GeminiContent?
)

interface GeminiRestService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

object GeminiNetworkClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service: GeminiRestService = retrofit.create(GeminiRestService::class.java)

    fun hasValidApiKey(): Boolean {
        val key = BuildConfig.GEMINI_API_KEY
        return !key.isNullOrBlank() && key != "MY_GEMINI_API_KEY"
    }

    suspend fun generateHeresyWithAI(
        setting: String,
        dogma: String,
        orthodoxDogma: String? = null,
        violationDesc: String? = null,
        absurdTheme: String,
        finePrintStyle: String?,
        keywords: String? = null,
        humorCategory: String? = null,
        targetAudience: String? = null,
        tone: String? = null,
        userModificationPrompt: String? = null
    ): String? = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isNullOrBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }

        val systemPrompt = """
            You are the Master Scribe of Hearsay and Heresy Jokes.
            You craft hilarious jokes strictly adhering to the 4-part foundational formula from the sacred canonical recipes:
            1. The Hearsay Chain: Construct a multi-layered, unreliable chain of telephone transmission (e.g. "My cousin's blacksmith's apprentice claims he overheard the Cardinal's butler whispering that...").
            2. The Heresy Formula: Identify actual established theological/institutional doctrines and violate their sacred boundary with absurdity.
            3. The Absurdist Engine: Inject mundane, hyper-specific modern items or surreal imagery (e.g., Cool Ranch Doritos, plastic kazoos, 1990s Golden Corral buffet in Ohio, DMV waiting rooms).
            4. The Petty Liturgical Fine Print (HOA Clause): An insanely specific bureaucratic exception or condition treating cosmic reality like a bad apartment lease or IT ticket.
            
            ADHERENCE TO ADVANCED CUSTOMIZATION PARAMETERS:
            - SPECIFIC KEYWORDS: You MUST naturally weave any requested keywords into the gossip chain, heretical claim, or fine print.
            - HUMOR CATEGORY: You MUST adjust the comedy mechanics to match the chosen humor category:
              * Puns & Wordplay: Incorporate clever liturgical/theological puns (e.g., pastor/pasta, altar/alter, friar/fryer, sole/soul, pews, holy water).
              * Observational Humor: Focus on relatable human foibles, social awkwardness, church coffee hour, group texts, or everyday quirks.
              * Surreal & Absurdist: Emphasize dreamlike juxtapositions, bizarre creature antics, and strange reality glitches.
              * Satirical Bureaucracy: Lean heavily into administrative red tape, corporate IT rules, and HOA covenants.
              * Deadpan & Dry Irony: Deliver shocking theological heresy in completely understated, matter-of-fact bureaucratic tone.
              * Slapstick & Chaos: Frame with frantic physical mishaps, runaway animals, or accidental confessions.
            - TARGET AUDIENCE: Tailor cultural idioms, vocabulary, and comedic perspective directly to the target audience (e.g., peasants, cynical monks/scholars, disgruntled corporate workers, HOA members, Gen Z).
            - TONE: Deliver in the explicit tone requested (Reverently Irreverent, Conspiratorial Whisper, Bureaucratic Deadpan, Melodramatic & Apocalyptic, or Bewildered).
            
            Return output formatted EXACTLY with these 5 sections:
            TITLE: <Short Catchy Joke Title>
            STATEMENT: "<Full quote with the hearsay chain and heretical statement>"
            WHY HEARSAY: <Explanation of the unreliable transmission chain>
            WHY HERESY: <Explanation of the theological dogma violated>
            THE FINE PRINT: <Petty bureaucratic clause, or None>
        """.trimIndent()

        val userPrompt = buildString {
            append("Generate a customized Hearsay & Heresy joke adhering to these exact parameters:")
            append("\n\n[BASE RECIPE PARAMETERS]")
            append("\n- Setting: $setting")
            append("\n- Dogma to subvert: $dogma")
            if (!orthodoxDogma.isNullOrBlank()) {
                append(" (Orthodox Doctrine: \"$orthodoxDogma\")")
            }
            if (!violationDesc.isNullOrBlank()) {
                append(" (Target Violation: \"$violationDesc\")")
            }
            append("\n- Absurdist element theme: $absurdTheme")
            if (!finePrintStyle.isNullOrBlank()) {
                append("\n- Fine Print Style: $finePrintStyle")
            }

            append("\n\n[ADVANCED CUSTOMIZATION PARAMETERS]")
            if (!keywords.isNullOrBlank()) {
                append("\n- Required Keywords to Integrate: \"$keywords\" (Seamlessly weave these into the joke statement, premise, or fine print)")
            }
            if (!humorCategory.isNullOrBlank()) {
                append("\n- Desired Humor Category: $humorCategory")
            }
            if (!targetAudience.isNullOrBlank()) {
                append("\n- Target Audience: $targetAudience")
            }
            if (!tone.isNullOrBlank()) {
                append("\n- Tone of Delivery: $tone")
            }
            if (!userModificationPrompt.isNullOrBlank()) {
                append("\n- Additional Custom Twist: $userModificationPrompt")
            }
        }

        try {
            val response = service.generateContent(
                apiKey = apiKey,
                request = GeminiRequest(
                    contents = listOf(
                        GeminiContent(parts = listOf(GeminiPart(text = userPrompt)))
                    ),
                    systemInstruction = GeminiContent(parts = listOf(GeminiPart(text = systemPrompt)))
                )
            )

            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
