/**
 * Hearsay & Heresy - Web Companion Engine
 * Complete client-side theological joke generator, recipe lab, vocal orator, and card exporter.
 */

// ==========================================
// 1. DATA DICTIONARIES & COMBINATORIAL BANKS
// ==========================================

const HUMOR_CATEGORIES = [
  { id: 'puns', icon: '📜', label: 'Theological Puns', desc: 'Clerical wordplay and scholastic linguistic gymnastics.' },
  { id: 'satire', icon: '🥖', label: 'Domestic Satire', desc: 'Daily earthly domestic absurdities colliding with high divine dogma.' },
  { id: 'absurdism', icon: '🦝', label: 'Surreal Absurdism', desc: 'Unhinged juxtapositions (trash pandas in robes, celestial kazoos).' },
  { id: 'bureaucracy', icon: '🏛️', label: 'Bureaucratic Dogma', desc: 'Saint Peter running municipal ticket queues and HOA fines.' },
  { id: 'dark_irony', icon: '🕯️', label: 'Dark Irony & Deadpan', desc: 'Sardonic theological twists and existential nihilism delivered with a sigh.' },
  { id: 'heresy', icon: '🔥', label: 'Anachronistic Heresy', desc: 'Modern technology and consumer culture projected into medieval dogma.' }
];

const TARGET_AUDIENCES = [
  { id: 'office', icon: '💼', label: 'Tired Office Workers', desc: 'Exhausted desk workers who understand purgatory via corporate meetings.' },
  { id: 'philosophers', icon: '🏛️', label: 'Existential Thinkers', desc: 'Contemplatives seeking divine comedy in the absurdity of creation.' },
  { id: 'anxious', icon: '☕', label: 'Anxious Moderns', desc: 'Over-caffeinated moderns grappling with cosmic dread and dietary rules.' },
  { id: 'potluck', icon: '🥧', label: 'Potluck Veterans', desc: 'Church basement attendees well-versed in casseroles and hymn drama.' },
  { id: 'tech', icon: '💻', label: 'Techno-Priests', desc: 'Engineers who treat Kubernetes as religion and StackOverflow as scripture.' },
  { id: 'owls', icon: '🦉', label: 'Night-Shift Owls', desc: 'Midnight wandering souls surviving on late-night snacks and obscure lore.' }
];

const JOKE_TONES = [
  { id: 'bureaucratic', icon: '📋', label: 'Bureaucratic Deadpan', preamble: 'Per ecclesiastical memorandum subsection 4-B:', rate: 0.95, pitch: 0.85, desc: 'Dry, flat, clerical monotony reciting an administrative circular.' },
  { id: 'preacher', icon: '🎙️', label: 'Hysterical Preacher', preamble: 'Hearken, sinners, for the truth doth thunder!', rate: 1.15, pitch: 1.3, desc: 'Fervent, booming revivalist cadence filled with holy alarm.' },
  { id: 'gossip', icon: '🤫', label: 'Whispered Gossip', preamble: 'Do not tell Father Gregory I told you this, but...', rate: 0.9, pitch: 1.05, desc: 'Hushed, conspiratorial village scandal leaned across the cloister wall.' },
  { id: 'scholarly', icon: '📖', label: 'Dry Scholarly Commentary', preamble: 'Footnote 17 of the lost 1284 Synod commentary states:', rate: 0.95, pitch: 1.0, desc: 'Detached academic footnote citing obscure manuscripts.' },
  { id: 'apocalyptic', icon: '⚡', label: 'Apocalyptic Urgency', preamble: 'Repent immediately, for the final seal has broken!', rate: 1.1, pitch: 1.15, desc: 'Breathless urgency proclaiming the end of days.' },
  { id: 'whimsical', icon: '🎭', label: 'Whimsical Chaos', preamble: 'Lo and behold, according to rumors in the butter churning shed:', rate: 1.05, pitch: 1.25, desc: 'Playful, merry absurdity sung like a tavern minstrel.' }
];

