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

public class TestCatERingKT6a {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Tony");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println("\nTEST CREATE SUMMARY SHEET");
            SummarySheet sumSheet = CatERing.getInstance().getKitchenTaskManager().createSummarySheet("Antipasto", 3, 6);
            System.out.println(sumSheet.toString());


            /** per prendere le ricette e preparazioni **/
            //System.out.println("\nTEST LOAD RECIPES");
            ObservableList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();


            //System.out.println("\nTEST LOAD PREPARATION");
            ObservableList<Preparation> preparation = CatERing.getInstance().getRecipeManager().getPreparations();


            System.out.println("\nTEST INSERT KITCHEN TASKS");
            KitchenTask ktask1 = CatERing.getInstance().getKitchenTaskManager().insertTask(preparation.get(2));  //salsa rosa
            KitchenTask ktask2 = CatERing.getInstance().getKitchenTaskManager().insertTask(preparation.get(3));  //crema chantilly
            KitchenTask ktask3 = CatERing.getInstance().getKitchenTaskManager().insertTask(recipes.get(15));     //pizzette
            KitchenTask ktask4 = CatERing.getInstance().getKitchenTaskManager().insertTask(recipes.get(9));      //pane al cioccolato

            System.out.println(ktask1.toString());
            System.out.println(ktask2.toString());
            System.out.println(ktask3.toString());
            System.out.println(ktask4.toString());


            System.out.println("\nTEST MOVE KITCHEN TASK");
            CatERing.getInstance().getKitchenTaskManager().moveKitchenTask(ktask3,0);


            System.out.println(sumSheet.toString());

            //System.out.println("\nTEST LOAD SHIFTS");
            ObservableList<Shift> shifts = CatERing.getInstance().getShiftManager().getShifts();

            System.out.println("\nTEST GET SHIFT TABLE");
            CatERing.getInstance().getKitchenTaskManager().getShiftTable();
            for (Shift e: shifts) {
                System.out.println(e);
            }


            System.out.println("\nTEST ASSIGN KITCHEN TASKS");
            CatERing.getInstance().getKitchenTaskManager().assignTask(ktask1, User.loadUser("Marinella"), shifts.get(1));
            CatERing.getInstance().getKitchenTaskManager().assignTask(ktask2, User.loadUser("Antonietta"), shifts.get(0));
            CatERing.getInstance().getKitchenTaskManager().assignTask(ktask3, User.loadUser("Guido"), shifts.get(2));
            CatERing.getInstance().getKitchenTaskManager().assignTask(ktask4, User.loadUser("Paola"), shifts.get(3));

            System.out.println(sumSheet.toString());

            System.out.println("\nTEST INSERT INFO");
            CatERing.getInstance().getKitchenTaskManager().insertMoreInfo(ktask3, "2 kg", "3 ore");
            CatERing.getInstance().getKitchenTaskManager().insertMoreInfo(ktask1, "1 kg", "2 ore");
            CatERing.getInstance().getKitchenTaskManager().insertMoreInfo(ktask2, "3 kg", "1,5 ore");
            CatERing.getInstance().getKitchenTaskManager().insertMoreInfo(ktask4, "100", "4 ore");

            System.out.println(sumSheet.toString());

            System.out.println("\nTEST INSERT READY QUANTITY");
            CatERing.getInstance().getKitchenTaskManager().insertReadyQuantity(ktask3, "5 kg");
            CatERing.getInstance().getKitchenTaskManager().insertReadyQuantity(ktask1, "1 kg");

            System.out.println(sumSheet.toString());

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
