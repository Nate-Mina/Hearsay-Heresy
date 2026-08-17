package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.generator.JokeGeneratorEngine
import com.example.model.GeneratedJoke

@Composable
fun SlotCustomizerDialog(
    joke: GeneratedJoke,
    onDismiss: () -> Unit,
    onSave: (statement: String, punchline: String?, whyHearsay: String, whyHeresy: String, finePrint: String?) -> Unit
) {
    var statement by remember { mutableStateOf(joke.statement) }
    var punchline by remember { mutableStateOf(joke.customPunchline ?: "") }
    var whyHearsay by remember { mutableStateOf(joke.whyHearsay) }
    var whyHeresy by remember { mutableStateOf(joke.whyHeresy) }
    var finePrint by remember { mutableStateOf(joke.finePrint ?: "") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(vertical = 24.dp)
                .testTag("slot_customizer_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Title Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Customize Joke & Punchline",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Fine-tune individual slots or punchline text",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(16.dp))

                // 1. Custom Punchline Slot
                Text(
                    text = "🎯 CUSTOM PUNCHLINE OVERRIDE",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "If set, this will replace the statement when displaying or sharing.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = punchline,
                    onValueChange = { punchline = it },
                    placeholder = { Text("e.g. \"The Pope admitted Purgatory is an endless DMV queue...\"") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_punchline_input"),
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Full Statement Slot
                Text(
                    text = "📜 THE FULL STATEMENT (GOSSIP CHAIN + ABSURDITY)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = statement,
                    onValueChange = { statement = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("full_statement_input"),
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Quick Absurd Element Injections:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.outline
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SuggestionChip(
                        onClick = {
                            val chain = statement.substringBefore("that ") + " that "
                            statement = "$chain the Holy Trinity is actually just three very clever raccoons sharing a single velvet cloak.\""
                        },
                        label = { Text("🦝 Raccoons", fontSize = 11.sp) }
                    )
                    SuggestionChip(
                        onClick = {
                            val chain = statement.substringBefore("that ") + " that "
                            statement = "$chain transubstantiation turns the host into a Cool Ranch Dorito if the priest winks.\""
                        },
                        label = { Text("🧀 Dorito", fontSize = 11.sp) }
                    )
                    SuggestionChip(
                        onClick = {
                            val chain = statement.substringBefore("that ") + " that "
                            statement = "$chain Purgatory is an infinite DMV waiting room where your ticket is B-69.\""
                        },
                        label = { Text("🪪 DMV B-69", fontSize = 11.sp) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Why it's Hearsay Slot
                Text(
                    text = "🗣️ WHY IT'S HEARSAY (TRANSMISSION CHAIN)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = whyHearsay,
                    onValueChange = { whyHearsay = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Why it's Heresy Slot
                Text(
                    text = "⚖️ WHY IT'S HERESY (DOGMA SUBVERSION)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = whyHeresy,
                    onValueChange = { whyHeresy = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 5. The HOA Fine Print Clause
                Text(
                    text = "🏛️ THE LITURGICAL FINE PRINT (HOA CLAUSE)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = finePrint,
                    onValueChange = { finePrint = it },
                    placeholder = { Text("e.g. Prayers sent after 5 PM EST receive automated auto-reply...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            onSave(
                                statement,
                                punchline.takeIf { it.isNotBlank() },
                                whyHearsay,
                                whyHeresy,
                                finePrint.takeIf { it.isNotBlank() }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.testTag("save_customized_slots_button")
                    ) {
                        Icon(Icons.Default.Done, contentDescription = null, modifier = Modifier.padding(end = 4.dp))
                        Text("Seal Customized Joke")
                    }
                }
            }
        }
    }
}
