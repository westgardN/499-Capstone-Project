var updateSocialAccountStatusAndRemoveFromDom = function (elementId, endPoint) {
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
            window.alert("Status: " + status + "\nError: " + error);
        }
    });
};

var deleteSocialNetwork = function (elementId, endPoint) {
    updateSocialAccountStatusAndRemoveFromDom(elementId, endPoint);
};

