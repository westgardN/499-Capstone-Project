var loadReport = function (reportName, reportData) {
    var chart = new CanvasJS.Chart("chartContainer", {

        title:{
            text: reportName
        },
        data: [//array of dataSeries
            { //dataSeries object

                /*** Change type "column" to "bar", "area", "line" or "pie"***/
                type: "column",
                dataPoints: processObjectListToDataPoints(reportData)
            }
        ]
    });

    chart.render();
};

var processObjectListToDataPoints = function(rawReportData) {
    var reportData = new Array(2);

    for (var i = 0; i < rawReportData.length; i++) {
        reportData[i] = {label: (rawReportData[i])[0], y: (rawReportData[i])[1]}
    }

    return reportData;
}

var getReportData = function(reportName, reportDataUrl) {
    var xmlhttp = new XMLHttpRequest();
    var url = reportDataUrl;

    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var reportData = JSON.parse(this.responseText);
            loadReport(reportName, reportData);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

var clickReport = function(reportName, reportDataUrl) {
    getReportData(reportName, reportDataUrl);

    return false;
}
