let isArrowBoxFold = true;
function clickMypage() {
    const mypage = document.querySelector(".mypage");
    const arrowBox = document.querySelector(".arrow-box");
    if (isArrowBoxFold) {
        mypage.classList.add("selected")
        arrowBox.style.display = "block";
        isArrowBoxFold = false;
    } else {
        mypage.classList.remove("selected")
        arrowBox.style.display = "none";
        isArrowBoxFold = true;
    }
}