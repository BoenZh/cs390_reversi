package plu.yellow.reversi.model;

/**
 * Created by King7 on 3/9/17.
 */
public abstract class AbstractPerson {
    protected int id;
    protected String name;
    protected boolean a;

    public AbstractPerson(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public boolean isAI(){
        return a;
    }
}
