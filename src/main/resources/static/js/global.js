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

function closeLoginPopup() {
    const login = document.querySelector("#popup-login");
    login.style.display = "none";
}

function openLoginPopup() {
    const login = document.querySelector("#popup-login");
    login.style.display = "block";
}

async function clickRivalButton(targetId) {
    const rivalButton = document.querySelector('.rival-btn');
    const isActive = rivalButton.classList.contains("active");
    const method =  isActive ? "DELETE" : "POST";

    const response = await fetch(`/rival/${targetId}`, {
        method
    });

    if (response.ok) {
        if (isActive) {
            rivalButton.classList.remove("active")
            rivalButton.innerText = "라이벌 등록";
        } else {
            rivalButton.classList.add("active")
            rivalButton.innerText = "라이벌 해제";
        }
    } else {
        alert("오류가 발생하였습니다.")
    }
}

window.onload = function () {
    let $tilContainer = $('.til-container').infiniteScroll({
        path: '.pagination__next',
        // load response as JSON
        responseBody: 'json',
        status: '.page-load-status',
        history: false,
    });

    $tilContainer.on('load.infiniteScroll', function (event, body) {
        // compile body data into HTML
        let itemsHTML = body.map(getItemHTMLFromTilCard).join('');
        // convert HTML string into elements
        let $items = $(itemsHTML);
        // append item elements
        $tilContainer.infiniteScroll('appendItems', $items);
    });

    // load initial page
    $tilContainer.infiniteScroll('loadNextPage');


    let $seriesContainer = $('.series-container').infiniteScroll({
        path: '.pagination__next_series',
        responseBody: 'json',
        status: '.page-load-status',
        history: false,
    });

    $seriesContainer.on('load.infiniteScroll', function (event, body) {
        // compile body data into HTML
        let itemsHTML = body.map(getItemHTMLFromSeriesCard).join('');
        // convert HTML string into elements
        let $items = $(itemsHTML);
        // append item elements
        $seriesContainer.infiniteScroll('appendItems', $items);
    });

    // load initial page
    $seriesContainer.infiniteScroll('loadNextPage');
}