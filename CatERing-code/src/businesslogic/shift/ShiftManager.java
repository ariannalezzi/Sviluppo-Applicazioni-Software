package businesslogic.shift;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShiftManager {

    public ShiftManager() {
        Shift.loadAllShifts();
    }


    public static ObservableList<Shift> getShifts() {
        return FXCollections.unmodifiableObservableList(Shift.getAllShifts());
    }

}


