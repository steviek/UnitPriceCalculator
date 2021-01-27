package com.sixbynine.unitpricecalculator.shared.unit

import dev.icerock.moko.resources.StringResource
import com.sixbynine.unitpricecalculator.shared.MR.strings
import com.sixbynine.unitpricecalculator.shared.load
import com.sixbynine.unitpricecalculator.shared.unit.UnitSystem.*
import com.sixbynine.unitpricecalculator.shared.unit.UnitType.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = UnitSerializer::class)
enum class MeasureUnit(
  val id: Int,
  val symbol: StringResource,
  val displayName: StringResource,
  val factor: Double,
  val defaultQuantity: Double,
  val type: UnitType,
  val systems: Set<UnitSystem>
) {
  GRAM(1, strings.gram_symbol, strings.gram_name, 1.0, 100.0, WEIGHT, METRIC),
  KILOGRAM(2, strings.kilogram_symbol, strings.kilogram_name, 1000.0, WEIGHT, METRIC),
  MILLIGRAM(3, strings.milligram_symbol, strings.milligram_name, 0.001, 100.0, WEIGHT, METRIC),
  OUNCE(4, strings.ounce_symbol, strings.ounce_name, 28.3495, WEIGHT, IMPERIAL_UK, IMPERIAL_US),
  POUND(5, strings.pound_symbol, strings.pound_name, 453.592, WEIGHT, IMPERIAL_UK, IMPERIAL_US),
  STONE(6, strings.stone_symbol, strings.stone_name, 6350.293, WEIGHT, IMPERIAL_UK, IMPERIAL_US),
  MILLILITRE(7, strings.millilitre_symbol, strings.millilitre_name, 0.001, 100.0, VOLUME, METRIC),
  LITRE(8, strings.litre_symbol, strings.litre_name, 1.0, VOLUME, METRIC),
  CUBIC_CENTIMETRE(9, strings.cubic_centimetre_symbol, strings.cubic_centimetre_name, 0.001, 100.0, VOLUME, METRIC),
  FLUID_OUNCE(10, strings.fluid_ounce_symbol, strings.fluid_ounce_name, 0.0284131, VOLUME, IMPERIAL_UK),
  QUART(11, strings.quart_symbol, strings.quart_name, 1.13652, VOLUME, IMPERIAL_UK),
  PINT(12, strings.pint_symbol, strings.pint_name, 0.568261, VOLUME, IMPERIAL_UK),
  GALLON(13, strings.gallon_symbol, strings.gallon_name, 4.54609, VOLUME, IMPERIAL_UK),
  TEASPOON(14, strings.teaspoon_symbol, strings.teaspoon_name, 0.00591939, VOLUME, IMPERIAL_UK),
  TABLESPOON(15, strings.tablespoon_symbol, strings.tablespoon_name, 0.0177582, VOLUME, IMPERIAL_UK),
  CUP(16, strings.cup_symbol, strings.cup_name, 0.284131, VOLUME, IMPERIAL_UK),
  US_FLUID_OUNCE(17, strings.us_fluid_ounce_symbol, strings.us_fluid_ounce_name, 0.0295735, VOLUME, IMPERIAL_US),
  US_QUART(18, strings.us_quart_symbol, strings.us_quart_name, 0.946353, VOLUME, IMPERIAL_US),
  US_PINT(19, strings.us_pint_symbol, strings.us_pint_name, 0.473176, VOLUME, IMPERIAL_US),
  US_GALLON(20, strings.us_gallon_symbol, strings.us_gallon_name, 3.78541, VOLUME, IMPERIAL_US),
  US_TEASPOON(21, strings.us_teaspoon_symbol, strings.us_teaspoon_name, 0.00492892, VOLUME, IMPERIAL_US),
  US_TABLESPOON(22, strings.us_tablespoon_symbol, strings.us_tablespoon_name, 0.0147868, VOLUME, IMPERIAL_US),
  US_CUP(23, strings.us_cup_symbol, strings.us_cup_name, 0.236588, VOLUME, IMPERIAL_US),
  METRE(24, strings.metre_symbol, strings.metre_name, 1.0, LENGTH, METRIC),
  MILLIMETRE(25, strings.millimetre_symbol, strings.millimetre_name, 0.001, 100.0, LENGTH, METRIC),
  CENTIMETRE(26, strings.centimetre_symbol, strings.centimetre_name, 0.01, 10.0, LENGTH, METRIC),
  KILOMETRE(27, strings.kilometre_symbol, strings.kilometre_name, 1000.0, LENGTH, METRIC),
  INCH(28, strings.inch_symbol, strings.inch_name, 0.0254, LENGTH, IMPERIAL_UK, IMPERIAL_US),
  FOOT(29, strings.foot_symbol, strings.foot_name, 0.3048, LENGTH, IMPERIAL_UK, IMPERIAL_US),
  YARD(30, strings.yard_symbol, strings.yard_name, 0.9144, LENGTH, IMPERIAL_UK, IMPERIAL_US),
  MILE(31, strings.mile_symbol, strings.mile_name, 1609.34, LENGTH, IMPERIAL_UK, IMPERIAL_US),
  SQUARE_METRE(32, strings.square_metre_symbol, strings.square_metre_name, 1.0, AREA, METRIC),
  SQUARE_CENTIMETRE(33, strings.square_centimetre_symbol, strings.square_centimetre_name, 0.0001, AREA, METRIC),
  SQUARE_MILLIMETRE(34, strings.square_millimetre_symbol, strings.square_millimetre_name, 0.000001, AREA, METRIC),
  SQUARE_KILOMETRE(35, strings.square_kilometre_symbol, strings.square_kilometre_name, 1000000.0, AREA, METRIC),
  ARE(36, strings.are_symbol, strings.are_name, 100.0, AREA, METRIC),
  HECTARE(37, strings.hectare_symbol, strings.hectare_name, 10000.0, AREA, METRIC),
  ACRE(38, strings.acre_symbol, strings.acre_name, 4046.856, AREA, IMPERIAL_UK, IMPERIAL_US),
  SQUARE_FOOT(39, strings.square_foot_symbol, strings.square_foot_name, 0.092903, AREA, IMPERIAL_UK, IMPERIAL_US),
  SQUARE_INCH(40, strings.square_inch_symbol, strings.square_inch_name, 0.00064516, AREA, IMPERIAL_UK, IMPERIAL_US),
  SQUARE_YARD(41, strings.square_yard_symbol, strings.square_yard_name, 0.836127, AREA, IMPERIAL_UK, IMPERIAL_US),
  SQUARE_MILE(42, strings.square_mile_symbol, strings.square_mile_name, 2589990.0, AREA, IMPERIAL_UK, IMPERIAL_US),
  UNIT(43, strings.unit_symbol, strings.unit_name, 1.0, QUANTITY, METRIC, IMPERIAL_UK, IMPERIAL_US),
  DOZEN(44, strings.dozen_symbol, strings.dozen_name, 12.0, QUANTITY, METRIC, IMPERIAL_UK, IMPERIAL_US);

  constructor(
    number: Int,
    symbol: StringResource,
    name: StringResource,
    factor: Double,
    defaultQuantity: Double,
    type: UnitType,
    system: UnitSystem,
    vararg otherSystems: UnitSystem
  ) : this(number, symbol, name, factor, defaultQuantity, type, otherSystems.toSet() + system)

  constructor(
    number: Int,
    symbol: StringResource,
    name: StringResource,
    factor: Double,
    type: UnitType,
    system: UnitSystem,
    vararg otherSystems: UnitSystem
  ) : this(number, symbol, name, factor, defaultQuantity = 1.0, type, otherSystems.toSet() + system)

  init {
    require(systems.isNotEmpty())
  }

  val displayNameWithSymbol: String
    get() = "${symbol.load()} - ${displayName.load()}"
}

object UnitSerializer: KSerializer<MeasureUnit> {

  override val descriptor = PrimitiveSerialDescriptor("MeasureUnit", PrimitiveKind.INT)

  override fun deserialize(decoder: Decoder): MeasureUnit {
    val id = decoder.decodeInt()
    return MeasureUnit.values().firstOrNull { it.id == id } ?: error("Invalid unit id: $id")
  }

  override fun serialize(encoder: Encoder, value: MeasureUnit) {
    encoder.encodeInt(value.id)
  }
}
