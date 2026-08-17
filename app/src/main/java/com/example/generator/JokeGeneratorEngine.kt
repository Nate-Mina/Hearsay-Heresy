package com.example.generator

import com.example.model.AbsurdistType
import com.example.model.DogmaCategory
import com.example.model.FinePrintCategory
import com.example.model.GeneratedJoke
import com.example.model.HearsaySetting
import com.example.model.JokeRecipe
import kotlin.random.Random

object JokeGeneratorEngine {

    val PRESET_RECIPES = listOf(
        JokeRecipe(
            id = "raccoon_trinity",
            title = "The Holy Raccoon Trinity",
            description = "Three clever trash pandas sharing one velvet cloak as the Godhead.",
            setting = HearsaySetting.MEDIEVAL_VILLAGE,
            dogma = DogmaCategory.HOLY_TRINITY,
            absurdistType = AbsurdistType.ANIMALS_IN_DISGUISE,
            hasFinePrint = false,
            isPreset = true
        ),
        JokeRecipe(
            id = "cool_ranch_sacrament",
            title = "The Cool Ranch Sacrament",
            description = "Transubstantiation turning communion hosts into 1990s seasoned tortilla chips.",
            setting = HearsaySetting.MONASTIC_CLOISTER,
            dogma = DogmaCategory.TRANSUBSTANTIATION,
            absurdistType = AbsurdistType.JUNK_FOOD,
            hasFinePrint = false,
            isPreset = true
        ),
        JokeRecipe(
            id = "oreo_fall",
            title = "The Oreo Fall from Grace",
            description = "Original Sin occurred strictly because Adam and Eve didn't offer milk with their Double Stuf.",
            setting = HearsaySetting.MEDIEVAL_VILLAGE,
            dogma = DogmaCategory.ORIGINAL_SIN_EDEN,
            absurdistType = AbsurdistType.JUNK_FOOD,
            hasFinePrint = false,
            isPreset = true
        ),
        JokeRecipe(
            id = "dmv_purgatory",
            title = "The DMV Purgatory",
            description = "Purgatory as an infinite municipal waiting room with ticket B-69 and chilly AC.",
            setting = HearsaySetting.SUBURBAN_BEAUTY_SALON,
            dogma = DogmaCategory.PURGATORY_ESCHATOLOGY,
            absurdistType = AbsurdistType.MUNICIPAL_BUREAUCRACY,
            hasFinePrint = false,
            isPreset = true
        ),
        JokeRecipe(
            id = "angelic_kazoo",
            title = "The Angelic Kazoo Mandate",
            description = "Heavenly hosts discarding angelic harps in favor of cheap plastic party favor kazoos.",
            setting = HearsaySetting.MEDIEVAL_VILLAGE,
            dogma = DogmaCategory.ANGELOLOGY,
            absurdistType = AbsurdistType.CHEAP_TOYS,
            hasFinePrint = false,
            isPreset = true
        ),
        JokeRecipe(
            id = "cleveland_buffet",
            title = "The Suburban Cleveland Eschatology",
            description = "The Beatific Vision replaced with an endless 1990s Golden Corral buffet.",
            setting = HearsaySetting.MEDIEVAL_VILLAGE,
            dogma = DogmaCategory.PURGATORY_ESCHATOLOGY,
            absurdistType = AbsurdistType.NOSTALGIC_BUFFETS,
            hasFinePrint = false,
            isPreset = true
        ),
        JokeRecipe(
            id = "heavenly_it_helpdesk",
            title = "The Heavenly IT Helpdesk",
            description = "Omnipotence constrained by 5:00 PM EST cutoffs and strict PDF attachment rules.",
            setting = HearsaySetting.VATICAN_PALACE,
            dogma = DogmaCategory.ANGELOLOGY,
            absurdistType = AbsurdistType.TECH_MIGRATION,
            hasFinePrint = true,
            finePrintCategory = FinePrintCategory.IT_HELPDESK,
            isPreset = true
        ),
        JokeRecipe(
            id = "purgatory_parking",
            title = "The Purgatory Parking Permit",
            description = "Salvation granted to all souls provided they validate parking at Pearly Gates kiosk.",
            setting = HearsaySetting.MONASTIC_CLOISTER,
            dogma = DogmaCategory.PURGATORY_ESCHATOLOGY,
            absurdistType = AbsurdistType.MUNICIPAL_BUREAUCRACY,
            hasFinePrint = true,
            finePrintCategory = FinePrintCategory.PARKING_CITATION,
            isPreset = true
        ),
        JokeRecipe(
            id = "dietary_transubstantiation",
            title = "The Dietary Transubstantiation Exemption",
            description = "Liturgical accommodation substituting salted saltines while whispering 'no offense'.",
            setting = HearsaySetting.MEDIEVAL_VILLAGE,
            dogma = DogmaCategory.TRANSUBSTANTIATION,
            absurdistType = AbsurdistType.JUNK_FOOD,
            hasFinePrint = true,
            finePrintCategory = FinePrintCategory.DIETARY_EXEMPTION,
            isPreset = true
        ),
        JokeRecipe(
            id = "dentist_reincarnation",
            title = "The Dentist's Chair Reincarnation",
            description = "Righteous souls reincarnating into mid-century dental modern swivel chairs.",
            setting = HearsaySetting.DENTAL_WAITING_ROOM,
            dogma = DogmaCategory.REINCARNATION,
            absurdistType = AbsurdistType.OFFICE_FURNITURE,
            hasFinePrint = false,
            isPreset = true
        ),
        JokeRecipe(
            id = "divine_carb_judgment",
            title = "The Divine Carb Judgment",
            description = "A talking goat reveals Saint Peter damns anyone judging late-night sourdough consumption.",
            setting = HearsaySetting.MEDIEVAL_VILLAGE,
            dogma = DogmaCategory.DIETARY_DAMNATION,
            absurdistType = AbsurdistType.ANIMALS_IN_DISGUISE,
            hasFinePrint = false,
            isPreset = true
        ),
        JokeRecipe(
            id = "tech_startup_orthodoxy",
            title = "The Tech Startup Orthodoxy",
            description = "Junior dev gossips that the core engine is junk and should be moved to Google Sheets.",
            setting = HearsaySetting.TECH_STARTUP_SLACK,
            dogma = DogmaCategory.TECH_STACK_ORTHODOXY,
            absurdistType = AbsurdistType.TECH_MIGRATION,
            hasFinePrint = false,
            isPreset = true
        )
    )

