$(document).ready(function () {
    let langName, imgName;
    const angle = "#lang-selector i";
    let locale = $("#locale").val();

    switch ($("#footer-locale").val()) {
        case "en":
            langName = "English";
            imgName = "/images/us-circle.png";
            break;
        case "hy":
            langName = "Armenian";
            imgName = "/images/am-circle.png";
            break;
        case "ru":
            langName = "Russian";
            imgName = "/images/ru-circle.png";
    }

    $("#lang-selector small").html(langName);
    $("#lang-selector img").attr("src", imgName);

    $("#lang-selector").click(function () {
        $(this).find("i").toggleClass("fa-angle-up fa-angle-down");
    });

    $(".lang-block").click(function () {
        $("#lang-selector").find("i").toggleClass("fa-angle-up fa-angle-down");
    });

    $(document).click(function () {
        if ($(angle).hasClass("fa-angle-down")) {
            $(angle).toggleClass("fa-angle-down fa-angle-up");
        }
    });

});
