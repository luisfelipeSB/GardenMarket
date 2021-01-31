async function insertAd() {
    try {

        let user = await $.ajax({
            url: "/api/users/" + sessionStorage.getItem("sessionUserId"),
            method: "get",
            dataType: "json"
        });

        catg = document.getElementById("categories")
        let ad = {
            seller: user,
            category: { id: catg.value, name: catg.options[catg.selectedIndex].text }, 
            title: document.getElementById("title").value,
            description: document.getElementById("description").value,
            price: document.getElementById("price").value,
            active: true
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