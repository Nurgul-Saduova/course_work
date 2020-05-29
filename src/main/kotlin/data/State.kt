package data


class State(
    val drugs: Map<Int,Drug>,
    var reviews: Array<Review>,
    val visibilityFilter: VisibilityFilter = VisibilityFilter.ALL
)

fun <T> Map<Int, T>.newId() =
    (this.maxBy { it.key }?.key ?: 0) + 1

fun initialState() =
    State(
        drugList().mapIndexed { index, drug ->
            index to drug
        }.toMap(),
        reviewList()
    )


