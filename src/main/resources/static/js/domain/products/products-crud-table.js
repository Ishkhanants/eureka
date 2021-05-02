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
        "order": [[3, 'asc'], [1, 'asc']],
        "initComplete": function (settings, json) {
            let word = sessionStorage.getItem('userName');
            if (word != null) {
                this.api().search(word).draw();
                sessionStorage.removeItem('userName');
            }
        },
        "pageLength": 15,
        "infoCallback": function (settings, start, end, max, total, pre) {
            return $.i18n("list.products") + " " + start + "-" + end + " " + $.i18n("list.from") + " " + total + $.i18n("list.form.arm");
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
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc"]},
            {"orderSequence": ["asc"]},
            {"orderSequence": ["asc"]},
        ],
        columnDefs: [
            {
                visible: document.getElementById('role').value == 'ADMIN_ROLE',
                targets: [0, 7]
            },
            {
                orderable: false,
                targets: [0, 5, 6, 7]
            }
        ]
    });

    let table = $('#myTable');

    table.on('click','.edit', function() {
        let id = $(this).parent().find('.id').val();
        let ownerId = $('.product-owner-id').val();
        $('#editProductModal #id-to-edit').val(id);
        $('#editProductModal #owner-id-to-edit').val(ownerId);
        $.ajax({
            type: 'GET',
            url: '/products/' + id,
            success: function (product){
                $('#editProductModal #edit-name').val(product.name);
                $('#editProductModal #edit-owner').val(product.owner.id);
                $('#editProductModal #edit-date').val(product.startDate);
                $('#editProductModal #edit-description').val(product.description);
            }
        })
    })

    table.on('click','.delete', function() {
        let id = $(this).parent().find('.id').val();
        let ownerId = $('.product-owner-id').val();
        $('#deleteProductModal #id-to-delete').val(id);
        $('#deleteProductModal #owner-id-to-delete').val(ownerId);
    })

    $('#deleteAllSelectedButton').on('click', function () {
        let ownerId = $('.product-owner-id').val();
        let array = [];
        dtable.rows().nodes().to$().find('input[type="checkbox"]:checked').each(function(){
            array.push($(this).val());
        });
        $('#ids-to-delete').val(array);
        $('#owner-ids-to-delete').val(ownerId);
    })

    $('#start-date').val(new Date().toDateInputValue());

    addValidationHtml();

    $(document).ready(function(){
        addValidationForNameAndDescription();
    })

    addDataTableFiltering(dtable);
});