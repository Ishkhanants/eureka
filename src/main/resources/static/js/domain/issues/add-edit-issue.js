$(document).ready(async function () {
    // $.validator.setDefaults({
    //     ignore: []
    // });
    // await $.i18n().load({
    //     "en": "/i18n/en.json",
    //     "hy": "/i18n/hy.json",
    //     "ru": "/i18n/ru.json",
    // });
    //
    // $.i18n().locale = $("#locale").val();

    let xButton = "<button type=\"button\" class=\"close\" id='clear-input' aria-label=\"CZlose\">\n" +
        "  <span aria-hidden=\"true\">&times;</span>\n" +
        "</button>";

    $('#labelContainer').append(xButton);

    $('.custom-file-upload').html(/*$.i18n(*/"Upload photo"/*)*/);

    $(".cancel-btn").click(function () {
        extendEndpoint();
    });

    $(".custom-close").click(function () {
        extendEndpoint();
    });

    addValidationHtml();

    $(document).ready(function () {
        addErrorIcon();

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

    })

    Date.prototype.toDateInputValue = (function () {
        let local = new Date(this);
        local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
        return local.toJSON().slice(0, 10);
    });
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