    // Curated exact canonical examples from PDF
    val CANONICAL_JOKES = mapOf(
        "raccoon_trinity" to GeneratedJoke(
            id = 1,
            title = "The Holy Raccoon Trinity",
            statement = "\"My cousin's blacksmith's apprentice claims he overheard the Cardinal's butler whispering that the Holy Trinity is actually just three very clever raccoons sharing a single velvet cloak.\"",
            whyHearsay = "It's a fourth-hand game of village telephone passing through an apprentice, a blacksmith, a cousin, and a butler.",
            whyHeresy = "Trinitarian theology explicitly forbids defining the Godhead as a trio of trash pandas in a coat.",
            finePrint = null,
            recipeTitle = "The Holy Raccoon Trinity",
            hearsaySetting = "Medieval Village",
            dogmaName = "The Holy Trinity",
            absurdistElement = "Three raccoons in a velvet cloak",
            finePrintStyle = null
        ),
        "cool_ranch_sacrament" to GeneratedJoke(
            id = 2,
            title = "The Cool Ranch Sacrament",
            statement = "\"Sister Beatrice told the gardener that the Mother Superior admitted during a wine tasting that transubstantiation secretly turns the communion host into a Cool Ranch Dorito, but only if the priest winks at the altar.\"",
            whyHearsay = "Unsubstantiated convent gossip passed along to the guy trimming the hedges.",
            whyHeresy = "Reducing the sacred Eucharistic transformation to 1990s snack food mechanics violates core Catholic dogma.",
            finePrint = null,
            recipeTitle = "The Cool Ranch Sacrament",
            hearsaySetting = "Monastic Cloister",
            dogmaName = "Transubstantiation",
            absurdistElement = "Cool Ranch Dorito host",
            finePrintStyle = null
        ),
        "oreo_fall" to GeneratedJoke(
            id = 3,
            title = "The Oreo Fall from Grace",
            statement = "\"A traveling cobbler swears his brother-in-law found a lost gospel where Adam told Eve the Forbidden Fruit was actually a Double Stuf Oreo, and the expulsion from Eden happened strictly because they didn't offer God a glass of milk.\"",
            whyHearsay = "A fourth-hand legend passed through a cobbler, his brother-in-law, an unverified scroll, and ancient pillow talk.",
            whyHeresy = "It completely rewrites Genesis, redefines Original Sin around snack etiquette, and attributes petty dairy cravings to the Almighty.",
            finePrint = null,
            recipeTitle = "The Oreo Fall from Grace",
            hearsaySetting = "Medieval Village",
            dogmaName = "Original Sin & Garden of Eden",
            absurdistElement = "Double Stuf Oreo and milk glass",
            finePrintStyle = null
        ),
        "dmv_purgatory" to GeneratedJoke(
            id = 4,
            title = "The DMV Purgatory",
            statement = "\"The parish secretary's manicurist told my aunt that the Pope confessed in a private sauna that Purgatory isn't a realm of purifying fire, but an infinite DMV waiting room where everyone's ticket is B-69 and the air conditioning is slightly too cold.\"",
            whyHearsay = "Salon gossip relayed through an aunt from an alleged sauna confession.",
            whyHeresy = "Replaces canonical Catholic eschatology and spiritual purification with municipal bureaucracy.",
            finePrint = null,
            recipeTitle = "The DMV Purgatory",
            hearsaySetting = "Suburban Salon & Sauna",
            dogmaName = "Purgatory & Afterlife",
            absurdistElement = "Infinite DMV ticket B-69",
            finePrintStyle = null
        ),
        "angelic_kazoo" to GeneratedJoke(
            id = 5,
            title = "The Angelic Kazoo Mandate",
            statement = "\"A chimney sweep told the fishmonger that the Archbishop's nephew overheard two Archangels agreeing that God actually despises harp music and requires all heavenly hosts to perform praise songs exclusively on plastic kazoos.\"",
            whyHearsay = "Street rumor jumping from sweep to fishmonger to nephew to celestial eavesdropping.",
            whyHeresy = "Subverts traditional angelology and sacred liturgy by declaring divine disdain for holy music in favor of party-favor woodwinds.",
            finePrint = null,
            recipeTitle = "The Angelic Kazoo Mandate",
            hearsaySetting = "Medieval Village",
            dogmaName = "Angelology & Divine Liturgy",
            absurdistElement = "Plastic party favor kazoos",
            finePrintStyle = null
        ),
        "cleveland_buffet" to GeneratedJoke(
            id = 6,
            title = "The Suburban Cleveland Eschatology",
            statement = "\"A traveling bard told the tavern keeper that a drunk Inquisitor confessed Heaven isn't a celestial city of light, but rather an endless, non-refundable Golden Corral buffet in 1990s Ohio.\"",
            whyHearsay = "Unverified tavern rumor originating from a drunk guy, told to a bard, told to a bartender.",
            whyHeresy = "Replaces the Beatific Vision and eternal salvation with a lukewarm chocolate fountain and discounted Salisbury steak.",
            finePrint = null,
            recipeTitle = "The Suburban Cleveland Eschatology",
            hearsaySetting = "Medieval Village",
            dogmaName = "Purgatory & Afterlife",
            absurdistElement = "Endless 1990s Golden Corral buffet in Ohio",
            finePrintStyle = null
        ),
        "heavenly_it_helpdesk" to GeneratedJoke(
            id = 7,
            title = "The Heavenly IT Helpdesk",
            statement = "\"A stable boy swears the cardinal's nephew overheard a seraphim admit that prayers are only processed if submitted as a properly formatted PDF, and anything sent after 5:00 PM on Friday receives an automated out-of-office auto-reply until Monday morning.\"",
            whyHearsay = "Multi-step rumor passed through a stable boy, a nephew, and an eavesdropped angel.",
            whyHeresy = "Reduces divine omnipresence and sacred prayer into an understaffed corporate IT ticketing queue.",
            finePrint = "God is omnipotent, but heavenly operations run on standard Eastern Standard Time business hours with strict file-type restrictions.",
            recipeTitle = "The Heavenly IT Helpdesk",
            hearsaySetting = "Vatican Palace",
            dogmaName = "Angelology & Divine Liturgy",
            absurdistElement = "Properly formatted PDF requirement",
            finePrintStyle = "Heavenly IT Helpdesk Ticket"
        ),
        "purgatory_parking" to GeneratedJoke(
            id = 8,
            title = "The Purgatory Parking Permit",
            statement = "\"My grocer's brother claims a monk confessed that full salvation is granted to every soul without exception—provided they don't parallel park in Heaven's main plaza without validating their ticket at the Pearly Gates kiosk within 15 minutes.\"",
            whyHearsay = "Grocer's brother quoting a monk's unverified confession.",
            whyHeresy = "Bases celestial entry not on grace or virtue, but on parking validation compliance.",
            finePrint = "Eternal redemption hangs not on your morality, but on your ability to avoid a $45 parking citation from celestial meter maids.",
            recipeTitle = "The Purgatory Parking Permit",
            hearsaySetting = "Monastic Cloister",
            dogmaName = "Purgatory & Afterlife",
            absurdistElement = "15-minute parking validation ticket",
            finePrintStyle = "Celestial Meter Maid Citation"
        ),
        "dietary_transubstantiation" to GeneratedJoke(
            id = 9,
            title = "The Dietary Transubstantiation Exemption",
            statement = "\"A traveling peddler told the innkeeper that an archdeacon admitted the communion host only grants grace if consumed with your left pinky extended, but if you have a gluten intolerance, the priest is required to substitute a salted saltine and whisper 'no offense' toward the altar.\"",
            whyHearsay = "Peddler hearsay passed over an innkeeper's bar.",
            whyHeresy = "Mangles sacrificial liturgical canon with modern dietary etiquette and awkward apologies.",
            finePrint = "Sacred dogma accommodates modern dietary restrictions, provided proper awkward politeness is observed by the clergy.",
            recipeTitle = "The Dietary Transubstantiation Exemption",
            hearsaySetting = "Medieval Village",
            dogmaName = "Transubstantiation",
            absurdistElement = "Salted saltine with 'no offense' whisper",
            finePrintStyle = "Gluten & Dietary Exemption"
        ),
        "dentist_reincarnation" to GeneratedJoke(
            id = 10,
            title = "The Dentist's Chair Reincarnation",
            statement = "\"My gym teacher's psychic told him that a local hermit admitted under hypnosis that righteous souls don't go to Heaven, but are reincarnated as mid-century modern swivel chairs in upscale dental offices.\"",
            whyHearsay = "An unsubstantiated report of a hypnotized hermit passed through a psychic to a PE teacher.",
            whyHeresy = "Blends Eastern soul-migration into Christian doctrine while flatly denying salvation in favor of commercial furniture design.",
            finePrint = null,
            recipeTitle = "The Dentist's Chair Reincarnation",
            hearsaySetting = "Suburban Waiting Room",
            dogmaName = "Soul Migration & Reincarnation",
            absurdistElement = "Mid-century dental swivel chairs",
            finePrintStyle = null
        ),
        "divine_carb_judgment" to GeneratedJoke(
            id = 11,
            title = "The Divine Carb Judgment",
            statement = "\"The village baker says a local hermit's goat told him that Saint Peter doesn't check your sins at the Pearly Gates—he just checks if you've ever judged someone for eating sourdough after 8 PM, and if so, hurls you into the fiery pit.\"",
            whyHearsay = "Secondhand testimony whose primary source is a livestock animal.",
            whyHeresy = "Fabricates a whole new system of divine salvation and damnation based entirely on late-night snacking habits.",
            finePrint = null,
            recipeTitle = "The Divine Carb Judgment",
            hearsaySetting = "Medieval Village",
            dogmaName = "Late-Night Carb Damnation",
            absurdistElement = "Eating sourdough after 8 PM",
            finePrintStyle = null
        ),
        "tech_startup_orthodoxy" to GeneratedJoke(
            id = 12,
            title = "The Tech Startup Orthodoxy",
            statement = "\"The junior dev told me that the lead engineer said our core proprietary algorithm is total junk and we should migrate our entire platform to basic Google Sheets.\"",
            whyHearsay = "Unsubstantiated workplace gossip passed down through the ranks.",
            whyHeresy = "It directly attacks the sacred, untouchable core doctrine of the company's tech stack and engineering culture.",
            finePrint = "All enterprise microservices are subject to spreadsheet cell limits and accidental row deletions.",
            recipeTitle = "The Tech Startup Orthodoxy",
            hearsaySetting = "Tech Startup & Slack",
            dogmaName = "Secular Tech Orthodoxy",
            absurdistElement = "Migrating core AI engine to Google Sheets",
            finePrintStyle = "Heavenly IT Helpdesk Ticket"
        )
    )

