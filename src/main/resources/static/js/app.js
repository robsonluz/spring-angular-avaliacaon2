var app = angular.module('app', ['ngResource']);


//HotelService
app.factory('HotelService', function($resource) {
	return $resource('/api/hoteis/:id', {}, {
		reservar: { 
			method: 'POST',
			url: '/api/hoteis/:id/reservar',
			params: { id: "@id" }
		},
		cancelarReserva: { 
			method: 'POST',
			url: '/api/hoteis/:id/cancelarReserva/:idReserva',
			params: { id: "@id", idReserva: "@idReserva" }
		}		
	});
});

//HotelListController
app.controller('HotelListController', function($scope, HotelService) {
	$scope.hoteis = HotelService.query();
});


//HotelShowController
app.controller('HotelShowController', function($scope, HotelService) {
	var id = getParameterByName("id");
	if(id) {
		$scope.hotel = HotelService.get({"id": id});
	}
	
	$scope.saveReserva = function() {
		var id = $scope.hotel.id;
		HotelService.reservar({"id": id}, $scope.reserva, function(){
			location.href="hotel-show.html?id=" + id;
		});		
	}
	
	$scope.cancelarReserva = function(reserva) {
		var id = $scope.hotel.id;
		HotelService.cancelarReserva({"id": id, "idReserva": reserva.id}, function(hotel) {
			$scope.hotel = hotel;
		});	
	}
});



//HotelFormController
app.controller('HotelFormController', function($scope, HotelService) {

	$scope.save = function() {
		HotelService.save($scope.hotel, function(){
			location.href = "hotel.html";	
		});
    }	
});



