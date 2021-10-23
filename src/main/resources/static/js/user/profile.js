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