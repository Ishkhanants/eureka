const csrfHeader = "X-CSRF-TOKEN";
const token = $('input[name^="_csrf"]').val();

$(document).ready(async function () {

    await $.i18n().load({
        "en": "/i18n/en.json",
        "hy": "/i18n/hy.json",
        "ru": "/i18n/ru.json",
    });

    $.i18n().locale = $("#locale").val();

    $.validator.setDefaults({
        ignore: []
    });

    let deleteContainer = $('.delete-account-container');
    let mainContainer = $('.main-container');
    let passwordField = $("#password");
    let profileAvatarImage = $(".avatar");
    let navAvatarImage = $(".nav-image");

    passwordField.on('input', function () {
        if (passwordField.val().length === 0) {
            $(".new-password-input-group").find(".custom-invalid-feedback").css("display", "none");
            $(".confirm-password-input-group").find(".custom-invalid-feedback").css("display", "none");
        }
    });

    $('#add-activity-btn2').click(function (e) {
        e.preventDefault();
        $.ajax({
            type: "delete",
            url: "/edit-profile",
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, token);
            },
            success: function () {
                location.href = location.origin + "/logoutCustom";
            },
        });
    });

    $('<input>').attr({
        type: 'hidden',
        id: 'deletedAvatar',
        name: 'deletedAvatar',
        value: 'notDeleted'
    }).appendTo($('#form'));

    let xButton = "<button type=\"button\" class=\"close\" id='clear-input' aria-label=\"CZlose\">\n" +
        "  <span aria-hidden=\"true\">&times;</span>\n" +
        "</button>";
    $('#labelContainer').append(xButton);
    $('.custom-file-upload').html($.i18n("upload.photo"));

    if (profileAvatar === null) $("#clear-input").hide();

    let userType = $('#userType');

    if($("#is-admin").val()=='true') {
        if (userType.val() !== 0) {
            let val = $('#userType').val();
            populate(val);
        }

        userType.change(function () {
            let val = $(this).val();
            populate(val);
        })
    }

    $("#clear-input").on("click", function (e) {
        $('#profileAvatar').parent().find(".custom-invalid-feedback").hide();
        let uploadLabel = $('.custom-file-upload');
        let file = $('#profileAvatar');
        if (profileAvatar !== null && uploadLabel.html() === $.i18n("upload.photo")) {
            profileAvatarImage.attr('src', '../../images/no-avatar.png');
            navAvatarImage.attr('src', '../../images/no-avatar.png');
            profileAvatar = null;
            $("#clear-input").hide();
            $("#deletedAvatar").val("deleted");
        } else if (uploadLabel.html() !== $.i18n("upload.photo")) {
            file.val(null);
            uploadLabel.html($.i18n("upload.photo"));
            if (profileAvatar === null) $(this).hide();
            $("#deletedAvatar").val("notDeleted");
        } else $(this).hide();
    });

    $('#profileAvatar').change(function (e) {
        $(this).parent().find(".custom-invalid-feedback").hide();
        let fileName = e.target.files[0].name;
        let uploadLabel = $('.custom-file-upload');
        if (fileName.length >= 30) uploadLabel.html(fileName.substring(0, 28) + "..");
        else uploadLabel.html(fileName);
        if ($("#clear-input").is(":hidden")) $("#clear-input").show();
    });

    $(".cancel-btn").click(function () {
        window.location.href = location.origin + "/products";
    });

    $(".custom-close").click(function () {
        window.location.href = location.origin + "/products";
    });

    $("#pop-up-link").click(function () {
        mainContainer.css("opacity", 0.6);
        mainContainer.css("background-color", "#F0F2FB");
        deleteContainer.css("display", "block");
        deleteContainer.css("margin-left", "26%");
        mainContainer.css("pointer-events", "none");
    });

    $("#cancel-btn2").click(function () {
        mainContainer.css("opacity", "unset");
        mainContainer.css("background-color", "unset");
        deleteContainer.css("display", "none");
        mainContainer.css("pointer-events", "unset");
    });

    addValidationHtml();

    $(document).ready(function () {

        const MAX_LENGTH = 65;
        const VALID_PASSWORD_MIN_LENGTH = 6;
        const VALID_PASSWORD_MAX_LENGTH = 20;
        const MAX_FILE_SIZE_MB = 16;

        $('#form').validate({
            rules: {
                fullName: {
                    customRequired: $("#full-name-valid-title").text(),
                    validFullName: true,
                    maxlength: MAX_LENGTH
                },
                password: {
                    validPassword: [VALID_PASSWORD_MIN_LENGTH, VALID_PASSWORD_MAX_LENGTH]
                },
                confirmPassword: {
                    equalTo: "#password"
                },
                profileAvatar: {
                    fileSize: 16777215,
                    accept: "image/*"
                },
                phone: {
                    customRequired: $("#phone-valid-title").text(),
                    validPhone: true
                },
                emailEditable: {
                    customRequired: $("#emailEditable-valid-title").text(),
                    validEmail: true
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {},

            submitHandler: (form) => doFormSubmit(form)

        });

        $.extend($.validator.messages, {
            customRequired: $.i18n("field.customRequired"),
            validFullName: $.i18n("user.fullName.validation"),
            validPassword: $.i18n("user.password.validation", VALID_PASSWORD_MIN_LENGTH, VALID_PASSWORD_MAX_LENGTH),
            equalTo: $.i18n("user.password.equals"),
            validPhone: $.i18n("user.phone.validation"),
            validEmail: $.i18n("user.email.validation"),
            fileSize: $.i18n("user.editProfile.fileSize", MAX_FILE_SIZE_MB),
            accept: $.i18n("user.editProfile.accept"),
        });

        addErrorIcon();

        let select = $('select');

        select.find('option').show();

        select.each(function () {
            let value = $(this).val();
            if (value) {
                let option = `option[value=${value}]`;
                select.find(option).hide();
            }
        });

        select.change(() => {
            select.find('option').show();
            select.each(function () {
                let value = $(this).val();
                if (value) {
                    let option = `option[value=${value}]`;
                    select.find(option).hide();
                }
            })
        });

        $('#profileAvatar').change(function () {
            $('#profileAvatar').removeAttr("title")
        })

    });
});

populate = val => {
    let v = $("#group").val();
    $.ajax({
        type: "GET",
        url: "/users/group-by-type/" + val,
        success: function (data) {
            $("#group").empty();
            for (let i = 0; i < data.length; i++) {
                $('#group').append($("<option></option>")
                    .attr("value", data[i])
                    .attr("selected", data[i] == v)
                    .text(processUserType(data[i])));
            }
        }
    })
}

doFormSubmit = form => {
    let data = new FormData(form);
    $.ajax({
        type: 'POST',
        url: form.action,
        data: data,
        processData: false,
        contentType: false,
        beforeSend: function (request) {
            spinner.show();
            disableBackground();
            request.setRequestHeader(csrfHeader, token);
        },
        success: function () {
            location.href = location.origin + "/products";
        }
    });
}