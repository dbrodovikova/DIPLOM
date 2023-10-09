package hw7.controller.impl;

import hw7.model.Note;
import hw7.model.repository.impl.NoteRepository;

import java.util.List;

public class Controller {

    private NoteRepository noteRepository;

    public Controller(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void create(Note note) {
        noteRepository.create(note);
    }

    public Note read(long id) {
        return noteRepository.read(id);
    }
    public List<Note> readAll() {
        return noteRepository.readAll();
    }


    public void update(long id, Note noteForUpdate) {
        noteRepository.update(id, noteForUpdate);
    }

    public boolean delete(long id) {
        return noteRepository.delete(id);
    }
}
