let $seriesContainer = $('.series-container').infiniteScroll({
  path: '.pagination__next_series',
  responseBody: 'json',
  status: '.page-load-status',
  history: false,
});

$seriesContainer.on( 'load.infiniteScroll', function( event, body ) {
  // compile body data into HTML
  let itemsHTML = body.map(getItemHTMLFromSeriesCard).join('');
  // convert HTML string into elements
  let $items =  $( itemsHTML );
  // append item elements
  $seriesContainer.infiniteScroll( 'appendItems', $items );
});

// load initial page
$seriesContainer.infiniteScroll('loadNextPage');

//------------------//
function getItemHTMLFromSeriesCard(data) {
  let result = document.createElement('div');

  let seriesCard = document.createElement('article');
  let title = document.createElement('h2');
  let description = document.createElement('p');
  let isCompleted = document.createElement('p');
  let tagList = document.createElement('ul');

  title.textContent = data.title;
  description.textContent = data.description;
  isCompleted.textContent = data.completed;

  let tags = data.tags;
  for (let j = 0; j < tags.length; j++) {
    let listItem = document.createElement('li');
    listItem.textContent = tags[j];
    tagList.appendChild(listItem);
  }

  seriesCard.appendChild(title);
  seriesCard.appendChild(description);
  seriesCard.appendChild(isCompleted);
  seriesCard.appendChild(tagList);

  result.appendChild(seriesCard);

  return result.outerHTML;
}

// function DateFormat(date) {
//   console.log(date);
//   let yyyymmdd = date.substring(0, 10);
//   let timeArr = yyyymmdd.split("-");
//   return timeArr.join("/");
// }