package businesslogic.kitchenTask;

import businesslogic.procedure.Procedure;
import businesslogic.shift.Shift;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SummarySheet {
    private static Map<Integer, SummarySheet> loadedSummarySheet = FXCollections.observableHashMap();
    private int id;
    private String title;
    private int eventId;
    private int serviceId;

    private SummarySheet() {
        this.loadedSummarySheet = FXCollections.observableHashMap();
    }

    private ObservableList<KitchenTask> kitchenTasks;

    private User owner;

    public SummarySheet(User user, String title, int eventId, int serviceId) {

        this.title = title;
        this.eventId = eventId;
        this.serviceId = serviceId;
        this.owner = user;

        kitchenTasks = FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public boolean isOwner(User u) {
        return u.getId() == this.owner.getId();
    }

    // STATIC METHODS FOR PERSISTENCE

    public static void saveNewSummarySheet(SummarySheet sumSheet) {
        String summaryInsert = "INSERT INTO SummarySheet (eventId, serviceId, title, ownerId, active) VALUES (?, ?, ?, ?, 1);";


        PersistenceManager.executeBatchUpdate(summaryInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, sumSheet.eventId);
                ps.setInt(2, sumSheet.serviceId);
                ps.setString(3, PersistenceManager.escapeString(sumSheet.title));
                ps.setInt(4, sumSheet.owner.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // should be only one
                if (count == 0) {
                    sumSheet.id = rs.getInt(1);
                }
            }
        });

    }

    public static void deleteSummarySheet(SummarySheet sumSheet) {
        // delete tasks
        String delTasks = "DELETE FROM KitchenTask WHERE sumSheetId = " + sumSheet.id;
        PersistenceManager.executeUpdate(delTasks);

        System.out.println("sumSheet.id: " + sumSheet.id);

        // delete summary sheet
        String del = "UPDATE SummarySheet SET active = 0 WHERE id = " + sumSheet.id;
        PersistenceManager.executeUpdate(del);
        loadedSummarySheet.remove(sumSheet);
    }


    public static void restoreSummarySheet(SummarySheet sumSheet) {
        System.out.println("sumSheet.id: " + sumSheet.id);
        String res = "UPDATE SummarySheet SET active = 1 WHERE id = " + sumSheet.id;
        PersistenceManager.executeUpdate(res);
    }


    public void deleteKitchenTask(KitchenTask kt){
        this.kitchenTasks.remove(kt);
    }



    public KitchenTask addTask(Procedure procedure){
        KitchenTask kt = new KitchenTask(procedure);
        this.kitchenTasks.add(kt);

        return kt;
    }

    public KitchenTask assignKitchenTask(KitchenTask kt, User cook, Shift shift ){
        kt.assignKitchenTask(cook,shift);
        return kt;
    }

    public boolean hasKitchenTask(KitchenTask kt) {
        return kitchenTasks.size() > 0 && kitchenTasks.contains(kt);
    }

    public void moveKitchenTask(KitchenTask kt, int position){
        this.kitchenTasks.remove(kt);
        this.kitchenTasks.add(position, kt);
    }



    public static void saveKitchenTaskOrder(SummarySheet sum) {
        String upd = "UPDATE KitchenTask SET position = ? WHERE ktId = ?";
        PersistenceManager.executeBatchUpdate(upd, sum.kitchenTasks.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, batchCount);
                ps.setInt(2, sum.kitchenTasks.get(batchCount).getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

            }
        });
    }

    public KitchenTask insertMoreInfo(KitchenTask kt, String quantity, String etimes) {
        kt.insertMoreInfo(quantity,etimes);
        return kt;
    }

    public KitchenTask insertReadyQuantity(KitchenTask kt, String ready_quantity) {
        kt.insertReadyQuantity(ready_quantity);
        return kt;
    }


    public String toString(){

        String result= "Titolo summary sheet: " + title + "\n" + "Servizio Id: " + serviceId + "\n" + "Evento Id: " + eventId;

        result+="\n";
        for(KitchenTask task:kitchenTasks){
            result+="\t"+task.toString()+ "\n";
        }

        return result;

    }
}
