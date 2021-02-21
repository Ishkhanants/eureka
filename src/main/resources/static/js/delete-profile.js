$(document).ready(function () {
    const addActivityBtn = $('#add-activity-btn2');
    const cancelBtn = $('#cancel-btn2');
    cancelBtn.focus();

    addActivityBtn.hover(function () {
        swapClass("#cancel-btn2", "activity-main-button", "#add-activity-btn2", "activity-whiteButton");
    }, function () {
        swapClass("#add-activity-btn2", "activity-main-button", "#cancel-btn2", "activity-whiteButton");
    });
    addActivityBtn.focus(function () {
        swapClass("#cancel-btn2", "activity-main-button", "#add-activity-btn2", "activity-whiteButton");
    });
    cancelBtn.hover(function () {
        swapClass("#add-activity-btn2", "activity-main-button", "#cancel-btn2", "activity-whiteButton");
    }, function () {
    });
    cancelBtn.focus(function () {
        swapClass("#add-activity-btn2", "activity-main-button", "#cancel-btn2", "activity-whiteButton");
    });

    function swapClass(selector1, selector1Class, selector2, selector2Class) {
        $(selector1).removeClass(selector1Class).addClass(selector2Class);
        $(selector2).removeClass(selector2Class).addClass(selector1Class);
    }
});
