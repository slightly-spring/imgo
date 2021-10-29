function getItemHTMLFromSeriesCard(data) {
  return `<li class="series">
           <a href="/series/${data.seriesId}">
               <div class="img-box" style="background-image: url(${data.seriesImageUrl || "/img/series-card-placeholder.png"})">
               </div>
           </a>
           <div class="content-info">
             <div class="info-left">
                <a href="/series/${data.seriesId}">
                   <strong class="title">${data.title}</strong>
                   <strong class="description">${data.description}</strong>
               </a>
               <div class="tag-box">
                ${data.tags[0] ? `<button type="button" class="tag-box-btn">${data.tags[0]}</button>`: ''}
                ${data.tags[1] ? `<button type="button" class="tag-box-btn">${data.tags[1]}</button>`: ''}
               </div>
             </div>
             <div class="info-right">
               <button class="scrap"><img src="/img/scrap.svg"></button>
               <button class="check"><img src="/img/completed.svg"></button>
             </div>
          </div>


      </li>`;
}