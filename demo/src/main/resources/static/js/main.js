document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const msg = params.get("msg");

    if (msg) {
        document.getElementById("dom-output").innerText = msg;
    }
});
