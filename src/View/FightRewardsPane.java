package View;

import Models.Actions.RelicActions;
import Models.Cards.AbstractCard;
import Controller.Dungeon.Room.Fight;
import Models.Object.AbstractRelic;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

import static View.Main.game;

public class FightRewardsPane extends StackPane {

    static int width;
    static int height;
    ImageView back;
    Node fightRewards;
    ImageView proceed;
    Pane pane;
    ImageView tick;

    public FightRewardsPane(int width, int height){
        this.width = width;
        this.height = height;
        pane =  new Pane();
        fightRewards = rewards();
        proceed = new ImageView(new Image("goAhead.png"));
        back = new ImageView(new Image("up.png"));
        tick = new ImageView(new Image("tick.png"));
        this.getChildren().add(pane);
        System.out.println("width : "+width);
        System.out.println("height : "+height);
    }

    public void initialize(){
        background();
        pane.getChildren().add(fightRewards);
        chosen();
    }

    public void chosen(){
        tick.setPreserveRatio(true);
        tick.setFitHeight(height/(93.0/10)); //tick.setFitHeight(75);
        tick.setX(width/2); //tick.setX(width/2-100+100); // x of node
        tick.setY(height/2.0-height/5.0+ height/(35.0/10)); //tick.setY(height/2-140+200);
        tick.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fightRewards.setVisible(false);
                tick.setVisible(false);
                //draw();
                proceed();
            }
        });
        pane.getChildren().add( tick );
    }

    public void visible(boolean bool){
        this.setVisible(bool);
    }

    public void proceed(){
        proceed.setPreserveRatio(true);
        proceed.setFitHeight(height/7);
        proceed.setX(width/5+ width/3*2);
        proceed.setY(height/4*3);
        pane.getChildren().add( proceed );
        proceed.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(" you are in next scene");
                game.getDungeon().ascend();
            }
        });
    }

    static Node rewards() {

        Group g = new Group();
        VBox vbox = new VBox(height/35);

        // gold reward
        HBox box1 = new HBox();
        Text goldText = new Text();
        int gold = ((Fight)game.getDungeon().getCurrentRoom()).getGoldAmount();
        goldText.setText(gold + " gold");
        goldText.setFill(Color.WHITE);
        goldText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        ImageView goldImage = new ImageView(new Image("gold.png"));
        goldImage.setFitWidth(width/(325.0/10)); //goldImage.setFitWidth(40);
        goldImage.setFitHeight(height/(175.0/10)); //goldImage.setFitHeight(40);
        goldImage.setVisible(true);
        goldImage.setPreserveRatio(true);
        box1.getChildren().addAll(goldImage, goldText);
        vbox.getChildren().add(box1);

        // card reward
        Rectangle cardRect = new Rectangle();
        ArrayList<AbstractCard> cards = ((Fight)game.getDungeon().getCurrentRoom()).getCards();
        String label = "add a card to your deck";
        Text text = new Text(label);
        vbox.getChildren().add(text);

        // relic reward
        ArrayList<AbstractRelic> relics = ((Fight)game.getDungeon().getCurrentRoom()).getRelics();
        String desc = relics.get(0).getDescription();
        String s = "get a relic";
        Text relicText = new Text(s);
        String name = relics.get(0).getName();
        Text relicDesc = new Text();
        relicDesc.setText(name+" : "+ desc);
        relicDesc.setFill(Color.WHITE);
        relicDesc.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        vbox.getChildren().add(relicText);


        // gold reward event listener
        goldImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                game.getPlayer().changeGold(((Fight) game.getDungeon().getCurrentRoom()).getGoldAmount());
                goldImage.setVisible(false);
                goldText.setVisible(false);
                relicText.setDisable(true);
                text.setDisable(true);
                goldImage.setDisable(true);
            }
        });


        // relic reward event listener
        HBox box2 = new HBox();
        relicText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String name = relics.get(0).getName();
                name = name + ".png";
                ImageView relicImage = new ImageView(new Image(name));
                relicImage.setPreserveRatio(true);
                relicImage.setFitHeight(height/7.0); //relicImage.setFitHeight(100);
                //box2.setPadding(new Insets(y/2,100, 100, x/2-90 ));
                box2.setPadding(new Insets(height/2.0,width/13.0, height/7.0, width/(232.0/100) ));
                box2.getChildren().addAll(relicImage, relicDesc);
                g.getChildren().add(box2);
                relicImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        RelicActions.addRelic(game.getPlayer(),relics.get(0));
                        relicImage.setVisible(false);
                        relicDesc.setVisible(false);
                        relicText.setDisable(true);
                        goldImage.setDisable(true);
                        text.setDisable(true);

                    }
                });

            }
        });

        // card reward event listener
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String name = cards.get(0).getName();
                name = name + ".png";
                cardRect.setX(width/(232.0/100)); //cardRect.setX(x/2-90);
                cardRect.setY(height/(33.0/10)); //cardRect.setY(y/2-140);
                cardRect.setFill(new ImagePattern(new Image(name)));
                cardRect.setWidth(width/(65/10.0)); //cardRect.setWidth(200);
                cardRect.setHeight(height/3.0); //cardRect.setHeight(250);
                cardRect.setVisible(true);
                g.getChildren().add(cardRect);
                cardRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        game.getPlayer().masterDeck.addCard(cards.get(0));
                        cardRect.setVisible(false);
                        text.setDisable(true);
                        goldImage.setDisable(true);
                        relicText.setDisable(true);
                        text.setDisable(true);
                    }
                });
            }
        });

        Rectangle rect = new Rectangle();
        rect.setX(width/2.0- width/13.0); //rect.setX(x/2-100);
        rect.setY(height/2-(height/(46.0/10))); //rect.setY(y/2-150);
        rect.setFill(Color.GREY);
        rect.setStroke(Color.BLACK);
        rect.setWidth(width/(65.0/10)); //rect.setWidth(200);
        rect.setHeight(height/(233.0/100)); //rect.setHeight(300);
        rect.setVisible(true);

        //vbox.setPadding(new Insets(y/2-130,100, 100, x/2-90));
        vbox.setPadding(new Insets(height/(32.0/10),width/13.0, height/7.0, width/(232.0/100)));

        g.getChildren().add(rect);
        g.getChildren().add(vbox);
        return g;
    }

    private  void background(){
        back.setFitWidth(width);
        back.setFitHeight(height);
        back.setOpacity(0.90);
        pane.getChildren().add(back);
    }
}