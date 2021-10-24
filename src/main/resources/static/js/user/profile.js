let $tilContainer = $('.til-container').infiniteScroll({
    path: '.pagination__next',
    // load response as JSON
    responseBody: 'json',
    status: '.page-load-status',
    history: false,
});

$tilContainer.on( 'load.infiniteScroll', function( event, body ) {
    // compile body data into HTML
    let itemsHTML = body.map(getItemHTMLFromTilCard).join('');
    // convert HTML string into elements
    let $items =  $(itemsHTML);
    // append item elements
    $tilContainer.infiniteScroll( 'appendItems', $items );
});

// load initial page
$tilContainer.infiniteScroll('loadNextPage');


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