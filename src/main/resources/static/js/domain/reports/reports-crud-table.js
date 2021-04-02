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
            return $.i18n("list.reports") + " " + start + "-" + end + " " + $.i18n("list.from") + " " + total + $.i18n("list.form.arm");
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
            }
        ]
    });

    let selectIssue = $('.select-issue');
    let issueToAdd = $('#issue-id-to-add');

    if(issueToAdd.val() == 0){
        issueToAdd.val(selectIssue.val());
    }

    selectIssue.change(function (){
        issueToAdd.val($(this).val());
    })

    $('.date-time-id').each(function(){
        $(this).text(parseDateTime($(this).text()));
    });

    function parseDateTime(dateTime) {
        let date = dateTime.substring(0, dateTime.indexOf('T'));
        let time = dateTime.substring(dateTime.indexOf('T') + 1, dateTime.indexOf('.'));
        return date + ' ' + time;
    }

    addDataTableFiltering(dtable);

    let table = $('#myTable');

    table.on('click','.edit', function() {
        let id = $(this).parent().find('.id').val();
        let issueId = $('.report-issue-id').val();
        $('#editReportModal #id-to-edit').val(id);
        $('#editReportModal #issue-id-to-edit').val(issueId);
        $.ajax({
            type: 'GET',
            url: '/issues/reports/' + id,
            success: function (report){
                $('#editReportModal #edit-comment').val(report.comment);
            }
        })
    })

    table.on('click','.delete', function() {
        let id = $(this).parent().find('.id').val();
        let issueId = $('.report-issue-id').val();
        $('#deleteReportModal #id-to-delete').val(id);
        $('#deleteReportModal #issue-id-to-delete').val(issueId);
    })

    $('#deleteAllSelectedButton').on('click', function () {
        let issueId = $('.report-issue-id').val();
        let array = [];
        dtable.rows().nodes().to$().find('input[type="checkbox"]:checked').each(function(){
            array.push($(this).val());
        });
        $('#ids-to-delete').val(array);
        $('#issue-ids-to-delete').val(issueId);
    })

    addValidationHtml();

    $(document).ready(function(){
        const MIN_COMMENT_LENGTH = 20;
        const MAX_COMMENT_LENGTH = 200;

        $('#add-form').validate({
            rules: {
                comment: {
                    customRequired: $('#comment-valid-title').text(),
                    validComment: [MIN_COMMENT_LENGTH, MAX_COMMENT_LENGTH]
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {}
        });

        $('#edit-form').validate({
            rules: {
                editComment: {
                    customRequired: $('#edit-comment-valid-title').text(),
                    validComment: [MIN_COMMENT_LENGTH, MAX_COMMENT_LENGTH]
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {}
        });

        $.extend($.validator.messages, {
            customRequired: $.i18n("field.customRequired"),
            validComment: $.i18n("report.comment.validation", MIN_COMMENT_LENGTH, MAX_COMMENT_LENGTH),
        });

        addErrorIcon();
    })

});