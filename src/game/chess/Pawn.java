/**
 * A pawn object class for pawn pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each pawn automatically has a score of 1 as it is the least valued piece on a Chess board
 *
 * @author Abdulrahman Negmeldin
 */

package game.chess;
import game.Board;
import game.Player;
import game.pieces.Piece;
import game.pieces.PieceType;
import game.pieces.MovingPiece;
import javafx.scene.paint.Color;

public class Pawn extends MovingPiece {
    private boolean doneFirstMove; //used to determine if pawn can move two forward if its on its first move
    private boolean doneSecondMove; //used to check if this pawn can be en pessant-ed

    /**
     * Normal piece attributes that are superclass sent to MovingPiece which supers it to Piece
     * @param x: x coordinate of location in board
     * @param y: y coordinate
     * @param color: pawn colour
     * @param ownedBy: player who owns the pawn
     */
    public Pawn(int x, int y, Color color, PieceType pieceType, Player ownedBy){
        super(x, y, color, pieceType, ownedBy, 1);
        doneFirstMove = false;
        doneSecondMove = false;
    }

    /**
     * Move method used to move the pawn to a different tile
     * @param newX: x coordinate pawn is being moved to
     * @param newY: y coordinate pawn is being moved to
     * @param gameBoard: the game.chess game's board
     * @return true if piece was moved
     * This method works by checking if isValidMove returns true and then moving the piece
     *
     */
    @Override
    public boolean move(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        boolean enpassant = isEnpassant(newX, newY, gameBoard);

        //must be valid move
        if (isValidMove(newX, newY, gameBoard)) {
            Piece[][] board = gameBoard.getBoardState();

            //actually move
            board[currentX][currentY] = null;
            this.setX(newX);
            this.setY(newY);
            board[newX][newY] = this;

            //if its an en passant move then delete enemy pawn off the board (MURDER)
            if (enpassant) {
                //get enemy pawn position
                int enemyX = currentX;
                int enemyY = currentY;

                //enemy pawn y coordinate depends on whether your pawn moved right or left
                if (newY < currentY) {
                    enemyY = currentY - 1;
                } else if (newY > currentY) {
                    enemyY = currentY + 1;
                }

                //murder the enemy pawn >:)
                board[enemyX][enemyY] = null;
            }

            //update accordingly as they are important for en passant checks
            if (!doneFirstMove) {
                doneFirstMove = true;
            } else if (!doneSecondMove) {
                doneSecondMove = true;
            }

            return true;
        }

        return false;//if reached end of method then move did not occur
    }


    /**
     * A method to check if the move being made is en passant, used to eat enemy piece in such a special move
     * @param newX: new x coordinate that pawn is being moved to
     * @param newY: new y coordinate that pawn is being moved to
     * @param gameBoard: board being played on
     * @return true if this is indeed an en passant move
     */
    private boolean isEnpassant(int newX, int newY, Board gameBoard){
        int currentX = this.getX();
        int currentY = this.getY();
        Piece[][] board = gameBoard.getBoardState();
        PieceType type = this.getPieceType();
        int compX = (type == PieceType.LIGHT) ? currentX-newX : newX-currentX;
        int compY = Math.abs(newY-currentY);
        Piece isPieceOnTile = board[newX][newY];
        //en passant logic from isValidMove
        if(compX == 1 && compY == 1){
            if (compX == 1 && compY == 1 && isPieceOnTile == null) {
                int sidePawnX = currentX;
                int sidePawnY = newY;
                Piece sidePiece = board[sidePawnX][sidePawnY];

                if (sidePiece instanceof Pawn && sidePiece.getPieceType() != type) {
                    Pawn enemyPawn = (Pawn) sidePiece;
                    boolean correctRow = (type == PieceType.LIGHT && currentX == 3) || (type == PieceType.DARK && currentX == 4);
                    boolean targetRow = (type == PieceType.LIGHT && newX == 2) || (type == PieceType.DARK && newX == 5);

                    if (correctRow && targetRow && !enemyPawn.isDoneSecondMove()) {
                        return true;
                    }
                }
            }
        }
        return false; //if reached end of method then this move is NOT en passant
    }

    /**
     * A basic method to check if the pawn is ready for promotion
     * This method does this by checking if it reached the last tile based on PieceType checks
     * @return true if the pawn is ready for promotion, false otherwise
     */
    public boolean checkPromotion(){
        boolean promote = false; //auto assume no promotion
        if(this.getPieceType() == PieceType.LIGHT){
            if(this.getX() == 0) promote = true;
        } else if(this.getPieceType() == PieceType.DARK){
            if(this.getX() == 7) promote = true;
        }
        return promote;
    }


