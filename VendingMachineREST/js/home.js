var incomingMoney =0;
$(document).ready(function(){
  loadVendingItems();

    $('#dollarButton').click(function(){
      resetDisplaysAndBuyButton();
      var dollarVal = Number($(this).val());
      incomingMoney += dollarVal;
      incomingMoney = Number((incomingMoney).toFixed(2));
      updateMoneyDisplay(incomingMoney);
    });


    $('#quarterButton').click(function(){
      resetDisplaysAndBuyButton();
      var quarterVal = Number($(this).val());
      incomingMoney += quarterVal;
      incomingMoney = Number((incomingMoney).toFixed(2));
      updateMoneyDisplay(incomingMoney);
    });

    $('#dimeButton').click(function(){
      resetDisplaysAndBuyButton();
      var dimeVal = Number($(this).val());
      incomingMoney += (dimeVal);
      incomingMoney = Number((incomingMoney).toFixed(2));
      updateMoneyDisplay(incomingMoney);
    });


    $('#nickelButton').click(function(){
      resetDisplaysAndBuyButton();
      var nickelVal = Number($(this).val());
      incomingMoney += nickelVal;
      incomingMoney = Number((incomingMoney).toFixed(2));
      updateMoneyDisplay(incomingMoney);
    });

    $('#changeButton').click(function(){
      $('#moneyDisplay').val('');
      resetDisplaysAndBuyButton();

      var numPennies = Math.round(incomingMoney/0.01);
      var numQuarters = 0;
      var numDimes= 0;
      var numNickels = 0;
      var numExtraPennies = 0;


      if(0<Math.floor(numPennies/25)){
        numQuarters = Math.floor(numPennies/25);
        numExtraPennies = numPennies%25;
        if(0<Math.floor(numExtraPennies/10)){
          numDimes = Math.floor(numExtraPennies/10);
          numExtraPennies = (numExtraPennies%10);
        }
        if(0<Math.floor(numExtraPennies/5)){
          numNickels = Math.floor(numExtraPennies/5);
          numExtraPennies = (numExtraPennies%5);
        }
      }else if(0< Math.floor(numPennies/10)){
        numDimes = Math.floor(numPennies/10);
        numExtraPennies = (numPennies%10);

        if(0<Math.floor(numExtraPennies/5)){
          numNickels = Math.floor(numExtraPennies/5);
          numExtraPennies = (numExtraPennies%5);
        }
      } else if(0<Math.floor(numPennies/5)){
        numNickels = Math.floor(numPennies/5);
        numExtraPennies = (numPennies%5);
      }else {
        numExtraPennies = numPennies;
      }
        numPennies = numExtraPennies;

      $('#itemDisplay').val('');
      loadVendingItems();
      displayChange(numQuarters, numDimes, numNickels, numPennies);
      incomingMoney = 0;

    });


    $('#buyButton').click(function(event){
      $('#changeDisplay').val('');
      var vendingId = $('#itemDisplay').val();

    $.ajax({
      type:'POST',
      url:'http://tsg-vending.herokuapp.com/money/'+incomingMoney+'/item/'+vendingId,
      headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			},
			'dataType': 'json',
      success: function(change,status){
        var numQuarters = change.quarters;
        var numDimes= change.dimes;
        var numNickels = change.nickels;
        var numPennies = change.pennies;


        loadVendingItems();
        incomingMoney =0;
        $('#moneyDisplay').val('');
        $('#itemDisplay').val('');
        $('#messageDisplay').val('Thank You!!!');
        displayChange(numQuarters, numDimes, numNickels, numPennies);

      },
      error: function(jqXHR){
          loadVendingItems();
          var errorMessage = jqXHR.responseJSON;
          $('#messageDisplay').val(errorMessage.message);
          $('#buyButton').removeClass('btn btn-success');
          $('#buyButton').addClass('btn btn-danger');
      }
    });

  });

});


function loadVendingItems(){
  clearItems();

  var itemRows = $('#itemsRow');

  $.ajax({

    type:'GET',
    url:'http://tsg-vending.herokuapp.com/items',
    success: function(items,status){

    $.each(items, function(index, item){
      var itemId = item.id;
      var itemName = item.name;
      var itemPrice = item.price;
      var itemQuantity = item.quantity;
      var button = "<button type='button' class='btn btn-primary col-3 m-4' style='justify-content-center text-align:center' id ='"+itemId+"' onclick='displayItemChoice("+itemId+")'>"
      button +=  itemId +'<hr style="border-top: 3px dashed yellow"/>';
      button += itemName +'<br/>';
      button +='$'  + itemPrice +'<br/>' ;
      button += 'Quantity Left: '+ itemQuantity +'<br/>';
      button += "</button>"
      itemRows.append(button);
    });

    },
    error: function(){
          $('#messageDisplay').val('Error could not load items. Please try again later.');
      }
  });
}


function displayChange(numQuarters, numDimes, numNickels, numPennies){
  var  quartersRemaining  ='';
  var  dimesRemaining  ='';
  var  nickelsRemaining  ='';
  var  penniesRemaining  ='';
  var  noChange ='';

  if(0 < numQuarters){
    quartersRemaining = numQuarters+ ' Quarter(s) ';
  }
  if(0 < numDimes && 0 < numQuarters){
     dimesRemaining = ', '+ numDimes+ ' Dime(s) ';
  }else if(0 < numDimes){
     dimesRemaining = numDimes+ ' Dime(s) ';
  }
  if(0 < numNickels && 0 < numQuarters){
    nickelsRemaining = ', '+ numNickels+ ' Nickel(s)';
  } else if (0 < numNickels && 0 < numDimes){
    nickelsRemaining = ', '+ numNickels+ ' Nickel(s)';
  }else if (0 < numNickels){
    nickelsRemaining = numNickels+ ' Nickel(s)';
  }
  if(0 < numPennies && 0 < numQuarters){
    penniesRemaining = ', '+numPennies+ ' Pennie(s)';
  }else if(0 < numPennies && 0 < numDimes){
    penniesRemaining = ', '+numPennies+ ' Pennie(s)';
  }else if(0 < numPennies && 0 < numNickels){
    penniesRemaining = ', '+numPennies+ ' Pennie(s)';
  }else if(0 < numPennies){
    penniesRemaining = numPennies+ ' Pennie(s)';
  }
  if(0 == numPennies &&  0 == numQuarters && 0 == numDimes && 0 == numNickels){
     noChange = 'No change remaining';
  }
    $('#changeDisplay').val(quartersRemaining + dimesRemaining + nickelsRemaining + penniesRemaining + noChange);

}

function displayItemChoice(itemId){
    resetDisplaysAndBuyButton();
    $('#itemDisplay').val('');
    $('#itemDisplay').val(itemId);

}

  function clearItems(){
    $('#itemsRow').empty();
}

function resetDisplaysAndBuyButton(){
  $('#changeDisplay').val('');
  $('#messageDisplay').val('');
  $('#buyButton').removeClass('btn btn-danger');
  $('#buyButton').addClass('btn btn-success');
}

function updateMoneyDisplay(incomingMoney){
  $('#moneyDisplay').val('');
  $('#moneyDisplay').val(incomingMoney.toFixed(2));
}
