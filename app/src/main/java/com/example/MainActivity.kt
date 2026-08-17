package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.GeneratedJoke
import com.example.model.JokeRecipe
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.IconButton
import com.example.ui.components.ShareHeresySheet
import com.example.ui.components.SlotCustomizerDialog
import com.example.ui.components.VocalOratorDialog
import com.example.ui.screens.GeneratorScreen
import com.example.ui.screens.RecipeBookScreen
import com.example.ui.screens.RecipeLabScreen
import com.example.ui.screens.SavedReliquaryScreen
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppTab
import com.example.viewmodel.JokeViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                HearsayApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HearsayApp(viewModel: JokeViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.statusMessage) {
        uiState.statusMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearStatusMessage()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("hearsay_main_scaffold"),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Hearsay & Heresy",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (uiState.activeJoke != null) {
                                viewModel.setShowVocalDialog(true)
                            }
                        },
                        modifier = Modifier.testTag("top_vocal_pulpit_action")
                    ) {
                        Icon(
                            imageVector = if (uiState.vocalState.isSpeaking) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                            contentDescription = "Vocal Pulpit",
                            tint = if (uiState.vocalState.isSpeaking) GoldPrimary else MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                NavigationBarItem(
                    selected = uiState.currentTab == AppTab.GENERATOR,
                    onClick = { viewModel.setTab(AppTab.GENERATOR) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == AppTab.GENERATOR) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
                            contentDescription = "Scriptorium",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Scriptorium", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                NavigationBarItem(
                    selected = uiState.currentTab == AppTab.RECIPE_LAB,
                    onClick = { viewModel.setTab(AppTab.RECIPE_LAB) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == AppTab.RECIPE_LAB) Icons.Filled.Tune else Icons.Outlined.Tune,
                            contentDescription = "Recipe Lab",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Recipe Lab", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                NavigationBarItem(
                    selected = uiState.currentTab == AppTab.RELIQUARY,
                    onClick = { viewModel.setTab(AppTab.RELIQUARY) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == AppTab.RELIQUARY) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Reliquary",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Reliquary", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                NavigationBarItem(
                    selected = uiState.currentTab == AppTab.RECIPE_BOOK,
                    onClick = { viewModel.setTab(AppTab.RECIPE_BOOK) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.currentTab == AppTab.RECIPE_BOOK) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                            contentDescription = "Guidebook",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Guidebook", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                AppTab.GENERATOR -> {
                    GeneratorScreen(
                        activeJoke = uiState.activeJoke,
                        activeRecipe = uiState.activeRecipe,
                        presetRecipes = uiState.presetRecipes,
                        isGenerating = uiState.isGenerating,
                        customTwistInput = uiState.customTwistInput,
                        onCustomTwistChange = viewModel::setCustomTwistInput,
                        onInjectRandomTwist = viewModel::injectRandomChaosTwist,
                        onSelectRecipe = viewModel::selectRecipe,
                        onGenerateJoke = viewModel::generateCurrentJoke,
                        onRandomizeRecipe = viewModel::randomizeRecipe,
                        onRandomizeChaos = viewModel::randomizeChaoticCustomRecipe,
                        onMutateSlot = viewModel::mutateSlot,
                        onEditJoke = { viewModel.setShowSlotCustomizer(true) },
                        onToggleFavorite = viewModel::toggleFavorite,
                        onShareJoke = { viewModel.setShowShareDialog(true) },
                        onSpeakJoke = { joke ->
                            viewModel.deliverJokeVocally(joke)
                        },
                        onOpenVocalOrator = { joke ->
                            viewModel.setActiveJoke(joke)
                            viewModel.setShowVocalDialog(true)
                        },
                        isSpeaking = uiState.vocalState.isSpeaking,
                        onNavigateToLab = { viewModel.setTab(AppTab.RECIPE_LAB) }
                    )
                }

                AppTab.RECIPE_LAB -> {
                    RecipeLabScreen(
                        activeRecipe = uiState.activeRecipe,
                        customSavedRecipes = uiState.customSavedRecipes,
                        onUpdateSetting = viewModel::updateSetting,
                        onUpdateDogma = viewModel::updateDogma,
                        onUpdateAbsurdistType = viewModel::updateAbsurdistType,
                        onUpdateFinePrint = viewModel::updateFinePrint,
                        onUpdateCustomFields = viewModel::updateCustomRecipeFields,
                        onRandomizeChaos = viewModel::randomizeChaoticCustomRecipe,
                        onRandomizeSetting = viewModel::randomizeStageSetting,
                        onRandomizeDogma = viewModel::randomizeStageDogma,
                        onRandomizeAbsurd = viewModel::randomizeStageAbsurd,
                        onRandomizeFinePrint = viewModel::randomizeStageFinePrint,
                        onRandomizeCustomChain = viewModel::randomizeCustomChain,
                        onRandomizeCustomAbsurd = viewModel::randomizeCustomAbsurd,
                        onRandomizeCustomDoctrine = viewModel::randomizeCustomDoctrine,
                        onRandomizeCustomFinePrint = viewModel::randomizeCustomFinePrint,
                        onSaveRecipe = viewModel::saveActiveAsCustomRecipe,
                        onDeleteSavedRecipe = viewModel::deleteCustomRecipe,
                        onSelectRecipe = viewModel::selectRecipe,
                        onGenerateFromRecipe = {
                            viewModel.generateCurrentJoke()
                            viewModel.setTab(AppTab.GENERATOR)
                        }
                    )
                }

                AppTab.RELIQUARY -> {
                    SavedReliquaryScreen(
                        jokes = uiState.allJokes,
                        searchQuery = uiState.searchQuery,
                        dogmaFilter = uiState.dogmaFilter,
                        onSearchChange = viewModel::setSearchQuery,
                        onDogmaFilterChange = viewModel::setDogmaFilter,
                        onToggleFavorite = viewModel::toggleFavorite,
                        onShareJoke = { joke ->
                            viewModel.setActiveJoke(joke)
                            viewModel.setShowShareDialog(true)
                        },
                        onDeleteJoke = viewModel::deleteJoke,
                        onSelectJokeForEditing = { joke ->
                            viewModel.setActiveJoke(joke)
                            viewModel.setShowSlotCustomizer(true)
                        },
                        onSpeakJoke = { joke ->
                            viewModel.setActiveJoke(joke)
                            viewModel.setShowVocalDialog(true)
                        }
                    )
                }

                AppTab.RECIPE_BOOK -> {
                    RecipeBookScreen(
                        onLoadRecipe = { recipe ->
                            viewModel.selectRecipe(recipe)
                            viewModel.setTab(AppTab.GENERATOR)
                        }
                    )
                }
            }

            // Customizer Dialog for Slot and Punchline customization
            if (uiState.showSlotCustomizerDialog && uiState.activeJoke != null) {
                SlotCustomizerDialog(
                    joke = uiState.activeJoke!!,
                    onDismiss = { viewModel.setShowSlotCustomizer(false) },
                    onSave = viewModel::updateJokeContent
                )
            }

            // Vocal Orator Dialog for speech delivery, voice personas & cadence
            if (uiState.showVocalDialog && uiState.activeJoke != null) {
                VocalOratorDialog(
                    joke = uiState.activeJoke!!,
                    vocalState = uiState.vocalState,
                    onDeliverJoke = { persona ->
                        viewModel.deliverJokeVocally(uiState.activeJoke, persona)
                    },
                    onStop = viewModel::stopVocalDelivery,
                    onSelectPersona = viewModel::setOratorPersona,
                    onSetSpeed = viewModel::setVocalSpeed,
                    onToggleAutoPlay = viewModel::setVocalAutoPlay,
                    onDismiss = { viewModel.setShowVocalDialog(false) }
                )
            }

            // Social Media Direct Sharing Sheet
            if (uiState.showShareDialog && uiState.activeJoke != null) {
                ShareHeresySheet(
                    joke = uiState.activeJoke!!,
                    selectedFormat = uiState.selectedShareFormat,
                    onFormatSelected = viewModel::setShareFormat,
                    onDismiss = { viewModel.setShowShareDialog(false) }
                )
            }
        }
    }
}
