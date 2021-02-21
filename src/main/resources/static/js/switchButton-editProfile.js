$(document).ready(function () {
    const addActivityBtn1 = $('#add-activity-btn');
    const cancelBtn1 = $('#cancel-btn');

    cancelBtn1.focus();
    addActivityBtn1.hover(function () {
        swapClass("#cancel-btn", "activity-main-button", "#add-activity-btn", "activity-whiteButton");
    }, function () {
    });
    addActivityBtn1.focus(function () {
        swapClass("#cancel-btn", "activity-main-button", "#add-activity-btn", "activity-whiteButton");
    });
    cancelBtn1.hover(function () {
        swapClass("#add-activity-btn", "activity-main-button", "#cancel-btn", "activity-whiteButton");
    }, function () {
        swapClass("#add-activity-btn", "activity-whiteButton", "#cancel-btn", "activity-main-button");

    });
    cancelBtn1.focus(function () {
        swapClass("#add-activity-btn", "activity-main-button", "#cancel-btn", "activity-whiteButton");
    });

    function swapClass(selector1, selector1Class, selector2, selector2Class) {
        $(selector1).removeClass(selector1Class).addClass(selector2Class);
        $(selector2).removeClass(selector2Class).addClass(selector1Class);
    }
});