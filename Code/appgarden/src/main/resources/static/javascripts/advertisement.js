window.onload = async function () {
    loadAdvertisement()
}

// Loading the advertisement
async function loadAdvertisement() {
    try {
        let adID = sessionStorage.getItem("adId")
        let ad = await $.ajax({
            url: "/api/ads/" + adID,
            method: "get",
            dataType: "json"
        })
        console.log(ad)
        fillPage(ad)
    } catch (err) {
        console.log(err)
    }
}

// Filling in page with the ad information
function fillPage(ad) {
    document.getElementById("title").innerHTML = "<h2>" + ad.title + "</h2>"
    document.getElementById("seller").innerHTML = ad.seller.name
    document.getElementById("sellerProfile").innerHTML = "<input type='button' " +
        "value='Ver Perfil' onclick='showProfile(" + ad.seller.id + ")'>"

    document.getElementById("category").innerHTML = ad.category.name
    document.getElementById("description").innerHTML = ad.description
    document.getElementById("price").innerHTML = ad.price

    document.getElementById("addToCart").innerHTML = "<input type='button' " +
        "value='Adicionar ao Carrinho' onclick='verifyAd()'>"
}

// Redirecting user to the seller's profile page
function showProfile(sellerId) {
    sessionStorage.setItem("sellerId", sellerId);
    window.location = "seller.html";
}

// Verifying if the ad can be added to the user's cart
async function verifyAd() {

    let userID = sessionStorage.getItem("sessionUserId")

    // Reloading ad
    let ad
    try {
        let adID = sessionStorage.getItem("adId")
        ad = await $.ajax({
            url: "/api/ads/" + adID,
            method: "get",
            dataType: "json"
        })
        console.log(ad)
    } catch (err) {
        console.log(err)
    }

    // Loading user's cart
    let cartView
    try {
        cartView = await $.ajax({
            url: "/api/users/" + userID + "/cart",
            method: "get",
            dataType: "json"
        })
        console.log(cartView)
    } catch (err) {
        console.log(err)
    }

    // If the user has a cart, add the ad to it
    if (cartView != null) {
        try {
            let result = await $.ajax({
                url: "api/ads/" + ad.id + "/verification?buyerId=" + userID,
                method: "get",
                dataType: "json",
                contentType: "application/json"
            })
            if (result) addToCart(ad, cartView)
            else alert("Ops! Não será possível adicionar este item ao carrinho.")
        } catch(err) {
            console.log(err)
        }
    } else {
        // If the user does not have a cart, create one for them, and call the function again
        await $.ajax({
            url: "/api/users/" + userID + "/cart",
            method: "post",
            dataType: "json",
            contentType: "application/json"
        })
        verifyAd()
    }
}

// Adding ad to user's cart
async function addToCart(ad, cart) {

    try {
        let result = await $.ajax({
            url: "/api/transactions/cart/" + cart.transactionId,
            method: "post",
            dataType: "json",
            data: JSON.stringify({ id: ad.id }),
            contentType: "application/json"
        });
        if (result) alert("Item guardado no carrinho!")
    } catch (err) {
        console.log(err);
        alert("Ops! Não foi possível adicionar item ao carrinho. Tente novamente mais tarde.")
    }
}