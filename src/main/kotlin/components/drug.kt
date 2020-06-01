package components

import data.Drug
import hoc.withDisplayName
import react.RBuilder
import react.RProps
import react.child
import react.dom.td
import react.dom.tr
import react.functionalComponent
import react.router.dom.navLink

interface DrugProps : RProps {
    var drug: Drug
    var index: Int
}

val fDrug =
    functionalComponent<DrugProps> {
        tr {
            td {  navLink("/list/${it.index}") {+it.drug.name } }
            td { +it.drug.form }
            td { +"${it.drug.quantity}" }
            td { +"${it.drug.price}" }
        }
    }

fun RBuilder.drug(
    drug: Drug,
    index: Int
) = child(
    withDisplayName("Drug", fDrug)
) {
    attrs.drug =  drug
    attrs.index = index
}