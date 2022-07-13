import javax.swing.*;
import java.awt.*;

public class BetterButton extends JButton {
    public int x;
    public int y;
    public boolean flagged;

    Color color1 = new Color(255, 203, 171);
    Color color2 = new Color(240, 203, 171);

    Color color3 = new Color(170, 215, 81);
    Color color4 = new Color(162, 209, 73);

    Color color5 = new Color(227, 23, 23);
    Color color6 = new Color(237, 24, 24);

    Color background;
    Color backgroundHidden;
    Color bombColor;

    public BetterButton(int x, int y){
        super();
        this.x = x;
        this.y = y;
        this.flagged = false;

        if (x%2 == 0){
            if (y%2 == 0){
                background = color1;
                backgroundHidden = color3;
                bombColor = color5;
            }else{
                background = color2;
                backgroundHidden = color4;
                bombColor = color6;
            }
        }else if(x%2 != 0){
            if (y%2 == 0){
                background = color2;
                backgroundHidden = color4;
                bombColor = color6;
            }else{
                background = color1;
                backgroundHidden = color3;
                bombColor = color5;
            }
        }

        this.setBackground(backgroundHidden);
    }

    public void makeHidden(){
        this.setBackground(backgroundHidden);
    }

    public boolean flag(){
        if (this.isEnabled()) {
            if (flagged) {
                this.setText("");
            }else{
                this.setText("■");
            }
            flagged = !flagged;
            return true;
        }
        return false;
    }
    public void setHidden(){
        this.setEnabled(false);
        this.setBackground(background);
    }

    public void setHidden(String value){
        if (value.equals("*")){
            this.setEnabled(false);
            this.setBackground(Color.RED);
        } else if (value.equals(" ")){
            this.setEnabled(false);
            this.setBackground(background);
        } else{
            this.setText(value);
            this.setEnabled(false);
            this.setBackground(background);
        }
    }

    public void bomb(){
        this.setBackground(bombColor);
        this.setEnabled(false);
    }
}
