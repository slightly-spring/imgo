const Editor = toastui.Editor;
const editor = new Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'markdown',
    previewStyle: 'vertical'
});
editor.getMarkdown();

const tagInput = document.querySelector('#tag-input');
const tagify = new Tagify(tagInput, {
    originalInputValueFormat: valuesArr => valuesArr.map(item => item.value).join(',')
});

const seriesInput = document.querySelector('#series-input');

async function searchMySeries() {
    const seriesList = document.querySelector('#series-list');

    const result = await _findMySeries(seriesInput.dataset.userId, seriesInput.value);
    if (result.length) {
        let series = "";
        for (r of result) {
            series += `<option value=${r.id}>${r.title}</option>`
        }
        seriesList.innerHTML = `<select class="form-select" size=${result.length}>` + series + "</select>";
    } else {
        seriesList.innerHTML = `<button type="button" onclick="addNewSeries(${seriesInput.dataset.userId}, '${seriesInput.value}')" value="${seriesInput.value}">'${seriesInput.value}'으로 시리즈 추가</button>`;
    }

}

async function _findMySeries(userId, q) {
    const url = `/series/${userId}?title=${q}`;
    let response = await fetch(url, {
        method: "GET",
    });
    if (response.ok) {
        return await response.json();
    } else {
        alert("HTTP-Error: " + response.status);
    }
}

async function addNewSeries(userId, q) {
    const url = `/series/${userId}`;
    let response = await fetch(url, {
        method: "POST",
        headers: {
            "content-type" : "application/json"
        },
        body: JSON.stringify({title: q})
    });
    if (response.ok) {
        seriesInput.value = "";
        await searchMySeries();
        return await response.json();
    } else {
        alert("HTTP-Error: " + response.status);
    }
}

function selectSeries() {
    const seriesTitle = document.querySelector('#series-title');
    const seriesSelect = document.querySelector('#series-select');
    seriesTitle.innerHTML = seriesSelect.options[seriesSelect.selectedIndex].text;
}

function onSubmit() {
    document.querySelector("#til-content").value = editor.getHTML();
    document.querySelector("#tags").value = tagInput.value.split(",");
    return true;
}