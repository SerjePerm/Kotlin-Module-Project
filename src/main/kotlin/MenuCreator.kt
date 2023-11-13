import data.Archive
import data.Library
import data.Menuable
import data.Note
import data.ITEM_FORM_CREATE
import data.ITEM_FORM_LIST
import data.ITEM_FORM_NEW

object MenuCreator {

    fun <T : Menuable> showSelectItem(
        list: MutableList<T>,
        itemType: Int,
        onCreate: (MutableList<Note>) -> Unit,
        onSelect: (Menuable) -> Unit,
        onExit: () -> Unit
    ) {
        while (true) {
            //Вывод экрана
            println("---------------")
            println("Список ${ITEM_FORM_LIST[itemType]}:")
            println("0. Создать ${ITEM_FORM_CREATE[itemType]}")
            list.forEachIndexed { index, item -> println("${index+1}. ${item.title}") }
            //for ((index, item) in list.withIndex()) println("${index+1}. ${item.title}")
            println("${list.size+1}. Выход")
            //
            //Обработка результата ввода
            val userInput = showUserInput(list.size+1) ?: continue
            when (userInput) {
                0 -> onCreate(list as MutableList<Note>)
                list.size+1 -> { onExit(); break }
                else -> onSelect(list[userInput-1])
            }
        }
    }

    fun showUserInput(inputRange: Int) : Int? {
        println("Введите команду:")
        val userInput = readln()
        //Проверяем ввод, если ошибка, возвращаем null
        var result = userInput.toIntOrNull()
        if (result==null) {
            println("Неправильная команда, следует ввести цифру")
        } else if ((result < 0) or (result > inputRange)) {
            println("Такой цифры нет, следует ввести цифру из меню")
            result = null
        }
        return result
    }

    fun showAddItem(notes: MutableList<Note>?) {
        println("---------------")
        if (notes == null) createNewArchive() //создание нового архива
        else createNewNote(notes) //создание новой заметки
    }

    fun createNewArchive() {
        val titles = Library.archives.map { it.title }
        val newTitle = showTitleInput(0, titles)
        Library.archives.add(Archive(newTitle, ArrayList<Note>()))
    }

    fun createNewNote(notes: MutableList<Note>) {
        val titles = notes.map { it.title }
        var newText: String
        val newTitle = showTitleInput(1, titles)
        while (true) {
            println("Введите текст новой заметки")
            val userInput = readln()
            if (userInput.isBlank()) println("Текст заметки не может быть пустым")
            else { newText = userInput; break }
        }
        notes.add(Note(newTitle, newText))
    }

    fun showTitleInput(itemType: Int, titles: List<String>): String {
        while (true) {
            println("Введите название ${ITEM_FORM_NEW[itemType]}")
            val userInput = readln()
            if (userInput.isBlank()) println("Название не может быть пустым")
            else if (userInput in titles) println("Такое название уже есть, введите уникальное название")
            else return userInput
        }
    }
}