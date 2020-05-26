package components

import container.drugFullContainer
import container.drugListContainer
import data.Drug
import data.Form
import data.Review
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*

interface AppProps : RProps {
    var drugs: Map<Int,Drug>
    var forms: Array<Form>
    var reviews: Array<Review>
}

interface RouteNumberResult : RProps {
    var number: String
}

fun fApp() =
    functionalComponent<AppProps> {props ->
        div("home") {
            div("desc") {
                h1 { +"Поиск лекарств" }
                div("text") { +"(Описание)" }
                div("menu") {
                    navLink(className = "nav", to = "/list") { +"Список лекарств" }
                }
            }

        }
        switch {
            route("/list",
                exact = true,
                render = { drugListContainer {} }
            )
            route("/list/:number",
                exact = true,
                render = renderObject(
                    { props.drugs[it] },
                    { index, drug ->
                        drugFullContainer {
                            attrs.index = index
                        }
                    }
                )
            )
        }
    }

fun <O> RBuilder.renderObject(
    selector: (Int) -> O?,
    rElement: (Int, O) -> ReactElement
) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val obj = selector(num)
        if (obj != null) {
            rElement(num, obj)
        } else
            p { +"Object not found" }
    }

fun RBuilder.app(
) =
    child(
        fApp()
    ) {
    }
