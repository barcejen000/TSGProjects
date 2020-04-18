var gmaps;
var gmaps2;
var mark;
var google;
var maps;

$(document).ready(function(){
var latforMap = parseFloat($('#mapLat').val());
var longforMap = parseFloat($('#mapLong').val());
var positionforMarker = {lat: latforMap, lng: longforMap};
 getMap(latforMap,longforMap,positionforMarker);
// getMap2(latforMap,longforMap);
    $("#imageForm").hide();
    
});
function getMap(latforMap, longforMap, positionforMarker){
//    $('#googleMap').each(function(){
     gmaps = new google.maps.Map(document.getElementById('googleMap'),{
     zoom:12,
     center: {lat: latforMap, lng: longforMap} 
     });
     marker = new google.maps.Marker({position: positionforMarker, map: gmaps});
//  });
  }
  
  function showForm(){
       $("#imageForm").toggle();
  }
