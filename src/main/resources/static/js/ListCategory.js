document.addEventListener("DOMContentLoaded", function () {
    const productDropdown = document.getElementById("productDropdown");

    // Sử dụng AJAX để tải dữ liệu từ API
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/api/category", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);

            productDropdown.innerHTML = "";

            // Duyệt qua dữ liệu từ API và thêm mỗi sản phẩm vào dropdown menu
            data.forEach(function (product) {
                const productItem = document.createElement("li");
                productItem.classList.add("dropdown-item");

                const name = product.categoryName;

                const productLink = document.createElement("a");
                productLink.href = "/product/search?category=" + name;
                productLink.textContent = name;

                productItem.appendChild(productLink);
                productDropdown.appendChild(productItem);
            });
        }
    };
    xhr.send();
});