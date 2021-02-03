window.onload = function () {
    setSessionUser()
    loadAdvertisements()
    loadCategories()
}

// Setting session user
async function setSessionUser() {
    // User 6: Johnny Test
    sessionStorage.setItem("sessionUserId", 6)
    document.getElementById("sessionUser").innerHTML = "Signed in as <b>Johnny Test</b>"
}

// Loading all active advertisements
async function loadAdvertisements() {
    try {
        let ads = await $.ajax({
            url: "/api/ads/active",
            method: "get",
            dataType: "json"
        })
        showAds(ads)

    } catch (err) {
        let elemMain = document.getElementById("main")
        console.log(err)
        elemMain.innerHTML = 
            "<h1> Página não está disponível</h1> <br>" +
            "<h2> Por favor tente mais tarde</h2>"
    }
}

// Load all ad categories and list them in combobox
async function loadCategories() {
    try {
        let categories = await $.ajax({
            url: "/api/ads/categories",
            method: "get",
            dataType: "json"
        })
        let elem = document.getElementById("2")
        let html = ""
        for (let catg of categories) {
            html += "<option value=" + catg.name + ">" + catg.name + "</option>"
        }
        elem.innerHTML = html;

    } catch (err) {
        console.log(err)
    }
}

// Showing ads
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

// Redirecting user to one ad's page
function showAd(adId) {
    sessionStorage.setItem("adId", adId);
    window.location = "advertisement.html";
}

// Getting active ads, filtered by a user-provided title or category
async function filter(filterType) {

    let filter = document.getElementById(filterType).value
    console.log(filterType, filter)
    try {
        let ads = await $.ajax({
            url: "/api/ads/active/" + parseInt(filterType) + "?filter=" + filter,
            method: "get",
            dataType: "json"
        });
        showAds(ads);
    } catch (err) {
        let elemMain = document.getElementById("main");
        console.log(err);
        elemMain.innerHTML =
            "<h1>Página não está disponível</h1> <br>" +
            "<h2>Por favor tente mais tarde</h2>";
    }
}

// Getting active ads, filtered by a user-provided title
async function filterTitle() {
    try {
        let title = document.getElementById("title").value;
        let ads = await $.ajax({
            url: "/api/ads/active/title?t=" + title,
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

// Getting active ads, filtered by a user-provided category
async function filterCategory() {
    try {
        let category = document.getElementById("categories").value;
        let ads = await $.ajax({
            url: "/api/ads/active/category?c=" + category,
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