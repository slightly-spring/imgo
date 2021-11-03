async function deleteRivalConfirm(targetId) {
    let answer = confirm("라이벌을 삭제하시겠습니까?");

    if (answer) {
        const response = await fetch(`/rival/${targetId}`, {
            method: "DELETE"
        });
        if (response.ok) {
            alert("삭제 되었습니다.")
        } else {
            alert("삭제할 수 없습니다.")
        }
        window.location.href = document.referrer
    }
}