import data.Archive
import data.Library
import data.Menuable
import data.Note
import data.StringConstants.Companion.item_form_create
import data.StringConstants.Companion.item_form_list
import data.StringConstants.Companion.item_form_new

object MenuCreator {

    fun <T : Menuable> ShowSelectItem(list: MutableList<T>, itemType: Int, onCreate: (MutableList<Note>) -> Unit, onSelect: (Menuable) -> Unit, onExit: () -> Unit) {
        while (true) {
            //Вывод экрана
            println("---------------")
            println("Список ${item_form_list[itemType]}:")
            println("0. Создать ${item_form_create[itemType]}")
            for ((index, item) in list.withIndex()) println("${index+1}. ${item.title}")
            println("${list.size+1}. Выход")
            //
            //Обработка результата ввода
            val userInput = UserInput(list.size+1) ?: continue
            when (userInput) {
                0 -> onCreate(list as MutableList<Note>)
                list.size+1 -> { onExit(); break }
                else -> onSelect(list[userInput-1])
            }
        }
    }

    fun UserInput(inputRange: Int) : Int? {
        println("Введите команду:")
        val userInput = readln()
        //Проверяем ввод, если ошибка, возвращаем null
        var result = userInput.toIntOrNull()
        if (result==null) println("Неправильная команда, следует ввести цифру")
        else if ((result < 0) or (result > inputRange)) { println("Такой цифры нет, следует ввести цифру из меню"); result = null }
        return result
    }

    fun ShowAddItem(notes: MutableList<Note>?) {
        println("---------------")
        if (notes == null) CreateNewArchive() //создание нового архива
        else CreateNewNote(notes) //создание новой заметки
    }

    fun CreateNewArchive() {
        val titles = Library.archives.map { it.title }
        val newTitle = ShowTitleInput(0, titles)
        Library.archives.add(Archive(newTitle, ArrayList<Note>()))
    }

    fun CreateNewNote(notes: MutableList<Note>) {
        val titles = notes.map { it.title }
        var newText: String
        val newTitle = ShowTitleInput(1, titles)
        while (true) {
            println("Введите текст новой заметки")
            val userInput = readln()
            if (userInput.isBlank()) println("Текст заметки не может быть пустым")
            else { newText = userInput; break }
        }
        notes.add(Note(newTitle, newText))
    }

    fun ShowTitleInput(itemType: Int, titles: List<String>): String {
        while (true) {
            println("Введите название ${item_form_new[itemType]}")
            val userInput = readln()
            if (userInput.isBlank()) println("Название не может быть пустым")
            else if (userInput in titles) println("Такое название уже есть, введите уникальное название")
            else return userInput
        }
    }
}