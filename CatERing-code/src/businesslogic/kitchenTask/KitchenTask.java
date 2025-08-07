package businesslogic.kitchenTask;

import businesslogic.procedure.Procedure;
import businesslogic.shift.Shift;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class KitchenTask {
    private int id;

    private Procedure tasksItem;
    private User taskCook;
    private Shift taskShift;

    private String estimatedTime;
    private String quantity;

    private String ready_quantity;


    public KitchenTask(Procedure procedure){
        this.tasksItem=procedure;
        id = tasksItem.getId();
    }

    public int getId() {
        return id;
    }

    public String getquantity() {
        return quantity;
    }

    public String getreadyquantity() {
        return ready_quantity;
    }

    public String gettime() {
        return estimatedTime;
    }

    public String toString() {
        return "TASK: " + tasksItem.getName() + " ASSEGNATO A: " + taskCook + " NEL " + taskShift + " QUANTITA': " + quantity + " TEMPO STIMATO: " + estimatedTime + " QUANTITA' PRONTA: " + ready_quantity;
    }


    public void assignKitchenTask(User cook, Shift shift){
        this.taskCook=cook;
        this.taskShift=shift;
    }


    public static void addTaskAssignment(SummarySheet sumSheet, KitchenTask t) {
        String itemInsert = "UPDATE catering.KitchenTask SET cook=?, shift=?  WHERE kTid = " + t.getId();
        PersistenceManager.executeBatchUpdate(itemInsert, 1, new BatchUpdateHandler() {

            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, t.taskCook.getId());
                ps.setInt(2, t.taskShift.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {


            }
        });
    }


    public void insertMoreInfo(String quantity, String etimes){

        this.quantity=quantity;
        this.estimatedTime=etimes;

    }

    public void insertReadyQuantity(String ready_quantity){

        this.ready_quantity=ready_quantity;

    }




    public static void saveNewKitchenTask(SummarySheet sum, KitchenTask kt) {
        String itemInsert = "INSERT INTO catering.KitchenTask (sumSheetId, procedureId, position, cook, estimatedTime, quantity, shift, readyQuantity) VALUES (?, ?, 0, 0, 0, 0, 0, 0);";
        PersistenceManager.executeBatchUpdate(itemInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, sum.getId());
                ps.setInt(2, kt.getId());
                //System.out.println(kt.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

                if (count == 0) {
                    kt.id = rs.getInt(1);
                }
            }
        });

    }

    public static void deleteKitchenTask(SummarySheet sumSheet, KitchenTask kt) {
        String remtask = "DELETE FROM KitchenTask WHERE sumSheetId = " + sumSheet.getId() + " AND ktId = " + kt.getId();
        PersistenceManager.executeUpdate(remtask);
    }



    public static void addKitchenTaskMoreInfo(SummarySheet sumSheet, KitchenTask kt) {
        String itemInsert = "UPDATE catering.KitchenTask SET quantity=?, estimatedTime=? WHERE ktId = " + kt.getId();
        PersistenceManager.executeBatchUpdate(itemInsert, 1, new BatchUpdateHandler() {


            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, kt.getquantity());
                ps.setString(2, kt.gettime());

            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {


            }
        });
    }

    public static void addKitchenTaskReadyQuantity(SummarySheet sumSheet, KitchenTask kt) {
        String itemInsert = "UPDATE catering.KitchenTask SET readyQuantity=? WHERE ktId = " + kt.getId();
        PersistenceManager.executeBatchUpdate(itemInsert, 1, new BatchUpdateHandler() {


            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, kt.getreadyquantity());

            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {


            }
        });
    }

}