    // Combinatorial Banks for dynamic recipe generation & element swapping
    val HEARSAY_CHAINS = listOf(
        "My cousin's blacksmith's apprentice claims he overheard the Cardinal's butler whispering that",
        "Sister Beatrice told the gardener that the Mother Superior admitted during a wine tasting that",
        "A traveling cobbler swears his brother-in-law found a lost parchment proving that",
        "The parish secretary's manicurist told my aunt after a hot yoga session that",
        "A chimney sweep told the local fishmonger that the Archbishop's third nephew overheard that",
        "A traveling bard told the tavern keeper that a tipsy Inquisitor confessed that",
        "My gym teacher's psychic told him that a hypnotized mountain hermit revealed that",
        "The village miller's apprentice overheard two Franciscan friars muttering that",
        "An off-duty notary told the tavern cook that the Pope's personal calligrapher leaked that",
        "The parish organist told the candle supplier that a cherub accidentally revealed that",
        "A junior frontend engineer overheard the Chief Architect admitting on an unmuted Zoom that",
        "My dog groomer swears her chiropractor's landlord intercepted an angel's voicemail confirming that",
        "A medieval cooper told the village blacksmith that Saint Nicholas's reindeer trainer admitted that",
        "The monastery beekeeper told the head librarian that an illuminated manuscript footnote proved that",
        "An apothecary's clerk told the town crier that the Grand Inquisitor's cat psychic confessed that"
    )

