$(document).ready(async function () {
    let image = $('#image');
    const csrfHeader = "X-CSRF-TOKEN";
    let csrfToken = $('input[name^="csrf_token"]').val();
    let username = $('#fc-username').text();

    // await $.i18n().load({
    //     "en": "/i18n/en.json",
    //     "hy": "/i18n/hy.json",
    //     "ru": "/i18n/ru.json",
    // });

    // let localeValue = $("#locale").val();
    // $.i18n().locale = localeValue;

    $(".section").click(function () {
        $(".section").removeClass("active");
        $(this).addClass("active");
    });

    $("#profile-dropdown").click(function () {
        $(this).children("i").toggleClass("fa-angle-down fa-angle-up");
        $(this).parent().find("div").toggle(300);
    });

    $.ajax({
        type: 'GET',
        url: location.origin + '/edit-profile/avatar/' + username,
        beforeSend: function (request) {
            request.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (data, textStatus, xhr) {
            console.log(username);
            if (xhr.status === 204) image.attr('src', 'images/no-avatar.png');
            else image.attr('src', 'data:image/jpg;base64,' + data)
        },
        error: function () {
            image.attr('src', 'images/no-avatar.png');
        }
    });

    if (location.pathname.substring(0,10) === "/issues/my") {
        $('#products').removeClass("select-tab-border");
        $("#my-issues").addClass("select-tab-border");
    } else if (location.pathname.substring(0,9) === "/products") {
        $('#products').addClass("select-tab-border");
        $("#my-issues").removeClass("select-tab-border");
    } else if (location.pathname === "/users") {
        $('#users').addClass("select-tab-border");
        $('#products').removeClass("select-tab-border");
    } else if (location.pathname.includes("/reports")) {
        $('#reports').addClass("select-tab-border");
        $("#my-issues").addClass("select-tab-border");
        $('#products').removeClass("select-tab-border");
    } else if (location.pathname.substring(0,7) === "/issues") {
        $('#issues').addClass("select-tab-border");
        $("#my-issues").addClass("select-tab-border");
        $('#products').removeClass("select-tab-border");
    }

    $('#issues').click(function () {
        $(this).addClass("select-tab-border");
        $('#my-issues').removeClass("select-tab-border");
        }
    );

    $("#my-issues").click(function () {
        saveViewDateAndMoveTo("/issues/my");
    });

    $("#users").click(function () {
        saveViewDateAndMoveTo("/users");
    })

    $("#issues").click(function () {
        saveViewDateAndMoveTo("/issues");
    })

    $("#issues").click(function () {
        saveViewDateAndMoveTo("/issues/create");
    })

    $("#products").click(function () {
        saveViewDateAndMoveTo("/");
    });

    $("#reports").click(function () {
        saveViewDateAndMoveTo("/issues/reports")
    })
});

saveViewDateAndMoveTo = endpoint => {
    location.href = location.origin + endpoint;
}
