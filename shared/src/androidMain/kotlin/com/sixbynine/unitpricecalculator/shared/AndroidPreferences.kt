package com.sixbynine.unitpricecalculator.shared

import android.content.Context
import android.content.SharedPreferences

object AndroidPreferences : Preferences {

  private val prefs: SharedPreferences
    get() = UnitPriceCalculatorApplication.instance.getSharedPreferences(
      "unit_price_calculator",
      Context.MODE_PRIVATE
    )

  override fun putBoolean(key: String, value: Boolean) {
    prefs.edit().putBoolean(key, value).apply()
  }

  override fun getBoolean(key: String): Boolean? {
    return if (prefs.contains(key)) {
      prefs.getBoolean(key, false)
    } else {
      null
    }
  }

  override fun putString(key: String, value: String) {
    prefs.edit().putString(key, value).apply()
  }

  override fun getString(key: String): String? {
    return prefs.getString(key, null)
  }

  override fun putInt(key: String, value: Int) {
    prefs.edit().putInt(key, value).apply()
  }

  override fun getInt(key: String): Int? {
    return if (prefs.contains(key)) {
      prefs.getInt(key, 0)
    } else {
      null
    }
  }

  override fun remove(key: String) {
    prefs.edit().remove(key).apply()
  }
}

actual val preferences: Preferences = AndroidPreferences