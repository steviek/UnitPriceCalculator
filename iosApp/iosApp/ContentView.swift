import SwiftUI
import shared

func greet() -> String {
    return Greeting().greeting()
}

struct ContentView: View {
        
    @State private var selectedTab = 0
    
    @State var comparison = Comparison.Companion().create()
    
    @State var savedComparisons = SavedComparisonManager().getSavedComparisons()
    
    @State var internalCurrency = CurrencyManager().current
    
    @State var internalUnitSystemOrder = UnitSystemOrderManager().current
    
    var body: some View {
        VStack {
            TabView(selection: $selectedTab) {
                ComparisonView(
                    comparison: $comparison,
                    savedComparisons: $savedComparisons,
                    currency: currency
                )
                .tabItem {
                    Image(systemName: "list.number")
                    Text(MR.strings().current)
                }
                .tag(0)
                SavedComparisonView(savedComparisons: $savedComparisons) { selectedComparison in
                    self.selectedTab = 0
                    self.comparison = selectedComparison
                    self.internalCurrency = selectedComparison.currency
                }
                .tabItem {
                    Image(systemName: "folder")
                    Text(MR.strings().saved)
                }
                .tag(1)
                SettingsView(
                    currency: currency,
                    unitSystemOrder: unitSystemOrder
                )
                    .tabItem {
                        Image(systemName: "gearshape")
                        Text(MR.strings().settings)
                    }
                    .tag(2)
            }
        }
    }
    
    private var currency: Binding<Currency> {
        Binding(
            get: { internalCurrency },
            set: { value in
                CurrencyManager().current = value
                comparison = comparison.withCurrency(currency: value)
                internalCurrency = value
            }
        )
    }
    
    private var unitSystemOrder: Binding<UnitSystemOrder> {
        Binding(
            get: { internalUnitSystemOrder },
            set: { value in
                UnitSystemOrderManager().current = value
                internalUnitSystemOrder = value
            }
        )
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
