const buyButtons = document.querySelectorAll('.buy-btn');
buyButtons.forEach(button => {
    button.addEventListener('click', () => {
        openStripeModal();
    });
});

function openStripeModal() {
    document.getElementById("stripe-modal").style.display = "flex";
}

function closeStripeModal() {
    document.getElementById("stripe-modal").style.display = "none";
}

function processPayment() {
    const cardNumber = document.getElementById('card-number').value;
    const expiryDate = document.getElementById('expiry-date').value;
    const cvv = document.getElementById('cvv').value;

    if (cardNumber && expiryDate && cvv) {
        alert("Payment Successful! Thank you for your purchase.");
        closeStripeModal();
    } else {
        alert("Please complete the payment information.");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("modal").addEventListener("click", (e) => {
        if (e.target.classList.contains("modal")) {
            closeModal();
        }
    });
});

function openModal(imageSrc, title, description, isLootbox, price, defaultQuantity = 1) {
    const modal = document.getElementById("modal");
    const quantityContainer = document.getElementById("quantity-container");
    const purchaseSelect = document.getElementById("purchase-select");
    const customQuantityInput = document.getElementById("custom-quantity");

    document.getElementById("modal-image").src = imageSrc;
    document.getElementById("modal-title").innerText = title;
    document.getElementById("modal-description").innerText = description;

    if (isLootbox) {
        quantityContainer.style.display = "block";
        purchaseSelect.style.display = "block";
        customQuantityInput.style.display = "none";

        purchaseSelect.value = defaultQuantity.toString();

        updatePrice(price);
    } else {
        quantityContainer.style.display = "none";
        updatePrice(price);
    }

    document.getElementById("modal").classList.add("show");

    customQuantityInput.addEventListener("input", () => updatePrice(price));
    purchaseSelect.addEventListener("change", () => updatePrice(price));
}


function closeModal() {
    document.getElementById("modal").classList.remove("show");
}

function handleQuantityChange() {
    const select = document.getElementById("purchase-select");
    const customQuantity = document.getElementById("custom-quantity");

    if (select.value === "other") {
        customQuantity.style.display = "inline-block";
        customQuantity.value = "1";
    } else {
        customQuantity.style.display = "none";
        updatePrice(parseInt(select.value));
    }
}

function validateCustomQuantity() {
    const input = document.getElementById("custom-quantity");
    const value = parseInt(input.value);

    if (isNaN(value) || value < 1 || value > 100) {
        input.value = "";
    } else {
        updatePrice(value);
    }
}

function updatePrice(price) {
    const select = document.getElementById("purchase-select");
    const customQuantityInput = document.getElementById("custom-quantity");

    let quantity = parseInt(select.value);

    if (select.value === "other") {
        quantity = parseInt(customQuantityInput.value) || 1;
    }

    const totalPrice = quantity * price;
    document.getElementById("modal-price").innerText = `Price: $${totalPrice.toFixed(2)}`;
}


function openTab(tabName, event) {
    const tabButtons = document.querySelectorAll(".tab-button");
    const tabContents = document.querySelectorAll(".tab-content");
    const tabContainer = document.querySelector(".tab-content-container");

    // Get index of clicked tab
    currentIndex = [...tabButtons].indexOf(event.currentTarget);

    // Update active tab button
    tabButtons.forEach(button => button.classList.remove("active"));
    event.currentTarget.classList.add("active");

    // Slide content container
    tabContainer.style.transform = `translateX(-${currentIndex * (100/3)}%)`;

    // Update tab visibility
    tabContents.forEach((tab, index) => {
        tab.classList.toggle("active", index === currentIndex);
    });
}