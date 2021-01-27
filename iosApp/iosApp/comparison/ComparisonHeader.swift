import SwiftUI
import shared

struct ComparisonHeader: View {
    @Binding var comparison: Comparison
    @Binding var currency: Currency
    let dimensions: ComparisonUiDimensions
    let width: CGFloat
    @State private var isCurrencyDropDownShowing: Bool = false
    
    init(
        comparison: Binding<Comparison>,
        currency: Binding<Currency>,
        width: CGFloat
    ) {
        self._comparison = comparison
        self._currency = currency
        self.dimensions = ComparisonUiDimensions.Companion().forScreenWidth(width: Int32(width))
        self.width = width
    }
    
    var body: some View {
        HStack(spacing: 0) {
            createFixedWidthText("", width: dimensions.indexWidth)
            Button(action: { isCurrencyDropDownShowing = true }) {
                Text(comparison.currency.symbol)
                    .foregroundColor(.primary)
            }.frame(width: CGFloat(dimensions.priceWidth))
            .fixedSize(horizontal: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/, vertical: false)
            .sheet(isPresented: $isCurrencyDropDownShowing) {
                CurrencyPickerView(
                    isPresented: $isCurrencyDropDownShowing,
                    currency: $currency
                )
            }
            createFixedWidthText("#", width: dimensions.quantityWidth)
            createFixedWidthText(MR.strings().size, width: dimensions.sizeWidth)
            createFixedWidthText(MR.strings().unit, width: dimensions.unitWidth)
        }
    }
    
    private func buildCurrencyButtons() -> [ActionSheet.Button] {
        var list: [ActionSheet.Button] = []
        for code in NSLocale.commonISOCurrencyCodes {
            list.append(.default(Text(code.toCurrency().displayName)) {
                currency = code.toCurrency()
            })
        }
        list.append(.cancel())
        return list
    }
}

struct ComparisonHeader_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            VStack {
                GeometryReader { geo in
                    Text("\(geo.size.width)")
                    ComparisonHeader(
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
                        currency: .constant("GBP".toCurrency()),
                        width: geo.size.width)
                }
            }
            VStack {
                GeometryReader { geo in
                    Text("\(geo.size.width)")
                    ComparisonHeader(
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
                        currency: .constant("GBP".toCurrency()),
                        width: geo.size.width)
                }
            }
        }
        
    }
}
