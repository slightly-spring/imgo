const Editor = toastui.Editor;
const editor = new Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'markdown',
    previewStyle: 'vertical'
});

editor.getMarkdown();

const inputElement = document.querySelector('#tag_input');
new Tagify(inputElement);