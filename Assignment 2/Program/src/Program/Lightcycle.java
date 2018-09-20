package Program;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

public class Lightcycle extends Rectangle {
    @FXML private double velocityX;
    @FXML private double velocityY;

    private ArrayList<Rectangle> allCoords = new ArrayList<Rectangle>();
    private int direction;
    private boolean alive;

    public Lightcycle() {
        this.setWidth(15);
        this.setHeight(15);
        this.direction = 1;
        this.alive = true;
    }

    //Adds current position

    public void addCurrentPosition(double x, double y) {
        Rectangle jetWall = new Rectangle();
        // Draws jet wall dimensions according to direction that
        // the lightcycle is moving in.
        // Moving Up
        if (this.direction == 1) {
            jetWall.setY(y + 10);
            jetWall.setX(x);
            jetWall.setWidth(15);
            jetWall.setHeight(5);
            //Right
        } else if (this.direction == 2) {
            jetWall.setX(x);
            jetWall.setY(y);
            jetWall.setWidth(5);
            jetWall.setHeight(15);
            //Down
        } else if (this.direction == 3){
            jetWall.setX(x);
            jetWall.setY(y);
            jetWall.setWidth(15);
            jetWall.setHeight(5);
            //Left
        } else {
            jetWall.setX(x + 10);
            jetWall.setY(y);
            jetWall.setWidth(5);
            jetWall.setHeight(15);
        }
        this.allCoords.add(jetWall);
    }

    //Gets current lightcycle position

    public Rectangle getCurrentPosition() {
        return this.allCoords.get(allCoords.size() - 1);
    }

    //Gets list of all coordinates lightcycle has been.
    public ArrayList<Rectangle> getPastPositions() {
        return this.allCoords;
    }


    //Changes direction of lightcycle

    public void setDirection(int newDirection) {
        this.direction = newDirection;
    }


    //Clears lightcycle's past positions

    public void clearAllPositions(){
        this.allCoords.clear();
    }


    public void step() {
        this.setLayoutX(this.getLayoutX() + this.velocityX);
        this.setLayoutY(this.getLayoutY() + this.velocityY);
    }


    //Gets horizontal velocity of lightcycle

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }


    //Gets vertical velocity of lightcycle
    public double getVelocityY() {
        return velocityY;
    }


    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }


    //Check for crashing of lightcycle

    public boolean checkCrash(ArrayList<Rectangle> opponentJetWalls) {
        this.alive = true;

        double top = this.getLayoutY() + this.getY();
        double side = this.getLayoutX() + this.getX();
        Bounds cycleBounds = this.getBoundsInParent();


        if (side <= 4 || side >= 583) {
            return !this.alive;
        } else if (top <=0 || top >= 485) {
            return !this.alive;
        } else {
            for (Rectangle j : opponentJetWalls) {
                Bounds otherJetBounds = j.getBoundsInParent();
                if (cycleBounds.intersects(otherJetBounds)) {
                    return !this.alive;
                }
            }
            for (int i = 0; i < this.getPastPositions().size() - 6; i++) {
                Rectangle j = this.getPastPositions().get(i);
                Bounds jetBounds = j.getBoundsInParent();
                if (cycleBounds.intersects(jetBounds)) {
                    return !this.alive;
                }
            }
        }
        return this.alive;
    }
}