package menus

import MenuCreator
import data.Archive
import data.Menuable
import data.Note

fun showNotesMenu(archive: Archive) {
    val onCreate: (MutableList<Note>) -> Unit = { notes -> MenuCreator.ShowAddItem(notes) }
    val onSelect: (Menuable) -> Unit =  { note -> showNote(note as Note) }
    val onExit = { println("Переходим на экран списка архивов") }
    MenuCreator.ShowSelectItem(archive.notes, 1, onCreate, onSelect, onExit)
}

fun showNote(note: Note) {
    println("------------")
    println("Название заметки: ${note.title}")
    println("Текст заметки: ${note.text}")
    println("Введите любой символ, чтобы вернуться назад")
    readln()
}