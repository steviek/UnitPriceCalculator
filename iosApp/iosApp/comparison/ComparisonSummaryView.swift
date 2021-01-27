import SwiftUI
import shared

struct ComparisonSummaryView: View {
    let summary: ComparisonSummary
    
    var body: some View {
        VStack(alignment: .center, spacing: 20) {
            Text(summary.summaryLine)
                .multilineTextAlignment(.center)
            ForEach(summary.items, id :\.rowIndex) { item in
                Text(item.summary)
                    .foregroundColor(item.isSuperior ? .green : .red)
                    .multilineTextAlignment(.center)
            }
        }.frame(maxWidth: .infinity)
    }
}

struct ComparisonSummaryView_Previews: PreviewProvider {
    static var previews: some View {
        ComparisonSummaryView(
            summary: ComparisonSummary(
                summaryLine: "This is the summary",
                items: [
                    SummaryItem(rowIndex: 0, isSuperior: true, summary: "This is good"),
                    SummaryItem(rowIndex: 1, isSuperior: false, summary: "This is not good")
                ]
            )
        )
    }
}

@ViewBuilder
func createSummaryView(_ summary: ComparisonSummary?) -> some View {
    if summary == nil {
        EmptyView()
    } else {
        ComparisonSummaryView(summary: summary!)
    }
}
