package menus

import MenuCreator
import data.Archive
import data.Library
import data.Menuable
import data.Note

fun createTestData() {
    //Тестовая информация
    val notes1 = mutableListOf(Note("Заметка №1", "Текст 1"), Note("Заметка №2", "Текст 2"))
    val notes2 = mutableListOf(Note("Заметка №3", "Текст 3"), Note("Заметка №4", "Текст 4"))
    val archive1 = Archive("Архив заметок №1", notes1)
    val archive2 = Archive("Архив заметок №2", notes2)
    Library.archives.add(archive1)
    Library.archives.add(archive2)
}

fun showArchivesMenu() {
    val onCreate: (MutableList<Note>) -> Unit = { _ -> MenuCreator.ShowAddItem(null) }
    val onSelect: (Menuable) -> Unit = { archive -> showNotesMenu(archive as Archive) }
    val onExit = { println("Программа завершена") }
    MenuCreator.ShowSelectItem(Library.archives, 0, onCreate, onSelect, onExit)
}