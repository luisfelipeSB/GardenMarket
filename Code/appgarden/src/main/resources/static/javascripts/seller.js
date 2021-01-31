window.onload = async function() {
    let sellerId = sessionStorage.getItem("sellerId");

    let seller = await $.ajax({
        url: "/api/users/"+sellerId,
        method: "get",
        dataType: "json"
    });
    console.log(seller);

    document.getElementById("sellerName").innerHTML = seller.name;
    showAds(seller.ads)
}

function showAds(ads) {
    let elemMain = document.getElementById("ads");
    let html = "";
    for (let ad of ads) {
        console.log(ad.active)
        if (ad.active) {
            html += 
            "<section onclick='showAd(" + ad.id + ")'>" +
                "<h3>" + ad.title + "</h3>" +
                "<p>Preço: €" + ad.price + "</p>" +
                "<p>Categoria: " + ad.category.name + "</p>" +
            "</section>";
        }
    }
    elemMain.innerHTML = html;
}

function showAd(adId) {
    sessionStorage.setItem("adId", adId);
    window.location = "advertisement.html";
}

