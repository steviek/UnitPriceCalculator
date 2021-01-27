package com.sixbynine.unitpricecalculator.shared

import android.app.Application

class UnitPriceCalculatorApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: UnitPriceCalculatorApplication? = null

        val instance: UnitPriceCalculatorApplication
            get() {
                check(_instance != null) {
                    "Cannot get instance before application onCreate"
                }
                return _instance!!
            }
    }
}