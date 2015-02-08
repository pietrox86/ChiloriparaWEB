var geocoder;
function codeAddress(address) {
	
  geocoder = new google.maps.Geocoder();
  geocoder.geocode( { 'address': address}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
     var latA = results[0].geometry.location.lat();
     var lonA = results[0].geometry.location.lng();
    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
  

  function getLat(address){
	  geocoder = new google.maps.Geocoder();
	  geocoder.geocode( { 'address': address}, function(results, status) {
	    if (status == google.maps.GeocoderStatus.OK) {
	     return results[0].geometry.location.lat();
	    
	    } else {
	      alert('Geocode was not successful for the following reason: ' + status);
	    }
	  
	  });
  }
  
  function getLng(address){
	  geocoder = new google.maps.Geocoder();
	  geocoder.geocode( { 'address': address}, function(results, status) {
	    if (status == google.maps.GeocoderStatus.OK) {
	     return results[0].geometry.location.lng();
	    
	    } else {
	      alert('Geocode was not successful for the following reason: ' + status);
	    }
	  
	  }); 
  }
  
  
  
}

    