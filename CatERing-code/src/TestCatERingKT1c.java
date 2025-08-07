import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.KitchenTask;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.procedure.Preparation;
import businesslogic.procedure.Procedure;
import businesslogic.procedure.Recipe;
import businesslogic.shift.Shift;
import businesslogic.user.User;
import javafx.collections.ObservableList;

public class TestCatERingKT1c {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Tony");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST CREATE SUMMARY SHEET");
            SummarySheet sumSheet = CatERing.getInstance().getKitchenTaskManager().createSummarySheet("Aperitivo1", 1, 2);
            SummarySheet sumSheet2 = CatERing.getInstance().getKitchenTaskManager().createSummarySheet("Aperitivo2", 1, 3);
            System.out.println(sumSheet.toString());
            System.out.println(sumSheet2.toString());


            System.out.println("\nTEST DELETE");
            CatERing.getInstance().getKitchenTaskManager().deleteSummarySheet(sumSheet2);



        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
