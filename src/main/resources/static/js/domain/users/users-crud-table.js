$(document).ready(async function () {
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
            return "Users" /*$.i18n("list.users")*/ + " " + start + "-" + end + " " + "from" /*$.i18n("list.from")*/ + " " + total; //+ $.i18n("list.form.arm");
        },
        'sPaginationType': 'twoNumbers',
        language: {
            searchPlaceholder: "",//$.i18n("list.search"),
            search: "",
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

});

populateAdd = (val) => {
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

populateEdit = (val) => {
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

processUserType = (str) => {
    switch (str) {
        case 'SECURITY_ADMINS':
            return 'Security Admins';
        case 'ADMINS_MANAGEMENT':
            return 'Admins (Management)';
        case 'ADMINS_DEVELOPMENT':
            return 'Admins (Development)';
        case 'TESTERS':
            return 'Testers';
        case 'DEVELOPERS':
            return 'Developers';
        case 'READ_ONLY_ACCESS':
            return 'Read Only Access';
    }
}