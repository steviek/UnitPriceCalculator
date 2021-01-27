package com.sixbynine.unitpricecalculator.shared

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

fun StringResource.load(): String {
  return StringDesc.Resource(this).load()
}

expect fun StringDesc.load(): String

/** Formats a plural string using our hacky custom version of ICU plurals. */
fun plurals(raw: String, quantity: Int): String {
  val tokens = raw.split(";;")
  require(tokens.size % 2 == 0) { "Invalid plural format: $raw"}
  var i = 0
  while (i < raw.length) {
    val filter = tokens[i]
    val string = tokens[i + 1]

    val matches = when {
      filter == "one" -> {
        when (MR.strings.string_locale.load()) {
          "fr" -> quantity < 2
          "en" -> quantity == 1
          else -> quantity == 1
        }
      }
      filter.matches("=[0-9]+".toRegex()) -> {
        val value = filter.replace("=", "").toInt()
        quantity == value
      }
      filter.matches("<=[0-9]+".toRegex()) -> {
        val value = filter.replace("<=", "").toInt()
        quantity <= value
      }
      filter.matches(">=[0-9]+".toRegex()) -> {
        val value = filter.replace(">=", "").toInt()
        quantity >= value
      }
      filter.matches("<[0-9]+".toRegex()) -> {
        val value = filter.replace("<", "").toInt()
        quantity < value
      }
      filter.matches(">[0-9]+".toRegex()) -> {
        val value = filter.replace(">", "").toInt()
        quantity > value
      }
      filter == "other" -> true
      else -> throw IllegalArgumentException("Invalid plural filter: $filter")
    }

    if (matches) return string
    i += 2
  }
  throw IllegalArgumentException("No matching filter in $raw, should have an other clause")
}