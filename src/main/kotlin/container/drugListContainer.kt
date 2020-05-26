package container

import components.DrugListProps
import components.fDrugList
import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.redux.rConnect
import redux.*

interface DrugListDispatchProps : RProps {
    var add: (Event) -> Unit
    var sortByAscending: (Event) -> Unit
    var sortByDescending: (Event) -> Unit
}

interface DrugListStateProps : RProps {
    var drugs: Map<Int,Drug>
    var forms: Array<Form>
}

val drugListHoc =
    rConnect<
            State,
            RAction,
            WrapperAction,
            RProps,                         // Own Props
            DrugListStateProps,
            DrugListDispatchProps,
            DrugListProps
            >(
        mapStateToProps = { state, _ ->
            drugs = state.drugs
            forms = state.forms
        },
        mapDispatchToProps = { dispatch, _ ->
            add = {dispatch(AddDrug())}
            sortByAscending = {dispatch(sortByAscending())}
            sortByDescending = {dispatch(sortByDescending())}
        }
    )

val drugListRClass =
    withDisplayName(
        "DrugList",
        fDrugList
    )
        .unsafeCast<RClass<DrugListProps>>()

val drugListContainer =
    drugListHoc(drugListRClass)