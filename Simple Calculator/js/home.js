$('#calcContainer').css({'border':'2px solid', 'background-color':'Gainsboro'});
$('h1').css('text-align','center');
$('.btn').css({'color':'white'});
$('#calcDisplay').css({'border':'2px solid', 'color':'orange'});
$('.btn').css({'background-color':'gray'});
$('#equalsButton').css('background-color','orange');
$('#multiplicationButton').css('background-color','black');
$('#additionButton').css('background-color','black');
$('#subtractionButton').css('background-color','black');
$('#divideButton').css('background-color','black');
$('.btn').css({'border':'2px solid','border-color':'DimGrey'});
$('#clearButton').css('background-color','red');

//function to operate calculator display
function display(displayValue){
document.getElementById("calcDisplay").value+=displayValue
}
//function to evaluate the math expression
function equals(){
var x = document.getElementById("calcDisplay").value
var y = eval(x)
document.getElementById("calcDisplay").value = y
}
//function to clear the calculator display
function clearDisplay(){
document.getElementById("calcDisplay").value = "";
}
