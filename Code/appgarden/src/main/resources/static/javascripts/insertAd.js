async function insertAd() {
    try {
        let ad = {
            title: document.getElementById("title").value,
            category: document.getElementById("categories").value,
            description: document.getElementById("description").value,
            price: document.getElementById("price").value,
        }
        alert(JSON.stringify(ad));
        let result = await $.ajax({
            url: "/api/ads",
            method: "post",
            dataType: "json",
            data: JSON.stringify(ad),
            contentType: "application/json"
        });
        alert(JSON.stringify(result));
    } catch (err) {
        console.log(err);
        // mensagem para o utilizador
    }
}