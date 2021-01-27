import SwiftUI
import shared

@ViewBuilder
func createTextField(_ hint: String, text: Binding<String>, width: Int32) -> some View {
    TextField(hint,text: text)
    .disableAutocorrection(true)
    .keyboardType(.decimalPad)
    .multilineTextAlignment(.trailing)
    .frame(width: CGFloat(width))
    .fixedSize(horizontal: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/, vertical: false)
}

@ViewBuilder
func createFixedWidthText(_ resource: ResourcesStringResource, width: Int32) -> some View {
    createFixedWidthText(resource.load(), width: width)
}

@ViewBuilder
func createFixedWidthText(_ text: String, width: Int32) -> some View {
    Text(text)
        .frame(width: CGFloat(width))
        .fixedSize(horizontal: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/, vertical: false)
}
