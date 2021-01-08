window.onload = function () {
    loadAdvertisements();
}

async function loadAdvertisements() {
    try {
        let ads = await $.ajax({
            url: "/api/ads",
            method: "get",
            dataType: "json"
        });
        showAds(ads);

    } catch (err) {
        let elemMain = document.getElementById("main");
        console.log(err);
        elemMain.innerHTML = "<h1> Página não está disponível</h1>" +
            "<h2> Por favor tente mais tarde</h2>";
    }
}

function showAds(ads) {
    let elemMain = document.getElementById("main");
    let html = "";
    for (let ad of ads) {
        html += "<section onclick='showAd(" + ad.id + ")'>" +
            "<h3>" + ad.title + "</h3>" +
            "<p> Anunciante: " + ad.seller.name + "</p></section>";
    }
    elemMain.innerHTML = html;
}


function showAd(adId) {
    sessionStorage.setItem("adId", adId);
    window.location = "advertisement.html";
}


async function filterTitle() {
    try {
        let title = document.getElementById("title").value;
        let ads = await $.ajax({
            url: "/api/ads/filter_title?title=" + title,
            method: "get",
            dataType: "json"
        });
        showAds(ads);
    } catch (err) {
        let elemMain = document.getElementById("main");
        console.log(err);
        elemMain.innerHTML = "<h1> Página não está disponível</h1>" +
            "<h2> Por favor tente mais tarde</h2>";
    }
}

async function filterCategory() {
    try {
        let category = document.getElementById("categories").value;
        let ads = await $.ajax({
            url: "/api/ads/filter_category?category=" + category,
            method: "get",
            dataType: "json"
        });
        showAds(ads);
    } catch (err) {
        let elemMain = document.getElementById("main");
        console.log(err);
        elemMain.innerHTML = "<h1> Página não está disponível</h1>" +
            "<h2> Por favor tente mais tarde</h2>";
    }
}
