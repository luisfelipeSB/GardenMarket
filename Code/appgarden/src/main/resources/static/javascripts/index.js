window.onload = function () {
    setSessionUser();
    loadAdvertisements();
}

async function setSessionUser() {
    sessionStorage.setItem("sessionUserId", 6);    // User 6: Johnny Test
    document.getElementById("sessionUser").innerHTML = "Signed in as <b>Johnny Test</b>";
}

async function loadAdvertisements() {
    try {
        let ads = await $.ajax({
            url: "/api/ads/active",
            method: "get",
            dataType: "json"
        });
        showAds(ads);

    } catch (err) {
        let elemMain = document.getElementById("main");
        console.log(err);
        elemMain.innerHTML = "<h1> Página não está disponível</h1> <br>" +
            "<h2> Por favor tente mais tarde</h2>";
    }
}

function showAds(ads) {
    let elemMain = document.getElementById("main");
    let html = "";
    for (let ad of ads) {
        html += 
            "<section onclick='showAd(" + ad.id + ")'>" +
                "<h3>" + ad.title + "</h3>" +
                "<p>Anunciante: " + ad.seller + "</p>" + 
                "<p>Preço: €" + ad.price + "</p>" +
                "<p>Categoria: " + ad.category + "</p>" +
            "</section>";
    }
    elemMain.innerHTML = html;
}

function showAd(adId) {
    console.log(adId)
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