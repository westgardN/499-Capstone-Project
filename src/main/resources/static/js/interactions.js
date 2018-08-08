var updateInteractionStatusAndRemoveFromDom = function (elementId, endPoint) {
    var headers = {};

    if (csrfHeader !== undefined && csrfHeader !== null && csrfHeader !== "") {
        headers[csrfHeader] = csrfToken;
    }

    var data = {};

    if (csrfParameter !== undefined && csrfParameter !== null && csrfParameter !== "") {
        data[csrfParameter] = csrfToken;
    }

    $.ajax({
        url: endPoint,
        headers: headers,
        type: "POST",
        data: data,
        success: function (dataResult, status, xhr) {
            if (status == "success") {
                $("#" + elementId).fadeOut(300, function () {
                    $("#" + elementId).remove();
                });
            }
        },
        error: function (xhr, status, error) {
            windows.alert("Status: " + status + "\nError: " + error);
        }
    });
};

var ignoreInteraction = function (elementId, endPoint) {
    updateInteractionStatusAndRemoveFromDom(elementId, endPoint);
};

var reopenInteraction = function (elementId, endPoint) {
    updateInteractionStatusAndRemoveFromDom(elementId, endPoint);
};

var closeInteraction = function (elementId, endPoint) {
    updateInteractionStatusAndRemoveFromDom(elementId, endPoint);
};

var followUpInteraction = function (elementId, endPoint) {
    updateInteractionStatusAndRemoveFromDom(elementId, endPoint);
};

var replyInteraction = function (elementId, endPoint) {
    updateInteractionStatusAndRemoveFromDom(elementId, endPoint);
};