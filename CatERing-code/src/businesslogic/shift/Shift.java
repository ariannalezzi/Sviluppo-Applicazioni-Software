package businesslogic.shift;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import persistence.PersistenceManager;
import persistence.ResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Shift {

    private static Map<Integer, Shift> all = new HashMap<>();

    private int id;
    private String date;
    private String time;

    public Shift(String date, String time ){
        id = 0;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "TURNO: " + id + " IN DATA: " + date + " ALL'ORARIO: " + time;
    }



    public static ObservableList<Shift> loadAllShifts() {
        String query = "SELECT * FROM ShiftTable";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Shift shift = all.get(id);
                    shift.date = rs.getString("date");
                    shift.time = rs.getString("time");
                } else {
                    Shift rec = new Shift(rs.getString("date"),rs.getString("time"));
                    rec.id = id;
                    all.put(rec.id, rec);
                }
            }
        });
        ObservableList<Shift> ret =  FXCollections.observableArrayList(all.values());
        return ret;
    }

    public static ObservableList<Shift> getAllShifts() {
        return FXCollections.observableArrayList(all.values());
    }

}
