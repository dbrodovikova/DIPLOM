package hw7.model.repository.impl;

import hw7.model.Note;
import hw7.model.dao.impl.FileOperation;
import hw7.model.repository.Repository;
import hw7.util.impl.Mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository implements Repository<Note> {

    private Mapper mapper;
    private FileOperation fileOperation;

    public NoteRepository(FileOperation fileOperation) {
        mapper = new Mapper();
        this.fileOperation = fileOperation;
    }

    public List<Note> readAll() {
        List<String> lines = fileOperation.findAll();
        List<Note> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.output(line));
        }
        return users;
    }

    @Override
    public void create(Note note) {
        List<Note> users = readAll();
        long max = 0L;
        for (Note n : users) {
            long id = n.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        note.setId(next);
        users.add(note);
        List<String> lines = new ArrayList<>();
        for (Note n : users) {
            lines.add(mapper.input(n));
        }
        fileOperation.saveAll(lines);
    }

    @Override
    public Note read(long id) {
        List<Note> allNotes = readAll();
        return allNotes.stream()
                .filter(searchable -> searchable.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void update(long id, Note notesForUpdate) {
        try {
            List<Note> notes = readAll();
            Note updatedNotes = notes.stream()
                    .filter(u -> u.getId() == id)
                    .findFirst()
                    .get();
            updatedNotes.setNote(notesForUpdate.getNote());
            updatedNotes.setLocalDate(LocalDate.now());
            List<String> list = new ArrayList<>();
            for (Note note : notes) {
                list.add(mapper.input(note));
            }
            fileOperation.saveAll(list);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean delete(Long id) {
        long idForDelete = readAll()
                .stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .get()
                .getId();

        List<String> list = new ArrayList<>();
        for (Note note : readAll()) {
            if (note.getId() == (idForDelete)) continue;
            list.add(mapper.input(note));
        }
        fileOperation.saveAll(list);
        System.out.println(idForDelete);
        return true;
    }
}
