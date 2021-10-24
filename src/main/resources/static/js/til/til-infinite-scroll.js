function getItemHTMLFromTilCard(data) {
    return `
    <li class="post">
        <div class="content-info">
            <strong class="title">${data.title}</strong>
            <div class="tag-box">
                ${data.tags[0] ? `<button type="button" class="tag-box-btn">${data.tags[0]}</button>`: ''}
                ${data.tags[1] ? `<button type="button" class="tag-box-btn">${data.tags[1]}</button>`: ''}
            </div>
            <div class="detail-box">
                <span class="detail-info">${data.createdAt}</span>
                <span class="like"><img src="/img/like.svg"/>${data.likeCount}</span>
                <span class="writer">by ${data.nickname}</span>
            </div>
        </div>
        <img src="${data.tilImage ?? "/img/til-card-placeholder.png"}"/>
    </li>`;
}