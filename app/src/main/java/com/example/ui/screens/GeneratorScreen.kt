package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.generator.JokeSlot
import com.example.model.GeneratedJoke
import com.example.model.JokeRecipe
import com.example.ui.components.IlluminatedJokeCard
import com.example.ui.theme.BurgundySecondary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.WaxSealRed

import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeUp

@Composable
fun GeneratorScreen(
    activeJoke: GeneratedJoke?,
    activeRecipe: JokeRecipe,
    presetRecipes: List<JokeRecipe>,
    isGenerating: Boolean,
    customTwistInput: String,
    onCustomTwistChange: (String) -> Unit,
    onInjectRandomTwist: () -> Unit,
    onSelectRecipe: (JokeRecipe) -> Unit,
    onGenerateJoke: () -> Unit,
    onRandomizeRecipe: () -> Unit,
    onRandomizeChaos: () -> Unit,
    onMutateSlot: (JokeSlot) -> Unit,
    onEditJoke: () -> Unit,
    onToggleFavorite: (GeneratedJoke) -> Unit,
    onShareJoke: (GeneratedJoke) -> Unit,
    onSpeakJoke: (GeneratedJoke) -> Unit,
    onOpenVocalOrator: (GeneratedJoke) -> Unit,
    isSpeaking: Boolean,
    onNavigateToLab: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("generator_screen")
    ) {
        // Hero Visual Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner_illustration_1786943982815),
                    contentDescription = "Hearsay and Heresy Scriptorium Banner",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.85f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "THE HERETICAL SCRIPTORIUM",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = "Guided Theological Recipe Engine",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Guided Presets Quick Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📜 GUIDED RECIPE PRESETS",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onRandomizeChaos,
                    modifier = Modifier.size(32.dp).testTag("randomize_chaos_button")
                ) {
                    Icon(
                        Icons.Default.Shuffle,
                        contentDescription = "Chaos Wildcard Recipe",
                        tint = GoldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                IconButton(
                    onClick = onNavigateToLab,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Tune,
                        contentDescription = "Open Recipe Lab",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            presetRecipes.forEach { recipe ->
                FilterChip(
                    selected = activeRecipe.id == recipe.id,
                    onClick = { onSelectRecipe(recipe) },
                    label = {
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (activeRecipe.id == recipe.id) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Optional Custom Twist input with Randomizer Dice
        OutlinedTextField(
            value = customTwistInput,
            onValueChange = onCustomTwistChange,
            placeholder = {
                Text(
                    "Optional custom twist (tap 🎲 for surreal chaos prompt)",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = onInjectRandomTwist,
                    modifier = Modifier.testTag("random_twist_dice_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = "Inject Random Chaos Twist",
                        tint = GoldPrimary
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("custom_twist_input"),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Primary Action Generation Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onGenerateJoke,
                enabled = !isGenerating,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("generate_joke_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.5.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Inscribing Heresy...")
                } else {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Transmit Heretical Joke",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            OutlinedButton(
                onClick = onRandomizeRecipe,
                modifier = Modifier
                    .size(52.dp)
                    .testTag("randomize_recipe_button"),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    Icons.Default.Casino,
                    contentDescription = "Randomize Recipe",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Active Joke Display with Vocal Orator Action
        if (activeJoke != null) {
            // Vocal Orator Quick Bar
            Surface(
                onClick = { onOpenVocalOrator(activeJoke) },
                shape = RoundedCornerShape(14.dp),
                color = if (isSpeaking) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                border = BorderStroke(
                    1.dp,
                    if (isSpeaking) GoldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .testTag("open_vocal_pulpit_button")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                            contentDescription = null,
                            tint = if (isSpeaking) GoldPrimary else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isSpeaking) "Now Proclaiming from the Pulpit..." else "Deliver This Heresy Vocally",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (isSpeaking) "Tap to open vocal controls & personas" else "Full voice acting, timing & punchline delivery",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    Text(
                        text = if (isSpeaking) "🎙️ Active" else "▶ Play",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )
                }
            }

            IlluminatedJokeCard(
                joke = activeJoke,
                onMutateSlot = onMutateSlot,
                onEditJoke = onEditJoke,
                onToggleFavorite = { onToggleFavorite(activeJoke) },
                onShare = { onShareJoke(activeJoke) },
                onSpeakJoke = { onOpenVocalOrator(activeJoke) },
                isSpeakingThisJoke = isSpeaking
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

