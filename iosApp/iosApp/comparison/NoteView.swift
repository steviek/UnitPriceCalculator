import SwiftUI
import shared

struct NoteView: View {
    @Binding var isPresented: Bool
    let initialText: String
    let onSubmit: (String) -> Void
    @State private var noteText: String
    
    init(
        isPresented: Binding<Bool>,
        initialText: String = "",
        onSubmit: @escaping (String) -> Void
    ) {
        self._isPresented = isPresented
        self.initialText = initialText
        self.onSubmit = onSubmit
        self._noteText = State(initialValue: initialText)
    }
    
    var title: ResourcesStringResource {
        initialText.count == 0 ? MR.strings().add_note : MR.strings().edit_note
    }
    
    var body: some View {
        NavigationView {
            VStack {
                TextView(text: $noteText)
            }.padding()
            .navigationBarTitle(Text(title), displayMode: .inline)
            .navigationBarItems(
                leading: Button(MR.strings().cancel.load()) {
                    isPresented = false
                }
                .padding(),
                trailing: Button(MR.strings().ok.load()) {
                    onSubmit(noteText)
                    isPresented = false
                }
                .padding()
            )
            
        }
    }
}

struct NoteView_Previews: PreviewProvider {
    static var previews: some View {
        NoteView(isPresented: .constant(false)) { value in
                
        }
    }
}

struct TextView: UIViewRepresentable {
    @Binding var text: String

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    func makeUIView(context: Context) -> UITextView {
        let textView = UITextView()
        textView.delegate = context.coordinator

        textView.font = UIFont(name: "HelveticaNeue", size: 18)
        textView.isScrollEnabled = true
        textView.isEditable = true
        textView.isUserInteractionEnabled = true
        textView.becomeFirstResponder()

        return textView
    }

    func updateUIView(_ uiView: UITextView, context: Context) {
        uiView.text = text
    }

    class Coordinator : NSObject, UITextViewDelegate {

        var parent: TextView

        init(_ uiTextView: TextView) {
            self.parent = uiTextView
        }

        func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
            return true
        }

        func textViewDidChange(_ textView: UITextView) {
            self.parent.text = textView.text
        }
    }
}