    /**
     * A method to promote the pawn to 1 of 4 pieces (knight, bishop, rook, queen) based on an integer input
     * @param choice: the integer that determines what the pawn is promoted to (1 = knight, 2 = bishop, 3 = rook, 4 = queen)
     * @param gameBoard: The board being played on
     */
    public void promote(int choice, Board gameBoard){
        Piece[][] board = gameBoard.getBoardState();
        int x = this.getX();
        int y = this.getY();
        Color color = this.getColor();
        PieceType type = this.getPieceType();
        Player owner = this.getOwnedBy();
        switch(choice){
            case 1: //knight
                Knight knight = new Knight(x, y, color, type, owner);
                board[x][y] = knight;
                break;
            case 2: //bishop
                Bishop bishop = new Bishop(x, y, color, type, owner);
                board[x][y] = bishop;
                break;
            case 3: //rook
                Rook rook = new Rook(x, y, color, type, owner);
                rook.setDoneFirstMove(true); //make sure you can't castle with a rook that was a promoted pawn
                board[x][y] = rook;
                break;
            case 4: //queen
                Queen queen = new Queen(x, y, color, type, owner);
                board[x][y] = queen;
                break;
        }
    }

    /**
     * A method to check if the pawn has done its first move yet
     * @return true if first move performed
     */
    protected boolean isDoneFirstMove(){
        return doneFirstMove;
    }

    /**
     * A method to check if the pawn has done its second move
     * This is used to check if the enemy player can perform en passant on your pawn
     * @return true if the second move has been performed
     */
    protected boolean isDoneSecondMove(){
        return doneSecondMove;
    }


    /**
     * A method to check if the tile the pawn is being moved to is a valid move
     * Some notes to remember the rules of pawn movement in game.chess,
     * 1. A pawn's default movement is to move one tile forward
     *      However, a pawn can move two tiles forward, if it is the pawn's first move from starting position
     * 2. A pawn may move one tile diagonally, still forward, given that there is an enemy piece present there
     *      Under this second rule, the pawn proceeds to eat that enemy piece
     * 3. PROMOTION: Once a pawn reaches the end of the board, it can be promoted to one of four game.pieces: Knight, Bishop, Rook, Queen
     *      The choice of piece promotion is up to the player who owns the pawn. This rule is major for game.chess end-game
     * 4. Special rules: EN PASSANT: One's pawn can take an enemy pawn if the enemy pawn hasn't moved yet,
     *      and proceeds to jump two tiles up on its first move, placing it directly next to your pawn.
     *      Your pawn can now take it, moving diagonally, being placed right behind the enemy pawn which then disappears
     *      Example case: You are white, your pawn is on g5, in terms of array board, it would be on [3][6].
     *              Black has a pawn on f7, aka [1][5]. This places it on your pawn's left and two tiles forward
     *              On black's turn, they move their pawn up two, in accordance with rule one (1). This places their pawn
     *              directly on the left of your pawn, on f5, or [3][5]. Now it is your turn, you can take black's pawn.
     *              This would result in your pawn ending up on f6, or [2][5], and black's pawn being eaten
     * @param newX: new x coordinate pawn might be moved to
     * @param newY: new y coordinate pawn might be moved to
     * @param gameBoard: board being played on
     * @return true if this is a valid move
     */
    @Override
    public boolean isValidMove(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        Piece[][] board = gameBoard.getBoardState();
        PieceType type = this.getPieceType();
        int compX = (type == PieceType.LIGHT) ? currentX-newX : newX-currentX;
        int compY = Math.abs(newY-currentY);
        Piece isPieceOnTile = board[newX][newY];

        //move 1 tile forward
        if(isPieceOnTile == null && compY == 0 && compX == 1){
            return true;
        //move 2 forward if first move
        } else if(isPieceOnTile == null && compY == 0 && compX == 2 && !doneFirstMove){
            if(type == PieceType.LIGHT && board[currentX-1][newY] == null){
                return true;
            } else if(type == PieceType.DARK && board[currentX+1][newY] == null){
                return true;
            }
        //move diagonal
        } else if(compX == 1 && compY == 1){
            //eat an enemy piece diagonally
            if (isPieceOnTile != null && isPieceOnTile.getPieceType() != type){
                return true;
            //en passant: essentially checks that tile moving to is empty, there's a piece next to your pawn, its an enemy piece
            } else if (compX == 1 && compY == 1 && isPieceOnTile == null) {
                int sidePawnX = currentX;
                int sidePawnY = newY;
                Piece sidePiece = board[sidePawnX][sidePawnY];

                if (sidePiece instanceof Pawn && sidePiece.getPieceType() != type) {
                    Pawn enemyPawn = (Pawn) sidePiece;
                    boolean correctRow = (type == PieceType.LIGHT && currentX == 3) || (type == PieceType.DARK && currentX == 4);
                    boolean targetRow = (type == PieceType.LIGHT && newX == 2) || (type == PieceType.DARK && newX == 5);

                    if (correctRow && targetRow && !enemyPawn.isDoneSecondMove()) {
                        return true;
                    }
                }
            }
        }
        return false; //if reached end of method then invalid move, return false
    }
}
