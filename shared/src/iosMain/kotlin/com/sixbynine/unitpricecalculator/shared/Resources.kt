package com.sixbynine.unitpricecalculator.shared

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

actual fun StringDesc.load(): String {
    return this.localized()
}