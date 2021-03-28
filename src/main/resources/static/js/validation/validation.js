function addValidationHtml() {
    document.querySelectorAll(".form-control").forEach(function (element) {
        let parent = element.parentElement;

        let invalidFeedback = document.createElement('DIV');
        invalidFeedback.classList.add('custom-invalid-feedback');

        let span = document.createElement('SPAN');
        span.classList.add('error-message');
        let inputName = element.getAttribute('name');
        span.id = `error-message-${inputName}`;

        invalidFeedback.appendChild(span);
        parent.appendChild(invalidFeedback)
    });
}

function addErrorIcon(){
    document.querySelectorAll(".custom-invalid-feedback").forEach(
        field => {
            let errorIcon = document.createElement("IMG");
            errorIcon.classList.add("error-icon");
            // errorIcon.setAttribute("src", "../images/icon.svg");
            field.prepend(errorIcon);
        })
}

// jQuery.validator.addMethod("customRequired", function (value, element) {
//     return value !== undefined && value !== null && value.trim().length > 0;
// }, $.validator.messages.customRequired);
