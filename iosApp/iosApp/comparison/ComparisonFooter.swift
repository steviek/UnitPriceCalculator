import SwiftUI
import shared

struct ComparisonFooter: View {
    @Binding var comparison: Comparison
    let dimensions: ComparisonUiDimensions
    let width: CGFloat
    
    init(comparison: Binding<Comparison>, width: CGFloat) {
        self._comparison = comparison
        self.dimensions = ComparisonUiDimensions.Companion().forScreenWidth(width: Int32(width))
        self.width = width
    }
    
    var body: some View {
        HStack(spacing: 0) {
            Text(MR.strings().show_price_per).multilineTextAlignment(.center)
            Spacer().frame(width: 8)
            createTextField(
                "\(Int(comparison.comparisonUnit.defaultQuantity))",
                text: Binding(
                    get: { return comparison.rawComparisonSize },
                    set: { value in
                        comparison = comparison.withComparisonSize(size: value)
                    }
                ),
                width: dimensions.sizeWidth - 4
            )
            Spacer().frame(width: 4)
            createPickerView(ComparisonViews().getUnitOptions(type: comparison.unitType))
                .fixedSize(horizontal: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/, vertical: false)
                .frame(width: CGFloat(dimensions.unitWidth - 2), alignment: .trailing)
        }.textFieldStyle(RoundedBorderTextFieldStyle())
    }
    
    @ViewBuilder
    private func createPickerView(_ units: [MeasureUnit]) -> some View {
        PickerView(
            selectedIndex: Binding(
                get: {
                    units.firstIndex(of: comparison.comparisonUnit)!
                },
                set: { unitIndex in
                    comparison = comparison.withComparisonUnit(unit: units[unitIndex])
                }
            ),
            title: MR.strings().choose_unit.load(),
            items: units.map { unit in unit.symbol.load() },
            listItems: units.map { unit in unit.displayNameWithSymbol })
    }
}

struct ComparisonFooter_Previews: PreviewProvider {
    static var previews: some View {
        ComparisonFooter(
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
            width: 288)
    }
}
