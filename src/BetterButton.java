import javax.swing.*;
import java.awt.*;

public class BetterButton extends JButton {
    public int x;
    public int y;

    Color color1 = new Color(255, 203, 171);

    public BetterButton(int x, int y){
        super();
        this.x = x;
        this.y = y;
    }

    public void setHidden(){
        this.setEnabled(false);
        this.setBackground(color1);
    }

    public void setHidden(String value){
        if (value.equals("*")){
            this.setEnabled(false);
            this.setBackground(Color.RED);
        } else if (value.equals(" ")){
            this.setEnabled(false);
            this.setBackground(color1);
        } else{
            this.setText(value);
            this.setEnabled(false);
            this.setBackground(color1);
        }
    }

    public void bomb(){
        this.setBackground(Color.RED);
        this.setEnabled(false);
    }
}
