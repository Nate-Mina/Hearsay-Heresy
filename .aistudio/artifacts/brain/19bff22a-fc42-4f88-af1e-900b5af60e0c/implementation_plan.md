# GitHub Pages Web Companion for Hearsay & Heresy

Deploy a dedicated, interactive web companion for **Hearsay & Heresy** ready for instant hosting on GitHub Pages via a pre-built `/docs` static bundle and automated GitHub Actions workflow, while preserving the Android application.

### User Review & Critical Decisions

> [!IMPORTANT]
> The following architectural and deployment decisions were confirmed based on your preferences:
> - **Platform Strategy**: Interactive web companion matching the illuminated medieval UI, running client-side with zero build steps or server dependencies.
> - **Deployment Setup**: Pre-built `/docs` folder compatible with GitHub's native Pages branch publishing, paired with an automated `.github/workflows/deploy-pages.yml` workflow for continuous deployment.
> - **Browser Capabilities**: Full feature parity with the Android core experience: theological recipe engine, advanced customization panel (keywords, humor categories, audiences, delivery tones), Web Speech API vocal orator, HTML5 Canvas illuminated card image export, social sharing, and a client-side localStorage Reliquary.

---

### 1. Overview & Core Concept

- **What It Does**: Enables users to generate, remix, speak, and export hilarious theological hearsay jokes directly in any web browser hosted for free on GitHub Pages.
- **Target Audience**: Mobile and desktop web visitors, social media followers, and repository visitors who want an instant, zero-install experience of the medieval satire engine alongside the native Android app.
- **Key Value**: Provides zero-friction accessibility and virality—visitors can click a GitHub Pages link, generate decrees, listen to the vocal orator, and export illuminated parchment cards directly to Twitter/Reddit/Instagram without downloading an APK.

---

### 2. User Experience & Visual Design

- **Key User Flows**:
  1. **Landing & Generation Flow**: Visitor lands on the page with medieval atmospheric parchment/obsidian aesthetics, browses preset recipes via illuminated carousel chips, tweaks slots or opens the Advanced Customization Panel, and taps *Summon Hearsay*.
  2. **Vocal Orator & Speech Flow**: Tapping *Listen to Decree* activates the browser's `SpeechSynthesis` modulated to the selected tone (preacher pulpit cadence, breathless gossip, or bureaucratic drone) with reactive audio wave animations.
  3. **Canvas Card Export & Sharing Flow**: Tapping *Export Decree Card* renders a high-resolution parchment graphic on an off-screen HTML5 `<canvas>` (illuminated border, Latin headers, seal stamp) and triggers an instant PNG image download or native `navigator.share`.
  4. **Reliquary & Persistence Flow**: Saved jokes are persisted in `localStorage` and viewable in a dedicated *Reliquary* tab with full-text search, category filtering, and favorite toggles.

- **Visual Identity & Theme**:
  - **Aesthetic Direction**: Illuminated Gothic manuscript meeting dark ecclesiastical antiquity.
  - **Color Palette**:
    - Primary Gold: `#D4AF37` and Bright Gold `#F3E5AB`
    - Deep Obsidian Base: `#12100E` / Card Background: `#1C1815`
    - Imperial Burgundy Accent: `#800020`
    - Parchment Light Tone: `#F9F6EE`
    - Border Filigree & Dividers: Gold metallic foil borders (`rgba(212, 175, 55, 0.4)`)
  - **Typography**: Google Fonts pairing:
    - Display / Headers: `Cinzel Decorative` & `MedievalSharp`
    - Body Text: `Crimson Pro` & `Cinzel`
  - **Ornamentation**: Wax seal SVG stamps, gothic illuminated initial drop caps, and scroll parchment borders.

---

### 3. Key Product Decisions & Trade-Offs

- **Zero-Dependency Vanilla Web Stack in `/docs`**:
  - *Chosen Approach*: Self-contained HTML5, CSS3, and modern modular ES6 JavaScript contained directly in `/docs/index.html` (or accompanying modular script files).
  - *Why*: Eliminates node/npm build complexity during GitHub Pages deployment. Any user cloning or forking the repository can enable GitHub Pages with one click from GitHub's repository settings pointing to `/docs`, or use the automated GitHub Actions workflow without build timeouts.
  - *Alternatives Considered*: KMP / Compose for Web (Wasm). Rejected because Compose Wasm requires complex Gradle compilation, large wasm bundle downloads (30MB+), and browser compatibility caveats, whereas a dedicated HTML5/Canvas client loads in milliseconds.

