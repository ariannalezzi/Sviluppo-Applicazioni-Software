package businesslogic.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.procedure.Procedure;
import businesslogic.shift.Shift;
import businesslogic.shift.ShiftManager;
import businesslogic.user.User;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class KitchenTaskManager {
    private SummarySheet currentSummary;
    private ArrayList<KitchenTaskEventReceiver> eventReceivers;

    public KitchenTaskManager(){
        eventReceivers = new ArrayList<>();
    }

    public SummarySheet getCurrentSheet(){
        return currentSummary;
    }


    // 1 CREAZIONE SUMMARY SHEET
    public SummarySheet createSummarySheet(String title, int eventId, int serviceId) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef()) {
            throw new UseCaseLogicException();
        }


        SummarySheet sum = new SummarySheet(user, title, eventId, serviceId);
        this.setCurrentSummarySheet(sum);
        this.notifySummarySheetAdded(sum);

        return sum;

    }

    //1a
    public SummarySheet openSummary (SummarySheet sumSheet) throws UseCaseLogicException{
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef()) throw new UseCaseLogicException();
        if (!sumSheet.isOwner(user)) throw new UseCaseLogicException();
        this.setCurrentSummarySheet(sumSheet);

        return sumSheet;
    }


    //1c
    public void deleteSummarySheet (SummarySheet sumSheet) throws UseCaseLogicException{
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef()) throw new UseCaseLogicException();
        if (!sumSheet.isOwner(user)) throw new UseCaseLogicException();
        this.notifySummarySheetDeleted(sumSheet);
    }

    //1b
    public SummarySheet restoreSummarySheet (SummarySheet sumSheet) throws UseCaseLogicException{
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef()) throw new UseCaseLogicException();
        if (!sumSheet.isOwner(user)) throw new UseCaseLogicException();
        this.notifySummarySheetRestore(sumSheet);
        return sumSheet;
    }


    //2
    public KitchenTask insertTask(Procedure procedure) throws UseCaseLogicException{
        if(this.currentSummary == null) throw new UseCaseLogicException();
        KitchenTask kt = this.currentSummary.addTask(procedure);
        this.notifyKitchenTaskAdded(kt);
        return kt;
    }

    private void notifySummarySheetAdded(SummarySheet sum) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateSummarySheetCreated(sum);
        }
    }

    private void notifySummarySheetDeleted(SummarySheet sumSheet) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateSummarySheetDeleted(sumSheet);
        }
    }

    private void notifySummarySheetRestore(SummarySheet sumSheet) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateSummarySheetRestore(sumSheet);
        }
    }

    private void notifyKitchenTaskAdded(KitchenTask kt) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateKitchenTaskAdded(this.currentSummary, kt);
        }
    }

    //3
    public void moveKitchenTask(KitchenTask kt, int position)throws UseCaseLogicException{
        if(this.currentSummary == null) throw new UseCaseLogicException();
        if(!this.currentSummary.hasKitchenTask(kt)) throw new UseCaseLogicException();
        currentSummary.moveKitchenTask(kt, position);
        this.notifyKitchenTaskRearranged(this.currentSummary);
    }

    private void notifyKitchenTaskRearranged(SummarySheet sumSheet) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateKitchenTaskRearranged(sumSheet);
        }
    }

    //4
    public ObservableList<Shift> getShiftTable(){


        return  ShiftManager.getShifts();

    }

    //5
    public KitchenTask assignTask(KitchenTask kt, User cook, Shift shift) throws UseCaseLogicException {
        if(this.currentSummary == null) throw new UseCaseLogicException();
        if(!this.currentSummary.hasKitchenTask(kt)) throw new UseCaseLogicException();
        this.currentSummary.assignKitchenTask(kt,cook,shift);
        this.notifyKitchenTaskAssigned(kt);
        return kt;
    }

    //5a
    public void deleteKitchenTask(KitchenTask kt) throws UseCaseLogicException {
        if(this.currentSummary == null) throw new UseCaseLogicException();
        this.currentSummary.deleteKitchenTask(kt);
        this.notifyKitchenTaskDeleted(kt);
    }

    //6
    public KitchenTask insertMoreInfo(KitchenTask kt, String quantity, String etimes) throws UseCaseLogicException {
        if(this.currentSummary == null) throw new UseCaseLogicException();
        if(!this.currentSummary.hasKitchenTask(kt)) throw new UseCaseLogicException();
        this.currentSummary.insertMoreInfo(kt,quantity,etimes);
        this.notifyKitchenTaskMoreInfoAdded(kt);
        return kt;
    }

    //6a
    public KitchenTask insertReadyQuantity(KitchenTask kt, String ready_quantity) throws UseCaseLogicException {
        if(this.currentSummary == null) throw new UseCaseLogicException();
        if(!this.currentSummary.hasKitchenTask(kt)) throw new UseCaseLogicException();
        this.currentSummary.insertReadyQuantity(kt,ready_quantity);
        this.notifyKitchenTaskReadyQuantityAdded(kt);
        return kt;
    }

    private void notifyKitchenTaskAssigned(KitchenTask kt) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateTaskAssigned(this.currentSummary, kt);
        }
    }

    private void notifyKitchenTaskMoreInfoAdded(KitchenTask kt) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateKitchenTaskMoreInfoAdded(this.currentSummary,kt);
        }
    }

    private void notifyKitchenTaskReadyQuantityAdded(KitchenTask kt) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateKitchenTaskReadyQuantityAdded(this.currentSummary,kt);
        }
    }

    private void notifyKitchenTaskDeleted(KitchenTask kt) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateKitchenTaskDeleted(this.currentSummary, kt);
        }
    }

    public void setCurrentSummarySheet(SummarySheet sum) {
        this.currentSummary = sum;
    }

    public void addEventReceiver(KitchenTaskEventReceiver kitchenTaskPersistence) {
        this.eventReceivers.add(kitchenTaskPersistence);
    }
}