    val ABSURDIST_ELEMENTS = listOf(
        "the Holy Trinity is actually just three very clever raccoons sharing a single velvet cloak",
        "transubstantiation secretly turns the communion host into a Flamin' Hot Cheeto if you chew on the left side",
        "transubstantiation converts the sacred wine into Baja Blast Mountain Dew, but only on bank holidays",
        "the Forbidden Fruit in Eden was actually a sleeve of Double Stuf Oreos eaten without offering milk",
        "Purgatory is an infinite DMV waiting room where everyone holds ticket B-69 with fluorescent flickering bulbs",
        "Heaven's celestial choir was ordered to retire all golden harps in favor of neon plastic kazoos",
        "the Beatific Vision is actually an all-you-can-eat Golden Corral dessert bar circa 1996 in Akron, Ohio",
        "righteous souls are reincarnated as ergonomic lumbar-support swivel chairs in upscale suburban dental offices",
        "the Archangel Gabriel delivers all divine prophecy strictly via automated Slack bot notifications with @channel pings",
        "the Ark of the Covenant was actually packed with unopened 1990s Beanie Babies with mint tag protectors",
        "Saint Peter grants immediate celestial entry to anyone who can successfully parallel park a 2004 Honda Civic in under 10 seconds",
        "the Ten Commandments originally contained an eleventh rule forbidding reply-all emails on corporate announcements",
        "burning bush revelations were actually powered by a temperamental air fryer left on max roast",
        "the Seraphim spend 90% of eternity arguing about the thermostat settings in the Heavenly throne room"
    )

