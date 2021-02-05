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

    if (ad.active) {
        document.getElementById("addToCart").innerHTML = 
            "<input type='button' value='Adicionar ao Carrinho' onclick='addToCart()'>"
    }   
}

// Redirecting user to the seller's profile page
function showProfile(sellerId) {
    sessionStorage.setItem("sellerId", sellerId);
    window.location = "seller.html";
}

// Verifying if the ad can be added to the user's cart
async function addToCart() {

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
    // The server has trouble deserializing a seller's transactions for some reason
    // So we remove it - the information is not used anyway (not an ideal solution)
    delete ad.seller["transactions"]

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
                url: "/api/transactions/cart/" + cartView.transactionId,
                method: "post",
                dataType: "json",
                data: JSON.stringify(ad),
                contentType: "application/json"
            })
            if (result) alert("Item guardado no carrinho!")
            else alert("Ops! Não será possível adicionar item ao carrinho.")
        } catch (err) {
            console.log(err)
            alert("Ops! Não foi possível adicionar item ao carrinho. Tente novamente mais tarde.")
        }
    } else {
        // If the user does not have a cart, create one for them, and call the function again
        await $.ajax({
            url: "/api/users/" + userID + "/cart",
            method: "post",
            dataType: "json",
            contentType: "application/json"
        })
        addToCart()
    }
}