var updateInteractionStatusAndRemoveFromDom = function (elementId, endPoint) {
    $.post(endPoint, function (reportData, status) {
        if (status == "success") {
            $("#" + elementId).fadeOut(300, function() {
                $("#" + elementId).remove();
            });
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