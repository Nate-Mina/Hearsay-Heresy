package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DogmaCategory
import com.example.model.GeneratedJoke
import com.example.ui.theme.GoldPrimary

import androidx.compose.material.icons.filled.VolumeUp

@Composable
fun SavedReliquaryScreen(
    jokes: List<GeneratedJoke>,
    searchQuery: String,
    dogmaFilter: DogmaCategory?,
    onSearchChange: (String) -> Unit,
    onDogmaFilterChange: (DogmaCategory?) -> Unit,
    onToggleFavorite: (GeneratedJoke) -> Unit,
    onShareJoke: (GeneratedJoke) -> Unit,
    onDeleteJoke: (Long) -> Unit,
    onSelectJokeForEditing: (GeneratedJoke) -> Unit,
    onSpeakJoke: (GeneratedJoke) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showFavoritesOnly by remember { mutableStateOf(false) }

    val filteredJokes = remember(jokes, searchQuery, dogmaFilter, showFavoritesOnly) {
        jokes.filter { joke ->
            val matchesFav = !showFavoritesOnly || joke.isFavorite
            val matchesDogma = dogmaFilter == null || joke.dogmaName.contains(dogmaFilter.label, ignoreCase = true)
            val matchesQuery = searchQuery.isBlank() ||
                    joke.title.contains(searchQuery, ignoreCase = true) ||
                    joke.statement.contains(searchQuery, ignoreCase = true) ||
                    joke.absurdistElement.contains(searchQuery, ignoreCase = true) ||
                    (joke.finePrint?.contains(searchQuery, ignoreCase = true) == true) ||
                    (joke.keywords?.contains(searchQuery, ignoreCase = true) == true) ||
                    (joke.humorCategory?.contains(searchQuery, ignoreCase = true) == true) ||
                    (joke.targetAudience?.contains(searchQuery, ignoreCase = true) == true) ||
                    (joke.tone?.contains(searchQuery, ignoreCase = true) == true)
            matchesFav && matchesDogma && matchesQuery
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("saved_reliquary_screen")
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Search jokes, raccoons, DMV, Doritos, fine print...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reliquary_search_input"),
            shape = RoundedCornerShape(14.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Bar (Favorites toggle + Dogma tags)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = showFavoritesOnly,
                onClick = { showFavoritesOnly = !showFavoritesOnly },
                label = { Text("⭐ Starred Only", style = MaterialTheme.typography.labelSmall) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )

            FilterChip(
                selected = dogmaFilter == null,
                onClick = { onDogmaFilterChange(null) },
                label = { Text("All Doctrines", style = MaterialTheme.typography.labelSmall) }
            )

            DogmaCategory.entries.forEach { dogma ->
                FilterChip(
                    selected = dogmaFilter == dogma,
                    onClick = { onDogmaFilterChange(if (dogmaFilter == dogma) null else dogma) },
                    label = { Text(dogma.label, style = MaterialTheme.typography.labelSmall) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (filteredJokes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "📜", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (jokes.isEmpty()) "The Reliquary is Empty" else "No matching heresies found",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (jokes.isEmpty()) "Generate jokes in the Scriptorium to preserve them here." else "Try searching for another rumor or clearing filters.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredJokes, key = { it.id }) { joke ->
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
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = joke.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${joke.hearsaySetting} • ${joke.dogmaName}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Row {
                                    IconButton(onClick = { onSpeakJoke(joke) }) {
                                        Icon(
                                            imageVector = Icons.Default.VolumeUp,
                                            contentDescription = "Speak Joke",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(onClick = { onSelectJokeForEditing(joke) }) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                    IconButton(onClick = { onToggleFavorite(joke) }) {
                                        Icon(
                                            imageVector = if (joke.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            contentDescription = "Favorite",
                                            tint = if (joke.isFavorite) GoldPrimary else MaterialTheme.colorScheme.outline
                                        )
                                    }
                                    IconButton(onClick = { onShareJoke(joke) }) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(onClick = { onDeleteJoke(joke.id) }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                }
                            }

                            if (!joke.humorCategory.isNullOrBlank() || !joke.targetAudience.isNullOrBlank() || !joke.tone.isNullOrBlank() || !joke.keywords.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    joke.humorCategory?.let {
                                        AssistChip(
                                            onClick = {},
                                            label = { Text(it, style = MaterialTheme.typography.labelSmall) },
                                            colors = AssistChipDefaults.assistChipColors(
                                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                                labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                            ),
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                                        )
                                    }
                                    joke.targetAudience?.let {
                                        AssistChip(
                                            onClick = {},
                                            label = { Text(it, style = MaterialTheme.typography.labelSmall) },
                                            colors = AssistChipDefaults.assistChipColors(
                                                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                                                labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                            ),
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f))
                                        )
                                    }
                                    joke.tone?.let {
                                        AssistChip(
                                            onClick = {},
                                            label = { Text(it, style = MaterialTheme.typography.labelSmall) },
                                            colors = AssistChipDefaults.assistChipColors(
                                                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                                                labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                                            ),
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f))
                                        )
                                    }
                                    joke.keywords?.let {
                                        AssistChip(
                                            onClick = {},
                                            label = { Text("🏷️ $it", style = MaterialTheme.typography.labelSmall) },
                                            colors = AssistChipDefaults.assistChipColors(
                                                containerColor = GoldPrimary.copy(alpha = 0.15f),
                                                labelColor = MaterialTheme.colorScheme.onSurface
                                            ),
                                            border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f))
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = joke.customPunchline?.takeIf { it.isNotBlank() } ?: joke.statement,
                                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (!joke.finePrint.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "⚖️ Fine Print: ${joke.finePrint}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
