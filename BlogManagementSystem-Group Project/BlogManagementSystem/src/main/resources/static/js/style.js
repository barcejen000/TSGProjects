/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var name;
$(document).ready(function () {

});

function showBlogOpts() {
    $(".dropdown-content").toggle();
}

function add() {
    var newTotal = parseInt($('#totalBoxes').val()) + 1;
    var newInputBox = "<input type='text' class='col-6 form-control' name='hashtagIds' id='new_" + name + "'>";
    $('#newInputArea').append(newInputBox);
    $('#totalBoxes').val(newTotal);
}
function remove() {
    var lastTotal = $('#totalBoxes').val();
    if (lastTotal > 1) {
        $('#new_' + name).remove();
        $('#totalBoxes').val(lastTotal - 1);
    }
}