    val WHY_HEARSAY_BANK = listOf(
        "A fourth-hand chain of village telephone jumping through an apprentice, a blacksmith, a cousin, and a butler.",
        "Unsubstantiated convent gossip relayed through the gardener who was busy pruning the topiary.",
        "Street rumor jumping from a chimney sweep to a fishmonger to an archbishop's nephew to celestial eavesdropping.",
        "Salon gossip passed through a manicurist, an aunt, and an alleged private sauna confession.",
        "An unverified tavern rumor originating from an inebriated official, repeated to a bard, repeated to a bartender.",
        "An unsubstantiated hypnosis session report passed from a hermit through a psychic to a high school PE teacher.",
        "Workplace watercooler gossip passed down through three ranks of non-technical stakeholders.",
        "Monastic footnote chatter passed along three generations of distracted scriptorium scribes."
    )

    val WHY_HERESY_BANK = listOf(
        "Trinitarian theology strictly forbids defining the Godhead as a trio of trash pandas in disguise.",
        "Reduces the sacred Eucharistic transformation to 1990s snack food mechanics and convenience store commerce.",
        "Completely rewrites Genesis, redefines Original Sin around snack etiquette, and attributes petty dairy cravings to the Almighty.",
        "Replaces canonical Catholic eschatology and spiritual purification with bureaucratic municipal agony.",
        "Subverts traditional angelology and sacred liturgy by declaring divine disdain for holy instruments in favor of party favors.",
        "Replaces the Beatific Vision and eternal salvation with a lukewarm chocolate fountain and discounted Salisbury steak.",
        "Blends Eastern soul-migration into Christian doctrine while denying salvation in favor of commercial furniture design.",
        "Reduces divine omnipresence and celestial grace into an understaffed corporate IT ticket queue.",
        "Bases celestial redemption not on grace, faith, or moral virtue, but on municipal parking validation compliance."
    )

    val HOA_FINE_PRINT_BANK = listOf(
        "God is omnipotent, but heavenly operations run on standard Eastern Standard Time business hours with strict file-type restrictions.",
        "Eternal redemption hangs not on your morality, but on your ability to avoid a $45 parking citation from celestial meter maids.",
        "Sacred dogma accommodates modern dietary restrictions, provided proper awkward politeness is observed by the clergy.",
        "Heavenly quiet hours are strictly enforced between 10:00 PM and 7:00 AM; harp solos during these hours result in a 50-year purgatory penalty.",
        "Subleasing celestial cloud space without prior written approval from Saint Peter's HOA committee voids your angel wings warranty.",
        "All prayer requests must be submitted with two forms of government ID and an unexpired utility bill from your earthly residence.",
        "Divine grace is subject to a 30-day money-back guarantee, provided the soul remains in original unopened spiritual condition.",
        "Seraphim baggage is strictly capped at one personal carry-on halo not exceeding 50 lbs."
    )

