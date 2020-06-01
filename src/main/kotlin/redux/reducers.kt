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
            val newDrugs = state.drugs.plus(newId to action.drug)
            State(
                newDrugs,
                state.reviews
            )
        }
        is AddReview -> {
            val newReviews = state.reviews.plus(action.review)
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