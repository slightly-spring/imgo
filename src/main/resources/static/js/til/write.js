const Editor = toastui.Editor;
const editor = new Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'markdown',
    previewStyle: 'vertical'
});
editor.getMarkdown();

const tagInput = document.querySelector('#tag_input');
const tagify = new Tagify(tagInput, {
    originalInputValueFormat: valuesArr => valuesArr.map(item => item.value).join(',')
});

function findMySeries() {
    // 검색
    // 비어있으면 기본값 show
    // 있으면 검색된 list 출력
    // 없으면 검색 버튼 출력
}

function onSubmit() {
    document.querySelector("#til-content").value = editor.getHTML();
    document.querySelector("#tags").value = tagInput.value.split(",");
    return true;
}