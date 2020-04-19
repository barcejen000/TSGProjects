$(document).ready(function () {
    loadMain();
    $('#approvedBlogs').click(function () {
        $('#unapprovedBlogs').toggle();
        $('#rejectedBlogs').toggle();
        $('#displayApprovedBlogs').toggle();
        $('#displayUnapprovedBlogs').hide();
        $('#displayRejectedBlogs').hide();
    });
    
    $('#unapprovedBlogs').click(function () {
        $('#approvedBlogs').toggle();
        $('#rejectedBlogs').toggle();
        $('#displayApprovedBlogs').hide();
        $('#displayUnapprovedBlogs').toggle();
        $('#displayRejectedBlogs').hide();
    });
    $('#rejectedBlogs').click(function () {
        $('#approvedBlogs').toggle();
        $('#unapprovedBlogs').toggle();
        $('#displayApprovedBlogs').hide();
        $('#displayUnapprovedBlogs').hide();
        $('#displayRejectedBlogs').toggle();
    });
    
    $('#mulitSelector').hide();

});

function loadMain() {
    $('#userButtons').show();
    $('#displayUnapprovedBlogs').hide();
    $('#displayRejectedBlogs').hide();
    $('#displayApprovedBlogs').hide();

}
;

