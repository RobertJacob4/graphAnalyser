package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.RadioButton;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Image blankMapImage;
    public double xCoord, yCoord;
    public double xCoordStart, yCoordStart, xCoordEnd, yCoordEnd;

    @FXML
    public ImageView originalImageView;
    public ImageView blackWhiteImageView;
    public Button chooseFileButton;
    public Button blackWhiteButton;
    public RadioButton startButton;
    public RadioButton endButton;
    public RadioButton landmarkButton;
    public AnchorPane anchorPane1;


    public void displayFile(ActionEvent actionEvent) {
        Stage stage = new Stage();
        blankMapImage = new Image("WaterfordMap.jpg", 700, 400, true, false);
        originalImageView.setImage(blankMapImage);
    }

    public WritableImage blackWhiteConversion(Image blankImage) {
        int width = (int) blankImage.getWidth();
        int height = (int) blankImage.getHeight();
        PixelReader pr = blankImage.getPixelReader();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pw = writableImage.getPixelWriter();

        for (int c = 0; c < height; c++) {
            for (int r = 0; r < width; r++) {
                Color color = pr.getColor(r, c);

                double red = color.getRed() * 255;
                double green = color.getGreen() * 255;
                double blue = color.getBlue() * 255;

                if (red > 205 && blue > 160 && green > 225) {
                    pw.setColor(r, c, Color.WHITE);
                } else {
                    pw.setColor(r, c, Color.BLACK);
                }
            }
        }
        return writableImage;
    }

    public void displayBlackWhiteImage(ActionEvent actionEvent) {
        blackWhiteImageView.setImage(blackWhiteConversion(blankMapImage));
    }

    public void markWaypoint() {

        int selectStart;
        int selectEnd;
        int selectLandmark;

        originalImageView.setOnMouseClicked(e -> {
            if (startButton.isSelected()) {
                xCoordStart = e.getX();
                yCoordStart = e.getY();
                System.out.print(xCoordStart);
                System.out.print(yCoordStart);

                Circle startMarker = new Circle( e.getX(),e.getY(),3,Color.GREEN);
                startMarker.setId("Start");
                startButton.setSelected(false);

                startMarker.setTranslateX(originalImageView.getLayoutX());
                startMarker.setTranslateY(originalImageView.getLayoutY());
                ((AnchorPane) originalImageView.getParent()).getChildren().add(startMarker);



            }else if (endButton.isSelected()) {
                xCoordEnd = e.getX();
                yCoordEnd = e.getY();
                System.out.print(xCoordEnd);
                System.out.print(yCoordEnd);


                Circle  endMarker = new Circle( e.getX(),e.getY(),3,Color.RED);
                endMarker.setId("Destination");
                endButton.setSelected(false);

                endMarker.setTranslateX(originalImageView.getLayoutX());
                endMarker.setTranslateY(originalImageView.getLayoutY());
                ((AnchorPane) originalImageView.getParent()).getChildren().add(endMarker);

            }
            else if (landmarkButton.isSelected()) {
                xCoordStart = e.getX();
                yCoordStart = e.getY();
                System.out.print(xCoordStart);
                System.out.print(yCoordStart);


                Circle  landMarker = new Circle( e.getX(),e.getY(),3,Color.BLUE);
                landMarker.setId("Landmark");
                landmarkButton.setSelected(false);

                landMarker.setTranslateX(originalImageView.getLayoutX());
                landMarker.setTranslateY(originalImageView.getLayoutY());
                ((AnchorPane) originalImageView.getParent()).getChildren().add(landMarker);
            }
        });
    }

    public void setGraphArray(){
        int[] graphArray;
        graphArray = makeGraphArray(blackWhiteConversion(blankMapImage));
    }

    public int[] makeGraphArray(WritableImage image){

        double width = image.getWidth(), height = image.getHeight();
        PixelReader pr = image.getPixelReader();
        PixelWriter pw = image.getPixelWriter();

        int[] graphArray = new int[(int) (width * height)];

        for(int c = 0; c < height; c++ ){
            for(int r = 0; r < width; r++){

                int current = (int) (c * width + r);
                Color color = pr.getColor(c, r);
                if(color == Color.WHITE){
                    graphArray[current] = 0;
                }else{
                    graphArray[current] = -1;
                }
            }
        }
        return graphArray;
    }



    public void clearPoints(ActionEvent actionEvent) {

        List<Circle> circleList = new ArrayList<>();
        for (Node x : ((AnchorPane) originalImageView.getParent()).getChildren()) {
            if (x instanceof Circle)
                circleList.add((Circle) x);
        }
        ((Pane) originalImageView.getParent()).getChildren().removeAll(circleList);
    }

    /**
     * Method exits the program.
     */
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void saveGraph() throws Exception {
        Utilities.save();
    }

    public void loadGraph() throws Exception {
        Utilities.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}


