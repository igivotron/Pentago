package quizz;

/**
 * A class that represents a game of Pentago.
 * <p>
 * The rules of the game are described in this short video
 * that we advise you to watch now.
 *   [FR] <a href="https://youtu.be/bpkpMxqDmjw">...</a>
 *   [EN] <a href="https://youtu.be/VX6-n1Wm5zI?si=QSLC2auuy7Q1DXFZ">...</a>
 * <p>
 * Pentago is a two-player game on a 6x6 grid board composed of four
 * 3x3 subparts that can be rotated left or right. The board looks
 * like this:
 * <p>
 * ------------------------------
 * |  x   x   x  |  x   x   x   |
 * |  x   x   x  |  x   x   x   |
 * |  x   x   x  |  x   x   x   |
 * ------------------------------
 * |  x   x   x  |  x   x   x   |
 * |  x   x   x  |  x   x   x   |
 * |  x   x   x  |  x   x   x   |
 * ------------------------------
 * <p>
 * with the four subparts being:
 * <p>
 * --------------------------------
 * |              |               |
 * |   TOP_LEFT   |   TOP_RIGHT   |
 * |              |               |
 * -------------------------------
 * |              |               |
 * | BOTTOM_LEFT  | BOTTOM_RIGHT  |
 * |              |               |
 * -------------------------------
 * <p> <p>
 * Two players A and B take turns. Each turn is composed of 2 successive actions:
 * 1) the player places one of his token on the board in a free position, then
 * 2) the player rotates by 90° or -90° one subpart of the board of his choice.
 * <p>
 * A player wins when he has 5 consecutive own tokens in a row, a
 * column, or a diagonal.
 * <p>
 * Your task is to model the game, by implementing:
 * 1) the main method "play()" that registers the move of the player, and
 * 2) the helper methods "rotateMatrix()", "checkWinPlayerVector()" and "checkWinPlayer()".
 * <p>
 * The 3 helper methods are supposed to help you build the code for
 * the "play()" method.  Therefore, you should implement the helper
 * methods first. The helper methods are graded separately.
 * <p>
 * We also provide the "isGridFilled()" helper method that can be
 * useful in your "play()" method.
 * <p>
 * Do not modify the enumerations, the method signatures, or the
 * instance variables. Even if this is not required to succeed this
 * quiz, you can possibly add new methods and import Java classes.
 * <p>
 * NOTE: If you have a question, you can ask it on the following Teams channel:
 * <a href="https://teams.microsoft.com/l/channel/19%3adaf32f5af520411ca9562ddbe76e51a7%40thread.tacv2/Quiz?groupId=0a375f0f-8e80-4903-8534-d6f40cc4b69d&tenantId=7ab090d4-fa2e-4ecf-bc7c-4127b4d582ec">...</a>
 * WARNING: Be careful not to copy your any part of your source code in this channel!
 */
public class Pentago {
    /**
     * An enum that represents the two players playing the game.
     */
    public enum Player {
        A,
        B
    }

    /**
     * An enum that represents the different outcomes of the game.
     * A_WINS is used when player A wins, B_WINS is used when player B wins, and
     * NO_WINNER is used when there is no winner yet, but also when
     * the board is full (which means that there is no winner) or when both
     * players win at the same time.
     */
    public enum Winner {
        A_WINS,
        B_WINS,
        NO_WINNER
    }

    /**
     * An enum that represents the four subparts of the board:
     * --------------------------------
     * |              |               |
     * |   TOP_LEFT   |   TOP_RIGHT   |
     * |              |               |
     * --------------------------------
     * |              |               |
     * | BOTTOM_LEFT  | BOTTOM_RIGHT  |
     * |              |               |
     * --------------------------------
     */
    public enum BoardSubpart {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    /**
     * An enum that represents the direction of the rotation applied on a subpart.
     */
    public enum RotationDirection {
        LEFT,
        RIGHT
    }

    Player[][] board;



    private static final int BOARD_SIZE = 6;
    private static final int SUBPART_SIZE = 3;

    public Pentago() {
        this.board = new Player[BOARD_SIZE][BOARD_SIZE];
    }

    /**
     * A helper method that verifies whether the grid is completely filled by player tokens, or not.
     *
     * @return true if the board is full and false otherwise
     */
    private boolean isGridFilled() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }
    void placePiece(int i, int j, Player player){
        if (this.board[i][j] != null){
            throw new IllegalArgumentException();
        }
        else{
            this.board[i][j] = player;
        }
    }

    void RotateSubBoard(BoardSubpart subpart, RotationDirection direction){
        Player [][] m = new Player[SUBPART_SIZE][SUBPART_SIZE];
        Player [][] mPrime = new Player[SUBPART_SIZE][SUBPART_SIZE];

        int a = 0;
        int b = 0;
        switch (subpart){
            case TOP_LEFT:
                a = 0;
                b = 0;
                break;
            case TOP_RIGHT:
                a = 0;
                b = SUBPART_SIZE;
                break;
            case BOTTOM_LEFT:
                a = SUBPART_SIZE;
                b = 0;
                break;
            case BOTTOM_RIGHT:
                a = SUBPART_SIZE;
                b = SUBPART_SIZE;
                break;
        }
        for(int i = 0; i<SUBPART_SIZE; i++){
            for (int j = 0; j<SUBPART_SIZE; j++){
                m[i][j] = this.board[i+a][j+b];
            }
        }
        switch (direction){
            case RIGHT:
                mPrime = rotateMatrix(m);
                break;
            case LEFT:
                mPrime = rotateMatrix(rotateMatrix(rotateMatrix(m)));
                break;
        }
        for(int i = 0; i<SUBPART_SIZE; i++){
            for (int j = 0; j<SUBPART_SIZE; j++){
                this.board[i+a][j+b] = mPrime[i][j];
            }
        }
    }

