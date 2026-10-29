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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.generator.JokeSlot
import com.example.model.GeneratedJoke
import com.example.model.HumorCategory
import com.example.model.JokeCustomizationParams
import com.example.model.JokeRecipe
import com.example.model.JokeTone
import com.example.model.TargetAudience
import com.example.ui.components.IlluminatedJokeCard
import com.example.ui.theme.BurgundySecondary
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.WaxSealRed

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
    customizationParams: JokeCustomizationParams,
    isCustomizationExpanded: Boolean,
    keywordSuggestions: List<String>,
    onKeywordsChange: (String) -> Unit,
    onKeywordSuggestionClick: (String) -> Unit,
    onHumorCategorySelect: (HumorCategory) -> Unit,
    onTargetAudienceSelect: (TargetAudience) -> Unit,
    onToneSelect: (JokeTone) -> Unit,
    onToggleCustomizationPanel: () -> Unit,
    onRandomizeCustomization: () -> Unit,
    onResetCustomization: () -> Unit,
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

        Spacer(modifier = Modifier.height(14.dp))

        // Advanced Customization Panel (Keywords, Humor Category, Target Audience, Tone)
        AdvancedCustomizationPanel(
            customizationParams = customizationParams,
            isExpanded = isCustomizationExpanded,
            keywordSuggestions = keywordSuggestions,
            onKeywordsChange = onKeywordsChange,
            onKeywordSuggestionClick = onKeywordSuggestionClick,
            onHumorCategorySelect = onHumorCategorySelect,
            onTargetAudienceSelect = onTargetAudienceSelect,
            onToneSelect = onToneSelect,
            onTogglePanel = onToggleCustomizationPanel,
            onRandomizeCustomization = onRandomizeCustomization,
            onResetCustomization = onResetCustomization,
            activeRecipeTitle = activeRecipe.title
        )

        Spacer(modifier = Modifier.height(14.dp))

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

@Composable
fun AdvancedCustomizationPanel(
    customizationParams: JokeCustomizationParams,
    isExpanded: Boolean,
    keywordSuggestions: List<String>,
    onKeywordsChange: (String) -> Unit,
    onKeywordSuggestionClick: (String) -> Unit,
    onHumorCategorySelect: (HumorCategory) -> Unit,
    onTargetAudienceSelect: (TargetAudience) -> Unit,
    onToneSelect: (JokeTone) -> Unit,
    onTogglePanel: () -> Unit,
    onRandomizeCustomization: () -> Unit,
    onResetCustomization: () -> Unit,
    activeRecipeTitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("advanced_customization_panel"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
        ),
        border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "ADVANCED CUSTOMIZATION",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "${customizationParams.humorCategory.icon} ${customizationParams.humorCategory.label.substringBefore(" &")} • ${customizationParams.targetAudience.icon} ${customizationParams.targetAudience.label.substringBefore(" ")} • ${customizationParams.tone.icon} ${customizationParams.tone.label.substringBefore(" ")}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            maxLines = 1
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onRandomizeCustomization,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("customization_surprise_mix")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = "Surprise Mix",
                            tint = GoldPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onTogglePanel,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("toggle_customization_panel")
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                // 1. SPECIFIC KEYWORDS
                Text(
                    text = "🔑 SPECIFIC KEYWORDS TO WEAVE IN",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = customizationParams.keywords,
                    onValueChange = onKeywordsChange,
                    placeholder = {
                        Text(
                            "e.g., air fryer, Costco, bluetooth, roomba, microwave fish",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    trailingIcon = {
                        if (customizationParams.keywords.isNotBlank()) {
                            IconButton(onClick = { onKeywordsChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear Keywords",
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("customization_keywords_input"),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Quick Suggestions Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sparks:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight.SemiBold
                    )
                    keywordSuggestions.forEach { suggestion ->
                        AssistChip(
                            onClick = { onKeywordSuggestionClick(suggestion) },
                            label = { Text("+ $suggestion", style = MaterialTheme.typography.labelSmall) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 2. HUMOR CATEGORY
                Text(
                    text = "🎭 DESIRED HUMOR CATEGORY",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    HumorCategory.entries.forEach { category ->
                        FilterChip(
                            selected = customizationParams.humorCategory == category,
                            onClick = { onHumorCategorySelect(category) },
                            label = {
                                Text(
                                    text = "${category.icon} ${category.label}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (customizationParams.humorCategory == category) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Style: ${customizationParams.humorCategory.description}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 3. TARGET AUDIENCE
                Text(
                    text = "👥 TARGET AUDIENCE",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TargetAudience.entries.forEach { audience ->
                        FilterChip(
                            selected = customizationParams.targetAudience == audience,
                            onClick = { onTargetAudienceSelect(audience) },
                            label = {
                                Text(
                                    text = "${audience.icon} ${audience.label}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (customizationParams.targetAudience == audience) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Perspective: ${customizationParams.targetAudience.description}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 4. TONE OF DELIVERY
                Text(
                    text = "🎙️ TONE OF DELIVERY",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    JokeTone.entries.forEach { tone ->
                        FilterChip(
                            selected = customizationParams.tone == tone,
                            onClick = { onToneSelect(tone) },
                            label = {
                                Text(
                                    text = "${tone.icon} ${tone.label}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (customizationParams.tone == tone) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Vibe: ${customizationParams.tone.description}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(6.dp))

                // Footer Row: Reset button + Base Recipe indication
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onResetCustomization,
                        modifier = Modifier.testTag("reset_customization_button")
                    ) {
                        Text(
                            text = "↺ Reset Customization",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    Text(
                        text = "Base: $activeRecipeTitle",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

