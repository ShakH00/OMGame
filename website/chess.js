const board = document.getElementById('board');
let pawnPosition = 48;
let enemyPosition = 9;
let queenPosition = null;
let rookPosition = null;
let kingPosition = null;
let tutorialPhase = 1;
let bishopPosition = null;
let knightPosition = null;
let blackRookPos = null;
let blackPawnPos = null;

function startTutorial() {
    const boardContainer = document.getElementById('board-container');
    boardContainer.classList.add('show');

    setTimeout(() => {
        createBoard();
    }, 700);

    // Scroll down to the board container
    document.getElementById('board-container').scrollIntoView({ behavior: 'smooth' });

    document.getElementById('startButton').style.display = 'none';
    document.getElementById('resetButton').style.display = 'inline-block';

    showPopup(`<strong>Hi! Welcome to Chess instructions!</strong><br><br>I'm Tetris Cat and I will be teaching you how to play!<br><br>Begin by moving the pawn forward!`);
}

function createBoard() {
    board.innerHTML = '';
    for (let i = 0; i < 64; i++) {
        const square = document.createElement('div');
        square.classList.add('square', (Math.floor(i / 8) + i % 8) % 2 === 0 ? 'light' : 'dark');
        board.appendChild(square);
    }
    placePawn();
    placeEnemy();
}

function placePawn() {
    placePiece(pawnPosition, 'assets/chessPawnWhite.png', 'pawn');
}

function placeEnemy() {
    placePiece(enemyPosition, 'assets/chessRookBlack.png', 'enemy');
}

function highlightMoves() {
    clearHighlights();
    const squares = document.querySelectorAll('.square');

    const movePos = pawnPosition - 8;
    if (movePos >= 0 && squares[movePos].innerHTML === '')
        highlightSquare(movePos, 'pawn');

    [pawnPosition - 9, pawnPosition - 7].forEach(pos => {
        if (pos === enemyPosition) highlightSquare(pos, 'pawn', true);
    });
}

function movePawn(newPos) {
    pawnPosition = newPos;
    updateBoard();
    if (pawnPosition < 8) promotePawn();
}

function capturePiece(newPos) {
    pawnPosition = newPos;
    enemyPosition = null;
    updateBoard();
    showPopup('Great job! You captured the enemy rook!');
}

function promotePawn() {
    tutorialPhase = 2;
    queenPosition = pawnPosition;
    pawnPosition = null;
    enemyPosition = null;

    setupCheckmateTutorial();
}

function setupCheckmateTutorial() {
    queenPosition = 59;
    rookPosition = 56;
    kingPosition = 5;
    updateCheckmateBoard();
    showPopup('Let’s checkmate the black king! Move your queen or rook to trap the black king.');
}

function placePiece(pos, imgSrc, id) {
    const squares = document.querySelectorAll('.square');
    const piece = document.createElement('img');
    piece.classList.add('piece');
    piece.src = imgSrc;
    squares[pos].appendChild(piece);

    if (id === 'pawn') piece.onclick = highlightMoves;
    else if (id === 'whiteQueen') piece.onclick = () => highlightMovesQueen(pos);
    else if (id === 'whiteRook') piece.onclick = () => highlightMovesRook(pos);
    else if (id === 'whiteBishop') piece.onclick = () => highlightMovesBishop(pos);
    else if (id === 'whiteKnight') piece.onclick = () => highlightMovesKnight(pos);
}

function highlightSquare(pos, pieceId, isCapture = false) {
    const squares = document.querySelectorAll('.square');
    const dot = document.createElement('div');
    dot.classList.add('highlight');
    squares[pos].appendChild(dot);
    dot.onclick = () => {
        if (pieceId === 'pawn') isCapture ? capturePiece(pos) : movePawn(pos);
        else moveCheckmatePiece(pieceId, pos, isCapture);
    };
}

function moveCheckmatePiece(pieceId, newPos, isCapture) {
    if (pieceId === 'queen') queenPosition = newPos;
    else if (pieceId === 'rook') rookPosition = newPos;
    else if (pieceId === 'bishop') bishopPosition = newPos;
    else if (pieceId === 'knight') knightPosition = newPos;

    // Remove captured black pieces
    if (isCapture) {
        if (newPos === kingPosition) {
            kingPosition = null;
            showPopup('Checkmate! You captured the black king!');
            checkCaptureTutorialComplete();  // Check if we should move to the next tutorial phase
        } else if (newPos === blackRookPos) {
            blackRookPos = null;
            showPopup('Nice! You captured the black rook!');
        } else if (newPos === blackPawnPos) {
            blackPawnPos = null;
            showPopup('Good job! You captured the black pawn!');
        }
    }

    if (tutorialPhase === 2) {
        updateCheckmateBoard();
    } else if (tutorialPhase === 3) {
        updateCaptureBoard();
        checkCaptureTutorialComplete();
    }
}

