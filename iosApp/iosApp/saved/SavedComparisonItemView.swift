import SwiftUI
import shared

struct SavedComparisonItemView: View {
    let comparison: Comparison
    
    init(comparison: Comparison) {
        self.comparison = comparison
    }
    
    var displayTitle: String {
        guard let name = comparison.name else {
            return MR.strings().untitled.load()
        }
        return name.isEmpty ? MR.strings().untitled.load() : name
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0){
            Spacer().frame(height: 4)
            HStack {
                Text(comparison.getSavedScreenTitle())
                    .font(.headline)
                    .frame(maxWidth: .infinity, alignment: .leading)
                Text(comparison.getReadableLastUpdatedDate())
                    .font(.subheadline)
            }
            Spacer().frame(height: 4)
            Text(comparison.getSavedScreenSubtitle())
                .font(.subheadline)
                .multilineTextAlignment(.leading)
            Spacer().frame(height: 4)
        }
    }
}

struct SavedComparisonItemView_Previews: PreviewProvider {
    static var previews: some View {
        SavedComparisonItemView(
            comparison: Comparison(
                id: nil,
                unitType: .volume,
                entries: [
                    UnitEntry(
                        unit: .litre,
                        rawPrice: "1.0",
                        rawQuantity: "1",
                        rawSize: "1",
                        note: ""
                    )
                ],
                comparisonUnit: .litre,
                rawComparisonSize: "",
                name: "My foo",
                createdAt: Instant(epochMillis: 1609783326435),
                lastUpdatedAt: Instant(epochMillis: 1609783326435),
                currency: Currency(code: "EUR")
            ))
    }
}
