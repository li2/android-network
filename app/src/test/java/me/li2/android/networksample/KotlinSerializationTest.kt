package me.li2.android.networksample

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

/*
Kotlin cross-platform reflectionless serialization

https://github.com/Kotlin/kotlinx.serialization
https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/examples.md
https://proandroiddev.com/kotlinx-serialization-83815cc1c57b
 */
@Serializable
data class Data(val a: Int, val b: String = "yes")

class KotlinSerializationTest {
    @Test
    fun `serializing a data object`() {
        val json = Json { encodeDefaults = true }
        val jsonStr = json.encodeToString(Data(21))
        assertEquals(jsonStr, """{"a":21,"b":"yes"}""")
    }

    @Test
    fun `serializing a list data objects`() {
        val json = Json { encodeDefaults = true }
        val dataList = listOf(Data(21), Data(66, "liuliuliu"))
        val jsonStr = json.encodeToString(dataList)
        // [{"a":42,"b":"42"}]
        assertEquals(jsonStr, """[{"a":21,"b":"yes"},{"a":66,"b":"liuliuliu"}]""")
    }

    @Test
    fun `parsing a date object`() {
        val data =
            Json.decodeFromString<Data>("""{"a":21}""") // b is optional since it has default value
        assertEquals(Data(21), data)
    }
}
