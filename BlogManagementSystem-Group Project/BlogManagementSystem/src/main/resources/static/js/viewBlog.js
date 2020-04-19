$(document).ready(function () {
    loadMain();
    $('#viewCommentsButton').click(function () {
        $('#viewComments').show();
        $('#hideCommentsButton').show();
        $('#viewCommentsButton').hide();
    });
    
    $('#hideCommentsButton').click(function () {
        $('#viewComments').hide();
        $('#hideCommentsButton').hide();
        $('#viewCommentsButton').show();
    });
});

function loadMain() {
    $('#viewComments').hide();
    $('#hideCommentsButton').hide();
}
;