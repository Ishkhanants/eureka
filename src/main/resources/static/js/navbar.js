$(document).ready(function () {

    $(".section").click(function () {
        $(".section").removeClass("active");
        $(this).addClass("active");
    });

    $("#profile-dropdown").click(function () {
        $(this).children("i").toggleClass("fa-angle-down fa-angle-up");
        $(this).parent().find("div").toggle(300);
    });
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
