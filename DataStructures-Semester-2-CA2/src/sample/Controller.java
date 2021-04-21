package sample;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller {

    public Image blankMapImage;

    public ImageView originalImageView;
    public ImageView blackWhiteImageView;
    public Button chooseFileButton;
    public Button blackWhiteButton;


    public void displayFile(ActionEvent actionEvent) {
        Stage stage = new Stage();
        blankMapImage = new Image("WaterfordMap.jpg", 700, 400, true,false);
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

    public void displayBlackWhiteImage(ActionEvent actionEvent){
        blackWhiteImageView.setImage(blackWhiteConversion(blankMapImage));
    }
}

