/**
 * Created by melikaayoughi on 4/28/17.
 */


app.controller('ReserveController', function($scope, $http, $rootScope, $location){
    localRef = this;

    $scope.message = 'reserve!';
    $scope.flight = $rootScope.chosenFlight;
    $scope.newFlight = $rootScope.newChosenFlight;
    $scope.seatClass = $rootScope.chosenSeatClass;


    $scope.adultPassengerList = [];
    $scope.childPassengerList = [];
    $scope.infantPassengerList = [];

    $rootScope.ticketList = [];

    $scope.searchRequest = $rootScope.searchRequest;

    $scope.ticketRequest = {
        // token: $rootScope.loginResponse.token,
        token: $scope.loginResponse.token,
        searchRequest: $scope.searchRequest,
        seatClass : $scope.seatClass.name,
        airlineCode : $scope.flight.airlineCode,
        flightNumber : $scope.flight.flightNumber,
        adultPassengerList: $scope.adultPassengerList,
        childPassengerList: $scope.childPassengerList,
        infantPassengerList: $scope.infantPassengerList
    };


    if(($scope.newFlight.arrivalTime != $scope.flight.arrivalTime) || ($scope.newFlight.departureTime != $scope.flight.departureTime) ||
        ($scope.newFlight.airplaneModel != $scope.flight.airplaneModel)){
        alert("basic flight information was changed including arrivaltime, departuretime, airplane model");
        //change original chosen flight to show
        $scope.flight = $scope.newFlight;
    }


    for(var i=0; i< $scope.newFlight.mapSeatClassCapacities.length; i++){
        if($scope.newFlight.mapSeatClassCapacities[i].seatClass.name == $scope.seatClass.name){
            $scope.newChosenMapSeatClass = $scope.newFlight.mapSeatClassCapacities[i];
        }
    }
    if( ($scope.newChosenMapSeatClass.seatClass.adultPrice != $scope.seatClass.adultPrice) || ($scope.newChosenMapSeatClass.seatClass.childPrice != $scope.seatClass.childPrice)
        || ($scope.newChosenMapSeatClass.seatClass.infantPrice != $scope.seatClass.infantPrice) ){
        alert("seatclass information containing adultprice, childprice, infantprice changed");
        $scope.seatClass = $scope.newChosenMapSeatClass.seatClass;
    }

    this.getTickets = function () {
        $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/ticketService/getTickets',$scope.ticketRequest).then(
            function (response) {
                $rootScope.ticketList = response.data;
                $location.path('/Tickets');
            });
    };



    for ( var index=0; index<$rootScope.searchRequest.adultCount; index++ ) {
        $scope.adultPassengerList.push(
            {
                firstName: "",
                lastName: "",
                gender: "",
                nationalID: ""
            });
    }

    for ( var index=0; index<$rootScope.searchRequest.childCount; index++ ) {
        $scope.childPassengerList.push(
            {
                firstName: "",
                lastName: "",
                gender: "",
                nationalID: ""
            });
    }

    for ( var index=0; index<$rootScope.searchRequest.infantCount; index++ ) {
        $scope.infantPassengerList.push(
            {
                firstName: "",
                lastName: "",
                gender: "",
                nationalID: ""
            });
    }


    this.refreshPassengerLists = function(){
        console.log("in refreshpassengerList");
        localRef.refreshAdultPassenger();
        localRef.refreshChildPassenger();
        localRef.refreshInfantPassenger();
    };

    this.refreshAdultPassenger = function() {
        if($scope.adultPassengerList.length > $rootScope.searchRequest.adultCount) {
            $scope.adultPassengerList.pop();
            localRef.refreshAdultPassenger();
        }
        else if($scope.adultPassengerList.length < $rootScope.searchRequest.adultCount) {
            (function () {
                $scope.adultPassengerList.push(
                    {
                        firstName: "",
                        lastName: "",
                        gender: "",
                        nationalID: ""
                    }
                );
                localRef.refreshAdultPassenger();
            })();
        }
    };


    this.refreshChildPassenger = function() {
        if($scope.childPassengerList.length > $rootScope.searchRequest.childCount) {
            $scope.childPassengerList.pop();
            localRef.refreshChildPassenger();
        }
        else if($scope.childPassengerList.length < $rootScope.searchRequest.childCount) {
            (function () {
                $scope.childPassengerList.push(
                    {
                        firstName: "",
                        lastName: "",
                        gender: "",
                        nationalID: ""
                    }
                );
                localRef.refreshChildPassenger();
            })();

        }
    };

    this.refreshInfantPassenger = function() {
        if($scope.infantPassengerList.length > $rootScope.searchRequest.infantCount) {
            $scope.infantPassengerList.pop();
            localRef.refreshInfantPassenger();
        }
        else if($scope.infantPassengerList.length < $rootScope.searchRequest.infantCount) {
            (function () {
                $scope.infantPassengerList.push(
                    {
                        firstName: "",
                        lastName: "",
                        gender: "",
                        nationalID: ""
                    }
                );
                localRef.refreshInfantPassenger();
            })();

        }
    };
});
