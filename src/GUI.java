import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class GUI extends JFrame implements MouseListener{

    public int sizeX = 16;
    public int sizeY = 16;
    public int numBombs = 32;
    public Cell[][] board = new Cell[sizeY][sizeX];
    public boolean firstMove = true;
    public boolean gameFinished = false;

    public int buttonSize = 40;
    public int fontSize = 11;
    public int border = 1;
    public BetterButton[][] buttons;
    public static void main(String args[]) {
        gui();
    }

    public static void gui(){

        GUI frame = new GUI("My First GUI");
        frame.setVisible(true);
    }

    public GUI(String title){
        super(title);
        frameInit();
        this.addMouseListener(this);

        GridLayout layout = new GridLayout(sizeY,sizeX, border, border);
        this.setLayout(layout);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(((sizeX * buttonSize) + (sizeX * border)),((sizeY * buttonSize) + (sizeY * border) + 10));
        this.setBackground(new Color(188,168,159));

        buttons = new BetterButton[sizeY][sizeX];

        for (int i = 0; i < sizeY; i++){
            for (int j = 0; j < sizeX; j++){
                buttons[i][j] = new BetterButton(j,i);
                buttons[i][j].setSize(buttonSize,buttonSize);
                buttons[i][j].setBackground(Color.green);
                buttons[i][j].setMargin(new Insets(1,1,1,1));
                buttons[i][j].addMouseListener(this);
                this.add(buttons[i][j]);

                board[i][j] = new Cell();
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof BetterButton){
            if (((BetterButton) e.getSource()).isEnabled()) {
                int[] userInputs = {((BetterButton) e.getSource()).x, ((BetterButton) e.getSource()).y};

                if (firstMove) {
                    firstMove = false;
                    board[userInputs[1]][userInputs[0]].setHidden();
                    buttons[userInputs[1]][userInputs[0]].setHidden();

                    //Create bombs
                    Random rand = new Random();
                    for (int i = 0; i < numBombs; i++){
                        int x = rand.nextInt(sizeX - 1);
                        int y = rand.nextInt(sizeY - 1);

                        if ( (x < userInputs[0] - 1 || x > userInputs[0] + 1) && (y < userInputs[1] - 1 || y > userInputs[1] + 1) ){
                            if (!board[y][x].getBomb()) {
                                board[y][x].setBomb();
                            } else{
                                i--;
                            }
                        }else{
                            i--;
                        }
                    }

                    //Calculate values for remaining cells
                    int count = 0;
                    for (int i = 0; i < sizeY; i++) {
                        for (int j = 0; j < sizeX; j++) {

                            for (int k = -1; k < 2; k++){
                                int y = i + k;

                                if (y < 0){continue;}
                                if (y >= sizeY){continue;}

                                for (int l = -1; l < 2; l++){
                                    int x = j + l;

                                    if (x < 0){continue;}
                                    if (x >= sizeX){continue;}

                                    if (board[y][x].getBomb()){
                                        count++;
                                    }
                                }
                            }

                            board[i][j].setValue(count);
                            count = 0;

                        }
                    }

                    //Reveal initial area
                    revealArea(userInputs);
                } else{
                    //Check if bomb
                    if (!board[userInputs[1]][userInputs[0]].getBomb()){
                        board[userInputs[1]][userInputs[0]].setHidden();
                        buttons[userInputs[1]][userInputs[0]].setHidden(String.valueOf(board[userInputs[1]][userInputs[0]].getRawValue()));
                        revealArea(userInputs);
                    }else{
                        //=================================================== printDeath();
                        gameFinished = true;
                        buttons[userInputs[1]][userInputs[0]].bomb();
                    }
                }
            }
        }
    }

    public Cell[][] revealArea(int[] userInputs){
        int[] temp = {userInputs[0], userInputs[1]};
        boolean areaCleared = false;


        int x = userInputs[0];
        int y = userInputs[1];

        if (x < sizeX - 1){
            if (board[y][x + 1].getRawValue() == 0 && board[y][x + 1].getHidden()){
                board[y][x + 1].setHidden();
                buttons[y][x + 1].setHidden();
                temp[0] = x + 1;
                temp[1] = y;
                revealArea(temp);
            }else if (!board[y][x + 1].getBomb() && board[y][x + 1].getHidden()){
                board[y][x + 1].setHidden();
                buttons[y][x + 1].setHidden(board[y][x + 1].getValue());
            }
        }

        if (x > 0){
            if (board[y][x - 1].getRawValue() == 0 && board[y][x - 1].getHidden()){
                board[y][x - 1].setHidden();
                buttons[y][x - 1].setHidden();
                temp[0] = x - 1;
                temp[1] = y;
                revealArea(temp);
            }else if (!board[y][x - 1].getBomb() && board[y][x - 1].getHidden()){
                board[y][x - 1].setHidden();
                buttons[y][x - 1].setHidden(board[y][x - 1].getValue());
            }
        }

        if (y < sizeY - 1){
            if (board[y + 1][x].getRawValue() == 0 && board[y + 1][x].getHidden()){
                board[y + 1][x].setHidden();
                buttons[y + 1][x].setHidden();
                temp[0] = x;
                temp[1] = y + 1;
                revealArea(temp);
            }else if (!board[y + 1][x].getBomb() && board[y + 1][x].getHidden()){
                board[y + 1][x].setHidden();
                buttons[y + 1][x].setHidden(board[y + 1][x].getValue());
            }
        }

        if (y > 0){
            if (board[y - 1][x].getRawValue() == 0 && board[y - 1][x].getHidden()){
                board[y - 1][x].setHidden();
                buttons[y - 1][x].setHidden();
                temp[0] = x;
                temp[1] = y - 1;
                revealArea(temp);
            }else if (!board[y - 1][x].getBomb() && board[y - 1][x].getHidden()){
                board[y - 1][x].setHidden();
                buttons[y - 1][x].setHidden(board[y - 1][x].getValue());
            }
        }

        return board;
    }

    public void printBoard(){
        for (int i = 0; i < sizeY; i++) {
            System.out.print((sizeY - 1 - i) + "\t|");
            for (int j = 0; j < sizeX; j++) {
                System.out.print(" " + board[i][j].getValue() + " ");
            }
            System.out.println();
        }

        System.out.print("\t-");
        for (int i = 0; i < sizeX; i++) {
            System.out.print("---");
        }
        System.out.println();

        System.out.print("\t ");
        for (int i = 0; i < sizeX; i++) {
            if (i > 9){
                System.out.print(" " + i);
            }else {
                System.out.print(" " + i + " ");
            }
        }
        System.out.println();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }


    public void run(){

        boolean gameFinished = false;
        int[] userInputs = new int[2];



        //Make initial choice
        //=================================================== userInputs = getCoordinates(sizeX, sizeY);
        board[userInputs[1]][userInputs[0]].setHidden();



        //Reveal initial area



        while (!gameFinished) {
            //Print board
            //=================================================== printBoard(board, sizeX, sizeY);

            //Get user input
            //=================================================== userInputs = getCoordinates(sizeX, sizeY);
            if (!board[userInputs[1]][userInputs[0]].getBomb()){
                board[userInputs[1]][userInputs[0]].setHidden();
                //=================================================== revealArea(board, sizeX, sizeY, userInputs);
            }else{
                //=================================================== printDeath();
                gameFinished = true;
            }

        }
    }

}
