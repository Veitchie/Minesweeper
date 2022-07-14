import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        run();
    }
    public static void printBoard(Cell[][] board, int sizeX, int sizeY){
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

    public static void printBlankBoard(int sizeX, int sizeY){
        for (int i = 0; i < sizeY; i++) {
            System.out.print((sizeY - 1 - i) + "\t|");
            for (int j = 0; j < sizeX; j++) {
                System.out.print(" ? ");
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

    public static Cell[][] revealArea(Cell[][] board, int sizeX, int sizeY, int[] userInputs){
        int[] temp = {userInputs[0], userInputs[1]};


        int x = userInputs[0];
        int y = userInputs[1];

        if (x < sizeX - 1){
            if (board[y][x + 1].getRawValue() == 0 && board[y][x + 1].getHidden()){
                board[y][x + 1].setHidden();
                temp[0] = x + 1;
                board = revealArea(board, sizeX, sizeY, temp);
            }else if (!board[y][x + 1].getBomb() && board[y][x + 1].getHidden()){
                board[y][x + 1].setHidden();
            }
        }

        if (x > 0){
            if (board[y][x - 1].getRawValue() == 0 && board[y][x - 1].getHidden()){
                board[y][x - 1].setHidden();
                temp[0] = x - 1;
                temp[1] = y;
                board = revealArea(board, sizeX, sizeY, temp);
            }else if (!board[y][x - 1].getBomb() && board[y][x - 1].getHidden()){
                board[y][x - 1].setHidden();
            }
        }

        if (y < sizeY - 1){
            if (board[y + 1][x].getRawValue() == 0 && board[y + 1][x].getHidden()){
                board[y + 1][x].setHidden();
                temp[0] = x;
                temp[1] = y + 1;
                board = revealArea(board, sizeX, sizeY, temp);
            }else if (!board[y + 1][x].getBomb() && board[y + 1][x].getHidden()){
                board[y + 1][x].setHidden();
            }
        }

        if (y > 0){
            if (board[y - 1][x].getRawValue() == 0 && board[y - 1][x].getHidden()){
                board[y - 1][x].setHidden();
                temp[0] = x;
                temp[1] = y - 1;
                board = revealArea(board, sizeX, sizeY, temp);
            }else if (!board[y - 1][x].getBomb() && board[y - 1][x].getHidden()){
                board[y - 1][x].setHidden();
            }
        }

        return board;
    }

    public static void run(){

        boolean gameFinished = false;
        int[] userInputs;
        int sizeX = 30;
        int sizeY = 16;
        int numBombs = 60;
        Cell[][] board = new Cell[sizeY][sizeX];
        Random rand = new Random();

        //Fill array
        for (int i = 0; i < sizeY; i++){
            for (int j = 0; j < sizeX; j++){
                board[i][j] = new Cell();
            }
        }

        printBlankBoard(sizeX, sizeY);

        //Make initial choice
        userInputs = getCoordinates(sizeX, sizeY);
        board[userInputs[1]][userInputs[0]].setHidden();

        //Create bombs
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
        revealArea(board, sizeX, sizeY, userInputs);


        while (!gameFinished) {
            //Print board
            printBoard(board, sizeX, sizeY);

            //Get user input
            userInputs = getCoordinates(sizeX, sizeY);
            if (!board[userInputs[1]][userInputs[0]].getBomb()){
                board[userInputs[1]][userInputs[0]].setHidden();
                if ( board[userInputs[1]][userInputs[0]].getRawValue() == 0 ){
                    revealArea(board, sizeX, sizeY, userInputs);
                }
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
