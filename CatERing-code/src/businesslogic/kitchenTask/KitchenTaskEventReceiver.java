package businesslogic.kitchenTask;

public interface KitchenTaskEventReceiver {
    public void updateSummarySheetCreated(SummarySheet sumSheet);

    public void updateSummarySheetDeleted(SummarySheet sumSheet);

    public void updateSummarySheetRestore(SummarySheet sumSheet);

    public void updateKitchenTaskAdded(SummarySheet sumSheet, KitchenTask kt);

    public void updateKitchenTaskDeleted(SummarySheet sumSheet, KitchenTask kt);

    public void updateKitchenTaskRearranged(SummarySheet sumSheet);

    public void updateTaskAssigned(SummarySheet sumSheet, KitchenTask kt);

    public void updateKitchenTaskMoreInfoAdded(SummarySheet sumSheet, KitchenTask kt);

    public void updateKitchenTaskReadyQuantityAdded(SummarySheet sumSheet, KitchenTask kt);


}
