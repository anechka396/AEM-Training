$(document).ready(function() {
    var imageURL = $('.background-image').text();
    $('.background-image').text("").css("background-image", "url('"+imageURL+"')");
});