package redux

import data.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date
import kotlin.math.absoluteValue

fun sortReducer(state: State, action: RAction) =
    when (action) {
        is sortByAscending -> {
            val newList = state.drugs.toList().sortedByDescending { (_,it) -> it.price }
            State(
                newList.toMap(),
                state.reviews,
                state.visibilityFilter
            )
        }
        is sortByDescending -> {
            val newList = state.drugs.toList().sortedBy{ (_,it) -> it.price }
            State(
                newList.toMap(),
                state.reviews,
                state.visibilityFilter
            )
        }
        else -> state
    }

fun visibilityReducer(state: State, action: RAction) = when (action) {
    is SetVisibilityFilter -> State(
        state.drugs,
        state.reviews,
        action.filter
    )
    else -> state
}

fun addReducer(state: State, action: RAction, newId: Int = -1) =
    when (action) {
        is AddDrug -> {
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
            val newDrug = Drug(
                name.value,
                quantity.value.toInt(),
                image.value,
                price.value.toInt(),
                desc.value,
                formName.value,
                date
            )
            val newDrugs = state.drugs.plus(newId to newDrug)
            console.log(state.drugs)
            modal.style.display = "none"
            name.value = ""
            formName.value = ""
            quantity.value = ""
            image.value = ""
            price.value = ""
            desc.value = ""
            State(
                newDrugs,
                state.reviews
            )
        }
        is AddReview -> {
            val author = document.getElementById("author") as HTMLInputElement
            val text = document.getElementById("review-text") as HTMLInputElement
            val newReview = Review(action.index, author.value, text.value)
            val newReviews = state.reviews.plus(newReview)
            console.log(state.reviews)
            State(
                state.drugs,
                newReviews
            )
        }
        else -> state
    }


fun rootReducer(state: State, action: RAction) =
    when (action) {
        is AddDrug -> {
            val id = state.drugs.newId()
            addReducer(state, action, id)
        }
        is AddReview -> {
            addReducer(state, action)
        }
        is sortByAscending -> {
            sortReducer(state,action)
        }
        is sortByDescending -> {
            sortReducer(state,action)
        }
        is SetVisibilityFilter -> {
            visibilityReducer(state,action)
        }
        else -> state
    }