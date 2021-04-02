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
        "order": [1, 'asc'],
        "initComplete": function (settings, json) {
            let word = sessionStorage.getItem('userName');
            if (word != null) {
                this.api().search(word).draw();
                sessionStorage.removeItem('userName');
            }
        },
        "pageLength": 15,
        "infoCallback": function (settings, start, end, max, total, pre) {
            return $.i18n("list.releases") + " " + start + "-" + end + " " + $.i18n("list.from") + " " + total + $.i18n("list.form.arm");
        },
        'sPaginationType': 'twoNumbers',
        language: {
            searchPlaceholder: $.i18n("list.search"),
            search: "",
            paginate: {
                next: '>',
                previous: '<'
            }
        },
        "aoColumns": [
            {"orderSequence": ["asc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc"]},
        ],
        columnDefs: [
            {
                orderable: false,
                targets: [0, 2, 5]
            },
            {
                visible: document.getElementById('role').value == 'ADMIN_ROLE',
                targets: [0, 5]
            }
        ]
    });

    addDataTableFiltering(dtable);

    let table = $('#myTable');

    table.on('click', '.edit', function () {
        let id = $(this).parent().find('.id').val();
        let productId = $('.release-product-id').val();
        $('#editReleaseModal #id-to-edit').val(id);
        $('#editReleaseModal #product-id-to-edit').val(productId);
        $.ajax({
            type: 'GET',
            url: '/products/release-versions/' + id,
            success: function (release) {
                $('#editReleaseModal #edit-version').val(release.version);
                $('#editReleaseModal #edit-date').val(release.startDate);
                $('#editReleaseModal #edit-description').val(release.description);
            }
        })
    })

    table.on('click', '.delete', function () {
        let id = $(this).parent().find('.id').val();
        let productId = $('.release-product-id').val();
        $('#deleteReleaseModal #id-to-delete').val(id);
        $('#deleteReleaseModal #product-id-to-delete').val(productId);
    })

    $('#deleteAllSelectedButton').on('click', function () {
        let productId = $('.release-product-id').val();
        let array = [];
        dtable.rows().nodes().to$().find('input[type="checkbox"]:checked').each(function () {
            array.push($(this).val());
        });
        $('#ids-to-delete').val(array);
        $('#product-ids-to-delete').val(productId);
    })

    $('#start-date').val(new Date().toDateInputValue());

    addValidationHtml();

    $(document).ready(function () {
        const MIN_VERSION_LENGTH = 4;
        const MAX_VERSION_LENGTH = 15;
        const MIN_DESCRIPTION_LENGTH = 20;
        const MAX_DESCRIPTION_LENGTH = 200;

        $('#add-form').validate({
            rules: {
                version: {
                    customRequired: $('#release-version').text(),
                    validVersion: [MIN_VERSION_LENGTH, MAX_VERSION_LENGTH]
                },
                description: {
                    customRequired: $('#release-description').text(),
                    validDescription: [MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH]
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {}
        });

        $('#edit-form').validate({
            rules: {
                editVersion: {
                    customRequired: $('#release-version').text(),
                    validVersion: [MIN_VERSION_LENGTH, MAX_VERSION_LENGTH]
                },
                editDescription: {
                    customRequired: $('#release-description').text(),
                    validDescription: [MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH]
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {}
        });

        $.extend($.validator.messages, {
            customRequired: $.i18n("field.customRequired"),
            validVersion: $.i18n("product.version.validation", MIN_VERSION_LENGTH, MAX_VERSION_LENGTH),
            validDescription: $.i18n("field.description.validation", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH),
        });

        addErrorIcon();
    });

});