package data

data class Form(
    val name: String
)

fun formList(): Array<Form> {
    return arrayOf(
        Form("Таблетки"),
        Form("Капсулы"),
        Form("Мази"),
        Form("Растворы")
    )
}