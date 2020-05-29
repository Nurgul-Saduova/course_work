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
    var drugs: Map<Int, Drug>
}


private fun getVisibilityDrugs(drugs: Map<Int, Drug>, filter: VisibilityFilter): Map<Int, Drug> =
    when (filter) {
        VisibilityFilter.ALL -> drugs
        VisibilityFilter.TABLETS -> drugs.filter { it.value.form == "Таблетки" }
        VisibilityFilter.CAPSULES -> drugs.filter { it.value.form == "Капсулы" }
        VisibilityFilter.OINTMENTS -> drugs.filter { it.value.form == "Мази" }
        VisibilityFilter.SOLUTIONS -> drugs.filter { it.value.form == "Растворы" }
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
            drugs = getVisibilityDrugs(state.drugs,state.visibilityFilter)
        },
        mapDispatchToProps = { dispatch, _ ->
            add = { dispatch(AddDrug()) }
            sortByAscending = { dispatch(sortByAscending()) }
            sortByDescending = { dispatch(sortByDescending()) }
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