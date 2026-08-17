package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator.JokeSlot
import com.example.model.GeneratedJoke
import com.example.ui.theme.AmberParchment
import com.example.ui.theme.BurgundySecondary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.WaxSealRed

import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import com.example.ui.components.AnimatedSpeechWaveform

@Composable
fun IlluminatedJokeCard(
    joke: GeneratedJoke,
    onMutateSlot: (JokeSlot) -> Unit,
    onEditJoke: () -> Unit,
    onToggleFavorite: () -> Unit,
    onShare: () -> Unit,
    onSpeakJoke: () -> Unit = {},
    isSpeakingThisJoke: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("illuminated_joke_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(
            if (isSpeakingThisJoke) 2.dp else 1.5.dp,
            if (isSpeakingThisJoke) GoldPrimary else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header: Title + Wax Seal / Favorite + Speak + Share
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(WaxSealRed, BurgundySecondary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📜",
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = joke.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${joke.hearsaySetting} • ${joke.dogmaName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (isSpeakingThisJoke) {
                    AnimatedSpeechWaveform(isSpeaking = true)
                    Spacer(modifier = Modifier.width(4.dp))
                }

                IconButton(
                    onClick = onSpeakJoke,
                    modifier = Modifier.testTag("speak_joke_button")
                ) {
                    Icon(
                        imageVector = if (isSpeakingThisJoke) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                        contentDescription = "Deliver Vocally",
                        tint = if (isSpeakingThisJoke) GoldPrimary else MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.testTag("favorite_joke_button")
                ) {
                    Icon(
                        imageVector = if (joke.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Favorite",
                        tint = if (joke.isFavorite) GoldPrimary else MaterialTheme.colorScheme.outline
                    )
                }

                IconButton(
                    onClick = onShare,
                    modifier = Modifier.testTag("share_joke_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // The Statement (Quote)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "THE HERETICAL RUMOR",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = joke.customPunchline?.takeIf { it.isNotBlank() } ?: joke.statement,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontStyle = FontStyle.Italic,
                            lineHeight = 24.sp
                        ),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section: Why it's Hearsay
            HeresyReasonBlock(
                icon = "🗣️",
                title = "Why it's Hearsay",
                description = joke.whyHearsay,
                accentColor = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Section: Why it's Heresy
            HeresyReasonBlock(
                icon = "⚖️",
                title = "Why it's Heresy",
                description = joke.whyHeresy,
                accentColor = BurgundySecondary
            )

            // Section: The Liturgical Fine Print (HOA Clause) if present
            if (!joke.finePrint.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(text = "🏛️", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "THE PETTY FINE PRINT (HOA CLAUSE)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = joke.finePrint,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(12.dp))

            // Interactive Slot Modification Toolbar
            Text(
                text = "⚡ QUICK REMIX & SLOT MUTATIONS",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.outline,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AssistChip(
                    onClick = { onMutateSlot(JokeSlot.HEARSAY_CHAIN) },
                    label = { Text("Swap Gossip", style = MaterialTheme.typography.labelSmall) },
                    leadingIcon = { Text("🗣️", fontSize = 12.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                        .testTag("swap_gossip_chip"),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )

                AssistChip(
                    onClick = { onMutateSlot(JokeSlot.ABSURD_ELEMENT) },
                    label = { Text("Swap Relic", style = MaterialTheme.typography.labelSmall) },
                    leadingIcon = { Text("🧀", fontSize = 12.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .testTag("swap_relic_chip"),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )

                AssistChip(
                    onClick = { onMutateSlot(JokeSlot.HERESY_DOCTRINE) },
                    label = { Text("Swap Dogma", style = MaterialTheme.typography.labelSmall) },
                    leadingIcon = { Text("📜", fontSize = 12.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                        .testTag("swap_dogma_chip"),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!joke.finePrint.isNullOrBlank()) {
                    AssistChip(
                        onClick = { onMutateSlot(JokeSlot.HOA_FINE_PRINT) },
                        label = { Text("Swap Fine Print", style = MaterialTheme.typography.labelSmall) },
                        leadingIcon = { Text("⚖️", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }

                AssistChip(
                    onClick = onEditJoke,
                    label = { Text("Customize Punchline & Slots", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier
                        .weight(if (!joke.finePrint.isNullOrBlank()) 1.2f else 1f)
                        .testTag("customize_punchline_chip"),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}

@Composable
private fun HeresyReasonBlock(
    icon: String,
    title: String,
    description: String,
    accentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = icon, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
