let $tilContainer = $('.til-container').infiniteScroll({
  path: '.pagination__next',
  //     function() {
  //   return `/til/1/til-cards?page=${this.pageIndex-1}&size=5`;
  // },
  // load response as JSON
  responseBody: 'json',
  status: '.page-load-status',
  history: false,
});

$tilContainer.on( 'load.infiniteScroll', function( event, body ) {
  // compile body data into HTML
  let itemsHTML = body.map(getItemHTMLFromTilCard).join('');
  // convert HTML string into elements
  let $items =  $( itemsHTML );
  // append item elements
  $tilContainer.infiniteScroll( 'appendItems', $items );
});

// load initial page
$tilContainer.infiniteScroll('loadNextPage');

//------------------//
function getItemHTMLFromTilCard(data) {
  let result = document.createElement('div');

  let tilCard = document.createElement('article');
  let title = document.createElement('h2');
  let likeCount = document.createElement('p');
  let nickname = document.createElement('p');
  let createdAt = document.createElement('p');
  let tagList = document.createElement('ul');

  title.textContent = data.title;
  likeCount.textContent = data.likeCount;
  nickname.textContent = data.nickname;
  createdAt.textContent = DateFormat(data.createdAt);

  let tags = data.tags;
  for (let j = 0; j < tags.length; j++) {
    let listItem = document.createElement('li');
    listItem.textContent = tags[j];
    tagList.appendChild(listItem);
  }

  tilCard.appendChild(title);
  tilCard.appendChild(likeCount);
  tilCard.appendChild(nickname);
  tilCard.appendChild(createdAt);
  tilCard.appendChild(tagList);

  result.appendChild(tilCard);

  return result.outerHTML;
}

function DateFormat(date) {
  console.log(date);
  let yyyymmdd = date.substring(0, 10);
  let timeArr = yyyymmdd.split("-");
  return timeArr.join("/");
}