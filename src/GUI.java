import java.awt.*;
import javax.swing.*;

public class GUI {
    public static void main(String args[]){
        int sizeX = 20;
        int sizeY = 10;
        int buttonSize = 40;
        int fontSize = 11;
        int border = 1;

        GridLayout layout = new GridLayout(sizeY,sizeX, border, border);
        JFrame frame = new JFrame("My First GUI");
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(((sizeX * buttonSize) + (sizeX * border)),((sizeY * buttonSize) + (sizeY * border)));
        frame.setBackground(new Color(188,168,159));

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                JButton tempButton = new JButton(j+","+i);
                tempButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
                tempButton.setBackground(Color.GREEN);
                tempButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
                tempButton.setMargin(new Insets(1, 1, 1, 1) );
                frame.getContentPane().add(tempButton); // Adds Button to content pane of frame
            }
        }

        frame.setVisible(true);
    }
}
