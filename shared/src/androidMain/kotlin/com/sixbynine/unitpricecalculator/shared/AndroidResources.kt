package com.sixbynine.unitpricecalculator.shared

import dev.icerock.moko.resources.desc.StringDesc

actual fun StringDesc.load(): String {
    return this.toString(context = UnitPriceCalculatorApplication.instance)
}