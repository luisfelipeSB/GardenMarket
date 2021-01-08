window.onload = async function() {
    let adId = sessionStorage.getItem("adId");

    let ad = await $.ajax({
        url: "/api/ads/"+adId,
        method: "get",
        dataType: "json"
    });
    console.log(ad);

    document.getElementById("title").innerHTML = ad.title;
    document.getElementById("seller").innerHTML = ad.seller.name;
    document.getElementById("category").innerHTML = ad.category.name;
    document.getElementById("description").innerHTML = ad.description;
    document.getElementById("price").innerHTML = ad.price;
}