const PRESET_RECIPES = [
  {
    id: "raccoon_trinity",
    title: "The Holy Raccoon Trinity",
    desc: "Three clever trash pandas sharing one velvet cloak as the Godhead.",
    statement: "My cousin's blacksmith's apprentice claims he overheard the Cardinal's butler whispering that the Holy Trinity is actually just three very clever raccoons sharing a single velvet cloak.",
    whyHearsay: "It's a fourth-hand game of village telephone passing through an apprentice, a blacksmith, a cousin, and a butler.",
    whyHeresy: "Trinitarian theology explicitly forbids defining the Godhead as a trio of trash pandas in a coat.",
    finePrint: "Discovered during a velvet laundering dispute behind Saint Peter's chapel.",
    category: "Theological Puns",
    audience: "Tired Office Workers",
    tone: "Whispered Gossip",
    keywords: "raccoons, velvet cloak"
  },
  {
    id: "cool_ranch_sacrament",
    title: "The Cool Ranch Sacrament",
    desc: "Transubstantiation turning communion hosts into 1990s seasoned tortilla chips.",
    statement: "Sister Beatrice told the gardener that the Mother Superior admitted during a wine tasting that transubstantiation secretly turns the communion host into a Cool Ranch Dorito, but only if the priest winks at the altar.",
    whyHearsay: "Unsubstantiated convent gossip passed along to the guy trimming the hedges.",
    whyHeresy: "Reducing the sacred Eucharistic transformation to 1990s snack food mechanics violates core Catholic dogma.",
    finePrint: "Valid only while supplies last at select parish convenience stores.",
    category: "Domestic Satire",
    audience: "Potluck Veterans",
    tone: "Whispered Gossip",
    keywords: "Cool Ranch, snack etiquette"
  },
  {
    id: "oreo_fall",
    title: "The Oreo Fall from Grace",
    desc: "Original Sin occurred strictly because Adam and Eve didn't offer milk with their Double Stuf.",
    statement: "A traveling cobbler swears his brother-in-law found a lost gospel where Adam told Eve the Forbidden Fruit was actually a Double Stuf Oreo, and the expulsion from Eden happened strictly because they didn't offer God a glass of milk.",
    whyHearsay: "A fourth-hand legend passed through a cobbler, his brother-in-law, an unverified scroll, and ancient pillow talk.",
    whyHeresy: "It completely rewrites Genesis, redefines Original Sin around snack etiquette, and attributes petty dairy cravings to the Almighty.",
    finePrint: "Oat milk substitutions incur a 40-year purgatory surcharge.",
    category: "Domestic Satire",
    audience: "Anxious Moderns",
    tone: "Dry Scholarly Commentary",
    keywords: "Double Stuf, dairy protocol"
  },
  {
    id: "dmv_purgatory",
    title: "The DMV Purgatory",
    desc: "Purgatory as an infinite municipal waiting room with ticket B-69 and chilly AC.",
    statement: "The parish secretary's manicurist told my aunt that the Pope confessed in a private sauna that Purgatory isn't a realm of purifying fire, but an infinite DMV waiting room where everyone's ticket is B-69 and the air conditioning is slightly too cold.",
    whyHearsay: "Salon gossip relayed through an aunt from an alleged sauna confession.",
    whyHeresy: "Replaces canonical Catholic eschatology and spiritual purification with municipal bureaucracy.",
    finePrint: "Please take a ticket and ensure you hold two forms of celestial identification.",
    category: "Bureaucratic Dogma",
    audience: "Tired Office Workers",
    tone: "Bureaucratic Deadpan",
    keywords: "ticket B-69, air conditioning"
  },
  {
    id: "angelic_kazoo",
    title: "The Angelic Kazoo Mandate",
    desc: "Heavenly hosts discarding angelic harps in favor of cheap plastic party favor kazoos.",
    statement: "A chimney sweep told the fishmonger that the Archbishop's nephew overheard two Archangels agreeing that God actually despises harp music and requires all heavenly hosts to perform praise songs exclusively on plastic kazoos.",
    whyHearsay: "Street rumor jumping from sweep to fishmonger to nephew to celestial eavesdropping.",
    whyHeresy: "Subverts traditional angelology and sacred liturgy by declaring divine disdain for holy music in favor of party-favor woodwinds.",
    finePrint: "Earplugs are strictly prohibited in the third tier of paradise.",
    category: "Surreal Absurdism",
    audience: "Existential Thinkers",
    tone: "Whimsical Chaos",
    keywords: "plastic kazoos, holy choir"
  },
  {
    id: "cleveland_buffet",
    title: "The Suburban Cleveland Eschatology",
    desc: "The Beatific Vision replaced with an endless 1990s Golden Corral buffet.",
    statement: "A traveling bard told the tavern keeper that a drunk Inquisitor confessed Heaven isn't a celestial city of light, but rather an endless, non-refundable Golden Corral buffet in 1990s Ohio.",
    whyHearsay: "Unverified tavern rumor originating from a drunk guy, told to a bard, told to a bartender.",
    whyHeresy: "Replaces the Beatific Vision and eternal salvation with a lukewarm chocolate fountain and discounted Salisbury steak.",
    finePrint: "Salad bar sneezeguard cleanliness is governed by Archangel Michael.",
    category: "Surreal Absurdism",
    audience: "Night-Shift Owls",
    tone: "Dark Irony & Deadpan",
    keywords: "buffet, lukewarm fountain"
  },
  {
    id: "heavenly_it_helpdesk",
    title: "The Heavenly IT Helpdesk",
    desc: "Omnipotence constrained by 5:00 PM EST cutoffs and strict PDF attachment rules.",
    statement: "A stable boy swears the cardinal's nephew overheard a seraphim admit that prayers are only processed if submitted as a properly formatted PDF, and anything sent after 5:00 PM on Friday receives an automated out-of-office auto-reply until Monday morning.",
    whyHearsay: "Multi-step rumor passed through a stable boy, a nephew, and an eavesdropped angel.",
    whyHeresy: "Reduces divine omnipresence and sacred prayer into an understaffed corporate IT ticketing queue.",
    finePrint: "God is omnipotent, but heavenly operations run on standard Eastern Standard Time business hours with strict file-type restrictions.",
    category: "Bureaucratic Dogma",
    audience: "Techno-Priests",
    tone: "Bureaucratic Deadpan",
    keywords: "PDF attachments, ticket queue"
  },
  {
    id: "purgatory_parking",
    title: "The Purgatory Parking Permit",
    desc: "Salvation granted to all souls provided they validate parking at Pearly Gates kiosk.",
    statement: "My grocer's brother claims a monk confessed that full salvation is granted to every soul without exception—provided they don't parallel park in Heaven's main plaza without validating their ticket at the Pearly Gates kiosk within 15 minutes.",
    whyHearsay: "Grocer's brother quoting a monk's unverified confession.",
    whyHeresy: "Bases celestial entry not on grace or virtue, but on parking validation compliance.",
    finePrint: "Eternal redemption hangs not on your morality, but on your ability to avoid a $45 parking citation from celestial meter maids.",
    category: "Bureaucratic Dogma",
    audience: "Tired Office Workers",
    tone: "Bureaucratic Deadpan",
    keywords: "parking kiosk, $45 citation"
  },
  {
    id: "dietary_transubstantiation",
    title: "The Dietary Transubstantiation Exemption",
    desc: "Liturgical accommodation substituting salted saltines while whispering 'no offense'.",
    statement: "A traveling peddler told the innkeeper that an archdeacon admitted the communion host only grants grace if consumed with your left pinky extended, but if you have a gluten intolerance, the priest is required to substitute a salted saltine and whisper 'no offense' toward the altar.",
    whyHearsay: "Peddler hearsay passed over an innkeeper's bar.",
    whyHeresy: "Mangles sacrificial liturgical canon with modern dietary etiquette and awkward apologies.",
    finePrint: "Sacred dogma accommodates modern dietary restrictions, provided proper awkward politeness is observed by the clergy.",
    category: "Domestic Satire",
    audience: "Anxious Moderns",
    tone: "Dry Scholarly Commentary",
    keywords: "gluten free, saltine"
  },
  {
    id: "dentist_reincarnation",
    title: "The Dentist's Chair Reincarnation",
    desc: "Righteous souls reincarnating into mid-century dental modern swivel chairs.",
    statement: "My gym teacher's psychic told him that a local hermit admitted under hypnosis that righteous souls don't go to Heaven, but are reincarnated as mid-century modern swivel chairs in upscale dental offices.",
    whyHearsay: "An unsubstantiated report of a hypnotized hermit passed through a psychic to a PE teacher.",
    whyHeresy: "Blends Eastern soul-migration into Christian doctrine while flatly denying salvation in favor of commercial furniture design.",
    finePrint: "Reclining mechanism guarantees lumbar alignment for eternity.",
    category: "Surreal Absurdism",
    audience: "Existential Thinkers",
    tone: "Dark Irony & Deadpan",
    keywords: "swivel chair, dentist"
  },
  {
    id: "divine_carb_judgment",
    title: "The Divine Carb Judgment",
    desc: "A talking goat reveals Saint Peter damns anyone judging late-night sourdough consumption.",
    statement: "The village baker says a local hermit's goat told him that Saint Peter doesn't check your sins at the Pearly Gates—he just checks if you've ever judged someone for eating sourdough after 8 PM, and if so, hurls you into the fiery pit.",
    whyHearsay: "Secondhand testimony whose primary source is a livestock animal.",
    whyHeresy: "Fabricates a whole new system of divine salvation and damnation based entirely on late-night snacking habits.",
    finePrint: "Bakers holding starter jars are granted provisional immunity.",
    category: "Theological Puns",
    audience: "Night-Shift Owls",
    tone: "Hysterical Preacher",
    keywords: "sourdough, talking goat"
  },
  {
    id: "tech_startup_orthodoxy",
    title: "The Tech Startup Orthodoxy",
    desc: "Junior dev gossips that the core engine is junk and should be moved to Google Sheets.",
    statement: "The junior dev told me that the lead engineer said our core proprietary algorithm is total junk and we should migrate our entire platform to basic Google Sheets.",
    whyHearsay: "Unsubstantiated workplace gossip passed down through the ranks.",
    whyHeresy: "It directly attacks the sacred, untouchable core doctrine of the company's tech stack and engineering culture.",
    finePrint: "All enterprise microservices are subject to spreadsheet cell limits and accidental row deletions.",
    category: "Anachronistic Heresy",
    audience: "Techno-Priests",
    tone: "Whispered Gossip",
    keywords: "Google Sheets, microservices"
  }
];

