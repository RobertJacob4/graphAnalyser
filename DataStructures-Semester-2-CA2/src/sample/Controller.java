package sample;

import java.io.*;
import java.util.*;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class Controller {


    public Boolean startPoint;
    public int[] graphArray;
    public GraphNodeAL start, dest;
    public double xCoord, yCoord, xCoordStart, yCoordStart, xCoordEnd, yCoordEnd;
    public ArrayList<Integer> breadthFirstSearchList;

    public WritableImage blackAndWhiteImage;

    public Button dijkstrasStart;
    public ComboBox selectStartPointCombo;
    public RadioButton usePointerStartButton;
    public ComboBox selectEndPointCombo;
    public RadioButton usePointerDestButton;
    public RadioButton toggleLabelsButton;
    public Button clearSelectionButton;
    public ComboBox dijkstrasButton;
    public Button bfsButton;
    public TextField landmarkNameField;
    public RadioButton usePointerLandmark;
    public TextField landmarkCoordFieldX;
    public TextField landmarkCoordFieldY;
    public Button addLandmarkButton;
    public Button deleteLandmarkButton;
    public RadioButton easyRouteButton;
    public RadioButton historicalRouteButton;
    public Button displayCostButton;
    public Button addCostButton;
    public ImageView mainImageView;
    public ComboBox selectDestCost;
    public ComboBox deleteLandmarkCombo;
    public Pane imagePane;
    public Pane labelPane;
    public Pane landmarkPane;

    public ComboBox selectStartAddCost;
    public TextField currentCost;
    public TextField pathCostTextField;
    public ComboBox searchModifier;

    public RadioButton chooseStartButton;
    public RadioButton chooseDestButton;
    public TextArea distanceTextArea;


    public void populateComboBox() {
        selectEndPointCombo.getItems().clear();
        selectStartPointCombo.getItems().clear();
        // selectDestCost.getItems().clear();
        selectStartPointCombo.getSelectionModel().clearSelection();
        deleteLandmarkCombo.getItems().clear();

        // selectStartAddCost.getItems().clear();

        selectStartPointCombo.getItems().addAll(Utilities.graphlist);
        selectEndPointCombo.getItems().addAll(Utilities.graphlist);
        // selectDestCost.getItems().addAll(Utilities.graphlist);
        // selectStartAddCost.getItems().addAll(Utilities.graphlist);
        deleteLandmarkCombo.getItems().addAll(Utilities.graphlist);

    }


    public void colorPath(ArrayList<Integer> arrayList, int index) {
        Paint paint;
        if (index == 0) {
            paint = Color.RED;
        } else if (index == 1) {
            paint = Color.BLUE;
        } else {
            paint = Color.PURPLE;
        }
        for (int path : arrayList) {

            double x = path % mainImageView.getImage().getWidth();
            double y = path / mainImageView.getImage().getWidth() + 1;

            Circle circle = new Circle();
            circle.setLayoutX(x);
            circle.setLayoutY(y);
            circle.setRadius(1);
            circle.setFill(paint);
            imagePane.getChildren().add(circle);
            arrayList = new ArrayList<>();
        }
    }


    public void createLabels() {

        if (toggleLabelsButton.isSelected()) {
            for (Object node : Utilities.graphlist) {
                Label landmarkName = new Label();
                landmarkName.setText(((Landmark) (((GraphNodeAL) node).data)).landmarkName);
                landmarkName.setLayoutX(((Landmark) (((GraphNodeAL) node).data)).getX());
                landmarkName.setLayoutY(((Landmark) (((GraphNodeAL) node).data)).getY());
                labelPane.getChildren().add(landmarkName);
            }
        } else {
            labelPane.getChildren().clear();
        }
    }


    public void displayLabels() {
        toggleLabelsButton.setOnAction(e -> {
            createLabels();

        });
    }


    public void deleteLandmark() {

        GraphNodeAL gnToDelete = (GraphNodeAL) deleteLandmarkCombo.getSelectionModel().getSelectedItem();
        Utilities.graphlist.remove(gnToDelete);
        updateLandmark();
        populateComboBox();
        Utilities.save();
    }


    public WritableImage setBlackWhite(Image blankImage) {

        int width = (int) blankImage.getWidth();
        int height = (int) blankImage.getHeight();
        PixelReader pixelReader = blankImage.getPixelReader();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int c = 0; c < height; c++) {
            for (int r = 0; r < width; r++) {

                Color color = pixelReader.getColor(r, c);
                double red = color.getRed() * 255;
                double green = color.getGreen() * 255;
                double blue = color.getBlue() * 255;

                if (red > 205 && blue > 165 && green > 225) {
                    pixelWriter.setColor(r, c, Color.WHITE);
                } else {
                    pixelWriter.setColor(r, c, Color.BLACK);
                }
            }
        }
        return writableImage;
    }


    public void setDijkstrasBtn() {

        ObservableList dijkstrasOptions = FXCollections.observableArrayList();
        dijkstrasOptions.add("Classic");
        dijkstrasOptions.add("Historical");
        dijkstrasOptions.add("Easiest");
        dijkstrasButton.getItems().addAll(dijkstrasOptions);

    }


    public void findRouteDijkstras() {


    }


    public void setGraphArray() {
        graphArray = createGraphArray(blackAndWhiteImage);
    }

    // this method selects the params for which to use with bfs and calls bfs
    public void breadthFirstSearch() {

        int width = (int) mainImageView.getFitWidth();

        if (usePointerStartButton.isSelected()) {
            start = new GraphNodeAL(xCoordStart, yCoordStart);
            start.x = xCoordStart;
            start.y = yCoordStart;
        } else
            start = (GraphNodeAL) selectStartPointCombo.getSelectionModel().getSelectedItem();
        if (usePointerDestButton.isSelected()) {
            dest = new GraphNodeAL(xCoordEnd, yCoordEnd);
            dest.x = xCoordEnd;
            dest.y = yCoordEnd;
        } else
            dest = (GraphNodeAL) selectEndPointCombo.getSelectionModel().getSelectedItem();
        int[] graphArr = createGraphArray(blackAndWhiteImage);
        breadthFirstSearchList = BreadthFirstSearch.breadthFirstSearch(start, dest, width, graphArr, distanceTextArea);
        colorPath(breadthFirstSearchList, 0);

    }


    public int[] createGraphArray(WritableImage image) {

        double width = image.getWidth();
        double height = image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        int[] graphArr = new int[(int) width * (int) height];

        for (int c = 0; c < height; c++) {
            for (int r = 0; r < width; r++) {

                int i = (c * (int) width + r);
                Color color = pixelReader.getColor(r, c);
                if (color.equals(Color.WHITE)) {
                    graphArr[i] = 0;
                } else {
                    graphArr[i] = -1;
                }
            }
        }
        return graphArr;
    }

    public void drawPoints(double x, double y, int selection) {

        if (selection == 1) {
            Circle startPoint = new Circle();
            startPoint.setLayoutX(x);
            startPoint.setLayoutY(y);
            startPoint.setRadius(4);
            startPoint.setFill(Color.GREEN);
            imagePane.getChildren().add(startPoint);
        }

        if (selection == 2) {
            Circle endPoint = new Circle();
            endPoint.setLayoutX(x);
            endPoint.setLayoutY(y);
            endPoint.setRadius(4);
            endPoint.setFill(Color.RED);
            imagePane.getChildren().add(endPoint);
        }

        if (selection == 3) {
            Circle landmarkPoint = new Circle();
            landmarkPoint.setLayoutX(x);
            landmarkPoint.setLayoutY(y);
            landmarkPoint.setRadius(4);
            landmarkPoint.setFill(Color.PURPLE);
            imagePane.getChildren().add(landmarkPoint);
        }
    }


    public void selectWaypoint() {
        int selectStart = 1;
        int selectEnd = 2;
        int selectLandmark = 3;

        landmarkPane.setOnMouseClicked(e -> {
            if (chooseStartButton.isSelected()) {
                xCoordStart = e.getX();
                yCoordStart = e.getY();
                drawPoints(xCoordStart, yCoordStart, selectStart);
                chooseStartButton.setSelected(false);
            } else if (chooseDestButton.isSelected()) {
                xCoordEnd = e.getX();
                yCoordEnd = e.getY();
                drawPoints(xCoordEnd, yCoordEnd, selectEnd);
                chooseDestButton.setSelected(false);
            } else {
                xCoord = e.getX();
                yCoord = e.getY();
                drawPoints(xCoord, yCoord, selectLandmark);
            }
        });
    }

    public void addLandmarkToDatabase() {

        double x, y;
        if (usePointerLandmark.isSelected()) {
            x = xCoord;
            y = yCoord;
        } else {
            x = Double.parseDouble(landmarkCoordFieldX.getText());
            y = Double.parseDouble(landmarkCoordFieldY.getText());
        }
        GraphNodeAL gn = new GraphNodeAL(new Landmark(x, y, landmarkNameField.getText()));
        Utilities.graphlist.add(gn);
        Utilities.save();
        updateLandmark();
        populateComboBox();
        landmarkNameField.setPromptText("Landmark Name");
        usePointerLandmark.setSelected(false);

    }


    public void updateLandmark() {

        List<Label> labelList = new ArrayList<>();
        imagePane.getChildren().clear();
        landmarkPane.getChildren().clear();

        for (Node y : ((AnchorPane) landmarkPane.getParent()).getChildren()) {
            if (y instanceof javafx.scene.control.Label)
                labelList.add((javafx.scene.control.Label) y);
        }
        ((Pane) landmarkPane.getParent()).getChildren().removeAll(labelList);


        for (Object node : Utilities.graphlist) {

            Circle landmark = new Circle();
            landmark.setLayoutX(((Landmark) ((GraphNodeAL) node).data).x);
            landmark.setLayoutY(((Landmark) ((GraphNodeAL) node).data).y);
            landmark.setRadius(6);
            landmark.setFill(Color.ORANGE);

            Label landmarkLabel = new Label();
            landmarkLabel.setLayoutX(((Landmark) ((GraphNodeAL) node).data).x);
            landmarkLabel.setLayoutY(((Landmark) ((GraphNodeAL) node).data).y);
            landmarkLabel.setText(((Landmark) ((GraphNodeAL) node).data).landmarkName);

            landmarkPane.getChildren().add(landmark);
            labelPane.getChildren().add(landmarkLabel);

            selectStartPointCombo.getItems().add(node);
            selectEndPointCombo.getItems().add(node);
            deleteLandmarkCombo.getItems().add(node);


        }


    }


    public void clearBoxes() {

        imagePane.getChildren().clear();
        landmarkPane.getChildren().clear();
        selectStartPointCombo.getItems().clear();
        selectEndPointCombo.getItems().clear();
        deleteLandmarkCombo.getItems().clear();
        selectStartAddCost.getItems().clear();
        selectDestCost.getItems().clear();

    }


    public void setClearSelection() {
        imagePane.getChildren().clear();
        Utilities.waypoints.clear();
        Utilities.avoids.clear();
        updateLandmark();
        populateComboBox();

    }


    public void quit() {
        Platform.exit();
    }

    public void initialize() {

        Utilities.createLandmarkList();
        File file = new File("resource\\WaterfordMap.jpg");

        Image im = new Image(String.valueOf(file.toURI()), 900, 742, false, false);
        blackAndWhiteImage = setBlackWhite(im);
        mainImageView.setImage(im);
        selectWaypoint();
        Utilities.createGraphList();
        try {
            Utilities.load();
        } catch (Exception e) {
            System.out.println("Load Failed");
        }
        updateLandmark();
        setGraphArray();
        startPoint = true;
        setDijkstrasBtn();
        createLabels();
        displayLabels();
    }
}
