package com.sixbynine.unitpricecalculator.shared.unit

import com.sixbynine.unitpricecalculator.shared.MR
import dev.icerock.moko.resources.StringResource
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind.INT
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = UnitTypeSerializer::class)
enum class UnitType(val id: Int, val displayName: StringResource) {
  WEIGHT(1, MR.strings.weight),
  VOLUME(2, MR.strings.volume),
  LENGTH(3, MR.strings.length),
  AREA(4, MR.strings.area),
  QUANTITY(5, MR.strings.quantity);

  companion object {
    fun fromId(id: Int): UnitType {
      return values().firstOrNull { it.id == id } ?: error("Invalid unit type: $id")
    }

    fun all(): List<UnitType> = values().toList()
  }
}

object UnitTypeSerializer: KSerializer<UnitType> {

  override val descriptor = PrimitiveSerialDescriptor("UnitType", INT)

  override fun deserialize(decoder: Decoder): UnitType {
    return UnitType.fromId(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, value: UnitType) {
    encoder.encodeInt(value.id)
  }
}