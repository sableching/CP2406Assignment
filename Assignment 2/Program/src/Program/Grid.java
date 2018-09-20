package Program;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Grid extends Pane{
    @FXML private Label whoCrashed;

    private ArrayList<Rectangle> jetWallList;
    private Rectangle menuBack;

    public Grid(){
        this.jetWallList = new ArrayList<Rectangle>();

        this.menuBack = new Rectangle(100, 140, 400, 200);
        this.menuBack.setFill(Color.BLACK);
        this.menuBack.setArcWidth(20);
        this.menuBack.setArcHeight(20);

        this.whoCrashed = new Label();
        this.whoCrashed.setLayoutX(120);
        this.whoCrashed.setLayoutY(220);
        this.whoCrashed.setStyle("-fx-font-size: 40px;" +
                "-fx-font-family:'Open Sans Light'; -fx-font-weight:bold");
    }
    //Returns everything to original postition
    public void resetBoard(){
        this.getChildren().removeAll(this.menuBack, this.whoCrashed);
        for (Rectangle j : this.jetWallList) {
            this.getChildren().remove(j);
        }
        this.jetWallList.clear();
    }

    //Colours in the route the player has taken
    public void drawJetWall(Color color, Rectangle jetWall){
        jetWall.setFill(color);
        this.jetWallList.add(jetWall);
        this.getChildren().add(jetWall);
    }

    //Shows who has won/avoided crashing
    public void drawCrashMenu(int menuCode){
        this.getChildren().removeAll(this.menuBack, this.whoCrashed);
        if (menuCode == 2) {
            this.whoCrashed.setText("Player 1 Wins!");
            this.whoCrashed.setTextFill(Color.CRIMSON);
        } else if (menuCode == 3) {
            this.whoCrashed.setText("Player 1 Wins!");
            this.whoCrashed.setTextFill(Color.CRIMSON);
        }
        this.getChildren().add(this.menuBack);
        this.getChildren().add(this.whoCrashed);
    }
}