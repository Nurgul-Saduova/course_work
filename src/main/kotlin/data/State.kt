package data

typealias DrugState = Map<Int, Drug>

class State(
    val drugs: DrugState,
    var reviews: Array<Review>,
    val visibilityFilter: VisibilityFilter = VisibilityFilter.ALL
)

fun <T> Map<Int, T>.newId() =
    (this.maxBy { it.key }?.key ?: 0) + 1

fun initialState() =
    State(
        drugList,
        reviewList()
    )


