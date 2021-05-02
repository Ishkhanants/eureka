$(document).ready(function () {
    const addActivityBtn1 = $('.add-activity-btn');
    const cancelBtn1 = $('.cancel-btn');
    const addActivityBtn2 = $('#add-activity-btn2');
    const cancelBtn2 = $('#cancel-btn2');

    cancelBtn1.focus();
    cancelBtn2.focus();

    addActivityBtn1.hover(() => {
        swapClass(".cancel-btn", "activity-main-button", ".add-activity-btn", "activity-whiteButton");
    },  () => {});

    addActivityBtn1.focus(() => {
        swapClass(".cancel-btn", "activity-main-button", ".add-activity-btn", "activity-whiteButton");
    });

    cancelBtn1.hover(() => {
        swapClass(".add-activity-btn", "activity-main-button", ".cancel-btn", "activity-whiteButton");
    }, () => {
        swapClass(".add-activity-btn", "activity-whiteButton", ".cancel-btn", "activity-main-button");
    });

    cancelBtn1.focus(() => {
        swapClass(".add-activity-btn", "activity-main-button", ".cancel-btn", "activity-whiteButton");
    });

    addActivityBtn2.hover(() => {
        swapClass("#cancel-btn2", "activity-main-button", "#add-activity-btn2", "activity-whiteButton");
    }, () => {
        swapClass("#add-activity-btn2", "activity-main-button", "#cancel-btn2", "activity-whiteButton");
    });

    addActivityBtn2.focus(() => {
        swapClass("#cancel-btn2", "activity-main-button", "#add-activity-btn2", "activity-whiteButton");
    });

    cancelBtn2.hover(() => {
        swapClass("#add-activity-btn2", "activity-main-button", "#cancel-btn2", "activity-whiteButton");
    }, () => {});

    cancelBtn2.focus(() => {
        swapClass("#add-activity-btn2", "activity-main-button", "#cancel-btn2", "activity-whiteButton");
    });

    let swapClass = (selector1, selector1Class, selector2, selector2Class) => {
        $(selector1).removeClass(selector1Class).addClass(selector2Class);
        $(selector2).removeClass(selector2Class).addClass(selector1Class);
    }
})
