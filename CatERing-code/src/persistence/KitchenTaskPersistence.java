package persistence;

import businesslogic.kitchenTask.KitchenTask;
import businesslogic.kitchenTask.KitchenTaskEventReceiver;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.menu.Menu;

public class KitchenTaskPersistence implements KitchenTaskEventReceiver {

    @Override
    public void updateSummarySheetCreated(SummarySheet sumSheet) {
        SummarySheet.saveNewSummarySheet(sumSheet);
    }

    @Override
    public void updateSummarySheetDeleted(SummarySheet sumSheet) {
        SummarySheet.deleteSummarySheet(sumSheet);
    }

    @Override
    public void updateSummarySheetRestore(SummarySheet sumSheet) {
        SummarySheet.restoreSummarySheet(sumSheet);
    }


    @Override
    public void updateKitchenTaskAdded(SummarySheet sumSheet, KitchenTask kt){
        KitchenTask.saveNewKitchenTask(sumSheet,kt);
    }

    @Override
    public void updateKitchenTaskDeleted(SummarySheet sumSheet, KitchenTask kt) {
        KitchenTask.deleteKitchenTask(sumSheet,kt);
    }

    @Override
    public void updateKitchenTaskRearranged(SummarySheet sumSheet) {
        SummarySheet.saveKitchenTaskOrder(sumSheet);
    }

    public void updateTaskAssigned(SummarySheet sumSheet, KitchenTask kt) {
        KitchenTask.addTaskAssignment(sumSheet,kt);
    }

    @Override
    public void updateKitchenTaskMoreInfoAdded(SummarySheet sumSheet, KitchenTask kt) {
        KitchenTask.addKitchenTaskMoreInfo(sumSheet, kt);
    }

    @Override
    public void updateKitchenTaskReadyQuantityAdded(SummarySheet sumSheet, KitchenTask kt) {
        KitchenTask.addKitchenTaskReadyQuantity(sumSheet, kt);
    }
}
