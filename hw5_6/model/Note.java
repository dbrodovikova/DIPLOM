package hw7.model;

import java.time.LocalDate;

public class Note {
    private long id;
    private String note;
    private LocalDate localDate;


    public Note(String note, LocalDate localDate) {
        this.note = note;
        this.localDate = localDate;
    }
    public Note(Long id, String note, LocalDate localDate) {
        this(note, localDate);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "Note: " +
                 note + '\'' +
                ", дата создания: " + localDate;
    }
}
