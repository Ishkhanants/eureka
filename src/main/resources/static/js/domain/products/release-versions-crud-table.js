$(document).ready(async function () {

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
            return "Releases" /*$.i18n("list.users")*/ + " " + start + "-" + end + " " + "from" /*$.i18n("list.from")*/ + " " + total; //+ $.i18n("list.form.arm");
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

    let table = $('#myTable');

    table.on('click','.edit', function() {
        let id = $(this).parent().find('.id').val();
        let productId = $('.release-product-id').val();
        $('#editReleaseModal #id-to-edit').val(id);
        $('#editReleaseModal #product-id-to-edit').val(productId);
        $.ajax({
            type: 'GET',
            url: '/products/release-versions/' + id,
            success: function (release){
                $('#editReleaseModal #edit-version').val(release.version);
                $('#editReleaseModal #edit-date').val(release.startDate);
                $('#editReleaseModal #edit-description').val(release.description);
            }
        })
    })

    table.on('click','.delete', function() {
        let id = $(this).parent().find('.id').val();
        let productId = $('.release-product-id').val();
        $('#deleteReleaseModal #id-to-delete').val(id);
        $('#deleteReleaseModal #product-id-to-delete').val(productId);
    })

    $('#deleteAllSelectedButton').on('click', function () {
        let productId = $('.release-product-id').val();
        let array = [];
        dtable.rows().nodes().to$().find('input[type="checkbox"]:checked').each(function(){
            array.push($(this).val());
        });
        $('#ids-to-delete').val(array);
        $('#product-ids-to-delete').val(productId);
    })

});