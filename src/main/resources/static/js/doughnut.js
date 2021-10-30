var configd = {
    type: 'doughnut',
    data: {
        datasets: [{
            data: [
                50,
                60,
                20,
                10,
                20

            ],
            backgroundColor: [
                '#ffde72',
                "#156a2b",
                "#4bc0c0",
                "#CDDC39",
                "#9C27B0",
                "#fb7145",
                "#5971f9"
            ],
            label: 'Energy usage'
        }],
        labels: [
            'CSS',
            'VPC',
            'Git',
            'Spring',
            'Javascript'
        ]
    },
    options: {
        responsive: true,
        legend: {
            display: false
        },
        legendCallback: function (chart) {
            // Return the HTML string here.
            console.log(chart.data.datasets);
            var text = [];
            text.push('<ul class="' + chart.id + '-legend">');
            for (var i = 0; i < chart.data.datasets[0].data.length; i++) {
                text.push('<li><span id="legend-' + i + '-item" style="background-color:' + chart.data.datasets[0].backgroundColor[i] + '"   onclick="updateDataset(event, ' + '\'' + i + '\'' + ')">');
                if (chart.data.labels[i]) {
                    text.push(chart.data.labels[i]);
                }
                text.push('</span></li>');
            }
            text.push('</ul>');
            return text.join("");
        },
        title: {
            display: false,
            text: 'Chart.js Doughnut Chart'
        },
        animation: {
            animateScale: true,
            animateRotate: true
        },
        tooltips: {
            mode: 'index',
            intersect: false,
            callbacks: {
                label: function (tooltipItem, data) {
                    let label = data.datasets[tooltipItem.datasetIndex].label + ' - ' + data.labels[tooltipItem.index];
                    let datasetLabel = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                    return label + ': ' + datasetLabel.toLocaleString();
                }
            }
        },
    }
};

 var ctxd = document.getElementById('canvas').getContext('2d');

    window.myDoughnut = new Chart(ctxd, configd);
    $("#do_legend").html(window.myDoughnut.generateLegend());

// Show/hide chart by click legend
updateDataset = function (e, datasetIndex) {
    var index = datasetIndex;
    var ci = e.view.myDoughnut;
    var meta = ci.getDatasetMeta(0);
    var result= (meta.data[datasetIndex].hidden == true) ? false : true;
    if(result==true)
    {
        meta.data[datasetIndex].hidden = true;
        $('#' + e.path[0].id).css("text-decoration", "line-through");
    }else{
        $('#' + e.path[0].id).css("text-decoration","");
        meta.data[datasetIndex].hidden = false;
    }

    ci.update();
};