const HEARSAY_CHAINS = [
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
  "A medieval cooper told the village blacksmith that Saint Nicholas's reindeer trainer admitted that"
];

const ABSURDIST_ELEMENTS = [
  "the Holy Trinity is actually three raccoons sharing a single velvet cloak",
  "transubstantiation converts the sacred wine into Baja Blast Mountain Dew, but only on bank holidays",
  "the Forbidden Fruit in Eden was actually a sleeve of Double Stuf Oreos eaten without offering milk",
  "Purgatory is an infinite DMV waiting room where everyone holds ticket B-69 under flickering fluorescent bulbs",
  "Heaven's celestial choir was ordered to retire all golden harps in favor of neon plastic kazoos",
  "the Beatific Vision is actually an all-you-can-eat Golden Corral dessert bar circa 1996 in Akron, Ohio",
  "righteous souls are reincarnated as ergonomic swivel chairs in suburban dental clinics",
  "the Archangel Gabriel delivers all divine prophecy strictly via automated Slack bot notifications with @channel pings",
  "Saint Peter grants immediate celestial entry to anyone who can successfully parallel park a 2004 Honda Civic in under 10 seconds",
  "the burning bush revelations were actually powered by a temperamental air fryer left on max roast",
  "resurrection of the body is subject to a 90-day manufacturer warranty voided if exposed to direct sunlight"
];

