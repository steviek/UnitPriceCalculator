import SwiftUI
import shared

struct CurrencyPickerView: View {
    
    private let allCurrencyCodes = NSLocale.commonISOCurrencyCodes
    
    @State private var searchText: String = ""
    
    @Binding var isPresented: Bool
    @Binding var currency: Currency
    
    var body: some View {
        List {
            SearchView(
                placeholder: MR.strings().search.load(),
                autoFocus: true,
                text: $searchText
            )
            ForEach(getDisplayedCurrencies(), id: \.self) { code in
                Button(action: {
                    currency = code.toCurrency()
                    isPresented = false
                }) {
                    HStack {
                        Text(code.toCurrency().displayName)
                        Spacer()
                        if code.toCurrency() == currency {
                            Image(systemName: "checkmark")
                                .foregroundColor(.accentColor)
                        }
                    }
                }
            }
        }.listStyle(PlainListStyle())
    }
    
    private func getDisplayedCurrencies() -> [String] {
        if searchText.isEmpty {
            return allCurrencyCodes.sorted(by: compareCurrencyCodes)
        }
        let normalizedSearchText = searchText.normalized()
        return allCurrencyCodes
            .sorted(by: compareCurrencyCodes)
            .filter { code in
                if code.toCurrency().displayName.normalized().contains(normalizedSearchText) {
                    return true
                }
                
                let fullDisplayName = NSLocale.current.localizedString(forCurrencyCode: code) ?? ""
                return fullDisplayName.normalized().contains(normalizedSearchText)
            }
    }
    
    private func compareCurrencyCodes(first: String, second: String) -> Bool {
        if first == NSLocale.current.currencyCode {
            return true
        } else if second == NSLocale.current.currencyCode {
            return false
        } else {
            return first < second
        }
    }
    
    private func getFullDisplayName(_ code: String) -> String {
        return NSLocale.current.localizedString(forCurrencyCode: code) ?? ""
    }
}

struct CurrencyPickerView_Previews: PreviewProvider {
    static var previews: some View {
        CurrencyPickerView(isPresented: .constant(true), currency: .constant("GBP".toCurrency()))
    }
}
