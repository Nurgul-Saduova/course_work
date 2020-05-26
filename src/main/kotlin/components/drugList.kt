package components

import data.Drug
import data.Form
import react.*
import org.w3c.dom.events.Event
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import react.dom.*
import kotlin.browser.document

interface DrugListProps : RProps {
    var drugs: Map<Int,Drug>
    var forms: Array<Form>
    var add: (Event) -> Unit
    var sortByAscending: (Event) -> Unit
    var sortByDescending: (Event) -> Unit
}

val fDrugList =
    functionalComponent<DrugListProps> {
        val (form, setForm) = useState("Таблетки")
        val (search, setSearch) = useState("")

        div("table-title") {
            h2 { +"Список лекарств" }
            button(classes = "desc-btn") {
                +"Добавить товар"
                attrs.onClickFunction = {
                    val modal = document.getElementById("myModal") as HTMLElement
                    modal.style.display = "block"
                }
            }
        }
        input(InputType.text, classes = "search") {
            attrs.id = "search"
            attrs.placeholder = "Поиск"
            attrs.onChangeFunction = {
                val srch = document.getElementById("search") as HTMLInputElement
                setSearch(srch.value)
            }
        }

        select {
            for (element in it.forms) {
                attrs.id = "form"
                option {
                    +element.name
                }
                attrs.onChangeFunction = {
                    val forms = document.getElementById("form") as HTMLSelectElement
                    setForm(forms.value)
                }
            }
        }
        table {
            attrs.id = "table-drugs"
            tr {
                th { +"Форма товара" }
                th { +"Форма товара" }
                th { +"Количество" }
                th {
                    +"Цена (рублей)"
                    button(classes = "srt-btn") {
                        +"По возрастанию"
                        attrs.onClickFunction = it.sortByAscending
                    }
                    button(classes = "srt-btn") {
                        +"По убыванию"
                        attrs.onClickFunction = it.sortByDescending
                    }
                }
                th { +"Дата добавления" }
            }
            it.drugs.forEach { (key, drug) ->
                if (drug.form == form) {
                    if (drug.name.contains(search)) {
                        drug(drug, key)
                    }
                }
            }
            div("modal") {
                attrs.id = "myModal"
                div("modal-content") {
                    h1("modal-h1") { +"Добавить товар" }
                    ul {
                        li {
                            +"Название"
                            input(InputType.text) {
                                attrs.id = "name"
                            }
                        }
                        li {
                            +"Форма (таблетки, капсулы и т.д)"
                            input(InputType.text) {
                                attrs.id = "formName"
                            }
                        }
                        li {
                            +"Количество"
                            input(InputType.text) {
                                attrs.id = "quantity"
                            }
                        }
                        li {
                            +"Изображение (ссылка на картинку)"
                            input(InputType.text) {
                                attrs.id = "image"
                            }
                            li {
                                +"Цена за штуку"
                                input(InputType.text) {
                                    attrs.id = "price"
                                }
                            }
                            li {
                                +"Описание"
                                input(InputType.text) {
                                    attrs.id = "desc"
                                }
                            }
                        }
                        button(classes = "submit-btn") {
                            +"Добавить"
                            attrs.onClickFunction = it.add
                        }
                        button(classes = "submit-btn") {
                            +"Закрыть"
                            attrs.onClickFunction = {
                                val modal = document.getElementById("myModal") as HTMLElement
                                val name = document.getElementById("name") as HTMLInputElement
                                val formName = document.getElementById("formName") as HTMLInputElement
                                val quantity = document.getElementById("quantity") as HTMLInputElement
                                val image = document.getElementById("image") as HTMLInputElement
                                val price = document.getElementById("price") as HTMLInputElement
                                val desc = document.getElementById("desc") as HTMLInputElement
                                modal.style.display = "none"
                                name.value = ""
                                formName.value = ""
                                quantity.value = ""
                                image.value = ""
                                price.value = ""
                                desc.value = ""
                            }
                        }
                    }
                }
            }
        }
    }

fun RBuilder.drugList(
    drugs: Map<Int,Drug>,
    forms: Array<Form>,
    add: (Event) -> Unit,
    sortByAscending: (Event) -> Unit,
    sortByDescending: (Event) -> Unit
) = child(
    withDisplayName("DrugList", fDrugList)
) {
    attrs.drugs = drugs
    attrs.forms = forms
    attrs.add = add
    attrs.sortByAscending = sortByAscending
    attrs.sortByDescending = sortByDescending
}