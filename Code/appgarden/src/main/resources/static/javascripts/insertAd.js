async function insertAd() {

    try {

        // Constructing the new advertisement
        let newAd = {
            seller: { id: sessionStorage.getItem("sessionUserId") },
            category: { id: document.getElementById("categories").value },
            title: document.getElementById("title").value,
            description: document.getElementById("description").value,
            price: document.getElementById("price").value,
            active: true
        }
        console.log(JSON.stringify(newAd))

        // Sending new ad to server
        let result = await $.ajax({
            url: "/api/ads/",
            method: "post",
            dataType: "json",
            data: JSON.stringify(newAd),
            contentType: "application/json"
        });
        if(result) alert("Anúncio inserido com sucesso!")
        
    } catch (err) {
        console.log(err);
        alert("Não foi possível inserir o anúncio. Tente novamente mais tarde.")
    }
}