package Controller.Dungeon;

import Controller.Dungeon.Room.RoomType;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractRoom implements Serializable {
    protected RoomType type;
    protected ArrayList<AbstractRoom> children;
    protected boolean done;
    protected boolean isUnion = false;



    protected boolean isUnknown = false;





    public abstract void start();

    public void setUnion( boolean isUnion ){
        this.isUnion = isUnion;
    }
    public boolean isUnion(){
        return this.isUnion;
    }

    public void setUnknown(boolean unknown) {
        isUnknown = unknown;
    }
    public boolean isUnknown() {
        return isUnknown;
    }

    public abstract RoomType getType();
    public abstract ArrayList<AbstractRoom> getChildren();
    public abstract boolean getDone();
}
