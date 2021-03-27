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
    let deleteContainer = $('.delete-account-container');
    let mainContainer = $('.main-container');
    let passwordField = $("#password");
    let spinner = $(".lds-dual-ring");

    const csrfHeader = "X-CSRF-TOKEN";

    const token = $('input[name^="_csrf"]').val();
    passwordField.on('input', function () {
        if (passwordField.val().length === 0) {
            $(".new-password-input-group").find(".custom-invalid-feedback").css("display", "none");
            $(".confirm-password-input-group").find(".custom-invalid-feedback").css("display", "none");
        }
    });

    let xButton = "<button type=\"button\" class=\"close\" id='clear-input' aria-label=\"CZlose\">\n" +
        "  <span aria-hidden=\"true\">&times;</span>\n" +
        "</button>";
    $('#labelContainer').append(xButton);
    $('.custom-file-upload').html(/*$.i18n(*/"Upload photo"/*)*/);

    $("#cancel-btn").click(function () {
        extendEndpoint();
    });
    $(".custom-close").click(function () {
        extendEndpoint();
    });
    $("#pop-up-link").click(function () {
        mainContainer.css("opacity", 0.6);
        mainContainer.css("background-color", "#F0F2FB");
        deleteContainer.css("display", "block");
        deleteContainer.css("margin-left", "26%");
        mainContainer.css("pointer-events", "none");
    });
    $("#cancel-btn2").click(function () {
        mainContainer.css("opacity", "unset");
        mainContainer.css("background-color", "unset");
        deleteContainer.css("display", "none");
        mainContainer.css("pointer-events", "unset");
    });

    const disableBackground = () => {
        mainContainer.css("opacity", 0.6);
        mainContainer.css("background-color", "#F0F2FB");
        mainContainer.css("pointer-events", "none");
    };

    addValidationHtml()
    $(document).ready(function () {

        addErrorIcon()

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

    function populate(val) {
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
                        .text(version));
                }
            }
        })

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
                        .text(name));
                }
            }
        })

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

    Date.prototype.toDateInputValue = (function () {
        let local = new Date(this);
        local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
        return local.toJSON().slice(0, 10);
    });
});

extendEndpoint = () => {
    let endpoint;
    $('#is-user-type').val() == 'true' ? endpoint = "/issues/my" : endpoint = "/issues";
    window.location.href = location.origin + endpoint;
}