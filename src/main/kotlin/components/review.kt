package components
import data.Review
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent

interface ReviewProps : RProps {
    var review: Review
}

val fReview =
    functionalComponent<ReviewProps> {
        div ("review") {
            h4 {
                + it.review.author
            }
            p {
                + it.review.text
            }
        }
    }

fun RBuilder.review(
    review: Review
) = child(fReview) {
    attrs.review = review
}