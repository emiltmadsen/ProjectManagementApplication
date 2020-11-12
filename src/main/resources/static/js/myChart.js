// Uses the decodeHtml to make chartData readable
var chartDataStr = decodeHtml(chartData);

// Parses the string from chartDataStr into an array
var chartJsonArray = JSON.parse(chartDataStr);


var arrayLength = chartJsonArray.length;

var numericData = [];
var labelData = [];

// Let's us populate the array
for(var i=0; i < arrayLength; i++){
    numericData[i] = chartJsonArray[i].value;
    labelData[i] = chartJsonArray[i].label;
}

new Chart(document.getElementById("myPieChart"), {
    type: 'pie',
    // The data for our dataset
    data: {
        labels: labelData,
        datasets: [{
            label: 'My First dataset',
            backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f"],
            data: numericData
        }]
    },

    // Configuration options go here
    options: {
        title: {
            display: true,
            text: 'Project Statuses'
        }
    }
});

// Decodes data and makes it readable - turns encoded JS into json
function decodeHtml(html){
    var txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
}