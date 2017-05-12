/**
 * Created by melikaayoughi on 4/28/17.
 */


app.controller('ResultsController', function($scope, $rootScope, $http, $location){
    $scope.message = 'results!';
    $scope.flightSeatClassList = [];
    $scope.myValueFunction = function(flightSeatClass) {
        return 0;
    };

    this.loadReservePage = function(flight, seatClass){

        $scope.resultRequest = {
            srcCode: flight.srcCode,
            destCode: flight.destCode,
            date: flight.date,
            airlineCode: flight.airlineCode,
            flightNumber: flight.flightNumber,
            seatClassName: seatClass.name
        };

        $rootScope.chosenFlight = flight;
        $rootScope.chosenSeatClass = seatClass;

        $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/resultService/getImmediateFlightInfo',$scope.resultRequest).then(
            function (response) {
                $rootScope.newChosenFlight = response.data;
                $location.path('/Reserve');
            });
    };

    for(var i = 0; i < $rootScope.flightList.length; i++) {
        for (var j = 0; j < $rootScope.flightList[i].mapSeatClassCapacities.length; j++) {
            (function () {
                $scope.flightSeatClassList.push(
                    {
                        flight: $rootScope.flightList[i],
                        mapSeatClassCapacity: $rootScope.flightList[i].mapSeatClassCapacities[j]
                    }
                );
            })();
        }
    }

    this.ascendingSortBasedOnPrice = function(){
        $scope.myValueFunction = function(flightSeatClass) {
            return flightSeatClass.mapSeatClassCapacity.seatClass.adultPrice * $rootScope.searchRequest.adultCount
                + flightSeatClass.mapSeatClassCapacity.seatClass.childPrice * $rootScope.searchRequest.childCount
                + flightSeatClass.mapSeatClassCapacity.seatClass.infantPrice * $rootScope.searchRequest.infantCount;
        };
    };

    this.descendingSortBasedOnPrice = function(){
        $scope.myValueFunction = function(flightSeatClass) {
            return -(flightSeatClass.mapSeatClassCapacity.seatClass.adultPrice * $rootScope.searchRequest.adultCount
            + flightSeatClass.mapSeatClassCapacity.seatClass.childPrice * $rootScope.searchRequest.childCount
            + flightSeatClass.mapSeatClassCapacity.seatClass.infantPrice * $rootScope.searchRequest.infantCount);
        };
    };

});