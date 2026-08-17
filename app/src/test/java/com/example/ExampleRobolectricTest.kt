package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.generator.JokeGeneratorEngine
import com.example.model.DogmaCategory
import com.example.model.HearsaySetting
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Hearsay & Heresy", appName)
  }

  @Test
  fun `generate joke from preset and chaotic recipes`() {
    val preset = JokeGeneratorEngine.PRESET_RECIPES.first()
    val joke = JokeGeneratorEngine.generateJoke(preset)
    assertNotNull(joke.statement)
    assertTrue(joke.statement.isNotBlank())
    assertTrue(joke.whyHearsay.isNotBlank())
    assertTrue(joke.whyHeresy.isNotBlank())

    val chaoticRecipe = JokeGeneratorEngine.generateChaoticCustomRecipe()
    val chaoticJoke = JokeGeneratorEngine.generateJoke(chaoticRecipe)
    assertNotNull(chaoticJoke.statement)
    assertTrue(chaoticJoke.statement.isNotBlank())
  }
}

