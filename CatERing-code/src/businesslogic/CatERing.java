package businesslogic;

import businesslogic.event.EventManager;
import businesslogic.kitchenTask.KitchenTaskManager;
import businesslogic.menu.MenuManager;
import businesslogic.procedure.RecipeManager;
import businesslogic.shift.ShiftManager;
import businesslogic.user.UserManager;
import persistence.MenuPersistence;
import persistence.KitchenTaskPersistence;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() { //restituisce singleton o lo crea se non ancora esistene
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private KitchenTaskManager kTaskMgr;
    private ShiftManager shiftMgr;

    private MenuPersistence menuPersistence;
    private KitchenTaskPersistence kitchentaskPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        kTaskMgr= new KitchenTaskManager();
        shiftMgr = new ShiftManager();
        menuPersistence = new MenuPersistence();
        kitchentaskPersistence = new KitchenTaskPersistence();
        menuMgr.addEventReceiver(menuPersistence);
        kTaskMgr.addEventReceiver(kitchentaskPersistence);

    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() { return eventMgr; }

    public ShiftManager getShiftManager() {
        return shiftMgr;
    }

    public KitchenTaskManager getKitchenTaskManager() {
        return kTaskMgr;
    }

}
