let xButton = "<button type=\"button\" class=\"close\" id='clear-input' aria-label=\"CZlose\">\n" +
    "  <span aria-hidden=\"true\">&times;</span>\n" +
    "</button>";
let spinner = $(".lds-dual-ring");

$(document).ready(async function () {

    $.validator.setDefaults({
        ignore: []
    });

    await $.i18n().load({
        "en": "/i18n/en.json",
        "hy": "/i18n/hy.json",
        "ru": "/i18n/ru.json",
    });

    $.i18n().locale = $("#locale").val();

    $('#labelContainer').append(xButton);

    $(".cancel-btn").click(function () {
        extendEndpoint();
    });

    $(".custom-close").click(function () {
        extendEndpoint();
    });

    addValidationHtml();

    $(document).ready(function () {
        let product = $('#product');

        if (product.val() !== 0) {
            let val = $('#product').val();
            populate(val);
        }

        product.change(function () {
            let val = $(this).val();
            populate(val);
        })

        $('#date-reported').val(new Date().toDateInputValue());

        const MIN_TITLE_LENGTH = 10;
        const MAX_TITLE_LENGTH = 50;
        const MIN_DESCRIPTION_LENGTH = 20;
        const MAX_DESCRIPTION_LENGTH = 200;

        $('#add-form').validate({
            rules: {
                title: {
                    customRequired: $('#title-valid-title').text(),
                    validTitle: [MIN_TITLE_LENGTH, MAX_TITLE_LENGTH]
                },
                description: {
                    customRequired: $('#description-valid-title').text(),
                    validDescription: [MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH]
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {},

            submitHandler: (form) => doFormSubmit(form)
        });

        $('#edit-form').validate({
            rules: {
                title: {
                    customRequired: $('#edit-title-valid-title').text(),
                    validTitle: [MIN_TITLE_LENGTH, MAX_TITLE_LENGTH]
                },
                description: {
                    customRequired: $('#edit-description-valid-title').text(),
                    validDescription: [MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH]
                }
            },

            errorPlacement: (label, element) => doErrorPlacement(label, element),

            success: function (a, b) {},

            submitHandler: (form) => doFormSubmit(form)
        });

        $.extend($.validator.messages, {
            customRequired: $.i18n("field.customRequired"),
            validTitle: $.i18n("issue.title.validation", MIN_TITLE_LENGTH, MAX_TITLE_LENGTH),
            validDescription: $.i18n("field.description.validation", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH),
        });

        addErrorIcon();
    })
})

populate = val => {
    getReleasesByProduct(val);
    getSubsystemsByProduct(val);

    if ($("#is-user-type").val() == 'true') {
        getOwnerByProduct(val);
    }
}

getOwnerByProduct = val => {
    $.ajax({
        type: "GET",
        url: "/products/owner-by-product/" + val,
        success: function (data) {
            $("#assignee").empty();

            const id = data.id;
            const name = data.fullName;

            $('#assignee').append($("<option></option>")
                .attr("value", id)
                .text(name));
        }
    })
}

getSubsystemsByProduct = val => {
    $.ajax({
        type: "GET",
        url: "/products/subsystems-by-product/" + val,
        success: function (data) {
            $("#subsystem").empty();
            for (let i = 0; i < data.length; i++) {
                const id = data[i].id;
                const name = data[i].name;
                $('#subsystem').append($("<option></option>")
                    .attr("value", id)
                    .attr("selected", $('#subsystem-id').val() == id)
                    .text(name));
            }
        }
    })
}

getReleasesByProduct = val => {
    $.ajax({
        type: "GET",
        url: "/products/release-versions-by-product/" + val,
        success: function (data) {
            $("#release-version").empty();
            for (let i = 0; i < data.length; i++) {
                const id = data[i].id;
                const version = data[i].version;
                $('#release-version').append($("<option></option>")
                    .attr("value", id)
                    .attr("selected", $('#release-version-id').val() == id)
                    .text(version));
            }
        }
    })
}

extendEndpoint = () => {
    let endpoint;
    $('#is-user-type').val() == 'true' ? endpoint = "/issues/my" : endpoint = "/issues";
    window.location.href = location.origin + endpoint;
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
        },
        success: function () {
            extendEndpoint();
        }
    });
}
