import SwiftUI
import shared

struct ComparisonView: View {
    @Binding var comparison: Comparison
    @Binding var savedComparisons: [Comparison]
    @Binding var currency: Currency
    
    var body: some View {
        GeometryReader { geo in
            VStack {
                ComparisonTopBar(comparison: $comparison, savedComparisons: $savedComparisons)
                ScrollView {
                    VStack {
                        HStack {
                            Text(MR.strings().unit_type).font(.headline)
                            Spacer()
                            PickerView(
                                selectedIndex: Binding(
                                    get: { Int(comparison.unitType.ordinal) },
                                    set: { index in
                                        comparison = comparison.withUnitType(unitType: UnitType.Companion().all()[index])
                                    }
                                ),
                                title: MR.strings().choose_unit_type,
                                items: UnitType.Companion().all().map { type in
                                    type.displayName
                                }
                            )
                        }
                        
                        Spacer().frame(height: 16)
                        
                        VStack {
                            ComparisonHeader(
                                comparison: $comparison,
                                currency: $currency,
                                width: geo.size.width - 32
                            )
                            ForEach(0..<comparison.entries.count, id: \.self) { rowIndex in
                                Divider()
                                ComparisonRow(
                                    comparison: $comparison,
                                    index: rowIndex,
                                    width: geo.size.width - 32
                                )
                            }
                            Divider()
                        }
                        Spacer().frame(height: 16)
                        HStack {
                            Spacer()
                            Button(MR.strings().remove_row.load()) {
                                withAnimation {
                                    comparison = comparison.withRowRemovedFromEnd()
                                }
                            }.disabled(comparison.entries.count <= 1)
                            Spacer().frame(width: 16)
                            Button(MR.strings().add_row.load()) {
                                withAnimation {
                                    comparison = comparison.withRowAddedToEnd()
                                }
                            }.disabled(comparison.entries.count >= 10)
                        }
                        ComparisonFooter(comparison: $comparison, width: geo.size.width - 32)
                            .frame(alignment: .trailing)
                        createSummaryView(comparison.getSummary())
                    }.padding(16)
                }
            }
        }
    }
}

struct ComparisonView_Previews: PreviewProvider {
    static var previews: some View {
        ComparisonView(
            comparison: .constant(
                        Comparison(
                            id: nil,
                            unitType: .volume,
                            entries: [],
                            comparisonUnit: .litre,
                            rawComparisonSize: "",
                            name: "",
                            createdAt: Instant.Companion().epoch,
                            lastUpdatedAt: Instant.Companion().epoch,
                            currency: Currency(code: "EUR")
                        )),
            savedComparisons: .constant([]),
            currency: .constant("GBP".toCurrency()))
    }
}
