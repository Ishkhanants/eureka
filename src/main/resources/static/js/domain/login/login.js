function success() {
    if (document.getElementById("username").value === "" ||
        document.getElementById("password").value === "") {
        document.getElementById('submit-button').disabled = true;
    } else {
        document.getElementById('submit-button').disabled = false;
    }
}

let localStorage = window.localStorage;
let attemptsCount = 0;
const ATTEMPTS_MAX_COUNT = 3;
const REJECTION_TIMEOUT = 60 * 5;
// localStorage.clear();
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
    let action = $('#loginForm').attr("action");
    let token = $('input[name^="_csrf"]').val();

    let username = $('#username').val();
    let password = $('#password').val();
    let rememberMe = $("#remember-me").prop("checked");

    $.ajax({
        url: action,
        type: 'POST',
        beforeSend: function (request) {
            request.setRequestHeader("X-CSRF-TOKEN", token);
        },
        data: {"username": username, "password": password, "remember-me": rememberMe},
        datatype: 'json',
        success: function () {
            window.location.href = "/products";
            console.log("LOL!")
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

            localStorage.setItem('ipCounter', 0);

            getIPs().then(ips => {
                    let count = localStorage.getItem('ipCounter');

                    if (Object.values(localStorage).indexOf(ips[0]) > -1) {
                        localStorage.setItem('ipCounter', parseInt(count) + 1);
                    } else {
                        localStorage.setItem('ip', ips[0]);
                        localStorage.setItem('ipCounter', parseInt(count) + 1);
                    }

                    let counter = localStorage.getItem('ipCounter');

                    if (parseInt(counter) >= ATTEMPTS_MAX_COUNT) {
                        $('#rejected-login').modal({
                            backdrop: 'static',
                            keyboard: false
                        })

                        console.log("EEE");

                        startTimer(REJECTION_TIMEOUT, document.querySelector('#timer'));
                        localStorage.clear();
                    }
                }
            )

            ++attemptsCount;

            if (attemptsCount >= ATTEMPTS_MAX_COUNT) {
                $('#rejected-login').modal({
                    backdrop: 'static',
                    keyboard: false
                })

                startTimer(REJECTION_TIMEOUT, document.querySelector('#timer'));
            }

            $("#username").val('');
            $("#password").val('');
            $("#username").focus();
        }
    });
}

function startTimer(duration, display) {
    let timer = duration, minutes, seconds;

    setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            $('#rejected-login').modal('hide');
        }

    }, 1000);
}