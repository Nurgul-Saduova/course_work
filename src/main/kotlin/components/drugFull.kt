package components

import data.Drug
import data.Review
import react.*
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import react.dom.*
import kotlin.browser.document


interface DrugFullProps : RProps {
    var index: Int
    var drugs: Map<Int,Drug>
    var reviews: Array<Review>
    var add: (Int) -> (Event) -> (Unit)
}

val fDrugFull =
    functionalComponent<DrugFullProps> {
        div("card-c") {
            div("card") {
                img(classes = "pic") {
                    attrs.src = it.drugs[it.index]?.image ?: "Изображение не найдено"
                    attrs.alt = "Неправильная ссылка на изображение"
                }
                div("container") {
                    h3 { +"${it.drugs[it.index]?.name}" }
                    h4 { +"Форма товара: ${it.drugs[it.index]?.form}" }
                    p { +"Количество: ${it.drugs[it.index]?.quantity}" }
                    p { +"Цена за штуку: ${it.drugs[it.index]?.price}" }
                    p { +"Описание: ${it.drugs[it.index]?.desc}" }
                    h3 { +"Отзывы" }
                    div {
                        input(InputType.text) {
                            attrs.id = "author"
                            attrs.placeholder = "Имя"
                        }
                        input(InputType.text) {
                            attrs.id = "review-text"
                            attrs.placeholder = "Отзыв"
                        }
                    }
                    button(classes = "review-btn") {
                        +"Добавить отзыв"
                        attrs.onClickFunction = it.add(it.index)
                    }
                    it.reviews.mapIndexed { _, review ->
                        if(review.drug == it.index) {
                                review(review)
                        }
                    }
                }
            }
        }

    }

fun RBuilder.fullDrugs(
    index: Int,
    drugs: Map<Int,Drug>,
    reviews: Array<Review>,
    add: (Int) -> (Event) -> (Unit)
) = child(
    withDisplayName("DrugFull", fDrugFull)
) {
    attrs.index = index
    attrs.drugs = drugs
    attrs.reviews = reviews
    attrs.add = add
}