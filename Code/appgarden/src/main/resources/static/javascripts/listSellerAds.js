// Aqui pretendo definir a injeção de HTML em seller.html, de forma que mostre seus anúncios

window.onload = async function() {
    let sellerId = sessionStorage.getItem("sellerId");

    let seller = await $.ajax({
        url: "/api/users/"+sellerId,
        method: "get",
        dataType: "json"
    });
    console.log(seller);

    document.getElementById("seller").innerHTML = seller.name;
}