    val CHAOS_TWISTS = listOf(
        "Incorporate a 14th-century air fryer possessed by an uncertified Franciscan monk",
        "Feature a Cardinal who is secretly three raccoons in a tailored scarlet cassock",
        "Introduce an HOA fine from Saint Peter over unapproved stained-glass color palettes",
        "Claim divine revelation was delivered strictly via an unmuted Zoom meeting with celestial static",
        "Make salvation contingent on holding an active 1997 Blockbuster video membership card",
        "Mandate that all archangels replace their golden trumpets with squeaky rubber chickens",
        "Assert that the Ark of the Covenant was packed with unopened Beanie Babies with mint tag protectors",
        "Argue that Purgatory is an endless suburban shopping mall where every store is a Spencer's Gifts",
        "Introduce a celestial meter maid issuing $45 parking tickets on cloud nine",
        "Claim that communion wine turns into Baja Blast Mountain Dew, but only on bank holidays",
        "Have the village blacksmith testify that Saint Nicholas's reindeer are actually just goats with cardboard antlers",
        "Insist that original sin happened because Adam and Eve didn't leave a 20% tip at the Tree of Knowledge"
    )

    val CUSTOM_CHAOS_CHAINS = listOf(
        "A tipsy monastery sommelier whispered to the abbey goat that",
        "The Grand Inquisitor's manicurist confessed under hypnosis that",
        "A disgruntled guild artisan overheard two archangels at a tavern bar stating that",
        "The cathedral roof-tiler told the parish baker after two flagons of ale that",
        "A wandering Venetian merchant's parrot squawked repeatedly that",
        "The Vatican's Chief IT Administrator admitted during an all-hands meeting that"
    )

    val CUSTOM_CHAOS_ABSURDITIES = listOf(
        "the Tree of Life is actually a temperamental soft-serve ice cream machine that is constantly under maintenance",
        "divine absolution is delivered via non-refundable automated monthly subscription renewing at $9.99",
        "the Seraphim choir spent four centuries arguing over whether a hot dog qualifies as a sacrificial sandwich",
        "Saint Peter checks heaven entrance tickets using a temperamental barcode scanner from a 1993 Kmart",
        "resurrection of the body is subject to a 90-day manufacturer warranty voided if exposed to direct sunlight",
        "the holy grail was accidentally donated to a goodwill drop-box by an overzealous deacon"
    )

    val CUSTOM_CHAOS_DOCTRINES = listOf(
        "the eternal mystery of late-night grocery store aisle navigation",
        "the sacred canon of microwave meal wattage conversions",
        "the liturgical etiquette of replying 'no worries' to papal encyclicals",
        "the cosmic dogma of untangling holiday string lights for eternity"
    )

    val CUSTOM_CHAOS_FINE_PRINTS = listOf(
        "Grace is valid for eternal salvation unless redeemed on a weekend or federal holiday.",
        "Halos must be wiped down with approved microfiber cloth; polishing with holy water voids the warranty.",
        "Souls occupying cloud units must keep decibel levels below 45 dB during Seraphim naptime.",
        "Failure to submit proof of baptism within 30 business days results in immediate 500-year DMV waiting room diversion."
    )

    fun getRandomChaosTwist(): String = CHAOS_TWISTS.random()

    fun getRandomSetting(): HearsaySetting = HearsaySetting.entries.random()
    fun getRandomDogma(): DogmaCategory = DogmaCategory.entries.random()
    fun getRandomAbsurdistType(): AbsurdistType = AbsurdistType.entries.random()
    fun getRandomFinePrintCategory(): FinePrintCategory = FinePrintCategory.entries.random()

    fun getRandomCustomChain(): String = CUSTOM_CHAOS_CHAINS.random()
    fun getRandomCustomAbsurd(): String = CUSTOM_CHAOS_ABSURDITIES.random()
    fun getRandomCustomDoctrine(): String = CUSTOM_CHAOS_DOCTRINES.random()
    fun getRandomCustomFinePrint(): String = CUSTOM_CHAOS_FINE_PRINTS.random()

    fun generateChaoticCustomRecipe(): JokeRecipe {
        val setting = getRandomSetting()
        val dogma = getRandomDogma()
        val absurdist = getRandomAbsurdistType()
        val fpCategory = getRandomFinePrintCategory()

        return JokeRecipe(
            id = "chaos_recipe_${System.currentTimeMillis()}",
            title = "The Chaotic ${dogma.label} Inquest",
            description = "A wildly randomized theological controversy mixing ${setting.label} rumors with absurd ${absurdist.label}.",
            setting = setting,
            dogma = dogma,
            absurdistType = absurdist,
            hasFinePrint = true,
            finePrintCategory = fpCategory,
            customHearsayChain = getRandomCustomChain(),
            customAbsurdElement = getRandomCustomAbsurd(),
            customOrthodoxyTarget = getRandomCustomDoctrine(),
            customFinePrintCondition = getRandomCustomFinePrint(),
            isPreset = false
        )
    }

