import SwiftUI
import shared

struct ComparisonTopBar: View {
    @Binding var comparison: Comparison
    @Binding var savedComparisons: [Comparison]
    
    var lastSavedComparison: Comparison? {
        savedComparisons.first { savedComparison in
            savedComparison.id == comparison.id
        }
    }
    
    var body: some View {
        ZStack {
            HStack {
                Button(MR.strings().clear.load()) {
                    withAnimation {
                        comparison = comparison.cleared()
                    }
                }
                .padding()
                .disabled(!comparison.isClearable())
                
                Spacer()
                
                Button(MR.strings().save.load()) {
                    let result = SavedComparisonManager().save(comparison: comparison)
                    savedComparisons = SavedComparisonManager().getSavedComparisons()
                    comparison = result
                }
                .padding()
                .disabled(!comparison.isSaveable(lastSavedComparison: lastSavedComparison))
            }
            HStack {
                Spacer().frame(width: 115)
                
                TextField(
                    MR.strings().untitled.load(),
                    text: Binding(
                        get: { comparison.name ?? "" },
                        set: { value in
                            comparison = comparison.withName(name: value)
                        }
                    )
                )
                .multilineTextAlignment(.center)
                
                Spacer().frame(width: 115)
            }
        }
    }
}

struct ComparisonTopBar_Previews: PreviewProvider {
    static var previews: some View {
        ComparisonTopBar(
            comparison: .constant(
                Comparison(
                    id: nil,
                    unitType: .volume,
                    entries: [],
                    comparisonUnit: .litre,
                    rawComparisonSize: "",
                    name: "Foo",
                    createdAt: Instant.Companion().epoch,
                    lastUpdatedAt: Instant.Companion().epoch,
                    currency: Currency(code: "EUR")
                )),
            savedComparisons: .constant([])
        )
    }
}
