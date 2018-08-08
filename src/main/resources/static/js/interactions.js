var updateInteractionStatusAndRemoveFromDom = function (elementId, endPoint, replyLink) {
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

                // if (replyLink !== undefined && replyLink !== null && replyLink !== "") {
                //     $("#replyBody").load(replyLink, function() {
                //         $("replyModal").modal({backdrop: "static"});
                //     });
                //
                // }
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

var closeInteraction = function (elementId, endPoint, replyLink) {
    updateInteractionStatusAndRemoveFromDom(elementId, endPoint, replyLink);
};

var followUpInteraction = function (elementId, endPoint) {
    updateInteractionStatusAndRemoveFromDom(elementId, endPoint);
};

var replyInteraction = function (elementId, endPoint, replyLink) {
    updateInteractionStatusAndRemoveFromDom(elementId, endPoint, replyLink);
};