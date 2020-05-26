package data


class State(
    val drugs: Map<Int,Drug>,
    val forms: Array<Form>,
    var reviews: Array<Review>
)

fun <T> Map<Int, T>.newId() =
    (this.maxBy { it.key }?.key ?: 0) + 1

fun initialState() =
    State(
        drugList().mapIndexed { index, drug ->
            index to drug
        }.toMap(),
        formList(),
        reviewList()
    )


