# 📜 Hearsay & Heresy: Illuminated Theological Satire

> Generate, remix, customize, and speak hilarious medieval theological hearsay jokes based on guided recipes, slot modifications, fine-print clauses, and social media sharing.

---

## 🌐 Running on GitHub Pages

The repository contains a fully responsive, zero-dependency illuminated web companion in the [`/docs`](./docs) directory that runs directly in any modern browser without requiring an Android device.

### Method 1: Automated GitHub Actions (Recommended)
1. Push your repository to GitHub.
2. Navigate to your repository's **Settings** ➔ **Pages**.
3. Under **Build and deployment** ➔ **Source**, select **GitHub Actions**.
4. The included [`.github/workflows/deploy-pages.yml`](./.github/workflows/deploy-pages.yml) workflow will automatically build and publish the site to `https://<your-username>.github.io/<repo-name>/`.

### Method 2: Deploy from Branch (`/docs`)
1. Go to repository **Settings** ➔ **Pages**.
2. Under **Build and deployment** ➔ **Source**, select **Deploy from a branch**.
3. Under **Branch**, select `main` and set the folder dropdown to `/docs`.
4. Click **Save**. Within 1–2 minutes, your site will be live.

---

## ✨ Features

### Web Companion (`/docs`)
- **Interactive Joke Generator**: Dual-engine generation (all 12+ preset canonical recipes + dynamic combinatorial hearsay chain generator).
- **Advanced Customization Panel**:
  - Keyword input with one-tap "Sparks" (e.g., *air fryer*, *Costco*, *roomba*, *microwave fish*).
  - Desired humor categories (*Theological Puns*, *Domestic Satire*, *Surreal Absurdism*, *Bureaucratic Dogma*, *Dark Irony*, *Anachronistic Heresy*).
  - Target audience selection (*Tired Office Workers*, *Existential Philosophers*, *Anxious Moderns*, *Potluck Veterans*, *Techno-Priests*, *Night-Shift Owls*).
  - Delivery tones (*Bureaucratic Deadpan*, *Hysterical Preacher*, *Whispered Gossip*, *Dry Scholarly Commentary*, *Apocalyptic Urgency*, *Whimsical Chaos*).
  - "Surprise Mix" (randomizer) and "Reset" buttons.
- **Vocal Orator (Web Speech API)**: Speaks the decree in real-time with pitch, rate, and timbre calibrated to the chosen delivery tone, accompanied by animated audio visualizers.
- **High-Resolution Card Export**: Client-side HTML5 Canvas rendering of illuminated parchment cards with wax seal stamp and gold foil borders downloaded as PNG.
- **Web Share & Clipboard**: Native `navigator.share` on mobile devices and clipboard copy.
- **Enshrined Reliquary**: Offline bookmarking in browser `localStorage` with full-text search and favorite toggles.

### Android Application (`/app`)
- Modern Jetpack Compose UI adhering strictly to Material Design 3 and medieval illuminated manuscript aesthetics.
- Android TextToSpeech engine with pitch/speed modulation and animated vocal orator dialog.
- Android Intent sharing and bitmap card rendering.
- Offline combinatorial recipe lab + online Gemini AI theological recipe creator.

---

## 🛠️ Project Structure

```
├── .github/workflows/
│   └── deploy-pages.yml      # GitHub Actions Pages deployment
├── docs/                     # Static website for GitHub Pages
│   ├── index.html            # Illuminated web interface
│   ├── styles.css            # Medieval illuminated manuscript styling
│   └── app.js                # Joke generator, Web Speech, Canvas export
├── app/                      # Android Native Application
│   └── src/main/java/com/example/
│       ├── generator/        # Kotlin joke generation engine
│       ├── model/            # Data models & category enums
│       ├── ui/               # Jetpack Compose screens & components
│       └── MainActivity.kt   # Android entry point
└── README.md
```
