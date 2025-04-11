const userMoves = [0, 0, 0, 0];
const yellowMoves = [1, 1, 1];
const board = document.getElementById('board');
let moveIndex = 0;

function createBoard() {
    board.innerHTML = '';
    for (let i = 0; i < 42; i++) {
        const cell = document.createElement('div');
        cell.classList.add('cell');
        board.appendChild(cell);
    }
    showPopup(`<strong>Hi! Welcome to Connect4 instructions!</strong><br><br>I'm Tetris Cat and I will be teaching you how to play!<br><br>Click the left column to start!`);
}

function resetGame() {
    moveIndex = 0;
    createBoard();
    showPopup('Click the left column to start!');
    enableColumn(userMoves[moveIndex]);
}

function enableColumn(col) {
    // Enable clicking the entire column
    for (let row = 5; row >= 0; row--) {
        const cell = board.children[row * 7 + col];

        // We want to check if the cell is empty, and if so, add the onclick event
        if (!cell.classList.contains('red') && !cell.classList.contains('yellow')) {
            cell.onclick = () => userMove(col);
        }
    }
}

function userMove(col) {
    dropPiece(col, 'red');
    if (checkWin('red')) {
        showPopup('You won vertically! You can also win diagonally or horizontally.');
        disableBoard();
        return;
    }

    setTimeout(() => {
        if (moveIndex < yellowMoves.length) {
            dropPiece(yellowMoves[moveIndex], 'yellow');
        }
        moveIndex++;

        if (moveIndex < userMoves.length) {
            showPopup(`Good! Now click the left column again.`);
            enableColumn(userMoves[moveIndex]);
        }
    }, 400);
}

function dropPiece(col, color) {
    for (let row = 5; row >= 0; row--) {
        const cell = board.children[row * 7 + col];
        if (!cell.classList.contains('red') && !cell.classList.contains('yellow')) {
            cell.classList.add(color);
            break;
        }
    }
}

function disableBoard() {
    document.querySelectorAll('.cell').forEach(cell => cell.onclick = null);
}

function checkWin(color) {
    // Vertical check
    for (let c = 0; c < 7; c++) {
        for (let r = 0; r < 3; r++) {
            if ([0,1,2,3].every(i => board.children[(r+i)*7+c].classList.contains(color))) {
                return true;
            }
        }
    }
    return false;
}

function showPopup(text) {
    document.getElementById('popup-text').innerHTML = text;
    document.getElementById('C4overlay').classList.add("show");
    document.getElementById('C4popupBox').classList.add("show");
}

function closePopup() {
    document.getElementById('C4overlay').classList.remove("show");
    document.getElementById('C4popupBox').classList.remove("show");
}

resetGame();

document.addEventListener("DOMContentLoaded", function() {
    const loggedInUser = localStorage.getItem("loggedInUser");
    const loginBtn = document.getElementById("login-btn");

    if (loggedInUser) {
        loginBtn.textContent = `User: ${loggedInUser}`;
    }
});