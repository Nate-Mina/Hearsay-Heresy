package com.example.model

enum class HearsaySetting(val label: String, val description: String) {
    MEDIEVAL_VILLAGE("Medieval Village", "Passed from blacksmith apprentice to cousin to tavern keeper"),
    VATICAN_PALACE("Vatican / Cardinal Palace", "Whispered by the Cardinal's butler to the parish secretary"),
    MONASTIC_CLOISTER("Monastic Cloister", "Monastic rumors between Brother Francis and the Abbot"),
    SUBURBAN_BEAUTY_SALON("Suburban Salon & Sauna", "Manicurist to aunt to neighborhood book club"),
    TECH_STARTUP_SLACK("Tech Startup & Slack", "Junior dev hearsay about the lead engineer's forbidden Google Sheets"),
    INQUISITORIAL_TRIBUNAL("Inquisitorial Witness", "Multi-level unverified tribunal hearsay"),
    DOCKSIDE_ALEXANDRIA("Alexandrian Docks", "Third-party merchant sailors passing dockside theology"),
    DENTAL_WAITING_ROOM("Suburban Waiting Room", "Gym teacher's psychic hypnotizing local hermits")
}

enum class DogmaCategory(val label: String, val orthodoxDogma: String, val violationDesc: String) {
    HOLY_TRINITY("The Holy Trinity", "Co-eternal, consubstantial three persons in one God", "Denies divine nature or redefines Godhead into absurdity"),
    TRANSUBSTANTIATION("Transubstantiation", "Real physical presence in sacred communion elements", "Reduces divine transformation to snack food mechanics"),
    ORIGINAL_SIN_EDEN("Original Sin & Garden of Eden", "The Fall from Grace and ancestral disobedience", "Attributes the Fall to snack etiquette or minor beverage cravings"),
    PURGATORY_ESCHATOLOGY("Purgatory & Afterlife", "Spiritual purification by holy fire and Beatific Vision", "Replaces salvation with municipal waiting rooms or 90s buffets"),
    ANGELOLOGY("Angelology & Divine Liturgy", "Celestial hierarchy and exalted harp psalms", "Declares divine preference for plastic party favor kazoos"),
    REINCARNATION("Soul Migration & Reincarnation", "Eternal salvation vs Eastern soul-migration", "Reincarnates righteous souls into mid-century dental furniture"),
    MONASTIC_PANTHEISM("Monastic Pantheism", "God distinct from created universe", "Asserts God and the universe are the exact same material substance"),
    DIETARY_DAMNATION("Late-Night Carb Damnation", "Divine judgment based on moral deeds", "Salvation determined by eating sourdough past 8 PM"),
    TECH_STACK_ORTHODOXY("Secular Tech Orthodoxy", "The sacred untouchable core proprietary algorithm", "Replaces entire tech stack with raw Google Sheets formulas")
}

enum class AbsurdistType(val label: String, val iconEmoji: String) {
    JUNK_FOOD("Snack Foods (Doritos, Oreos, Cheetos)", "🧀"),
    ANIMALS_IN_DISGUISE("Raccoons in Cloaks & Talking Goats", "🦝"),
    MUNICIPAL_BUREAUCRACY("DMV Waiting Rooms & Parking Permits", "🪪"),
    NOSTALGIC_BUFFETS("1990s Golden Corral & Sizzler", "🍗"),
    CHEAP_TOYS("Plastic Kazoos & Party Favors", "🎺"),
    OFFICE_FURNITURE("Mid-Century Dental Swivel Chairs", "🪑"),
    TECH_MIGRATION("Google Sheets & PDF Attachments", "💻")
}

enum class FinePrintCategory(val label: String, val flavor: String) {
    IT_HELPDESK("Heavenly IT Helpdesk Ticket", "Automated out-of-office replies for Friday prayers"),
    PARKING_CITATION("Celestial Meter Maid Citation", "$45 parking ticket for staying at Pearly Gates kiosk > 15 min"),
    DIETARY_EXEMPTION("Gluten & Dietary Exemption", "Substituting salted saltine while whispering 'no offense'"),
    HOA_COVENANT("Neighborhood HOA Cosmic Clause", "Quiet hours in paradise and strict architectural shrub reviews"),
    APARTMENT_LEASE("Celestial Sublease Clause", "Non-refundable deposits and unauthorized guest surcharges"),
    EXPIRATION_DATE("Grace Expiration Notice", "Sacraments void after 30 days without original proof of purchase")
}

