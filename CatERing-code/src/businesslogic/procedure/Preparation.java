package businesslogic.procedure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Preparation extends Procedure{
    private static Map<Integer, Preparation> all = new HashMap<>();

    private int id;
    private String name;
    private ArrayList<Procedure> procedures;


    private Preparation() {

    }

    public Preparation(String name) {
        id = 0;
        this.name = name;
        procedures = new ArrayList<>();
    }

    @Override
    public ArrayList<Procedure> getProcedure() {
        return procedures;
    }

    @Override
    public int getProcedureId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<Preparation> loadAllPreparations() {
        String query = "SELECT * FROM Preparations";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Preparation rec = all.get(id);
                    rec.name = rs.getString("name");
                } else {
                    Preparation rec = new Preparation(rs.getString("name"));
                    rec.id = id;
                    all.put(rec.id, rec);
                }
            }
        });
        ObservableList<Preparation> ret =  FXCollections.observableArrayList(all.values());
        Collections.sort(ret, new Comparator<Preparation>() {
            @Override
            public int compare(Preparation o1, Preparation o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }

    public static ObservableList<Preparation> getAllPreparations() {
        return FXCollections.observableArrayList(all.values());
    }

    public static Preparation loadPreparationById(int id) {
        if (all.containsKey(id)) return all.get(id);
        Preparation rec = new Preparation();
        String query = "SELECT * FROM Preparations WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                rec.name = rs.getString("name");
                rec.id = id;
                all.put(rec.id, rec);
            }
        });
        return rec;
    }


}
