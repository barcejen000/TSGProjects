$(document).ready(function () {
     if ($('.alert-danger').length > 0) {
        var oldValue = document.getElementById("oldName").value;
        $('#hiddenId').remove();
        
        var t = '<input id="hiddenId" type="hidden" name="originalname" value="' + oldValue + '"/>';
        $('#newInput').append(t);
    };
});
