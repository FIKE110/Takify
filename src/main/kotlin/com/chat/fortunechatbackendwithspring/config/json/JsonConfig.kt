import com.cohere.api.types.ChatFinishReason
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.util.*

class ChatFinishReasonDeserializer : JsonDeserializer<ChatFinishReason>() {
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ChatFinishReason {
        val value = p.text.uppercase(Locale.getDefault()) // Convert to uppercase
        return ChatFinishReason.valueOf(value) // Match with enum
    }
}