$(document).ready(function () {

    $(".show-hide-password").on('click', function (event) {
        event.preventDefault();
        var input = $(this).parentsUntil('.input_div').children('input');

        $(this).toggleClass("fa-eye fa-eye-slash");

        if (input.attr("type") === "text") {
            input.attr('type', 'password');
        } else if (input.attr("type") === "password") {
            input.attr('type', 'text');
        }
    });

});

// function addDatePicker() {
//     let localeValue = $("#locale").val();
//     let today = new Date();
//     const GMT = 4;
//     today.setHours(today.getHours() + GMT);
//
//     $("#date").datepicker({
//         format: "dd-mm-yyyy",
//         autoclose: true,
//         todayHighlight: true,
//         weekStart: 1,
//         daysOfWeekDisabled: "0",
//         orientation: "auto bottom",
//         startDate: today,
//         language: localeValue
//     });
// }