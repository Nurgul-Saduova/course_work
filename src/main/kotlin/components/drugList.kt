package components

import container.filterLink
import data.Drug
import data.VisibilityFilter
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
import kotlin.js.Date

interface DrugListProps : RProps {
    var drugs: Map<Int,Drug>
    var sortByAscending: (Event) -> Unit
    var sortByDescending: (Event) -> Unit
    var addDrug: (Drug) -> Unit
}

val fDrugList =
    functionalComponent<DrugListProps> {
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

        filterLink {
            attrs.filter = VisibilityFilter.ALL
            +"Все"
        }
        filterLink {
            attrs.filter = VisibilityFilter.TABLETS
            +"Таблетки"
        }
        filterLink {
            attrs.filter = VisibilityFilter.CAPSULES
            +"Капсулы"
        }
        filterLink {
            attrs.filter = VisibilityFilter.OINTMENTS
            +"Мази"
        }
        filterLink {
            attrs.filter = VisibilityFilter.SOLUTIONS
            +"Растворы"
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
                    if (drug.name.contains(search)) {
                        drug(drug, key)
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
                            attrs.onClickFunction = {_:Event->
                                val modal = document.getElementById("myModal") as HTMLElement
                                val name = document.getElementById("name") as HTMLInputElement
                                val formName = document.getElementById("formName") as HTMLInputElement
                                val quantity = document.getElementById("quantity") as HTMLInputElement
                                val image = document.getElementById("image") as HTMLInputElement
                                val price = document.getElementById("price") as HTMLInputElement
                                val desc = document.getElementById("desc") as HTMLInputElement
                                val day = Date().getDate()
                                val month = Date().getMonth() + 1
                                val year = Date().getFullYear()
                                val date = "${day}.${month}.${year}"
                                it.addDrug(
                                    Drug(
                                        name.value,
                                        quantity.value.toInt(),
                                        image.value,
                                        price.value.toInt(),
                                        desc.value,
                                        formName.value,
                                        date
                                    )
                                )
                                modal.style.display = "none"
                                name.value = ""
                                formName.value = ""
                                quantity.value = ""
                                image.value = ""
                                price.value = ""
                                desc.value = ""
                            }
                        }
                        button(classes = "submit-btn") {
                            +"Закрыть"
                            attrs.onClickFunction = {_:Event->
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
    sortByAscending: (Event) -> Unit,
    sortByDescending: (Event) -> Unit,
    addDrug: (Drug) -> Unit
) = child(
    withDisplayName("DrugList", fDrugList)
) {
    attrs.drugs = drugs
    attrs.sortByAscending = sortByAscending
    attrs.sortByDescending = sortByDescending
    attrs.addDrug = addDrug
}