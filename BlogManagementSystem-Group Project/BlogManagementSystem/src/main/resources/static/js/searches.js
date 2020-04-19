$(document).ready(function () {
    displayMain();

    $('#hashtags').click(function () {
        $('#titles').toggle();
        $('#authors').toggle();
        $('#searchByHashTag').toggle();
        $('#searchBytitle').hide();
        $('#searchByAuthor').hide();
    });
    
    $('#titles').click(function () {
        $('#hashtags').toggle();
        $('#authors').toggle();
        $('#searchByHashTag').hide();
        $('#searchBytitle').toggle();
        $('#searchByAuthor').hide();
    });
    
    $('#authors').click(function () {
        $('#hashtags').toggle();
        $('#titles').toggle();
        $('#searchByHashTag').hide();
        $('#searchBytitle').hide();
        $('#searchByAuthor').toggle();
    });

});

function displayMain() {
    $('#hashtags').show();
    $('#titles').show();
    $('#authors').show();
    $('#searchByHashTag').hide();
    $('#searchBytitle').hide();
    $('#searchByAuthor').hide();
}