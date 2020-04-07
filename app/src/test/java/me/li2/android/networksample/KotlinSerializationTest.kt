package me.li2.android.networksample

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import org.junit.Assert.assertEquals
import org.junit.Test

/*
Kotlin cross-platform reflectionless serialization

https://github.com/Kotlin/kotlinx.serialization
https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/examples.md
https://proandroiddev.com/kotlinx-serialization-83815cc1c57b
 */
@Serializable
data class Data(val a: Int, val b: String = "42")

class KotlinSerializationTest {
    @Test
    fun `serializing a data object`() {
        val json = Json(JsonConfiguration.Stable)
        val jsonStr = json.stringify(Data.serializer(), Data(42))
        // {"a":42,"b":"42"}
        assertEquals(jsonStr, """{"a":42,"b":"42"}""")
    }

    @Test
    fun `serializing a list data objects`() {
        val json = Json(JsonConfiguration.Stable)
        val jsonStr = json.stringify(Data.serializer().list, listOf(Data(42)))
        // [{"a":42,"b":"42"}]
        assertEquals(jsonStr, """[{"a":42,"b":"42"}]""")
    }

    @Test
    fun `parsing a date object`() {
        val json = Json(JsonConfiguration.Stable)
        val data = json.parse(Data.serializer(), """{"a":42}""") // b is optional since it has default value
        assertEquals(Data(42), data)
    }
}