    void printBoard(){
        for (int i=0; i<BOARD_SIZE; i++){
            for (int j=0; j<BOARD_SIZE; j++){
                System.out.print(this.board[i][j] +" ");
            }
            System.out.println("");
        }
        System.out.println(" ");
    }

    boolean checkH(Player player){
        boolean b;
        for(int i =0; i<BOARD_SIZE;i++){
            b = checkWinPlayer(this.board[i], player);
            if (b){
                return true;
            }
        }
        return false;
    }

    boolean checkV(Player player){
        Player [][] m = rotateMatrix(this.board);
        boolean b;
        for(int i =0; i<BOARD_SIZE;i++){
            b = checkWinPlayer(m[i], player);
            if (b){
                return true;
            }
        }
        return false;
    }

    boolean checkDD(Player player){
        Player [] m = new Player[6];
        Player [] m2 = new Player[6];
        for (int i=0; i<BOARD_SIZE;i++){
            m[i] = this.board[i][i];
            m2[i] = this.board[board.length-1-i][i];
        }
        if (checkWinPlayer(m, player)){
            return true;
        }
        if (checkWinPlayer(m2, player)){
            return true;
        }

    return false;
    }
    boolean LD1(Player player){
        Player [] m = new Player[5];
        Player [] m2 = new Player[5];
        for (int i=0; i<BOARD_SIZE-1;i++){
            m[i] = this.board[i+1][i];
            m2[i] = this.board[i][i+1];
        }
        if (checkWinPlayer(m, player)){
            return true;
        }
        if (checkWinPlayer(m2, player)){
            return true;
        }
    return false;
    }

    boolean LD2(Player player){
        Player [] m = new Player[5];
        Player [] m2 = new Player[5];
        for (int i=0; i<BOARD_SIZE-1;i++){
            m[i] = rotateMatrix(this.board)[i+1][i];
            m2[i] = rotateMatrix(this.board)[i][i+1];
        }
        if (checkWinPlayer(m, player)){
            return true;
        }
        if (checkWinPlayer(m2, player)){
            return true;
        }
        return false;
    }


    /**
     * Implementation of the two steps move and computation of the winner:
     * 1) Plays a piece in the given position for the given player, then
     * 2) Rotates the given subpart in the given direction.
     *
     * The method returns the winner (A_WINS / B_WINS) if only one player is in a victory state.
     * Otherwise, NO_WINNER is returned. It means that NO_WINNER is also returned if both players
     * have aligned 5 pieces in a row, column or diagonal.
     *
     * <p>
     * The methods you should have implemented before are "rotateMatrix", "checkWinPlayerVector" and "checkWinPlayer"
     * Those methods should help you build the code for the play method.
     * Notice that rotating a matrix to the left three times is equivalent to rotating it once the right. 💡
     *
     * @param i         the row index
     * @param j         the column index
     * @param player    the player (A or B)
     * @param subpart   the subpart to rotate by +- 90°
     * @param direction the direction to rotate the subpart (LEFT for -90° or RIGHT for +90°)
     * @throws IllegalArgumentException if i or j is not a valid index or if the position is not empty
     * @return Winner   the winning status of the game after the play (cf. above)
     */
    public Winner play(int i, int j, Player player, BoardSubpart subpart, RotationDirection direction) {
        // TODO
        if (isGridFilled()){return Winner.NO_WINNER;}
        if (i>BOARD_SIZE-1 || j> BOARD_SIZE-1){throw new IllegalArgumentException();}
        placePiece(i, j, player);
        RotateSubBoard(subpart, direction);
        if (checkWinPlayer(Player.A) && checkWinPlayer(Player.B)){return Winner.NO_WINNER;}
        if (checkWinPlayer(player)){
            switch (player){
                case A:
                    return Winner.A_WINS;
                case B:
                    return Winner.B_WINS;
            }
        }
        return Winner.NO_WINNER; // add your own code here
    }


    /**
     * Rotate the given matrix 90° to the right (i.e., clockwise).
     *
     * Beware that the matrix may have an arbitrary n x m shape (i.e.,
     * do NOT assume that the matrix has the 3 x 3 shape of the
     * subparts of the Pentago game).
     *
     * @param matrix   the n x m matrix to rotate
     * @return A version of the input matrix rotated to the right having thus an m x n shape.
     *         The input matrix is left unchanged.
     */

    public Player[][] rotateMatrix(Player[][] matrix) {
        Player[][] m = new Player[matrix[0].length][matrix.length];
        for (int i=0; i< m.length; i++){
            for (int j=0; j<m[0].length; j++){
                m[i][j] = matrix[matrix.length-1-j][i];
            }
        }
        // TODO
         return m;
    }

    /**
     * Returns if five consecutive positions of the given player are present in the array.
     * This  array can represent a row, a column or a diagonal of the board.
     *
     * @param vector    An array.
     * @param player    The player for which we want to find the possible win.
     * @return true if and only if the given player has five consecutive positions in the array.
     */
    public boolean checkWinPlayer(Player[] vector, Player player){
        // TODO
        for(int i=0; i< vector.length-4;i++){
            boolean b = true;
            for (int j=0; j<5; j++){
                if (vector[i+j] != player){
                    b = false;
                    break;
                }
            }
            if (b){
                return b;
            }
        }
         return false;
    }

    /**
     * Indicates if the given player has won.
     *
     * @return true if the given player has won and false otherwise.
     */
    private boolean checkWinPlayer(Player player) {
        //TODO
        if(checkH(player) || checkV(player) || checkDD(player) || LD1(player) || LD2(player)){return true;}
        return false;
    }
}
