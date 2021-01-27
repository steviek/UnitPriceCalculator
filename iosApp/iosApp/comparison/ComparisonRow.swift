import SwiftUI
import shared

struct ComparisonRow: View {
    @Binding var comparison: Comparison
    let index: Int
    let entry: UnitEntry
    let dimensions: ComparisonUiDimensions
    let width: CGFloat
    @State private var priceText: String
    private var lastEntry: UnitEntry? = nil
    @State private var isNoteSheetShowing: Bool = false
    
    init(comparison: Binding<Comparison>, index: Int, width: CGFloat) {
        self._comparison = comparison
        self.index = index
        self.entry = comparison.wrappedValue.entries[index]
        self.dimensions = ComparisonUiDimensions.Companion().forScreenWidth(width: Int32(width))
        self.width = width
        self._priceText = State(initialValue: comparison.wrappedValue.entries[index].rawPrice)
    }
    
    private var statusColor: Color? {
        if entry.unit.type != comparison.unitType {
            // This should only occur while transitioning between comparisons from
            // clicking a saved item.
            return nil
        }
        let status = comparison.getStatus(entry: entry)
        switch status {
        case.inferior:
            return .red
        case .superior:
            return .green
        default:
            return nil
        }
    }
    
    var body: some View {
        VStack {
            HStack(spacing: 0) {
                Text("\(index + 1)")
                    .frame(width: CGFloat(dimensions.indexWidth - 2), alignment: .leading)
                    .fixedSize(horizontal: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/, vertical: false)
                    .foregroundColor(statusColor)
                Spacer().frame(width: 4)
                createTextField(
                    MR.strings().price.load(),
                    text: Binding(
                        get: { return entry.rawPrice },
                        set: { value in
                            comparison = comparison.withRawPrice(at: Int32(index), rawPrice: value)
                        }
                    ),
                    width: dimensions.priceWidth - 4
                )
                Spacer().frame(width: 4)
                createTextField(
                    "\(1)",
                    text: Binding(
                        get: { return entry.rawQuantity },
                        set: { value in
                            comparison = comparison.withRawQuantity(at: Int32(index), rawQuantity: value)
                        }
                    ),
                    width: dimensions.quantityWidth - 4
                )
                Spacer().frame(width: 4)
                createTextField(
                    "\(1)",
                    text: Binding(
                        get: { return entry.rawSize },
                        set: { value in
                            comparison = comparison.withRawSize(at: Int32(index), rawSize: value)
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
        
        HStack {
            Button(action: { isNoteSheetShowing = true }) {
                HStack {
                    if (entry.note ?? "").count <= 0 {
                        Text(MR.strings().add_note)
                    } else {
                        Image(systemName: "pencil")
                    }
                    Text(entry.note ?? "").foregroundColor(.primary)
                }
            }
            .padding()
            .sheet(isPresented: $isNoteSheetShowing) {
                NoteView(
                    isPresented: $isNoteSheetShowing,
                    initialText: entry.note ?? ""
                ) { newNote in
                    comparison = comparison.withNote(
                        at: Int32(index),
                        note: newNote
                    )
                }
            }
            Spacer()
            Text(comparison.createSummaryString(entry: entry))
                .foregroundColor(statusColor)
        }
    }
    
    @ViewBuilder
    private func createPickerView(_ units: [MeasureUnit]) -> some View {
        PickerView(
            selectedIndex: Binding(
                get: {
                    units.firstIndex(of: entry.unit) ?? -1
                },
                set: { unitIndex in
                    comparison = comparison.withUnit(at: Int32(index), unit: units[unitIndex])
                }
            ),
            title: MR.strings().choose_unit.load(),
            items: units.map { unit in unit.symbol.load() },
            listItems: units.map { unit in unit.displayNameWithSymbol })
    }
}

struct ComparisonRow_Previews: PreviewProvider {
    static var previews: some View {
        ComparisonRow(
            comparison: .constant(
                Comparison(
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
                    name: "",
                    createdAt: Instant.Companion().epoch,
                    lastUpdatedAt: Instant.Companion().epoch,
                    currency: Currency(code: "EUR")
                )),
            index: 0,
            width: 260)
    }
}
