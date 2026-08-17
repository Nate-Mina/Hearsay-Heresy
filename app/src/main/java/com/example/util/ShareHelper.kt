package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.model.GeneratedJoke

object ShareHelper {

    enum class ShareFormat(val label: String) {
        FULL_INQUEST("Full Inquest (Statement + Analysis + Fine Print)"),
        PUNCHY_TWEET("Social Snippet (Optimized for X/Threads)"),
        CLEAN_QUOTE("Sacred Quote Only")
    }

    fun formatJoke(joke: GeneratedJoke, format: ShareFormat = ShareFormat.FULL_INQUEST): String {
        val punchlineOrStatement = joke.customPunchline?.takeIf { it.isNotBlank() } ?: joke.statement

        return when (format) {
            ShareFormat.FULL_INQUEST -> buildString {
                append("📜 *${joke.title}*\n\n")
                append("$punchlineOrStatement\n\n")
                append("🗣️ *Why it's Hearsay:*\n${joke.whyHearsay}\n\n")
                append("⚖️ *Why it's Heresy:*\n${joke.whyHeresy}\n\n")
                if (!joke.finePrint.isNullOrBlank()) {
                    append("🏛️ *The Liturgical Fine Print (HOA Clause):*\n${joke.finePrint}\n\n")
                }
                append("#HearsayAndHeresy #TheologyHumor #AbsurdistComedy")
            }

            ShareFormat.PUNCHY_TWEET -> buildString {
                append("📜 ${joke.title}\n\n")
                append("$punchlineOrStatement\n\n")
                if (!joke.finePrint.isNullOrBlank()) {
                    append("Fine Print: ${joke.finePrint}\n\n")
                }
                append("#HearsayAndHeresy")
            }

            ShareFormat.CLEAN_QUOTE -> buildString {
                append("$punchlineOrStatement\n\n")
                append("— Recorded in the Hearsay & Heresy Reliquary")
            }
        }
    }

    fun shareToSocialMedia(
        context: Context,
        joke: GeneratedJoke,
        format: ShareFormat = ShareFormat.FULL_INQUEST
    ) {
        val textToShare = formatJoke(joke, format)

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, joke.title)
            putExtra(Intent.EXTRA_TEXT, textToShare)
        }

        val chooserIntent = Intent.createChooser(sendIntent, "Share Heresy via:")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }

    fun copyToClipboard(
        context: Context,
        joke: GeneratedJoke,
        format: ShareFormat = ShareFormat.FULL_INQUEST
    ) {
        val textToShare = formatJoke(joke, format)
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(joke.title, textToShare)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(context, "Heresy copied to parchment (clipboard)!", Toast.LENGTH_SHORT).show()
    }
}
