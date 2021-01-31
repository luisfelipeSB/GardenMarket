async function insertAd() {
    try {

        let user = await $.ajax({
            url: "/api/users/" + sessionStorage.getItem("sessionUserId"),
            method: "get",
            dataType: "json"
        });
        console.log(user)

        catg = document.getElementById("categories")
        let ad = {
            active: true
        }
        let sellerId = sessionStorage.getItem("sessionUserId")
        let catgId = catg.value
        let title = document.getElementById("title").value
        let description = document.getElementById("description").value
        let price = document.getElementById("price").value

        alert(JSON.stringify(ad));
        let result = await $.ajax({
            url: "/api/ads/" + sellerId + "/" + catgId + "/" + title + "/" + description + "/" + price,
            method: "post",
            dataType: "json",
            data: JSON.stringify(ad.active),
            contentType: "application/json"
        });
        alert(JSON.stringify(result));
    } catch (err) {
        console.log(err);
        // mensagem para o utilizador
    }
}