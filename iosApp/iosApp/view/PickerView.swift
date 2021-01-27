import SwiftUI
import shared

struct PickerView: View {
    @Binding var selectedIndex: Int
    let title: String
    let items: [String]
    let listItems: [String]?
    @State private var isDropDownShowing: Bool = false
    
    init(selectedIndex: Binding<Int>, title: ResourcesStringResource, items: [ResourcesStringResource], listItems: [ResourcesStringResource]? = nil) {
        self._selectedIndex = selectedIndex
        self.title = title.load()
        self.items = items.map { item in
            item.load()
        }
        self.listItems = listItems?.map { item in
            item.load()
        }
    }
    
    init(selectedIndex: Binding<Int>, title: String, items: [String], listItems: [String]? = nil) {
        self._selectedIndex = selectedIndex
        self.title = title
        self.items = items
        self.listItems = listItems
    }
    
    var body: some View {
        Button(action: {
            isDropDownShowing = true
        }) {
            HStack {
                if selectedIndex >= 0 && selectedIndex < items.count {
                    Text(items[selectedIndex])
                } else {
                    Text("")
                }
                Image(systemName: "chevron.down")
            }
        }.actionSheet(isPresented: $isDropDownShowing) {
            ActionSheet(
                title: Text(title),
                message: nil,
                buttons: buildActionList()
            )
        }
    }
    
    private func buildActionList() -> [ActionSheet.Button] {
        var list: [ActionSheet.Button] = []
        for (index, item) in (listItems ?? items).enumerated() {
            list.append(.default(Text(item)) {
                selectedIndex = index
            })
        }
        list.append(.cancel())
        return list
    }
}

struct PickerView_Previews: PreviewProvider {
    static var previews: some View {
        PickerView(selectedIndex: .constant(0), title: "Demo", items: ["Foo"])
    }
}
