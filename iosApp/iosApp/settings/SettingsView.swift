import Foundation
import SwiftUI
import shared

struct SettingsView: View {
    
    @Binding var currency: Currency
    @Binding var unitSystemOrder: UnitSystemOrder
    @State private var currencySearchText: String = ""
    @State private var isCurrencySheetShowing: Bool = false
    
    var body: some View {
        NavigationView {
            Form {
                Button(action: { isCurrencySheetShowing = true }) {
                    HStack {
                        Text(MR.strings().currency_header)
                            .foregroundColor(.primary)
                        Spacer()
                        Text(currency.displayName)
                            .font(.caption)
                            .foregroundColor(.secondary)
                        Image(systemName: "chevron.right")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                }
                .sheet(isPresented: $isCurrencySheetShowing) {
                    CurrencyPickerView(
                        isPresented: $isCurrencySheetShowing,
                        currency: $currency
                    )
                }
                Section(
                    header: EditButton()
                        .frame(maxWidth: .infinity, alignment: .trailing)
                        .overlay(Text(MR.strings().preferred_system), alignment: .leading)
                ) {
                    ForEach(unitSystemOrder.pairs, id: \.system) { pair in
                        Text(pair.system.displayName)
                    }
                    .onMove(perform: { source, destination in
                        var firstIndex: Int? = nil
                        for index in source {
                            firstIndex = index
                            break
                        }
                        guard let previousIndex = firstIndex else { return }
                        let newIndex = destination > previousIndex ? destination - 1 : destination
                        if previousIndex == newIndex {
                            return
                        }
                                            
                        unitSystemOrder = unitSystemOrder.withMovedIndex(
                            previousIndex: Int32(previousIndex),
                            newIndex: Int32(newIndex)
                        )
                        UnitSystemOrderManager().current = unitSystemOrder
                    })
                }
            }
            .navigationBarTitle(Text(MR.strings().settings), displayMode: .inline)
        }
    }
    
    private func getFullDisplayName(_ code: String) -> String {
        return NSLocale.current.localizedString(forCurrencyCode: code) ?? ""
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView(
            currency: .constant(Currency(code: "GBP")),
            unitSystemOrder: .constant(UnitSystemOrderManager().current)
        )
    }
}
