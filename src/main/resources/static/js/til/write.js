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

function findSeries() {

}

function onSubmit() {
    document.querySelector("#til-content").value = editor.getHTML();
    document.querySelector("#tags").value = tagInput.value.split(",");
    return true;
}