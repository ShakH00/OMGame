/**
 * A pawn object class for pawn pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each pawn automatically has a score of 1 as it is the least valued piece on a Chess board
 *
 * @author Abdulrahman
 */
public class Pawn extends MovingPiece{
    boolean firstMove; //used to determine if pawn can move two forward if its on its first move

    /**
     * Normal piece attributes that are superclass sent to MovingPiece which supers it to Piece
     * @param x: x coordinate of location in board
     * @param y: y coordinate
     * @param colour: pawn colour
     * @param ownedBy: player who owns the pawn
     */
    public Pawn(int x, int y, String colour, PieceType pieceType,  Player ownedBy){
        super(x, y, colour, pieceType, ownedBy, 1);
        firstMove = false;
    }

    /**
     * Move method used to move the pawn to a different tile
     * @param currentX: current x coordinate of pawn
     * @param currentY: current y coordinate of pawn
     * @param newX: x coordinate pawn is being moved to
     * @param newY: y coordinate pawn is being moved to
     * @param gameBoard: the chess game's board
     *
     * This method works by checking if isValidMove returns true and then moving the piece
     */
    @Override
    void move(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        if(isValidMove(currentX, currentY, newX, newY, gameBoard)){
            Piece[][] board = gameBoard.getBoardState();
            board[currentX][currentY] = null;
            setX(newX);
            setY(newY);
            board[newX][newY] = this;
            if(!firstMove){
                firstMove = true;
            }
        }


    }

    /**
     * A method to check if the tile the pawn is being moved to is a valid move
     * Some notes to remember the rules of pawn movement in chess,
     * 1. A pawn's default movement is to move one tile forward
     *      However, a pawn can move two tiles forward, if it is the pawn's first move from starting position
     * 2. A pawn may move one tile diagonally, still forward, given that there is an enemy piece present there
     *      Under this second rule, the pawn proceeds to eat that enemy piece
     * 3. PROMOTION: Once a pawn reaches the end of the board, it can be promoted to one of four pieces: Knight, Bishop, Rook, Queen
     *      The choice of piece promotion is up to the player who owns the pawn. This rule is major for chess end-game
     * 4. Special rules: EN PASSANT: One's pawn can take an enemy pawn if the enemy pawn hasn't moved yet,
     *      and proceeds to jump two tiles up on its first move, placing it directly next to your pawn.
     *      Your pawn can now take it, moving diagonally, being placed right behind the enemy pawn which then disappears
     *      Example case: You are white, your pawn is on g5, in terms of array board, it would be on [3][6].
     *              Black has a pawn on f7, aka [1][5]. This places it on your pawn's left and two tiles forward
     *              On black's turn, they move their pawn up two, in accordance with rule one (1). This places their pawn
     *              directly on the left of your pawn, on f5, or [3][5]. Now it is your turn, you can take black's pawn.
     *              This would result in your pawn ending up on f6, or [2][5], and black's pawn being eaten
     * @param currentX: current x coordinate of the pawn
     * @param currentY: current y coordinate of the pawn
     * @param newX: new x coordinate pawn might be moved to
     * @param newY: new y coordinate pawn might be moved to
     * @param gameBoard: board being played on
     * @return true if this is a valid move
     */

    //WILL BE REWORKED A BIT NOW THAT WE HAVE PIECETYPE!!!!
    @Override
    boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        Piece[][] board = gameBoard.getBoardState();
        int compX = Math.abs(currentX-newX);
        int compY = Math.abs(currentY-newY);
        boolean out = false;
        //normal move one forward
        if(board[newX][newY] == null && compY == 0 && compX == 1){
            out = true;
        //move diagonal and eat enemy piece
        } else if(board[newX][newY] != null && (newY == currentY+1 || newY == currentY-1) && compX == 1){
            out = true;
        //first move
        } else if((compX == 2 && compY == 0) && !firstMove && board[newX][newY] == null && (board[newX+1][newY] == null || board[newX-1][newY] == null)){
            out = true;
        } //havent added en passant yet
        return out;
    }
}
