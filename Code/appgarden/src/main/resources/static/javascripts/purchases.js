window.onload = function () {
    loadAdvertisements();
}

async function loadAdvertisements() {
    let userId = sessionStorage.getItem("sessionUserId");
    try {
        let ads = await $.ajax({
            url: "/api/transactions/user/" + userId + "/purchases",
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
        "<br> <h3>Total de Gastos: €" + subtotal + "</h3> "
    elemMain.innerHTML = html;
}