const FINE_PRINTS = [
  "God is omnipotent, but heavenly operations run on standard Eastern Standard Time business hours with strict file-type restrictions.",
  "Eternal redemption hangs not on your morality, but on your ability to avoid a $45 parking citation from celestial meter maids.",
  "Sacred dogma accommodates modern dietary restrictions, provided proper awkward politeness is observed by the clergy.",
  "Heavenly quiet hours are strictly enforced between 10:00 PM and 7:00 AM; harp solos during these hours result in a 50-year purgatory penalty.",
  "Subleasing celestial cloud space without prior written approval from Saint Peter's HOA committee voids your angel wings warranty.",
  "All prayer requests must be submitted with two forms of government ID and an unexpired utility bill from your earthly residence."
];

// ==========================================
// 2. STATE MANAGEMENT
// ==========================================

const state = {
  activeTab: 'generator', // 'generator', 'recipes', 'reliquary'
  currentJoke: null,
  activeRecipeId: 'raccoon_trinity',
  customization: {
    keywords: '',
    category: 'puns',
    audience: 'office',
    tone: 'bureaucratic'
  },
  isPanelExpanded: false,
  isSpeaking: false,
  savedJokes: [],
  searchQuery: '',
  filterCategory: 'all'
};

// ==========================================
// 3. GENERATION LOGIC
// ==========================================

function generateJoke(recipeId = null) {
  let joke;
  const custom = state.customization;
  const toneObj = JOKE_TONES.find(t => t.id === custom.tone) || JOKE_TONES[0];
  const catObj = HUMOR_CATEGORIES.find(c => c.id === custom.category) || HUMOR_CATEGORIES[0];
  const audObj = TARGET_AUDIENCES.find(a => a.id === custom.audience) || TARGET_AUDIENCES[0];

  if (recipeId && PRESET_RECIPES.some(r => r.id === recipeId)) {
    const preset = PRESET_RECIPES.find(r => r.id === recipeId);
    state.activeRecipeId = preset.id;
    
    // Mix custom parameters if user has specified them
    let statement = preset.statement;
    if (custom.keywords.trim()) {
      statement = statement.replace(/\.$/, '') + `, weaving in rumors concerning ${custom.keywords.trim()}.`;
    }
    
    joke = {
      id: Date.now(),
      title: preset.title,
      statement: statement,
      whyHearsay: preset.whyHearsay,
      whyHeresy: preset.whyHeresy,
      finePrint: preset.finePrint,
      category: catObj.label,
      audience: audObj.label,
      tone: toneObj.label,
      keywords: custom.keywords.trim() || preset.keywords,
      isFavorite: false
    };
  } else {
    // Dynamic combinatorial generation
    const chain = HEARSAY_CHAINS[Math.floor(Math.random() * HEARSAY_CHAINS.length)];
    let absurd = ABSURDIST_ELEMENTS[Math.floor(Math.random() * ABSURDIST_ELEMENTS.length)];
    const finePrint = FINE_PRINTS[Math.floor(Math.random() * FINE_PRINTS.length)];

    let statement = `"${toneObj.preamble} ${chain} ${absurd}`;
    if (custom.keywords.trim()) {
      statement += `, specifically involving ${custom.keywords.trim()}`;
    }
    statement += '."';

    joke = {
      id: Date.now(),
      title: `The Decree of ${catObj.label.split(' ')[0]} & ${audObj.label.split(' ')[0]}`,
      statement: statement,
      whyHearsay: `A multi-tier chain of gossip passed between village tradesmen, novices, and eavesdropping attendants.`,
      whyHeresy: `Directly conflicts with orthodox canon by replacing holy mysteries with ${absurd.substring(0, 40)}...`,
      finePrint: finePrint,
      category: catObj.label,
      audience: audObj.label,
      tone: toneObj.label,
      keywords: custom.keywords.trim() || 'theology, medieval, canon',
      isFavorite: false
    };
  }

  state.currentJoke = joke;
  renderCurrentJoke();
}

