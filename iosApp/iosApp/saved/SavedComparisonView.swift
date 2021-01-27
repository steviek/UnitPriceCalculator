import Foundation
import SwiftUI
import shared

struct SavedComparisonView: View {
    let listener: (Comparison) -> ()
    
    @State private var searchText: String = ""
    @State private var editMode = EditMode.inactive
    @Binding private var savedComparisons: [Comparison]
    
    init(savedComparisons: Binding<[Comparison]>, listener: @escaping (Comparison) -> ()) {
        self._savedComparisons = savedComparisons
        self.listener = listener
    }
    
    var filteredComparisons: [Comparison] {
        if searchText.isEmpty {
            return savedComparisons
        }
        
        let normalizedFilter = searchText.normalized()
        return savedComparisons.filter { comparison in
            if searchText.isEmpty {
                return true
            }
            
            guard let name = comparison.name else { return false }
            let normalizedName = name.normalized()
            if normalizedName.contains(normalizedFilter) {
                return true
            }
            
            for entry in comparison.entries {
                if (entry.note ?? "").normalized().contains(normalizedFilter) {
                    return true
                }
            }
            
            return false
        }
    }
    
    var body: some View {
        NavigationView {
            VStack {
                SearchView(
                    placeholder: MR.strings().search.load(),
                    text: Binding(
                        get: { searchText },
                        set: { value in withAnimation { searchText = value }}
                    )
                )
                if savedComparisons.isEmpty {
                    Text(MR.strings().saved_comparisons_empty)
                        .multilineTextAlignment(.center)
                        .padding()
                }
                List {
                    ForEach(filteredComparisons, id: \.self) { comparison in
                        Button(action: {
                            listener(comparison)
                        }) {
                            SavedComparisonItemView(comparison: comparison)
                        }.buttonStyle(PlainButtonStyle())
                        .disabled(editMode == .active)
                    }.onDelete(perform: delete)
                }
                .navigationBarItems(trailing: navBarItem())
                .navigationBarTitle(
                    Text(MR.strings().saved_comparisons),
                    displayMode: .inline
                )
                .environment(\.editMode, $editMode)
                .listStyle(PlainListStyle())
                Spacer()
            }
        }
    }
    
    private func delete(at offsets: IndexSet) {
        let comparisonsToDelete: [Comparison?] = offsets.map { index in
            if index < filteredComparisons.count {
                return filteredComparisons[index]
            } else {
                return nil
            }
        }
        comparisonsToDelete.forEach { comparison in
            if comparison != nil {
                SavedComparisonManager().delete(comparison: comparison!)
            }
        }
        savedComparisons = SavedComparisonManager().getSavedComparisons()
    }
    
    @ViewBuilder
    private func navBarItem() -> some View {
        if savedComparisons.isEmpty {
            EmptyView()
        } else {
            EditButton()
        }
    }
}

struct SavedComparisonView_Previews: PreviewProvider {
    static var previews: some View {
        SavedComparisonView(savedComparisons: .constant([])) { comparison in
            
        }
    }
}
