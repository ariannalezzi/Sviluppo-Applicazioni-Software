package businesslogic.procedure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecipeManager {

    public RecipeManager() {
        Recipe.loadAllRecipes();
        Preparation.loadAllPreparations();
    }

    public static ObservableList<Recipe> getRecipes() {
        return FXCollections.unmodifiableObservableList(Recipe.getAllRecipes());
    }

    public ObservableList<Preparation> getPreparations() {
        return FXCollections.unmodifiableObservableList(Preparation.getAllPreparations());
    }
}
