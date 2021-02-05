window.onload = function () {
    loadAdvertisements();
}

// Loading all advertisements in user's cart
async function loadAdvertisements() {
    let userId = sessionStorage.getItem("sessionUserId");
    try {
        let ads = await $.ajax({
            url: "/api/users/" + userId + "/cartItems",
            method: "get",
            dataType: "json"
        });
        showAds(ads);
    } catch (err) {
        let elemMain = document.getElementById("main");
        console.log(err);
        elemMain.innerHTML =
            "<h1> Página não está disponível</h1> <br>" +
            "<h2> Por favor tente mais tarde</h2>";
    }
}

// Showing ads in cart
function showAds(ads) {
    let elemMain = document.getElementById("main");
    let html = "";
    let subtotal = 0;
    for (let ad of ads) {
        subtotal += ad.price
        html +=
            "<section>" +
            "<h3>" + ad.title + "</h3>" +
            "<p>Anunciante: " + ad.seller + "</p>" +
            "<p>Preço: €" + ad.price + "</p>" +
            "<p>Categoria: " + ad.category + "</p>" +
            "<input type='button' value='Ver Anúncio' onclick='showAd(" + ad.id + ")'>" +
            "<input type='button' value='Remover' onclick='removeItem(" + ad.id + ")'>"
        "</section>";
    }
    html +=
        "<br> <h3>Subtotal: €" + subtotal + "</h3> "
    elemMain.innerHTML = html;
}

// Redirecting user to one ad's page
function showAd(adId) {
    sessionStorage.setItem("adId", adId);
    window.location = "advertisement.html";
}

// Removing one ad from cart
async function removeItem(adId) {

    let userId = sessionStorage.getItem("sessionUserId")
    console.log(userId)
    // Loading user's cart
    let cartView
    try {
        cartView = await $.ajax({
            url: "/api/users/" + userId + "/cart",
            method: "get",
            dataType: "json"
        })
    } catch (err) {
        console.log(err)
    }
    console.log(cartView)

    // Removing cart item
    try {
        let result = await $.ajax({
            url: "/api/transactions/cart/" + cartView.transactionId + "/" + adId,
            method: "delete",
            dataType: "json"
        })
        console.log(result)
        if (result) {
            alert("Item removido do carrinho")
            location.reload()
        } else {
            alert("Não foi possível remover o item do carrinho. Tente novamente mais tarde.")
        }
    } catch (err) {
        console.log(err)
    }
}

// Purchasing the ads
async function purchase() {

    if (confirm("Efetuar compra?")) {
        let userId = sessionStorage.getItem("sessionUserId");
        try {
            let cartView = await $.ajax({
                url: "/api/users/" + userId + "/cart",
                method: "get",
                dataType: "json"
            })

            // 2 is the state id for "PURCHASED"
            let result = await $.ajax({
                url: "/api/transactions/update/" + 2,
                method: "post",
                dataType: "json",
                data: JSON.stringify(cartView.transactionId),
                contentType: "application/json"
            })

            if (result != 0) {
                alert("Itens comprados com sucesso!")
                window.location = "purchases.html";
            }
        } catch (err) {
            let elemMain = document.getElementById("main")
            console.log(err)
            elemMain.innerHTML =
                "<h1> Página não está disponível</h1> <br>" +
                "<h2> Por favor tente mais tarde</h2>"
        }
    }
}