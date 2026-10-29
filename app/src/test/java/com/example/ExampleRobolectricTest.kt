package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.generator.JokeGeneratorEngine
import com.example.model.DogmaCategory
import com.example.model.HearsaySetting
import com.example.model.HumorCategory
import com.example.model.JokeCustomizationParams
import com.example.model.JokeTone
import com.example.model.TargetAudience
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

  @Test
  fun `generate joke with advanced customization parameters`() {
    val preset = JokeGeneratorEngine.PRESET_RECIPES.first()
    val customParams = JokeCustomizationParams(
      keywords = "air fryer, costco",
      humorCategory = HumorCategory.PUNS,
      targetAudience = TargetAudience.TIRED_OFFICE_WORKERS,
      tone = JokeTone.BUREAUCRATIC_DEADPAN
    )

    val customizedJoke = JokeGeneratorEngine.generateJoke(
      recipe = preset,
      customParams = customParams,
      customTwist = "audited by divine HR"
    )

    assertNotNull(customizedJoke.statement)
    assertTrue(customizedJoke.statement.contains("air fryer, costco"))
    assertTrue(customizedJoke.statement.contains("Per ecclesiastical memorandum"))
    assertEquals(HumorCategory.PUNS.label, customizedJoke.humorCategory)
    assertEquals(TargetAudience.TIRED_OFFICE_WORKERS.label, customizedJoke.targetAudience)
    assertEquals(JokeTone.BUREAUCRATIC_DEADPAN.label, customizedJoke.tone)
    assertEquals("air fryer, costco", customizedJoke.keywords)
  }
}