- **Client-Side Speech Synthesis (Web Speech API)**:
  - *Chosen Approach*: `window.speechSynthesis` with dynamic pitch, rate, and voice pitch adjustments matching the Android TextToSpeech engine.
  - *Why*: Works natively across Safari, Chrome, Edge, and mobile browsers without requiring server-side API keys or audio streaming costs.

- **Dual Deployment Readiness (Pages Branch & Actions)**:
  - *Chosen Approach*: Deliver both the pre-built `/docs` directory and a `.github/workflows/deploy-pages.yml` GitHub Actions workflow.
  - *Why*: Gives users complete flexibility—they can deploy either via GitHub's standard `Deploy from a branch -> /docs` setting or via modern GitHub Actions with zero configuration.

---

### 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────┐
│                   GitHub Repository                    │
│                                                        │
│  ┌─────────────────────────┐  ┌─────────────────────┐  │
│  │   Android App Module    │  │  GitHub Pages docs  │  │
│  │ (Kotlin/Jetpack Compose)│  │ (Standalone Web App)│  │
│  │  - JokeGeneratorEngine  │  │  - docs/index.html  │  │
│  │  - Compose Screens      │  │  - docs/app.js      │  │
│  │  - Android TTS Engine   │  │  - docs/styles.css  │  │
│  └─────────────────────────┘  └──────────┬──────────┘  │
│                                          │             │
│  ┌───────────────────────────────────────┴──────────┐  │
│  │   .github/workflows/deploy-pages.yml             │  │
│  │   (Automated deployment to GitHub Pages)         │  │
│  └──────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────┘
                           │
                           ▼
             ┌───────────────────────────┐
             │   GitHub Pages Hosting    │
             │  (https://<user>.github   │
             │   .io/<repository>/)      │
             └───────────────────────────┘
```

#### Web Client Component Architecture

1. **`docs/index.html`**:
   - Modern, responsive semantic HTML layout designed for both mobile devices and wide desktop screens.
   - Header with illuminated title, parchment decree banner, and navigation tabs (*Generator*, *Recipe Lab*, *Reliquary*).
   - Interactive recipe selector carousel with filter badges.
   - Slot customization card (Topic, Absurdist Twist, Ecclesiastical Setting, Fine Print).
   - Advanced Customization Drawer (Keywords input with sparks, Humor Categories, Target Audience, Tone of Delivery).
   - Illuminated Decree Display Card with decorative gold borders, illuminated drop cap, and wax seal.
   - Action toolbar: *Summon Hearsay*, *Vocal Orator*, *Export Card (PNG)*, *Share Decree*, *Save to Reliquary*.

2. **`docs/app.js`**:
   - **`TheologicalJokeEngine`**: Complete JavaScript port of `JokeGeneratorEngine.kt` containing all preset recipes, slot options, absurdist elements, tone preambles, and fine-print legal disclaimers.
   - **`VocalOrator`**: Controls `window.speechSynthesis` with speech parameters matched to the selected tone (e.g., solemn cathedral resonance, rapid bureaucratic disclaimer, fervent revivalist pace).
   - **`IlluminatedCardRenderer`**: HTML5 Canvas rendering pipeline that draws an authentic antique parchment card, gold leaf borders, gothic typography, seal graphics, and triggers instant download as `hearsay-decree.png`.
   - **`ReliquaryStore`**: `localStorage` CRUD management for saving, favoriting, and searching decrees.

3. **`docs/styles.css`**:
   - High-fidelity medieval illuminated stylesheet using modern CSS variables, gold foil gradients, parchment texture effects, smooth transitions, and responsive mobile-first layouts.

4. **`.github/workflows/deploy-pages.yml`**:
   - Continuous deployment action using `actions/configure-pages`, `actions/upload-pages-artifact` pointing to `docs/`, and `actions/deploy-pages`.

5. **`README.md` Update**:
   - Adds a "Run on GitHub Pages" section with 2-step setup instructions for repository forks or GitHub releases.
