package container

import components.DrugFullProps
import components.fDrugFull
import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.redux.rConnect
import redux.*

interface DrugFullDispatchProps : RProps {
    var addReview: (Review) -> Unit
}

interface DrugFullStateProps: RProps {
    var drugs: Map<Int,Drug>
    var reviews: Array<Review>
}

interface FilmFullOwnProps : RProps {
    var index: Int
}

val drugFullHoc =
    rConnect<
            State,
            RAction,
            WrapperAction,
            FilmFullOwnProps,                         // Own Props
            DrugFullStateProps,
            DrugFullDispatchProps,
            DrugFullProps
            >(
        mapStateToProps = { state, _ ->
            drugs = state.drugs
            reviews = state.reviews
        },
        mapDispatchToProps = { dispatch, _ ->
            addReview = { dispatch(AddReview(it)) }
        }
    )

val drugFullRClass =
    withDisplayName(
        "drugFull",
        fDrugFull
    )
        .unsafeCast<RClass<DrugFullProps>>()

val drugFullContainer =
    drugFullHoc(drugFullRClass)