package businesslogic.procedure;

import java.util.ArrayList;

public abstract class Procedure {

    protected String name;

    protected int id;

    public Procedure() {}

    public void add(Procedure proc) {
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public abstract int getProcedureId();

    public abstract ArrayList<Procedure> getProcedure();
}
