/**
 * Created by melikaayoughi on 4/28/17.
 */


app.controller('ReserveController', function($scope, $http, $rootScope, $location){
    localRef = this;

    $scope.message = 'reserve!';
    $scope.flight = $rootScope.chosenFlight;
    $scope.seatClass = $rootScope.chosenSeatClass;

    // $scope.adultCount = $rootScope.passengerInfo.adultCount;
    // $scope.childCount = $rootScope.passengerInfo.childCount;
    // $scope.infantCount = $rootScope.passengerInfo.infantCount;

    $scope.adultPassengerList = [];
    $scope.childPassengerList = [];
    $scope.infantPassengerList = [];

    $rootScope.ticketList = [];

    $scope.searchRequest = $rootScope.searchRequest;

    $scope.ticketRequest = {
        searchRequest: $scope.searchRequest,
        seatClass : $scope.seatClass.name,
        airlineCode : $scope.flight.airlineCode,
        flightNumber : $scope.flight.flightNumber,
        adultPassengerList: $scope.adultPassengerList,
        childPassengerList: $scope.childPassengerList,
        infantPassengerList: $scope.infantPassengerList
    };

    this.getTickets = function () {


        $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/ticketService/getTickets',$scope.ticketRequest).then(
            function (response) {
                $rootScope.ticketList = response.data;
                $location.path('/Tickets');
            });

        //
        // //just for testing
        // $rootScope.ticketList = [{
        //     "firstName": "mm",
        //     "surname": "mm",
        //     "referenceCode": "f2230245-ff09-85ac-8d44-5a883c47217e",
        //     "ticketNo": "192f34a9-b8bc-0320-f997-e09b03db1f60",
        //     "originCode": "THR",
        //     "destinationCode": "MHD",
        //     "airlineCode": "IR",
        //     "flightNo": "452",
        //     "seatClassName": "Y",
        //     "date": "05Feb",
        //     "departureTime": "1740",
        //     "arrivalTime": "1850",
        //     "airplaneModel": "M80",
        //     "type": "adult",
        //     "gender": "Mrs."
        // }, {
        //     "firstName": "kk",
        //     "surname": "kk",
        //     "referenceCode": "f2230245-ff09-85ac-8d44-5a883c47217e",
        //     "ticketNo": "03ed98c9-397f-d2e9-40cf-d0ccbd91696b",
        //     "originCode": "THR",
        //     "destinationCode": "MHD",
        //     "airlineCode": "IR",
        //     "flightNo": "452",
        //     "seatClassName": "Y",
        //     "date": "05Feb",
        //     "departureTime": "1740",
        //     "arrivalTime": "1850",
        //     "airplaneModel": "M80",
        //     "type": "child",
        //     "gender": "Mr."
        // }, {
        //     "firstName": "kk",
        //     "surname": "pp",
        //     "referenceCode": "f2230245-ff09-85ac-8d44-5a883c47217e",
        //     "ticketNo": "84094528-3e63-6aa1-9bbf-04223fc9c203",
        //     "originCode": "THR",
        //     "destinationCode": "MHD",
        //     "airlineCode": "IR",
        //     "flightNo": "452",
        //     "seatClassName": "Y",
        //     "date": "05Feb",
        //     "departureTime": "1740",
        //     "arrivalTime": "1850",
        //     "airplaneModel": "M80",
        //     "type": "infant",
        //     "gender": "Mrs."
        // }];
        //
        // $location.path('/Tickets');

    };



    for ( let index=0; index<$rootScope.searchRequest.adultCount; index++ ) {
        $scope.adultPassengerList.push(
            {
                firstName: "",
                lastName: "",
                gender: "",
                nationalID: ""
            });
    }

    for ( let index=0; index<$rootScope.searchRequest.childCount; index++ ) {
        $scope.childPassengerList.push(
            {
                firstName: "",
                lastName: "",
                gender: "",
                nationalID: ""
            });
    }

    for ( let index=0; index<$rootScope.searchRequest.infantCount; index++ ) {
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
