$(document).ready(function () {
    loadMain();
    $('#addUserButton').click(function () {
        $('#adminButtons').show();
        $('#addUser').toggle();
        $('#listUsers').hide();
        $('#blogToApprove').hide();
        $('#blogsRejected').hide();
    });
    $('#viewAllUsersButton').click(function () {
        $('#adminButtons').show();
        $('#addUser').hide();
        $('#listUsers').toggle();
         $('#blogsRejected').hide();
        $('#blogToApprove').hide();
    });
    $('#viewAllPendingPosts').click(function () {
        $('#adminButtons').show();
        $('#addUser').hide();
        $('#listUsers').hide();
         $('#blogsRejected').hide();
        $('#blogToApprove').toggle();
    });
    
    $('#viewAllRejectedPosts').click(function(){
        $('#adminButtons').show();
        $('#addUser').hide();
        $('#listUsers').hide();
        $('#blogToApprove').hide();
         $('#blogsRejected').toggle();
    });
    $('#mulitSelector').hide();
    
});

function loadMain() {
    $('#adminButtons').show();
    $('#addUser').hide();
    $('#listUsers').hide();
    $('#blogToApprove').hide();
    $('#blogsRejected').hide();

};
