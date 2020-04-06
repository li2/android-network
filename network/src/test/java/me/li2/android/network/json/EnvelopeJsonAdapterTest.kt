package me.li2.android.network.json

import com.squareup.moshi.Moshi
import junit.framework.Assert.assertTrue
import org.junit.Test

class EnvelopeJsonAdapterTest {
    @Test
    fun testFromJson() {
        val json = (""
                + "{\"data\":"
                + "  {\n"
                + "    \"rank\": \"2\",\n"
                + "    \"suit\": \"CLUBS\"\n"
                + "  }"
                + "}")
        val moshi = Moshi.Builder().add(EnvelopeJsonAdapter.FACTORY).build()
        val adapter = moshi.adapter<Card>(Card::class.java, EnvelopeJsonAdapter.Enveloped::class.java)
        val result = adapter.fromJson(json)
        println(result)
        assertTrue(result?.rank == 2)
        assertTrue(result?.suit == Suit.CLUBS)
    }

    private data class Card(val rank: Int, val suit: Suit)

    private enum class Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
    }
}