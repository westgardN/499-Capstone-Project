var loadReport = function (reportName, reportData, reportType) {

    if (reportData === undefined || reportData === null || reportData.length == 0) {
        $("#chartContainer").empty();
        $("#chartContainer").append("<h3>No data</h3>")
    } else {
        var chart = new CanvasJS.Chart("chartContainer", {

            title:{
                text: reportName
            },
            data: [//array of dataSeries
                { //dataSeries object

                    /*** Change type "column" to "bar", "area", "line" or "pie"***/
                    type: reportType,
                    dataPoints: processObjectListToDataPoints(reportData)
                }
            ]
        });

        chart.render();
    }
};

var processObjectListToDataPoints = function(rawReportData) {
    var reportData = new Array(rawReportData.length);

    for (var i = 0; i < rawReportData.length; i++) {
        reportData[i] = {label: (rawReportData[i])[0], y: (rawReportData[i])[1]}
    }

    return reportData;
}

var getReportData = function(reportName, reportDataUrl, reportType) {
    if (reportType === undefined || reportType === null || reportType === "") {
        reportType = "column";
    }

    var headers = {};

    if (csrfHeader !== undefined && csrfHeader !== null && csrfHeader !== "") {
        headers[csrfHeader] = csrfToken;
    }

    $.ajax({
        url: reportDataUrl,
        cache: false,
        type: "GET",
        dataType: "json",
        headers: headers,
        success: function (reportData, status, xhr) {
            if (status == "success") {
                loadReport(reportName, reportData, reportType);
            }
        }
    });
    // $.get(reportDataUrl, function (reportData, status) {
    //     if (status == "success") {
    //         loadReport(reportName, reportData, reportType);
    //     }
    // });
}

var clickReport = function(reportName, reportDataUrl) {
    getReportData(reportName, reportDataUrl);

    return false;
}