    fun generateJoke(recipe: JokeRecipe): GeneratedJoke {
        // If it's a known preset and no custom fields are set, return authentic canonical joke
        if (recipe.isPreset && CANONICAL_JOKES.containsKey(recipe.id) &&
            recipe.customHearsayChain.isNullOrBlank() &&
            recipe.customAbsurdElement.isNullOrBlank() &&
            recipe.customOrthodoxyTarget.isNullOrBlank() &&
            recipe.customFinePrintCondition.isNullOrBlank()
        ) {
            val base = CANONICAL_JOKES[recipe.id]!!
            return base.copy(id = System.currentTimeMillis())
        }

        // Custom or Dynamic generation
        val hearsayPrefix = recipe.customHearsayChain?.takeIf { it.isNotBlank() }
            ?: when (recipe.setting) {
                HearsaySetting.MEDIEVAL_VILLAGE -> "A traveling cobbler's cousin told the tavern keeper that"
                HearsaySetting.VATICAN_PALACE -> "The Cardinal's valet overheard the Monsignor whispering that"
                HearsaySetting.MONASTIC_CLOISTER -> "Brother Francis told the Abbot that the herb gardener admitted that"
                HearsaySetting.SUBURBAN_BEAUTY_SALON -> "The parish secretary's manicurist told my aunt that"
                HearsaySetting.TECH_STARTUP_SLACK -> "The junior engineer told the product manager that the CTO secretly admitted that"
                HearsaySetting.INQUISITORIAL_TRIBUNAL -> "A court bailiff swears he heard the chief witness confess that"
                HearsaySetting.DOCKSIDE_ALEXANDRIA -> "A spice merchant from Alexandria told the harbor master that"
                HearsaySetting.DENTAL_WAITING_ROOM -> "My gym teacher's psychic told him that a local hermit revealed that"
            }

        val absurdCore = recipe.customAbsurdElement?.takeIf { it.isNotBlank() }
            ?: when (recipe.absurdistType) {
                AbsurdistType.JUNK_FOOD -> "transubstantiation turns the sacred elements into Nacho Cheese Doritos if the priest winks at the altar"
                AbsurdistType.ANIMALS_IN_DISGUISE -> "the Holy Trinity is actually three raccoons in a single velvet cloaked trench coat"
                AbsurdistType.MUNICIPAL_BUREAUCRACY -> "Purgatory is an infinite DMV waiting room where your ticket is permanently B-69"
                AbsurdistType.NOSTALGIC_BUFFETS -> "Heaven is an endless 1990s Golden Corral buffet with a lukewarm chocolate fountain"
                AbsurdistType.CHEAP_TOYS -> "God despises golden harps and requires celestial choirs to play neon plastic kazoos"
                AbsurdistType.OFFICE_FURNITURE -> "righteous souls are reincarnated as mid-century modern swivel chairs in dental clinics"
                AbsurdistType.TECH_MIGRATION -> "prayers are only accepted if attached as a properly formatted PDF before 5 PM EST"
            }

        val statement = "\"$hearsayPrefix $absurdCore.\""

        val whyHearsay = when (recipe.setting) {
            HearsaySetting.MEDIEVAL_VILLAGE -> "A classic fourth-hand game of village telephone passing through multiple unverified tavern patrons."
            HearsaySetting.VATICAN_PALACE -> "High-level ecclesiastical palace gossip relayed down three tiers of clerical staff."
            HearsaySetting.MONASTIC_CLOISTER -> "Monastic rumors whispered between the scriptorium and the herb garden without canonical proof."
            HearsaySetting.SUBURBAN_BEAUTY_SALON -> "Salon gossip relayed through an aunt from an alleged private sauna confession."
            HearsaySetting.TECH_STARTUP_SLACK -> "Unsubstantiated workplace gossip passed down through Slack threads and hallway chatter."
            HearsaySetting.INQUISITORIAL_TRIBUNAL -> "Multi-level courtroom hearsay—a witness testifying about what someone else claimed another person said."
            HearsaySetting.DOCKSIDE_ALEXANDRIA -> "Dockside sailors passing third-hand theological rumors across Mediterranean trade routes."
            HearsaySetting.DENTAL_WAITING_ROOM -> "An unsubstantiated hypnosis report passed from a hermit through a psychic to a PE teacher."
        }

        val whyHeresy = recipe.customOrthodoxyTarget?.takeIf { it.isNotBlank() }?.let {
            "Violates established orthodoxy regarding $it by introducing chaotic absurdity into sacred doctrine."
        } ?: when (recipe.dogma) {
            DogmaCategory.HOLY_TRINITY -> "Trinitarian theology explicitly forbids defining the Godhead as three disguised creatures in a single garment."
            DogmaCategory.TRANSUBSTANTIATION -> "Reducing sacred Eucharistic transformation to modern snack food mechanics violates core sacramental dogma."
            DogmaCategory.ORIGINAL_SIN_EDEN -> "Completely rewrites Genesis, redefines Original Sin around snack etiquette, and attributes petty cravings to the Almighty."
            DogmaCategory.PURGATORY_ESCHATOLOGY -> "Replaces canonical eschatology and spiritual purification with soul-crushing municipal bureaucracy."
            DogmaCategory.ANGELOLOGY -> "Subverts traditional angelology and sacred liturgy by declaring divine disdain for holy music in favor of toy woodwinds."
            DogmaCategory.REINCARNATION -> "Blends Eastern soul-migration into Christian doctrine while flatly denying salvation in favor of commercial furniture design."
            DogmaCategory.MONASTIC_PANTHEISM -> "Asserts Pantheism, violating orthodox monotheism by denying a distinct Creator separate from creation."
            DogmaCategory.DIETARY_DAMNATION -> "Fabricates a whole new system of divine damnation based entirely on late-night sourdough snacking habits."
            DogmaCategory.TECH_STACK_ORTHODOXY -> "Directly attacks the sacred, untouchable core doctrine of the company's tech stack and engineering culture."
        }

        val finePrint = if (recipe.hasFinePrint) {
            recipe.customFinePrintCondition?.takeIf { it.isNotBlank() }
                ?: when (recipe.finePrintCategory) {
                    FinePrintCategory.IT_HELPDESK -> "God is omnipotent, but heavenly operations run on standard Eastern Standard Time business hours with strict file-type restrictions."
                    FinePrintCategory.PARKING_CITATION -> "Eternal redemption hangs not on your morality, but on your ability to avoid a $45 parking citation from celestial meter maids."
                    FinePrintCategory.DIETARY_EXEMPTION -> "Sacred dogma accommodates modern dietary restrictions, provided proper awkward politeness is observed by the clergy."
                    FinePrintCategory.HOA_COVENANT -> "Paradise quiet hours are strictly enforced between 10 PM and 7 AM; celestial harp solos incur a 50-year fine."
                    FinePrintCategory.APARTMENT_LEASE -> "Celestial cloud subletting without written approval from Saint Peter voids your golden halo warranty."
                    FinePrintCategory.EXPIRATION_DATE -> "Sacramental absolution is subject to a 30-day proof-of-purchase window with no cash refunds."
                }
        } else null

        return GeneratedJoke(
            id = System.currentTimeMillis(),
            title = recipe.title,
            statement = statement,
            whyHearsay = whyHearsay,
            whyHeresy = whyHeresy,
            finePrint = finePrint,
            recipeTitle = recipe.title,
            hearsaySetting = recipe.setting.label,
            dogmaName = recipe.dogma.label,
            absurdistElement = recipe.customAbsurdElement ?: recipe.absurdistType.label,
            finePrintStyle = if (recipe.hasFinePrint) recipe.finePrintCategory.label else null
        )
    }

