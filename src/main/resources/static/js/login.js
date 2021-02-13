function success() {
    if (document.getElementById("username").value === "" || document.getElementById("password").value === "") {
        document.getElementById('submit-button').disabled = true;
    } else {
        document.getElementById('submit-button').disabled = false;
    }
}

let attemptsCount = 0;
const attemptsMaxCount = 3;

$(document).ready(function () {
    $.i18n().load({
        "en": "/i18n/en.json",
        "hy": "/i18n/hy.json",
        "ru": "/i18n/ru.json",
    });

    let localeValue = $("#locale").val();
    $.i18n().locale = localeValue;

    $("#username").val('');
    $('#submit-button').click(function (e) {
        authenticate(e);
    });
});

$("#username").focus();

function authenticate(e) {
    e.preventDefault();
    var action = $('#loginForm').attr("action");
    var token = $('input[name^="_csrf"]').val();

    var username = $('#username').val();
    var password = $('#password').val();
    var rememberMe = $("#remember-me").prop("checked");

    $.ajax({
        url: action,
        type: 'POST',
        beforeSend: function (request) {
            request.setRequestHeader("X-CSRF-TOKEN", token);
        },
        data: {"username": username, "password": password, "remember-me": rememberMe},
        datatype: 'json',
        success: function () {
            window.location.href = "/homepage";
        },
        error: function (xhr) {
            $("#errorMessage").removeClass("invisible");
            if (xhr.status === 409) {
                $("#validationMessage").html($.i18n("account.blocked.login"));
            } else if (xhr.status === 404) {
                $("#validationMessage").html($.i18n("username.not.found"));

            } else if (xhr.status === 401) {
                $("#validationMessage").html($.i18n("username.pass.incorrect"));
            }
            ++attemptsCount;
            if (attemptsCount >= attemptsMaxCount) {
                window.setTimeout(function () {
                    window.location.href = "/forgot-password";
                }, 1000);
            }
            $("#username").val('');
            $("#password").val('');
            $("#username").focus();
        }
    });
}


