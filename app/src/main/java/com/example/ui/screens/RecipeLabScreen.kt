package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AbsurdistType
import com.example.model.DogmaCategory
import com.example.model.FinePrintCategory
import com.example.model.HearsaySetting
import com.example.model.JokeRecipe

import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Shuffle

@Composable
fun RecipeLabScreen(
    activeRecipe: JokeRecipe,
    customSavedRecipes: List<JokeRecipe>,
    onUpdateSetting: (HearsaySetting) -> Unit,
    onUpdateDogma: (DogmaCategory) -> Unit,
    onUpdateAbsurdistType: (AbsurdistType) -> Unit,
    onUpdateFinePrint: (Boolean, FinePrintCategory) -> Unit,
    onUpdateCustomFields: (title: String?, chain: String?, absurd: String?, doctrine: String?, finePrint: String?) -> Unit,
    onRandomizeChaos: () -> Unit = {},
    onRandomizeSetting: () -> Unit = {},
    onRandomizeDogma: () -> Unit = {},
    onRandomizeAbsurd: () -> Unit = {},
    onRandomizeFinePrint: () -> Unit = {},
    onRandomizeCustomChain: () -> Unit = {},
    onRandomizeCustomAbsurd: () -> Unit = {},
    onRandomizeCustomDoctrine: () -> Unit = {},
    onRandomizeCustomFinePrint: () -> Unit = {},
    onSaveRecipe: (title: String, desc: String) -> Unit,
    onDeleteSavedRecipe: (String) -> Unit,
    onSelectRecipe: (JokeRecipe) -> Unit,
    onGenerateFromRecipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSaveDialog by remember { mutableStateOf(false) }
    var recipeNameInput by remember { mutableStateOf("") }
    var recipeDescInput by remember { mutableStateOf("") }

    var customChainInput by remember(activeRecipe) { mutableStateOf(activeRecipe.customHearsayChain ?: "") }
    var customAbsurdInput by remember(activeRecipe) { mutableStateOf(activeRecipe.customAbsurdElement ?: "") }
    var customDoctrineInput by remember(activeRecipe) { mutableStateOf(activeRecipe.customOrthodoxyTarget ?: "") }
    var customFinePrintInput by remember(activeRecipe) { mutableStateOf(activeRecipe.customFinePrintCondition ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("recipe_lab_screen")
    ) {
        // Lab Header Card with Chaos Injection
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "🧪", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Hearsay & Heresy Recipe Lab",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Modify the 4 guided structural layers or inject chaos",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    Button(
                        onClick = onRandomizeChaos,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(),
                        modifier = Modifier.testTag("lab_chaos_button")
                    ) {
                        Icon(Icons.Default.Shuffle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Chaos", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // STAGE 1: The Hearsay Chain
        RecipeStageSection(
            number = "1",
            icon = "🗣️",
            title = "The Hearsay Formula",
            subtitle = "Construct an unreliable multi-layered chain of transmission",
            onRandomizeStage = onRandomizeSetting
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HearsaySetting.entries.forEach { setting ->
                    FilterChip(
                        selected = activeRecipe.setting == setting,
                        onClick = { onUpdateSetting(setting) },
                        label = { Text(setting.label, style = MaterialTheme.typography.labelSmall) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = customChainInput,
                onValueChange = {
                    customChainInput = it
                    onUpdateCustomFields(null, it.takeIf { it.isNotBlank() }, null, null, null)
                },
                placeholder = { Text("Optional custom hearsay transmission prefix...") },
                trailingIcon = {
                    IconButton(onClick = onRandomizeCustomChain) {
                        Icon(Icons.Default.Casino, contentDescription = "Randomize Chain", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // STAGE 2: The Theological Heresy
        RecipeStageSection(
            number = "2",
            icon = "📜",
            title = "The Heresy Formula",
            subtitle = "Target established religious or institutional orthodoxy",
            onRandomizeStage = onRandomizeDogma
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                DogmaCategory.entries.chunked(2).forEach { rowEntries ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowEntries.forEach { dogma ->
                            FilterChip(
                                selected = activeRecipe.dogma == dogma,
                                onClick = { onUpdateDogma(dogma) },
                                label = { Text(dogma.label, style = MaterialTheme.typography.labelSmall, maxLines = 1) },
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }
                        if (rowEntries.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Orthodox Doctrine: ${activeRecipe.dogma.orthodoxDogma}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Theological Boundary: ${activeRecipe.dogma.violationDesc}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = customDoctrineInput,
                onValueChange = {
                    customDoctrineInput = it
                    onUpdateCustomFields(null, null, null, it.takeIf { it.isNotBlank() }, null)
                },
                placeholder = { Text("Optional custom orthodoxy target...") },
                trailingIcon = {
                    IconButton(onClick = onRandomizeCustomDoctrine) {
                        Icon(Icons.Default.Casino, contentDescription = "Randomize Doctrine", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // STAGE 3: The Absurdist Engine
        RecipeStageSection(
            number = "3",
            icon = "🧀",
            title = "The Absurdist Engine",
            subtitle = "Inject mundane modern items or surreal imagery into sacred slots",
            onRandomizeStage = onRandomizeAbsurd
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AbsurdistType.entries.forEach { type ->
                    FilterChip(
                        selected = activeRecipe.absurdistType == type,
                        onClick = { onUpdateAbsurdistType(type) },
                        label = { Text("${type.iconEmoji} ${type.label}", style = MaterialTheme.typography.labelSmall) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = customAbsurdInput,
                onValueChange = {
                    customAbsurdInput = it
                    onUpdateCustomFields(null, null, it.takeIf { it.isNotBlank() }, null, null)
                },
                placeholder = { Text("Optional custom absurd relic (e.g. 'a lukewarm chocolate fountain in Ohio')...") },
                trailingIcon = {
                    IconButton(onClick = onRandomizeCustomAbsurd) {
                        Icon(Icons.Default.Casino, contentDescription = "Randomize Absurdity", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // STAGE 4: The HOA Fine Print Clause
        RecipeStageSection(
            number = "4",
            icon = "⚖️",
            title = "The Petty Liturgical Fine Print",
            subtitle = "Treat divine cosmic reality like an apartment lease or IT helpdesk queue",
            onRandomizeStage = onRandomizeFinePrint
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Enable HOA / Fine Print Clause",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Adds bureaucratic friction to infinite celestial stakes",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Switch(
                    checked = activeRecipe.hasFinePrint,
                    onCheckedChange = { onUpdateFinePrint(it, activeRecipe.finePrintCategory) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }

            if (activeRecipe.hasFinePrint) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FinePrintCategory.entries.forEach { fp ->
                        FilterChip(
                            selected = activeRecipe.finePrintCategory == fp,
                            onClick = { onUpdateFinePrint(true, fp) },
                            label = { Text(fp.label, style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = customFinePrintInput,
                    onValueChange = {
                        customFinePrintInput = it
                        onUpdateCustomFields(null, null, null, null, it.takeIf { it.isNotBlank() })
                    },
                    placeholder = { Text("Optional custom fine print rule...") },
                    trailingIcon = {
                        IconButton(onClick = onRandomizeCustomFinePrint) {
                            Icon(Icons.Default.Casino, contentDescription = "Randomize Fine Print", tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons: Generate & Save as Custom Recipe
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = { showSaveDialog = true },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("save_recipe_template_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.BookmarkAdd, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Template")
            }

            Button(
                onClick = onGenerateFromRecipe,
                modifier = Modifier
                    .weight(1.3f)
                    .height(50.dp)
                    .testTag("generate_from_lab_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Generate Joke", fontWeight = FontWeight.Bold)
            }
        }

        // Custom Saved Recipes section if any exist
        if (customSavedRecipes.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "📁 YOUR SAVED RECIPE TEMPLATES",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            customSavedRecipes.forEach { customRecipe ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = customRecipe.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${customRecipe.setting.label} • ${customRecipe.dogma.label}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        Button(
                            onClick = {
                                onSelectRecipe(customRecipe)
                                onGenerateFromRecipe()
                            },
                            modifier = Modifier.padding(end = 4.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Load", fontSize = 12.sp)
                        }

                        IconButton(onClick = { onDeleteSavedRecipe(customRecipe.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.outline)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    // Save Template Dialog
    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Save Recipe Template") },
            text = {
                Column {
                    Text(
                        "Give your custom joke recipe a name to reuse anytime:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = recipeNameInput,
                        onValueChange = { recipeNameInput = it },
                        placeholder = { Text("e.g. The Subway Footlong Eschatology") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Recipe Name") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = recipeDescInput,
                        onValueChange = { recipeDescInput = it },
                        placeholder = { Text("e.g. Monastic telephone gossiping about sandwich salvation") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Description") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSaveRecipe(recipeNameInput, recipeDescInput)
                        showSaveDialog = false
                    },
                    enabled = recipeNameInput.isNotBlank()
                ) {
                    Text("Save to Lab")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showSaveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun RecipeStageSection(
    number: String,
    icon: String,
    title: String,
    subtitle: String,
    onRandomizeStage: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = " STEP $number ",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Text(text = icon, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (onRandomizeStage != null) {
                    IconButton(
                        onClick = onRandomizeStage,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = "Randomize Step $number",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}
