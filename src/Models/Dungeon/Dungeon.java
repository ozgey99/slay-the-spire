package Models.Dungeon;

import Models.Dungeon.Room.Fight;
import Models.Dungeon.Room.Merchant;
import Models.Dungeon.Room.Rest;
import Models.Dungeon.Room.Treasure;
import sts.FightScene;
import sts.RoomScene;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static sts.Main.game;

public class Dungeon {
    private int act;
    private String name;

    private AbstractRoom root;
    private AbstractRoom currentRoom;


    public Dungeon() {
        this.act = 1;
        this.name = "The Exordium";
    }

    public void generate() {

        //assign number to n
        int n = 5;

        //create boss
        Fight boss = new Fight(null, false, true);

        ArrayList<AbstractRoom> children = new ArrayList<>();
        ArrayList<AbstractRoom> prevRooms = new ArrayList<>();

        //randomize the number of paths
        int noOfPaths = (int) (Math.random() * n) + 1;

        //randomize the length
        int length = (int) (Math.random() * n) + 1;

        //populate from top to bottom
        for(int i = 0; i < noOfPaths; i++){
            children.clear();
            children.add(boss);

            for(int j = 0; j < length; j++){
                int rand = (int) (Math.random() * 5) + 1; //for 5 room types
                AbstractRoom room;

                switch(rand){
                    case 1:
                        room = new Fight(children, true, false);
                        break;
                    case 2:
                        room = new Merchant(children);
                        break;
                    case 3:
                        room = new Rest(children);
                        break;
                    case 4:
                        room = new Treasure(children);
                        break;
                    default:
                        room = new Fight(children, false, false);
                        break;
                }

                if(j == length - 1){
                    prevRooms.add(room);
                }

                children.clear();
                children.add(room);
            }
        }

        //add the beginning fight and chest
        Treasure beginningTreasure = new Treasure(prevRooms);

        ArrayList<AbstractRoom> c = new ArrayList<>();
        c.add(beginningTreasure);

        Fight beginningFight = new Fight(c, false, false);

        currentRoom = beginningFight;

        game.currentScene = new FightScene();
    }

    public int getAct() {
        return act;
    }

    public String getName() {
        return name;
    }

    public AbstractRoom getCurrentRoom() {
        return currentRoom;
    }

    public boolean ascend() {

        if (this.currentRoom.getChildren() == null) return false;
        System.out.println("ASCEND IN");
        this.currentRoom = this.currentRoom.getChildren().get(0); //change this to input from user
        this.currentRoom.start();
        return true;
    }
}
