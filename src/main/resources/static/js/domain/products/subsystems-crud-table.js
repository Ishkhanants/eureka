$(document).ready(async function () {

    $.validator.setDefaults({
        ignore: []
    });

    await $.i18n().load({
        "en": "/i18n/en.json",
        "hy": "/i18n/hy.json",
        "ru": "/i18n/ru.json",
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
            return $.i18n("list.subsystems") + " " + start + "-" + end + " " + $.i18n("list.from") + " " + total + $.i18n("list.form.arm");
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

    table.on('click','.edit', function() {
        let id = $(this).parent().find('.id').val();
        let productId = $('.subsystem-product-id').val();
        $('#editSubsystemModal #id-to-edit').val(id);
        $('#editSubsystemModal #product-id-to-edit').val(productId);
        $.ajax({
            type: 'GET',
            url: '/products/subsystems/' + id,
            success: function (subsystem){
                $('#editSubsystemModal #edit-name').val(subsystem.name);
                $('#editSubsystemModal #edit-shortName').val(subsystem.shortName);
                $('#editSubsystemModal #edit-description').val(subsystem.description);
            }
        })
    })

    table.on('click','.delete', function() {
        let id = $(this).parent().find('.id').val();
        let productId = $('.subsystem-product-id').val();
        $('#deleteSubsystemModal #id-to-delete').val(id);
        $('#deleteSubsystemModal #product-id-to-delete').val(productId);
    })

    $('#deleteAllSelectedButton').on('click', function () {
        let productId = $('.subsystem-product-id').val();
        let array = [];
        dtable.rows().nodes().to$().find('input[type="checkbox"]:checked').each(function(){
            array.push($(this).val());
        });
        $('#ids-to-delete').val(array);
        $('#product-ids-to-delete').val(productId);
    })

    addValidationHtml();

    $(document).ready(function(){
        addValidationForNameAndDescription();
    })

});