// ==========================================
// 4. UI RENDERING
// ==========================================

function renderCurrentJoke() {
  const joke = state.currentJoke;
  if (!joke) return;

  const initialLetter = joke.statement.replace(/^["'\s]+/, '').charAt(0) || 'I';
  const statementRest = joke.statement.replace(/^["'\s]+[A-Za-z]/, '');

  document.getElementById('decreeTitle').textContent = joke.title;
  document.getElementById('decreeStatement').innerHTML = `
    <span class="initial-letter">${initialLetter}</span>${statementRest}
  `;
  document.getElementById('whyHearsayText').textContent = joke.whyHearsay;
  document.getElementById('whyHeresyText').textContent = joke.whyHeresy;
  
  const fpEl = document.getElementById('decreeFinePrint');
  if (joke.finePrint) {
    fpEl.style.display = 'block';
    fpEl.textContent = `⚖️ Ecclesiastical Clause: ${joke.finePrint}`;
  } else {
    fpEl.style.display = 'none';
  }

  // Chips
  const chipsContainer = document.getElementById('decreeChips');
  chipsContainer.innerHTML = `
    <span class="decree-chip">🎭 ${joke.category}</span>
    <span class="decree-chip">👥 ${joke.audience}</span>
    <span class="decree-chip">🎙️ ${joke.tone}</span>
    ${joke.keywords ? `<span class="decree-chip">🏷️ ${joke.keywords}</span>` : ''}
  `;

  // Update Save button icon
  const isSaved = state.savedJokes.some(j => j.id === joke.id || j.statement === joke.statement);
  const saveBtn = document.getElementById('saveBtn');
  saveBtn.innerHTML = isSaved ? '⭐ In Reliquary' : '🔖 Save to Reliquary';
}

function renderRecipes() {
  const container = document.getElementById('recipesGrid');
  container.innerHTML = PRESET_RECIPES.map(recipe => `
    <div class="recipe-card ${recipe.id === state.activeRecipeId ? 'active' : ''}" onclick="selectRecipe('${recipe.id}')">
      <div class="recipe-title">${recipe.title}</div>
      <div class="recipe-desc">${recipe.desc}</div>
      <div class="recipe-badges">
        <span class="badge">🎭 ${recipe.category}</span>
        <span class="badge">👥 ${recipe.audience}</span>
      </div>
    </div>
  `).join('');
}

function selectRecipe(id) {
  state.activeRecipeId = id;
  renderRecipes();
  generateJoke(id);
  switchTab('generator');
  showToast(`Loaded "${PRESET_RECIPES.find(r => r.id === id).title}"`);
}

function renderReliquary() {
  const container = document.getElementById('reliquaryGrid');
  const countEl = document.getElementById('reliquaryCount');
  countEl.textContent = state.savedJokes.length;

  const query = state.searchQuery.toLowerCase();
  const filtered = state.savedJokes.filter(joke => {
    return joke.title.toLowerCase().includes(query) ||
      joke.statement.toLowerCase().includes(query) ||
      (joke.keywords && joke.keywords.toLowerCase().includes(query)) ||
      (joke.finePrint && joke.finePrint.toLowerCase().includes(query));
  });

  if (filtered.length === 0) {
    container.innerHTML = `
      <div style="grid-column: 1/-1; text-align: center; padding: 40px; color: var(--text-muted);">
        <p style="font-size: 2rem; margin-bottom: 8px;">📜</p>
        <p style="font-family: var(--font-serif); font-size: 1.1rem;">The Reliquary is empty.</p>
        <p style="font-size: 0.9rem;">Generate decrees and tap "Save to Reliquary" to enshrine them here.</p>
      </div>
    `;
    return;
  }

  container.innerHTML = filtered.map(joke => `
    <div class="saved-card">
      <div class="saved-card-header">
        <h3 style="font-family: var(--font-display); font-size: 1.1rem; color: var(--gold);">${joke.title}</h3>
        <button class="icon-btn" onclick="toggleFavorite(${joke.id})" title="Favorite">
          ${joke.isFavorite ? '⭐' : '☆'}
        </button>
      </div>
      <div class="saved-card-statement">${joke.statement}</div>
      <div class="decree-chips" style="margin-bottom: 12px;">
        <span class="badge">🎭 ${joke.category}</span>
        <span class="badge">👥 ${joke.audience}</span>
      </div>
      <div class="saved-card-footer">
        <button class="action-btn" style="padding: 6px 12px; font-size: 0.8rem;" onclick="speakText('${encodeURIComponent(joke.statement)}')">
          🎙️ Speak
        </button>
        <button class="action-btn" style="padding: 6px 12px; font-size: 0.8rem;" onclick="deleteSavedJoke(${joke.id})">
          🗑️ Remove
        </button>
      </div>
    </div>
  `).join('');
}

// ==========================================
// 5. VOCAL ORATOR (WEB SPEECH API)
// ==========================================

function toggleVocalOrator() {
  if (!('speechSynthesis' in window)) {
    showToast('Speech synthesis is not supported on this browser.');
    return;
  }

  if (state.isSpeaking) {
    window.speechSynthesis.cancel();
    state.isSpeaking = false;
    updateOratorUI(false);
  } else {
    speakCurrentJoke();
  }
}

function speakCurrentJoke() {
  if (!state.currentJoke) return;
  speakText(encodeURIComponent(state.currentJoke.statement));
}

function speakText(encodedText) {
  const text = decodeURIComponent(encodedText);
  window.speechSynthesis.cancel();

  const utterance = new SpeechSynthesisUtterance(text);
  const toneObj = JOKE_TONES.find(t => t.id === state.customization.tone) || JOKE_TONES[0];
  
  utterance.rate = toneObj.rate;
  utterance.pitch = toneObj.pitch;

  // Prefer an English voice with character if available
  const voices = window.speechSynthesis.getVoices();
  const ukVoice = voices.find(v => v.lang.includes('en-GB') || v.name.includes('UK') || v.name.includes('Serena') || v.name.includes('Daniel'));
  if (ukVoice) {
    utterance.voice = ukVoice;
  }

  utterance.onstart = () => {
    state.isSpeaking = true;
    updateOratorUI(true);
  };

  utterance.onend = () => {
    state.isSpeaking = false;
    updateOratorUI(false);
  };

  utterance.onerror = () => {
    state.isSpeaking = false;
    updateOratorUI(false);
  };

  window.speechSynthesis.speak(utterance);
}

function updateOratorUI(isSpeaking) {
  const btn = document.getElementById('speakBtn');
  if (isSpeaking) {
    btn.innerHTML = `
      <div class="orator-wave">
        <span class="orator-bar"></span>
        <span class="orator-bar"></span>
        <span class="orator-bar"></span>
        <span class="orator-bar"></span>
      </div>
      Silence Orator
    `;
    btn.classList.add('gold');
  } else {
    btn.innerHTML = '🎙️ Vocal Orator';
    btn.classList.remove('gold');
  }
}

// ==========================================
// 6. ILLUMINATED CARD EXPORT (HTML5 CANVAS)
// ==========================================

function exportIlluminatedCard() {
  const joke = state.currentJoke;
  if (!joke) return;

  showToast('Illuminating parchment card...');
  const canvas = document.getElementById('exportCanvas');
  const ctx = canvas.getContext('2d');

  // High-resolution canvas (1200 x 800)
  canvas.width = 1200;
  canvas.height = 800;

  // Background Parchment
  ctx.fillStyle = '#f5eedc';
  ctx.fillRect(0, 0, 1200, 800);

  // Outer Border & Gold Filigree
  ctx.lineWidth = 14;
  ctx.strokeStyle = '#2b1f17';
  ctx.strokeRect(20, 20, 1160, 760);

  ctx.lineWidth = 4;
  ctx.strokeStyle = '#d4af37';
  ctx.strokeRect(32, 32, 1136, 736);

  ctx.lineWidth = 1.5;
  ctx.strokeStyle = 'rgba(139, 30, 27, 0.4)';
  ctx.strokeRect(40, 40, 1120, 720);

  // Header Title
  ctx.fillStyle = '#8b1e1b';
  ctx.font = 'bold 22px Cinzel, serif';
  ctx.letterSpacing = '3px';
  ctx.fillText('ECCLESIASTICAL DECREE • HEARSAY & HERESY', 70, 95);

  ctx.fillStyle = '#1a100b';
  ctx.font = 'bold 36px "Cinzel Decorative", Georgia, serif';
  ctx.fillText(joke.title, 70, 150);

  // Divider line
  ctx.beginPath();
  ctx.moveTo(70, 175);
  ctx.lineTo(1130, 175);
  ctx.strokeStyle = 'rgba(139, 30, 27, 0.3)';
  ctx.lineWidth = 2;
  ctx.stroke();

  // Joke Statement (Wrapped text)
  ctx.fillStyle = '#241712';
  ctx.font = 'italic 600 32px "Crimson Pro", Georgia, serif';
  
  const words = joke.statement.split(' ');
  let line = '';
  let y = 240;
  const maxWidth = 1040;
  const lineHeight = 46;

  for (let n = 0; n < words.length; n++) {
    const testLine = line + words[n] + ' ';
    const metrics = ctx.measureText(testLine);
    if (metrics.width > maxWidth && n > 0) {
      ctx.fillText(line, 70, y);
      line = words[n] + ' ';
      y += lineHeight;
    } else {
      line = testLine;
    }
  }
  ctx.fillText(line, 70, y);

  // Fine Print Section
  if (joke.finePrint) {
    y += 50;
    ctx.fillStyle = 'rgba(139, 30, 27, 0.08)';
    ctx.fillRect(70, y - 26, 1060, 56);
    ctx.fillStyle = '#8b1e1b';
    ctx.font = 'bold 18px Cinzel, serif';
    ctx.fillText('CANONICAL FINE PRINT:', 85, y);
    ctx.fillStyle = '#4a2420';
    ctx.font = 'italic 20px "Crimson Pro", Georgia, serif';
    ctx.fillText(joke.finePrint.substring(0, 95) + (joke.finePrint.length > 95 ? '...' : ''), 85, y + 22);
  }

  // Footer & Wax Seal Stamp
  ctx.fillStyle = '#800020';
  ctx.beginPath();
  ctx.arc(1060, 700, 36, 0, Math.PI * 2);
  ctx.fill();
  ctx.strokeStyle = '#d4af37';
  ctx.lineWidth = 3;
  ctx.stroke();

  ctx.fillStyle = '#fcd34d';
  ctx.font = 'bold 22px serif';
  ctx.textAlign = 'center';
  ctx.fillText('✠', 1060, 708);
  ctx.textAlign = 'left';

  ctx.fillStyle = '#7a6a5e';
  ctx.font = '16px Cinzel, serif';
  ctx.fillText('Issued via Hearsay & Heresy • GitHub Pages Web Companion', 70, 740);

  // Download Image
  const dataUrl = canvas.toDataURL('image/png');
  const a = document.createElement('a');
  a.href = dataUrl;
  a.download = `hearsay-decree-${Date.now()}.png`;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);

  showToast('Illuminated decree card downloaded!');
}

// ==========================================
// 7. SHARING & CLIPBOARD
// ==========================================

function shareDecree() {
  const joke = state.currentJoke;
  if (!joke) return;

  const shareText = `${joke.title}\n\n${joke.statement}\n\n— Generated via Hearsay & Heresy`;

  if (navigator.share) {
    navigator.share({
      title: joke.title,
      text: shareText,
      url: window.location.href
    }).catch(() => copyToClipboard(shareText));
  } else {
    copyToClipboard(shareText);
  }
}

function copyToClipboard(text) {
  navigator.clipboard.writeText(text).then(() => {
    showToast('Copied decree to clipboard!');
  }).catch(() => {
    showToast('Failed to copy to clipboard.');
  });
}

// ==========================================
// 8. RELIQUARY (LOCALSTORAGE)
// ==========================================

function loadSavedJokes() {
  try {
    const raw = localStorage.getItem('hearsay_saved_jokes');
    if (raw) {
      state.savedJokes = JSON.parse(raw);
    }
  } catch (e) {
    console.error('Failed to load saved jokes', e);
  }
}

function saveJokesToStorage() {
  try {
    localStorage.setItem('hearsay_saved_jokes', JSON.stringify(state.savedJokes));
  } catch (e) {
    console.error('Failed to write saved jokes', e);
  }
}

function toggleSaveCurrentJoke() {
  const joke = state.currentJoke;
  if (!joke) return;

  const index = state.savedJokes.findIndex(j => j.id === joke.id || j.statement === joke.statement);
  if (index >= 0) {
    state.savedJokes.splice(index, 1);
    showToast('Removed decree from Reliquary');
  } else {
    state.savedJokes.unshift({ ...joke, id: Date.now() });
    showToast('Enshrined decree into Reliquary!');
  }
  saveJokesToStorage();
  renderCurrentJoke();
  renderReliquary();
}

function deleteSavedJoke(id) {
  state.savedJokes = state.savedJokes.filter(j => j.id !== id);
  saveJokesToStorage();
  renderReliquary();
  renderCurrentJoke();
  showToast('Decree removed from Reliquary');
}

function toggleFavorite(id) {
  const joke = state.savedJokes.find(j => j.id === id);
  if (joke) {
    joke.isFavorite = !joke.isFavorite;
    saveJokesToStorage();
    renderReliquary();
  }
}

// ==========================================
// 9. CONTROLS & NAVIGATION
// ==========================================

function switchTab(tabId) {
  state.activeTab = tabId;
  document.querySelectorAll('.nav-btn').forEach(btn => {
    btn.classList.toggle('active', btn.dataset.tab === tabId);
  });
  document.querySelectorAll('.app-section').forEach(sec => {
    sec.classList.toggle('active', sec.id === `section-${tabId}`);
  });
  if (tabId === 'recipes') renderRecipes();
  if (tabId === 'reliquary') renderReliquary();
}

function toggleCustomizationPanel() {
  state.isPanelExpanded = !state.isPanelExpanded;
  const body = document.getElementById('panelBody');
  const icon = document.getElementById('panelToggleIcon');
  if (state.isPanelExpanded) {
    body.style.display = 'block';
    icon.textContent = '▲';
  } else {
    body.style.display = 'none';
    icon.textContent = '▼';
  }
}

function randomizeCustomization() {
  const randomCat = HUMOR_CATEGORIES[Math.floor(Math.random() * HUMOR_CATEGORIES.length)].id;
  const randomAud = TARGET_AUDIENCES[Math.floor(Math.random() * TARGET_AUDIENCES.length)].id;
  const randomTone = JOKE_TONES[Math.floor(Math.random() * JOKE_TONES.length)].id;

  setCustomCategory(randomCat);
  setCustomAudience(randomAud);
  setCustomTone(randomTone);
  showToast('Rolled a surprise theological mix!');
}

function resetCustomization() {
  document.getElementById('keywordInput').value = '';
  state.customization.keywords = '';
  setCustomCategory('puns');
  setCustomAudience('office');
  setCustomTone('bureaucratic');
  showToast('Customization reset to defaults.');
}

function appendSpark(keyword) {
  const input = document.getElementById('keywordInput');
  const current = input.value.trim();
  input.value = current ? `${current}, ${keyword}` : keyword;
  state.customization.keywords = input.value;
}

function setCustomCategory(id) {
  state.customization.category = id;
  document.querySelectorAll('#categoryChips .select-chip').forEach(c => {
    c.classList.toggle('active', c.dataset.id === id);
  });
  const cat = HUMOR_CATEGORIES.find(c => c.id === id);
  document.getElementById('categoryDesc').textContent = `Style: ${cat.desc}`;
}

function setCustomAudience(id) {
  state.customization.audience = id;
  document.querySelectorAll('#audienceChips .select-chip').forEach(c => {
    c.classList.toggle('active', c.dataset.id === id);
  });
  const aud = TARGET_AUDIENCES.find(a => a.id === id);
  document.getElementById('audienceDesc').textContent = `Perspective: ${aud.desc}`;
}

function setCustomTone(id) {
  state.customization.tone = id;
  document.querySelectorAll('#toneChips .select-chip').forEach(c => {
    c.classList.toggle('active', c.dataset.id === id);
  });
  const tone = JOKE_TONES.find(t => t.id === id);
  document.getElementById('toneDesc').textContent = `Vibe: ${tone.desc}`;
}

function showToast(message) {
  const toast = document.getElementById('toast');
  toast.textContent = message;
  toast.style.display = 'block';
  setTimeout(() => {
    toast.style.display = 'none';
  }, 2600);
}

// ==========================================
// 10. INITIALIZATION
// ==========================================

document.addEventListener('DOMContentLoaded', () => {
  loadSavedJokes();

  // Populate Customization Chips
  const catContainer = document.getElementById('categoryChips');
  catContainer.innerHTML = HUMOR_CATEGORIES.map(c => `
    <button class="select-chip ${c.id === state.customization.category ? 'active' : ''}" data-id="${c.id}" onclick="setCustomCategory('${c.id}')">
      ${c.icon} ${c.label}
    </button>
  `).join('');

  const audContainer = document.getElementById('audienceChips');
  audContainer.innerHTML = TARGET_AUDIENCES.map(a => `
    <button class="select-chip ${a.id === state.customization.audience ? 'active' : ''}" data-id="${a.id}" onclick="setCustomAudience('${a.id}')">
      ${a.icon} ${a.label}
    </button>
  `).join('');

  const toneContainer = document.getElementById('toneChips');
  toneContainer.innerHTML = JOKE_TONES.map(t => `
    <button class="select-chip ${t.id === state.customization.tone ? 'active' : ''}" data-id="${t.id}" onclick="setCustomTone('${t.id}')">
      ${t.icon} ${t.label}
    </button>
  `).join('');

  // Search input in Reliquary
  document.getElementById('reliquarySearch').addEventListener('input', (e) => {
    state.searchQuery = e.target.value;
    renderReliquary();
  });

  // Keyword input
  document.getElementById('keywordInput').addEventListener('input', (e) => {
    state.customization.keywords = e.target.value;
  });

  // Initial joke generation
  generateJoke('raccoon_trinity');
  renderRecipes();
  renderReliquary();
});
