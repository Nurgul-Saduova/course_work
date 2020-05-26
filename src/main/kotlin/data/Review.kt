package data

data class Review (
    val drug: Int,
    val author: String,
    val text: String
)

fun reviewList(): Array<Review> {
    return arrayOf(
        Review(0,"Иван","Отличный препарат!")
    )
}