    fun mutateSlot(joke: GeneratedJoke, slotType: JokeSlot): GeneratedJoke {
        return when (slotType) {
            JokeSlot.HEARSAY_CHAIN -> {
                val newChain = HEARSAY_CHAINS.filter { !joke.statement.contains(it) }.randomOrNull() ?: HEARSAY_CHAINS.random()
                val currentCore = joke.statement.trim('"').substringAfter("that ").substringAfter("proving ").substringAfter("saying ")
                joke.copy(
                    statement = "\"$newChain $currentCore\"",
                    whyHearsay = WHY_HEARSAY_BANK.random()
                )
            }
            JokeSlot.ABSURD_ELEMENT -> {
                val newAbsurd = ABSURDIST_ELEMENTS.random()
                val currentPrefix = joke.statement.trim('"').substringBefore("that ") + " that"
                joke.copy(
                    statement = "\"$currentPrefix $newAbsurd.\"",
                    absurdistElement = newAbsurd.take(40) + "..."
                )
            }
            JokeSlot.HERESY_DOCTRINE -> {
                joke.copy(
                    whyHeresy = WHY_HERESY_BANK.filter { it != joke.whyHeresy }.randomOrNull() ?: WHY_HERESY_BANK.random()
                )
            }
            JokeSlot.HOA_FINE_PRINT -> {
                val newFinePrint = HOA_FINE_PRINT_BANK.filter { it != joke.finePrint }.randomOrNull() ?: HOA_FINE_PRINT_BANK.random()
                joke.copy(finePrint = newFinePrint)
            }
        }
    }
}

enum class JokeSlot(val label: String, val icon: String) {
    HEARSAY_CHAIN("Hearsay Gossip Chain", "🗣️"),
    ABSURD_ELEMENT("Absurdist Mundane Relic", "🧀"),
    HERESY_DOCTRINE("Theological Dogma Reason", "📜"),
    HOA_FINE_PRINT("Petty HOA Fine Print", "⚖️")
}
