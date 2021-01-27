package com.sixbynine.unitpricecalculator.shared.unit

import com.sixbynine.unitpricecalculator.shared.MR
import dev.icerock.moko.resources.StringResource
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind.INT
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = UnitSystemSerializer::class)
enum class UnitSystem(val id: Int, val displayName: StringResource) {
  METRIC(1, MR.strings.metric),
  IMPERIAL_UK(2, MR.strings.imperial_uk),
  IMPERIAL_US(3, MR.strings.imperial_us)
}

object UnitSystemSerializer: KSerializer<UnitSystem> {

  override val descriptor = PrimitiveSerialDescriptor("UnitSystem", INT)

  override fun deserialize(decoder: Decoder): UnitSystem {
    val id = decoder.decodeInt()
    return UnitSystem.values().firstOrNull { it.id == id } ?: error("Invalid unit system id: $id")
  }

  override fun serialize(encoder: Encoder, value: UnitSystem) {
    encoder.encodeInt(value.id)
  }
}