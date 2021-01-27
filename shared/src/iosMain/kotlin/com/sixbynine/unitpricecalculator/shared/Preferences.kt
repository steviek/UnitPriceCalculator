package com.sixbynine.unitpricecalculator.shared

import platform.Foundation.NSUserDefaults

object IosPreferences: Preferences {

  private val userDefaults = NSUserDefaults.standardUserDefaults

  override fun putBoolean(key: String, value: Boolean) = userDefaults.setBool(value, key)

  override fun getBoolean(key: String): Boolean? {
    return if (isKeyMissing(key)) null else userDefaults.boolForKey(key)
  }

  override fun putString(key: String, value: String) = userDefaults.setObject(value, key)

  override fun getString(key: String): String? {
    return if (isKeyMissing(key)) null else userDefaults.stringForKey(key)
  }

  override fun putInt(key: String, value: Int) {
    userDefaults.setInteger(value.toLong(), key)
  }

  override fun getInt(key: String): Int? {
    return if (isKeyMissing(key)) null else userDefaults.integerForKey(key).toInt()
  }

  override fun remove(key: String) {
    userDefaults.removeObjectForKey(key)
  }

  private fun isKeyMissing(key: String) = userDefaults.objectForKey(key) == null
}

actual val preferences: Preferences = IosPreferences