let bungalowInput = document.getElementById("number");
bungalowInput.addEventListener("change", () => {
    let bungalowList = document.getElementsByClassName("bungalow-section")
    for (let i = 0; i < bungalowList.length; i++) {
       bungalowList[i].classList.add("d-none");
    }
    document.getElementById(bungalowInput.value).classList.toggle("d-none",false)
})
