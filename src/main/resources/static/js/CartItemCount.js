document.addEventListener("DOMContentLoaded", function () {
    const cartItemCountElement = document.getElementById("cartItemCount");
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/cart/cartItemCount", true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            const cartItemCount = JSON.parse(xhr.responseText);
            cartItemCountElement.textContent = cartItemCount;
        }
    };
    xhr.send();
});