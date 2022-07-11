import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printBoard(Cell[][] board, int boardSizeX, int boardSizeY){
        for (int i = 0; i < boardSizeY; i++) {
            System.out.print((boardSizeY - 1 - i) + "\t|");
            for (int j = 0; j < boardSizeX; j++) {
                System.out.print(" " + board[i][j].getValue() + " ");
            }
            System.out.println();
        }

        System.out.print("\t-");
        for (int i = 0; i < boardSizeX; i++) {
            System.out.print("---");
        }
        System.out.println();

        System.out.print("\t ");
        for (int i = 0; i < boardSizeX; i++) {
            if (i > 9){
                System.out.print(" " + i);
            }else {
                System.out.print(" " + i + " ");
            }
        }
        System.out.println();
    }

    public static void printBlankBoard(int boardSizeX, int boardSizeY){
        for (int i = 0; i < boardSizeY; i++) {
            System.out.print((boardSizeY - 1 - i) + "\t|");
            for (int j = 0; j < boardSizeX; j++) {
                System.out.print(" ? ");
            }
            System.out.println();
        }

        System.out.print("\t-");
        for (int i = 0; i < boardSizeX; i++) {
            System.out.print("---");
        }
        System.out.println();

        System.out.print("\t ");
        for (int i = 0; i < boardSizeX; i++) {
            if (i > 9){
                System.out.print(" " + i);
            }else {
                System.out.print(" " + i + " ");
            }
        }
        System.out.println();
    }

    public static int[] getCoordinates(int limitX, int limitY){
        boolean valid = false;
        int[] output = {0,0};
        Scanner scanner = new Scanner(System.in);

        while (!valid) {
            System.out.print(">>>");
            String[] input = scanner.nextLine().split(",");
            if (input.length == 2){
                try{
                    output[0] = Integer.parseInt(input[0]);
                    output[1] = limitY - 1 - Integer.parseInt(input[1]);
                    if (output[0] < limitX && output[1] < limitY) {
                        valid = true;
                        return output;
                    }
                }catch (Exception e){
                    System.out.println("That wasn't the correct format");
                }
            } else{
                System.out.println("Enter two numbers");
            }
        }
        return output;

    }

    public static Cell[][] revealArea(Cell[][] board, int boardSizeX, int boardSizeY, int[] userInputs){
        int[] temp = {userInputs[0], userInputs[1]};
        boolean areaCleared = false;


        int x = userInputs[0];
        int y = userInputs[1];

        if (x < boardSizeX - 1){
            if (board[y][x + 1].getRawValue() == 0 && board[y][x + 1].getHidden()){
                board[y][x + 1].setHidden();
                temp[0] = x + 1;
                temp[1] = y;
                board = revealArea(board, boardSizeX, boardSizeY, temp);
            }else if (!board[y][x + 1].getBomb() && board[y][x + 1].getHidden()){
                board[y][x + 1].setHidden();
            }
        }

        if (x > 0){
            if (board[y][x - 1].getRawValue() == 0 && board[y][x - 1].getHidden()){
                board[y][x - 1].setHidden();
                temp[0] = x - 1;
                temp[1] = y;
                board = revealArea(board, boardSizeX, boardSizeY, temp);
            }else if (!board[y][x - 1].getBomb() && board[y][x - 1].getHidden()){
                board[y][x - 1].setHidden();
            }
        }

        if (y < boardSizeY - 1){
            if (board[y + 1][x].getRawValue() == 0 && board[y + 1][x].getHidden()){
                board[y + 1][x].setHidden();
                temp[0] = x;
                temp[1] = y + 1;
                board = revealArea(board, boardSizeX, boardSizeY, temp);
            }else if (!board[y + 1][x].getBomb() && board[y + 1][x].getHidden()){
                board[y + 1][x].setHidden();
            }
        }

        if (y > 0){
            if (board[y - 1][x].getRawValue() == 0 && board[y - 1][x].getHidden()){
                board[y - 1][x].setHidden();
                temp[0] = x;
                temp[1] = y - 1;
                board = revealArea(board, boardSizeX, boardSizeY, temp);
            }else if (!board[y - 1][x].getBomb() && board[y - 1][x].getHidden()){
                board[y - 1][x].setHidden();
            }
        }

        return board;
    }

    public static void main(String args[])
    {

        boolean gameFinished = false;
        int[] userInputs = new int[2];
        int boardSizeX = 30;
        int boardSizeY = 16;
        int numBombs = 60;
        Cell[][] board = new Cell[boardSizeY][boardSizeX];
        Random rand = new Random();

        //Fill array
        for (int i = 0; i < boardSizeY; i++){
            for (int j = 0; j < boardSizeX; j++){
                board[i][j] = new Cell();
            }
        }

        printBlankBoard(boardSizeX, boardSizeY);

        //Make initial choice
        userInputs = getCoordinates(boardSizeX, boardSizeY);
        board[userInputs[1]][userInputs[0]].setHidden();

        //Create bombs
        for (int i = 0; i < numBombs; i++){
            int x = rand.nextInt(boardSizeX - 1);
            int y = rand.nextInt(boardSizeY - 1);

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
        for (int i = 0; i < boardSizeY; i++) {
            for (int j = 0; j < boardSizeX; j++) {

                for (int k = -1; k < 2; k++){
                    int y = i + k;

                    if (y < 0){continue;}
                    if (y >= boardSizeY){continue;}

                    for (int l = -1; l < 2; l++){
                        int x = j + l;

                        if (x < 0){continue;}
                        if (x >= boardSizeX){continue;}

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
        revealArea(board, boardSizeX, boardSizeY, userInputs);


        while (!gameFinished) {
            //Print board
            printBoard(board, boardSizeX, boardSizeY);

            //Get user input
            userInputs = getCoordinates(boardSizeX, boardSizeY);
            if (!board[userInputs[1]][userInputs[0]].getBomb()){
                board[userInputs[1]][userInputs[0]].setHidden();
                revealArea(board, boardSizeX, boardSizeY, userInputs);
            }else{
                printDeath();
                gameFinished = true;
            }

        }
    }

    public static void printDeath(){
        String death = "                             __xxxxxxxxxxxxxxxx___.\n" +
                "                        _gxXXXXXXXXXXXXXXXXXXXXXXXX!x_\n" +
                "                   __x!XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!x_\n" +
                "                ,gXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx_\n" +
                "              ,gXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!_\n" +
                "            _!XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!.\n" +
                "          gXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXs\n" +
                "        ,!XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!.\n" +
                "       g!XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "      iXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "     ,XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx\n" +
                "     !XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx\n" +
                "   ,XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx\n" +
                "   !XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXi\n" +
                "  dXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "  !XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "   XXXXXXXXXXXXXXXXXXXf~~~VXXXXXXXXXXXXXXXXXXXXXXXXXXvvvvvvvvXXXXXXXXXXXXXX!\n" +
                "   !XXXXXXXXXXXXXXXf`       'XXXXXXXXXXXXXXXXXXXXXf`          '~XXXXXXXXXXP\n" +
                "    vXXXXXXXXXXXX!            !XXXXXXXXXXXXXXXXXX!              !XXXXXXXXX\n" +
                "     XXXXXXXXXXv`              'VXXXXXXXXXXXXXXX                !XXXXXXXX!\n" +
                "     !XXXXXXXXX.                 YXXXXXXXXXXXXX!                XXXXXXXXX\n" +
                "      XXXXXXXXX!                 ,XXXXXXXXXXXXXX                VXXXXXXX!\n" +
                "      'XXXXXXXX!                ,!XXXX ~~XXXXXXX               iXXXXXX~\n" +
                "       'XXXXXXXX               ,XXXXXX   XXXXXXXX!             xXXXXXX!\n" +
                "        !XXXXXXX!xxxxxxs______xXXXXXXX   'YXXXXXX!          ,xXXXXXXXX\n" +
                "         YXXXXXXXXXXXXXXXXXXXXXXXXXXX`    VXXXXXXX!s. __gxx!XXXXXXXXXP\n" +
                "          XXXXXXXXXXXXXXXXXXXXXXXXXX!      'XXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "          XXXXXXXXXXXXXXXXXXXXXXXXXP        'YXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "          XXXXXXXXXXXXXXXXXXXXXXXX!     i    !XXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "          XXXXXXXXXXXXXXXXXXXXXXXX!     XX   !XXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "          XXXXXXXXXXXXXXXXXXXXXXXXx_   iXX_,_dXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "          XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXP\n" +
                "          XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "           ~vXvvvvXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXf\n" +
                "                    'VXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXvvvvvv~\n" +
                "                      'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX~\n" +
                "                  _    XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXv`\n" +
                "                 -XX!  !XXXXXXX~XXXXXXXXXXXXXXXXXXXXXX~   Xxi\n" +
                "                  YXX  '~ XXXXX XXXXXXXXXXXXXXXXXXXX`     iXX`\n" +
                "                  !XX!    !XXX` XXXXXXXXXXXXXXXXXXXX      !XX\n" +
                "                  !XXX    '~Vf  YXXXXXXXXXXXXXP YXXX     !XXX\n" +
                "                  !XXX  ,_      !XXP YXXXfXXXX!  XXX     XXXV\n" +
                "                  !XXX !XX           'XXP 'YXX!       ,.!XXX!\n" +
                "                  !XXXi!XP  XX.                  ,_  !XXXXXX!\n" +
                "                  iXXXx X!  XX! !Xx.  ,.     xs.,XXi !XXXXXXf\n" +
                "                   XXXXXXXXXXXXXXXXX! _!XXx  dXXXXXXX.iXXXXXX\n" +
                "                   VXXXXXXXXXXXXXXXXXXXXXXXxxXXXXXXXXXXXXXXX!\n" +
                "                   YXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXV\n" +
                "                    'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX!\n" +
                "                    'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXf\n" +
                "                       VXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXf\n" +
                "                         VXXXXXXXXXXXXXXXXXXXXXXXXXXXXv`\n" +
                "                          ~vXXXXXXXXXXXXXXXXXXXXXXXf`\n" +
                "                              ~vXXXXXXXXXXXXXXXXv~\n" +
                "                                 '~VvXXXXXXXV~~\n" +
                "                                       ~~";

        System.out.println(death);
    }
}
