package Program;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Timer;
import java.util.TimerTask;


public class Controller implements EventHandler<KeyEvent>{
    final private double framesPerSecond = 20.0;

    @FXML private Button playButton;
    @FXML private Button resetButton;
    @FXML private Lightcycle playerOne;
    @FXML private Grid theGrid;

    private boolean paused;
    private boolean crashed;

    private Timer timer;

    public Controller(){
        this.paused = false;
        this.crashed = false;
    }

    //Starts game in a paused state
    public void initialize(){
        this.setUpAnimationTimer();
        this.pauseGame();
    }

    //Sets timer for game animation
    private void setUpAnimationTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                    }
                });
            }
        };

        final long startTimeInMilliseconds = 0;
        final long repetitionPeriodInMilliseconds = 100;
        long frameTimeInMilliseconds = (long)(1000.0 / framesPerSecond);
        this.timer = new java.util.Timer();
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    //Updates Lightcycle route and location
    private void updateAnimation() {
        // Calls grid to update jet wall placement
        this.playerOne.addCurrentPosition(this.playerOne.getLayoutX(), this.playerOne.getLayoutY());
        Rectangle jetWallPiece1 = this.playerOne.getCurrentPosition();

        this.theGrid.drawJetWall(Color.RED, jetWallPiece1);

        this.playerOne.step();


    }

    //Controls lightcycle movement
    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        if (!this.paused) {
            if (code == KeyCode.A) {
                if (this.playerOne.getVelocityX() != 5) {
                    this.playerOne.setVelocityX(-5);
                    this.playerOne.setVelocityY(0);
                    this.playerOne.setRotate(90);
                    this.playerOne.setDirection(4);
                }
                keyEvent.consume();
            } else if (code == KeyCode.D) {
                if (this.playerOne.getVelocityX() != -5) {
                    this.playerOne.setVelocityX(5);
                    this.playerOne.setVelocityY(0);
                    this.playerOne.setRotate(90);
                    this.playerOne.setDirection(2);
                }
                keyEvent.consume();
            } else if (code == KeyCode.W) {
                if (this.playerOne.getVelocityY() != 5) {
                    this.playerOne.setVelocityX(0);
                    this.playerOne.setVelocityY(-5);
                    this.playerOne.setRotate(0);
                    this.playerOne.setDirection(1);
                }
                keyEvent.consume();
            } else if (code == KeyCode.S) {
                if (this.playerOne.getVelocityY() != -5) {
                    this.playerOne.setVelocityX(0);
                    this.playerOne.setVelocityY(5);
                    this.playerOne.setRotate(0);
                    this.playerOne.setDirection(3);
                }
                keyEvent.consume();
            } else if (code == KeyCode.SPACE) {
                if (!this.crashed) {
                    this.pauseGame();
                } else{
                    this.playButton.disarm();
                }
                keyEvent.consume();
            }
        }
    }

    //Pauses and resumes game
    private void pauseGame(){
        if (this.paused) {
            this.setUpAnimationTimer();
            this.playButton.setText("Pause");
        } else {
            this.timer.cancel();
            this.playButton.setText("Resume");
        }
        this.paused = !this.paused;
    }

    //Play Button
    public void onPlayButton(ActionEvent actionevent) {
        if (this.crashed) {
            this.theGrid.resetBoard();

            this.playerOne.setLayoutX(300);
            this.playerOne.setLayoutY(465);
            this.playerOne.setVelocityX(0);
            this.playerOne.setVelocityY(-5);
            this.playerOne.setDirection(1);
            this.playerOne.clearAllPositions();


            this.playButton.setText("Resume");
            this.crashed = false;
            this.paused = true;
        } else {
            this.pauseGame();
        }
        this.playButton.isFocused();
        actionevent.consume();
    }

    //Resets the game
    public void onResetButton(ActionEvent actionevent) {
        this.paused = false;
        this.pauseGame();
        this.theGrid.resetBoard();

        this.playerOne.setLayoutX(300);
        this.playerOne.setLayoutY(465);
        this.playerOne.setVelocityX(0);
        this.playerOne.setVelocityY(-5);
        this.playerOne.setDirection(1);
        this.playerOne.clearAllPositions();



        if (this.crashed) {
            this.playButton.setVisible(true);
        }
        this.crashed = false;
        this.playButton.requestFocus();
        actionevent.consume();
    }

    //Quits the game and application
    public void onQuit(ActionEvent actionEvent){
        Platform.exit();
        System.exit(0);
    }
}