function highlightMovesQueen(pos) {
    highlightLinearMoves(pos, [-8, 8, -1, 1, -9, -7, 7, 9], 'queen');
}

function highlightMovesRook(pos) {
    highlightLinearMoves(pos, [-8, 8, -1, 1], 'rook');
}

function highlightLinearMoves(pos, directions, pieceId) {
    clearHighlights();
    const squares = document.querySelectorAll('.square');
    directions.forEach(dir => {
        let newPos = pos;
        while (true) {
            let prevPos = newPos;
            newPos += dir;

            if (newPos < 0 || newPos >= 64) break;

            // Prevent wrap-around horizontally
            if (Math.abs((prevPos % 8) - (newPos % 8)) > 2) break;

            if (newPos === kingPosition) {
                highlightSquare(newPos, pieceId, true);
                break;
            }
            if (squares[newPos].innerHTML === '') {
                highlightSquare(newPos, pieceId);
            } else {
                break;
            }
        }
    });
}

function checkCaptureTutorialComplete() {
    if (blackRookPos === null && blackPawnPos === null) {
        setupCaptureTutorial();
    }
}

function updateBoard() {
    document.querySelectorAll('.square').forEach(sq => sq.innerHTML = '');
    if (pawnPosition !== null) placePawn();
    if (enemyPosition !== null) placeEnemy();
}

function updateCheckmateBoard() {
    document.querySelectorAll('.square').forEach(sq => sq.innerHTML = '');
    if (queenPosition !== null) placePiece(queenPosition, 'assets/chessQueenWhite.png', 'whiteQueen');
    if (rookPosition !== null) placePiece(rookPosition, 'assets/chessRookWhite.png', 'whiteRook');
    if (kingPosition !== null) placePiece(kingPosition, 'assets/chessKingBlack.png', 'blackKing');
}

function setupCaptureTutorial() {
    tutorialPhase = 3;

    bishopPosition = 35;
    knightPosition = 18;
    blackRookPos = 42;
    blackPawnPos = 25;

    showPopup("Now let’s practice capturing with bishops and knights!<br><br>Click the bishop or knight to begin.");
}

function updateCaptureBoard() {
    document.querySelectorAll('.square').forEach(sq => sq.innerHTML = '');
    if (bishopPosition !== null) placePiece(bishopPosition, 'assets/chessBishopWhite.png', 'whiteBishop');
    if (knightPosition !== null) placePiece(knightPosition, 'assets/chessKnightWhite.png', 'whiteKnight');
    if (blackRookPos !== null) placePiece(blackRookPos, 'assets/chessRookBlack.png', 'blackRook');
    if (blackPawnPos !== null) placePiece(blackPawnPos, 'assets/chessPawnBlack.png', 'blackPawn');
}

function highlightMovesBishop(pos) {
    highlightLinearMoves(pos, [-9, -7, 7, 9], 'bishop');
}

function highlightMovesKnight(pos) {
    clearHighlights();
    const knightMoves = [-17, -15, -10, -6, 6, 10, 15, 17];
    const squares = document.querySelectorAll('.square');

    knightMoves.forEach(offset => {
        const newPos = pos + offset;
        if (newPos < 0 || newPos >= 64) return;
        if (Math.abs((pos % 8) - (newPos % 8)) > 2) return;

        if (newPos === blackRookPos || newPos === blackPawnPos) {
            highlightSquare(newPos, 'knight', true);
        } else if (squares[newPos].innerHTML === '') {
            highlightSquare(newPos, 'knight');
        }
    });
}

function clearHighlights() {
    document.querySelectorAll('.highlight').forEach(dot => dot.remove());
}

function resetBoard() {
    pawnPosition = 48; enemyPosition = 9; queenPosition = rookPosition = kingPosition = null; tutorialPhase = 1;
    createBoard();
}

function showPopup(text) {
    document.getElementById('popup-text').innerHTML = text;
    document.getElementById('Csoverlay').classList.add("show");
    document.getElementById('CspopupBox').classList.add("show");
}

function closePopup() {
    document.getElementById('Csoverlay').classList.remove("show");
    document.getElementById('CspopupBox').classList.remove("show");
}

createBoard();

document.addEventListener("DOMContentLoaded", function() {
    const loggedInUser = localStorage.getItem("loggedInUser");
    const loginBtn = document.getElementById("login-btn");

    if (loggedInUser) {
        loginBtn.textContent = `User: ${loggedInUser}`;
    }
});