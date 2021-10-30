$(function() {

    $('.dropdown > .caption').on('click', function() {
        $(this).parent().toggleClass('open');
    });

    $(document).on('keyup', function(evt) {
        if ( (evt.keyCode || evt.which) === 27 ) {
            $('.dropdown').removeClass('open');
        }
    });

    $(document).on('click', function(evt) {
        if ( $(evt.target).closest(".dropdown > .caption").length === 0 ) {
            $('.dropdown').removeClass('open');
        }
    });

});

async function deleteConfirm(tilId) {
    let answer = confirm("TIL을 삭제하시겠습니까?");

    if (answer) {
        const response = await fetch(`/til/${tilId}`, {
            method: "DELETE"
        });
        if (response.ok) {
            alert("삭제 되었습니다.")
            window.location.href = "/";
        } else {
            alert("삭제할 수 없습니다.")
            window.location.href = document.referrer
        }
    }

}

function copyUrlToClipboard(){
    const dummy   = document.createElement("input");
    const text    = location.href;

    document.body.appendChild(dummy);
    dummy.value = text;
    dummy.select();
    document.execCommand("copy");
    document.body.removeChild(dummy);
    alert("주소가 클립보드에 복사되었습니다.")
}