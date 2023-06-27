function searchRequests() {
    var namaPengaju = document.getElementById("namaPengaju").value;
    var bentukRequest = document.getElementById("bentukRequest").value;
    var judulRequest = document.getElementById("judulRequest").value;

    var xhr = new XMLHttpRequest();
    var url = "/search-requests?namaPengaju=" + encodeURIComponent(namaPengaju)
        + "&bentukRequest=" + encodeURIComponent(bentukRequest)
        + "&judulRequest=" + encodeURIComponent(judulRequest);

    xhr.open("GET", url, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            // Update the page with the search results
        }
    };

    xhr.send();
}
