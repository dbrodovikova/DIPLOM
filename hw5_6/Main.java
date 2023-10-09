package hw7;

import hw7.controller.impl.Controller;
import hw7.model.dao.impl.FileOperation;
import hw7.model.repository.impl.NoteRepository;
import hw7.util.connecttodb.DBConnector;
import hw7.view.impl.ConsoleView;


public class Main {

    public static void main(String[] args) {
        FileOperation fileOperation = new FileOperation(DBConnector.DBPATH);
        DBConnector db = new DBConnector();
        db.connect();
        NoteRepository noteRepository = new NoteRepository(fileOperation);
        Controller controller = new Controller(noteRepository);
        ConsoleView console = new ConsoleView(controller);
        console.runProgram();
    }
}
