import Foundation
import SwiftUI
import shared

extension Text {
    init(_ resource: ResourcesStringResource) {
        self.init(resource.load())
    }
}

extension String {
    func normalized() -> String {
        return lowercased().folding(options: .diacriticInsensitive, locale: .current)
    }
    
    func toCurrency() -> Currency {
        Currency(code: self)
    }
}
