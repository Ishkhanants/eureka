let mainContainer = $('.main-container');

const disableBackground = () => {
    mainContainer.css("opacity", 0.6);
    mainContainer.css("background-color", "#F0F2FB");
    mainContainer.css("pointer-events", "none");
};