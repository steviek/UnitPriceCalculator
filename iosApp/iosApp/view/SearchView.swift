import SwiftUI

struct SearchView: UIViewRepresentable {

    let placeholder: String
    let autoFocus: Bool
    @Binding var text: String
    
    init(placeholder: String = "", autoFocus: Bool = false, text: Binding<String>) {
        self.placeholder = placeholder
        self._text = text
        self.autoFocus = autoFocus
    }

    class Coordinator: NSObject, UISearchBarDelegate {

        @Binding var text: String

        init(text: Binding<String>) {
            _text = text
        }

        func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
            text = searchText
        }
    }

    func makeCoordinator() -> SearchView.Coordinator {
        return Coordinator(text: $text)
    }

    func makeUIView(context: UIViewRepresentableContext<SearchView>) -> UISearchBar {
        let searchBar = UISearchBar(frame: .zero)
        searchBar.delegate = context.coordinator
        searchBar.searchBarStyle = .minimal
        if (autoFocus) {
            searchBar.becomeFirstResponder()
        }
        return searchBar
    }

    func updateUIView(_ uiView: UISearchBar, context: UIViewRepresentableContext<SearchView>) {
        uiView.text = text
        uiView.placeholder = placeholder
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView(text: .constant(""))
    }
}
