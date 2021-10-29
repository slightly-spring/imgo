function getItemHTMLFromTilCard(data) {
    return `
    <li class="post">
        <div class="content-info">
            <a href="/til/${data.tilId}">
                <strong class="title">${data.title}</strong>
            </a>
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
        <a href="/til/${data.tilId}">
           <img src="${data.tilImageUrl || "/img/til-card-placeholder.png"}"/>
        </a>
    </li>`;
}