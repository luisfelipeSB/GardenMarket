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
            "<section onclick='showAd(" + ad.id + ")'>" +
                "<h3>" + ad.title + "</h3>" +
                "<p>Anunciante: " + ad.seller + "</p>" +
                "<p>Preço: €" + ad.price + "</p>" +
                "<p>Categoria: " + ad.category + "</p>" +
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

// Purchasing the ads
async function purchase() {
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
        if (result > 1) alert("Itens comprados com sucesso!")
    } catch (err) {
        let elemMain = document.getElementById("main")
        console.log(err)
        elemMain.innerHTML =
            "<h1> Página não está disponível</h1> <br>" +
            "<h2> Por favor tente mais tarde</h2>"
    }
}