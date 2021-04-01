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

    let dtable = $("#myTable").DataTable({
        "bLengthChange": false,
        "order": [2, 'asc'],
        "initComplete": function (settings, json) {
            let word = sessionStorage.getItem('userName');
            if (word != null) {
                this.api().search(word).draw();
                sessionStorage.removeItem('userName');
            }
        },
        "pageLength": 15,
        "infoCallback": function (settings, start, end, max, total, pre) {
            return $.i18n("list.users") + " " + start + "-" + end + " " + $.i18n("list.from") + " " + total + $.i18n("list.form.arm");
        },
        'sPaginationType': 'twoNumbers',
        language: {
            searchPlaceholder: "alo",
            search: "vholuvjb",
            paginate: {
                next: '>',
                previous: '<'
            }
        },
        "aoColumns": [
            {"orderSequence": ["asc"]},
            {"orderSequence": ["asc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc"]},
        ],
        columnDefs: [
            {
                orderable: false,
                targets: [0, 1, 10]
            }
        ]
    });

    let userType = $('#userType');

    if (userType.val() !== 0) {
        let val = $('#userType').val();
        populateAdd(val);
    }

    userType.change(function () {
        let val = $(this).val();
        populateAdd(val);
    })

    addDataTableFiltering(dtable);

    let table = $('#myTable');

    table.on('click', '.edit', function () {
        let id = $(this).parent().find('.id').val();
        $('#editUserModal #id-to-edit').val(id);

        let editUserType = $('#editUserModal #edit-userType');
        let editUserGroup = $('#editUserModal #edit-group');

        $.ajax({
            type: 'GET',
            url: '/users/edit/' + id,
            success: function (user) {
                $('#editUserModal #edit-username').val(user.username);
                $('#editUserModal #edit-fullName').val(user.fullName);
                $('#editUserModal #edit-email').val(user.email);
                $('#editUserModal #edit-phone').val(user.phone);
                editUserType.val(user.userType);
                editUserGroup.val(user.group);

                populateEdit(editUserType.val());
            }
        })

        editUserType.change(function () {
            let val = $(this).val();
            populateEdit(val);
        })
    })

    table.on('click', '.delete', function () {
        let id = $(this).parent().find('.id').val();
        $('#deleteUserModal #id-to-delete').val(id);
    })

    $('#deleteAllSelectedButton').on('click', function () {
        let array = [];
        dtable.rows().nodes().to$().find('input[type="checkbox"]:checked').each(function () {
            array.push($(this).val());
        });
        $('#ids-to-delete').val(array);
    })

    addValidationHtml();

    $(document).ready(function () {

        const MAX_LENGTH = 65;
        const VALID_USERNAME_MIN_LENGTH = 5;
        const VALID_USERNAME_MAX_LENGTH = 20;
        const VALID_PASSWORD_MIN_LENGTH = 6;
        const VALID_PASSWORD_MAX_LENGTH = 20;

        let confirmPasswordCustomRequired = $("#confirm-password-valid-title").text();

        $('#add-form').validate({
            rules: {
                username: {
                    customRequired: $("#username-valid-title").text(),
                    validUsername: [VALID_USERNAME_MIN_LENGTH, VALID_USERNAME_MAX_LENGTH]
                },
                fullName: {
                    customRequired: $("#full-name-valid-title").text(),
                    validFullName: true,
                    maxlength: MAX_LENGTH
                },
                password: {
                    customRequired: $("#password-valid-title").text(),
                    validPassword: [VALID_PASSWORD_MIN_LENGTH, VALID_PASSWORD_MAX_LENGTH]
                },
                confirmPassword: {
                    customRequired: confirmPasswordCustomRequired,
                    equalTo: "#password"
                },
                email: {
                    customRequiredEmail: $("#email-valid-title").text(),
                    validEmail: true
                },
                phone: {
                    customRequired: $("#phone-valid-title").text(),
                    validPhone: true
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {}
        });

        $('#edit-form').validate({
            rules: {
                editUsername: {
                    customRequired: $("#edit-username-valid-title").text(),
                    validUsername: [VALID_USERNAME_MIN_LENGTH, VALID_USERNAME_MAX_LENGTH]
                },
                editFullName: {
                    customRequired: $("#edit-full-name-valid-title").text(),
                    validFullName: true,
                    maxlength: MAX_LENGTH
                },
                editEmail: {
                    customRequiredEmail: $("#edit-email-valid-title").text(),
                    validEmail: true
                },
                editPhone: {
                    customRequired: $("#edit-phone-valid-title").text(),
                    validPhone: true
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {}
        });

        $.extend($.validator.messages, {
            customRequired: $.i18n("field.customRequired"),
            validUsername: $.i18n("user.userName.validation", VALID_USERNAME_MIN_LENGTH, VALID_USERNAME_MAX_LENGTH),
            validFullName: $.i18n("user.fullName.validation"),
            validPassword: $.i18n("user.password.validation", VALID_PASSWORD_MIN_LENGTH, VALID_PASSWORD_MAX_LENGTH),
            equalTo: $.i18n("user.password.equals"),
            validPhone: $.i18n("user.phone.validation"),
            validEmail: $.i18n("user.email.validation"),
        });

        addErrorIcon();
    });

});

populateAdd = val => {
    $.ajax({
        type: "GET",
        url: "/users/group-by-type/" + val,
        success: function (data) {
            $("#group").empty();
            for (let i = 0; i < data.length; i++) {
                $('#group').append($("<option></option>")
                    .attr("value", data[i])
                    .text(processUserType(data[i])));
            }
        }
    })
}

populateEdit = val => {
    let edv = $('#edit-group').val();
    $.ajax({
        type: "GET",
        url: "/users/group-by-type/" + val,
        success: function (data) {
            $('#edit-group').empty();
            for (let i = 0; i < data.length; i++) {
                    $('#edit-group').append($("<option></option>")
                        .attr("value", data[i])
                        .attr("selected", edv == data[i])
                        .text(processUserType(data[i])));
            }
        }
    })
}

