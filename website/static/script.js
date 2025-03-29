window.onscroll = function () {
    scrollBar();
};

function scrollBar() {
    var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
    var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    var scrolled = (winScroll / height) * 100;
    document.getElementById("scrollBar").style.width = scrolled + "%";
}

window.onload = function() {
    document.getElementById('popupBox').classList.add('show');
    document.getElementById('overlay').classList.add('show');
};

window.onload = function () {
    const chatButton = document.getElementById('chatToggle');
    chatButton.classList.add('bouncing');
    setTimeout(() => {
        chatButton.classList.remove('bouncing');
    }, 5000);
}

function closePopup() {
    const popupBox = document.getElementById('popupBox');
    const overlay = document.getElementById('overlay');

    popupBox.classList.add('fade-out');
    overlay.classList.add('fade-out');

    setTimeout(() => {
        popupBox.remove();
        overlay.remove();
    }, 500);
}

const chatToggle = document.getElementById('chatToggle');
const chatPopup = document.getElementById('chatPopup');
const chatBody = document.getElementById('chatBody');
const chatInput = document.getElementById('chatInput');

chatToggle.addEventListener('click', () => {
    chatPopup.classList.toggle('show');
});

function closeChat() {
    chatPopup.classList.remove('show');
}

function sendMessage() {
    const message = chatInput.value.trim();
    if (message !== '') {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('chat-message', 'user');
        messageDiv.textContent = message;
        chatBody.appendChild(messageDiv);
        chatBody.scrollTop = chatBody.scrollHeight;
        chatInput.value = '';

        setTimeout(() => {
            const botReply = document.createElement('div');
            botReply.classList.add('chat-message', 'bot');
            botReply.innerHTML = 'Thanks for reaching out! Our chat is currently in development.<br><br> Please email support at <a href="mailto:tetrisfans@omgame.club" class="email-link">tetrisfans@omgame.club</a> 😊';
            chatBody.appendChild(botReply);
            chatBody.scrollTop = chatBody.scrollHeight;
        }, 1000);
    }
}

chatInput.addEventListener("keypress", function (e) {
    if (e.key === "Enter") {
      sendMessage();
    }
});