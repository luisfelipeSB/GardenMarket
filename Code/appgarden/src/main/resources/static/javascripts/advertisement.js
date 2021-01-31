window.onload = async function () {

    try {
        let adId = sessionStorage.getItem("adId");
        let ad = await $.ajax({
            url: "/api/ads/" + adId,
            method: "get",
            dataType: "json"
        });
        console.log(ad);

        document.getElementById("title").innerHTML = "<h2>" + ad.title + "</h2>";
        document.getElementById("seller").innerHTML = ad.seller.name;
        document.getElementById("category").innerHTML = ad.category.name;
        document.getElementById("description").innerHTML = ad.description;
        document.getElementById("price").innerHTML = ad.price;

        document.getElementById("sellerProfile").innerHTML = "<input type='button' " +
            "value='ver perfil' onclick='showProfile(" + ad.seller.id + ")'>";
    } catch (err) {
        console.log(err)
    }
}

function showProfile(sellerId) {
    sessionStorage.setItem("sellerId", sellerId);
    window.location = "seller.html";
}

// To add an item to a user's cart, we 1) check whether theyre trying to buy their own ad, 
// 2) check whether they already have a cart; if they do, we add it to that cart
// Otherwise, we create a cart for them, then add the ad to it.

async function checkAdSeller() {

    let adId = sessionStorage.getItem("adId");
    let ad = await $.ajax({
        url: "/api/ads/" + adId,
        method: "get",
        dataType: "json"
    });

    // Getting the user
    let userId = sessionStorage.getItem("sessionUserId");
    let user = await $.ajax({
        url: "/api/users/" + userId,
        method: "get",
        dataType: "json"
    });
    console.log(user);

    // Checking whether the user is trying to buy their own item ALSO CHECK WHETHER THE ITEM THEY'RE BUYING IS INACTIVE
    if (ad.seller.id == userId) {
        alert("Oops! Não é permitido comprar vosso próprio anúncio.")
    } else {
        // If it's not their own ad, we begin the process of adding it to their cart
        getCartTransaction(userId)
    }

}

async function getCartTransaction(userId) {

    // Checking whether the user already has a cart
    let cartView = await $.ajax({
        url: "api/transactions/user/" + parseInt(userId) + "/cart",
        method: "get",
        datatype: "json"
    });
    console.log(cartView);      // cartView is _ if there is no cart

    if (cartView != null) {
        // If they do, we get that cart and send it to addToCart()
        let cart = await $.ajax({
            url: "api/transactions/" + cartView.transactionId,
            method: "get",
            datatype: "json"
        });
        addToCart(cart);
    } else {
        // If they don't, we create one for them, then send it to addToCart()
        let newCart = await $.ajax({
            url: "/api/transactions/createCart/" + userId,
            method: "post",
            dataType: "json",
            data: JSON.stringify(userId),       // sending this for no reason
            contentType: "application/json"
        });
        addToCart(newCart);
    }
}

async function addToCart(cart) {

    console.log(cart.id)

    let adId = sessionStorage.getItem("adId");
    let ad = await $.ajax({
        url: "/api/ads/" + adId,
        method: "get",
        dataType: "json"
    });

    console.log(JSON.stringify(ad.id))

    // Having received the cart, and knowing what ad to ad to it, we call the post method
    try {
        let result = await $.ajax({
            url: "/api/transactions/" + cart.id + "/addToCart",
            method: "post",
            dataType: "json",
            data: JSON.stringify({ id: ad.id }),
            contentType: "application/json"
        });
        alert(JSON.stringify(result));
    } catch (err) {
        console.log(err);
        alert("Ops! Não foi possível adicionar item ao carrinho. Tente novamente mais tarde.")
    }
}