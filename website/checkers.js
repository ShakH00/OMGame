const board = document.getElementById('board');
let piecePosition = 10;
let enemyPosition = 7;
let isKing = false;
let isPlayerTurn = true;

function createBoard() {
    board.innerHTML = '';
    for (let i = 0; i < 64; i++) {
        const square = document.createElement('div');
        square.classList.add('square');
        square.classList.add((Math.floor(i / 8) + i % 8) % 2 === 0 ? 'light' : 'dark');
        board.appendChild(square);
    }
    placePiece();
    showPopup(`<strong>Hi! Welcome to checkers instructions!</strong><br><br>I'm Tetris Cat and I will be teaching you how to play!<br><br>Click your piece (purple), then choose a highlighted diagonal square to move and promote it to a king.`);
    if (enemyPosition !== null) placeEnemy();
}

function placePiece() {
    const squares = document.querySelectorAll('.square');
    const piece = document.createElement('img');
    piece.classList.add('piece');
    piece.src = isKing ? 'assets/purpleKingChecker.png' : 'assets/purpleChecker.png';
    squares[piecePosition].appendChild(piece);
    piece.onclick = highlightMoves;
}

function placeEnemy() {
    const squares = document.querySelectorAll('.square');
    const enemy = document.createElement('img');
    enemy.classList.add('piece', 'enemy');
    enemy.src = 'assets/greenChecker.png';
    squares[enemyPosition].appendChild(enemy);
}

function highlightMoves() {
    if (!isPlayerTurn) return;

    clearHighlights();
    const squares = document.querySelectorAll('.square');
    let moves = [];
    if (piecePosition >= 8) moves.push(piecePosition - 9, piecePosition - 7);
    if (isKing && piecePosition <= 55) moves.push(piecePosition + 7, piecePosition + 9);

    moves.forEach(pos => {
        if (pos >= 0 && pos < 64 && squares[pos].classList.contains('dark') && squares[pos].innerHTML === '') {
            const highlightDot = document.createElement('div');
            highlightDot.classList.add('highlight');
            squares[pos].appendChild(highlightDot);
            highlightDot.onclick = () => movePiece(pos);
        }
    });

    if (isKing && enemyPosition !== null && (Math.abs(piecePosition - enemyPosition) === 9 || Math.abs(piecePosition - enemyPosition) === 7)) {
        const jumpPos = enemyPosition + (enemyPosition - piecePosition);
        if (jumpPos >= 0 && jumpPos < 64 && squares[jumpPos].innerHTML === '' && squares[jumpPos].classList.contains('dark')) {
            const highlightDot = document.createElement('div');
            highlightDot.classList.add('highlight');
            squares[jumpPos].appendChild(highlightDot);
            highlightDot.onclick = () => capturePiece(jumpPos);
            showPopup('Jump over the enemy piece to capture it!');
        }
    }
}

function movePiece(newPos) {
    piecePosition = newPos;
    updatePiece();
    clearHighlights();
    if (piecePosition < 8 && !isKing) {
        isKing = true;
        showPopup('Your piece is now a king!');
        setTimeout(() => {
            showPopup("It's your turn!");
        }, 1500);
    }

    isPlayerTurn = false;
    setTimeout(enemyMove, 500);
}

function capturePiece(newPos) {
    piecePosition = newPos;
    enemyPosition = null;
    updatePiece();
    clearHighlights();
    showPopup(`Excellent! You captured the enemy piece by jumping over it.<br><br>Remember, play strategically to win the game and try to win as many pieces as possible`);
    isPlayerTurn = false;
    setTimeout(enemyMove, 500);
}

function enemyMove() {
    const squares = document.querySelectorAll('.square');
    let possibleMoves = [];
    const enemySquares = getEnemyValidMoves();

    enemySquares.forEach(pos => {
        if (pos >= 0 && pos < 64 && squares[pos].classList.contains('dark') && squares[pos].innerHTML === '') {
            possibleMoves.push(pos);
        }
    });

    if (possibleMoves.length > 0) {
        const randomMove = possibleMoves[Math.floor(Math.random() * possibleMoves.length)];
        enemyPosition = randomMove;
        updatePiece();
    }

    isPlayerTurn = true;
}

function getEnemyValidMoves() {
    let moves = [];
    if (enemyPosition <= 55) moves.push(enemyPosition + 7, enemyPosition + 9);

    return moves.filter(pos => !isNearEdge(pos));
}

function isNearEdge(pos) {
    return pos < 8 || pos >= 56 || pos % 8 === 0 || pos % 8 === 7;
}

function updatePiece() {
    document.querySelectorAll('.square').forEach(sq => sq.innerHTML = '');
    placePiece();
    if (enemyPosition !== null) placeEnemy();
}

function clearHighlights() {
    document.querySelectorAll('.highlight').forEach(dot => dot.remove());
}

function resetBoard() {
    piecePosition = 10;
    enemyPosition = 7;
    isKing = false;
    showPopup(`<strong>Hi! Welcome to checkers instructions!</strong><br><br>I'm Tetris Cat and I will be teaching you how to play!<br><br>Click your piece (purple), then choose a highlighted diagonal square to move and promote it to a king.`);
    createBoard();
    isPlayerTurn = true;
}

function showPopup(text) {
    document.getElementById('popup-text').innerHTML = text;
    document.getElementById('CHoverlay').classList.add("show");
    document.getElementById('CHpopupBox').classList.add("show");
}

function closePopup() {
    document.getElementById('CHoverlay').classList.remove("show");
    document.getElementById('CHpopupBox').classList.remove("show");
}

createBoard();

document.addEventListener("DOMContentLoaded", function() {
    const loggedInUser = localStorage.getItem("loggedInUser");
    const loginBtn = document.getElementById("login-btn");

    if (loggedInUser) {
        loginBtn.textContent = `User: ${loggedInUser}`;
    }
});