enum class HumorCategory(val label: String, val icon: String, val description: String) {
    SURREAL("Surreal & Absurdist", "🦝", "Uncanny twists, bizarre juxtapositions, dreamlike non-sequiturs"),
    PUNS("Puns & Sacred Wordplay", "✨", "Theological double meanings, liturgical spoonerisms, altar quips"),
    OBSERVATIONAL("Observational Humor", "👀", "Relatable social habits, everyday human quirks, modern awkwardness"),
    SATIRICAL_BUREAUCRACY("Satirical Bureaucracy", "📋", "Municipal red tape, HOA decrees, corporate IT ticketing rules"),
    DEADPAN_DRY("Deadpan & Dry Irony", "😐", "Solemn understatement, stone-faced delivery of heretical facts"),
    SLAPSTICK_CHAOS("Slapstick & Chaos", "💥", "Frantic mishaps, runaway animals, spilled communion chalices")
}

enum class TargetAudience(val label: String, val icon: String, val description: String) {
    GENERAL("General Scribes", "📜", "Broadly accessible, balanced comedic heresy"),
    MEDIEVAL_PEASANTS("Medieval Peasants", "🌾", "Agrarian folklore, tavern barters, superstitious lore"),
    CYNICAL_SCHOLARS("Cynical Monks & Academics", "🕯️", "Obscure footnotes, Latin quips, canonical nitpicking"),
    TIRED_OFFICE_WORKERS("Disgruntled Corporate Workers", "💼", "Slack etiquette, quarterly OKRs, corporate synergy vs eternity"),
    HOA_MEMBERS("Strict HOA Board Members", "🏡", "Neighborhood lawn regulations, architectural review committees"),
    GEN_Z_CYNICS("Digital Natives & Gen Z", "📱", "Existential dread, notification fatigue, chaotic internet culture")
}

enum class JokeTone(val label: String, val icon: String, val description: String) {
    REVERENTLY_IRREVERENT("Reverently Irreverent", "🕊️", "Solemn liturgical language conveying utter nonsense"),
    CONSPIRATORIAL("Conspiratorial Whisper", "🤫", "Hushed tavern rumors and behind-the-altar secrets"),
    BUREAUCRATIC_DEADPAN("Bureaucratic Deadpan", "⚖️", "Dry, official regulatory handbook cadence"),
    MELODRAMATIC("Melodramatic & Apocalyptic", "⚡", "Grandiose cosmic stakes over minuscule trivialities"),
    BEWILDERED("Bewildered & Befuddled", "😵", "A humble witness trying desperately to make sense of what they heard")
}

data class JokeCustomizationParams(
    val keywords: String = "",
    val humorCategory: HumorCategory = HumorCategory.SURREAL,
    val targetAudience: TargetAudience = TargetAudience.GENERAL,
    val tone: JokeTone = JokeTone.REVERENTLY_IRREVERENT
)

data class JokeRecipe(
    val id: String,
    val title: String,
    val description: String,
    val setting: HearsaySetting,
    val dogma: DogmaCategory,
    val absurdistType: AbsurdistType,
    val hasFinePrint: Boolean = true,
    val finePrintCategory: FinePrintCategory = FinePrintCategory.IT_HELPDESK,
    val customHearsayChain: String? = null,
    val customAbsurdElement: String? = null,
    val customOrthodoxyTarget: String? = null,
    val customFinePrintCondition: String? = null,
    val isPreset: Boolean = false
)

data class GeneratedJoke(
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
    val timestamp: Long = System.currentTimeMillis(),
    val keywords: String? = null,
    val humorCategory: String? = null,
    val targetAudience: String? = null,
    val tone: String? = null
)
