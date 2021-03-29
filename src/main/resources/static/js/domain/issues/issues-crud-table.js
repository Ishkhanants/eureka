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
            return "Issues" /*$.i18n("list.users")*/ + " " + start + "-" + end + " " + "from" /*$.i18n("list.from")*/ + " " + total; //+ $.i18n("list.form.arm");
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
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc", "desc"]},
            {"orderSequence": ["asc"]},
            {"orderSequence": ["asc"]},
        ],
        columnDefs: [
            {
                orderable: false,
                targets: [0, 11, 12]
            }
        ]
    });

    let table = $('#myTable');

    table.on('click','.delete', function() {
        let id = $(this).parent().find('.id').val();
        $('#deleteIssueModal #id-to-delete').val(id);
    })

    $('#deleteAllSelectedButton').on('click', function () {
        let array = [];
        dtable.rows().nodes().to$().find('input[type="checkbox"]:checked').each(function(){
            array.push($(this).val());
        });
        $('#ids-to-delete').val(array);
    })

});