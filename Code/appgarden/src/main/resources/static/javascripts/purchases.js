window.onload = function () {
    loadAdvertisements();
}

// Loading all advertisements purchased by the user, carrying their transactionIds
async function loadAdvertisements() {

    let userId = sessionStorage.getItem("sessionUserId");
    try {
        let purchasedAds = await $.ajax({
            url: "/api/users/" + userId + "/purchasedItems",
            method: "get",
            dataType: "json"
        });
        showAds2(purchasedAds);

    } catch (err) {
        let elemMain = document.getElementById("main");
        console.log(err);
        elemMain.innerHTML =
            "<h1> Página não está disponível</h1> <br>" +
            "<h2> Por favor tente mais tarde</h2>";
    }
}

// Showing purchased ads
function showAds2(ads) {

    let elemMain = document.getElementById("main")
    let t = []
    for (let ad of ads)
        t.push(ad.transactionId)
    let transactions = unique(t)
    console.log(transactions)

    let html = "";
    for (let t of transactions) {
        html += "<section class='transaction'>"
        let subtotal = 0
        let purchaseDate
        for (let ad of ads) {
            purchaseDate = ad.purchaseDate
            if (ad.transactionId == t) {
                subtotal += ad.price
                html +=
                    "<section>" +
                    "<h3>" + ad.title + "</h3>" +
                    "<p>Anunciante: " + ad.seller + "</p>" +
                    "<p>Preço: €" + ad.price + "</p>" +
                    "<p>Categoria: " + ad.category + "</p>" +
                    "</section>"
            }
        }
        html += "<br> <h3>Data de pagamento: " + purchaseDate + "</h3>"
        html += "<br> <h3>Total de Gastos: €" + subtotal + "</h3> "
        html += "</section>"
    }
    console.log(html)
    elemMain.innerHTML = html
}

function unique(a) {
    return a.sort().filter(function(item, pos, ary) {
        return !pos || item != ary[pos - 1];
    })
}

// Showing purchased ads
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
            "</section>";
    }
    html +=
        "<br> <h3>Total de Gastos: €" + subtotal + "</h3> "
    elemMain.innerHTML = html;
}