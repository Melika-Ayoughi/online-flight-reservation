/**
 * Created by melikaayoughi on 4/25/17.
 */

var app = angular.module("App", ["ngRoute"]);

// directive for the head tag in html for loading partial css
app.directive('head', ['$rootScope','$compile',
    function($rootScope, $compile){
        return {
            restrict: 'E',
            link: function(scope, elem){
                var html = '<link rel="stylesheet" ng-repeat="(routeCtrl, cssUrl) in routeStyles" ng-href="{{cssUrl}}" />';
                elem.append($compile(html)(scope));
                scope.routeStyles = {};
                $rootScope.$on('$routeChangeStart', function (e, next, current) {
                    if(current && current.$$route && current.$$route.css){
                        if(!angular.isArray(current.$$route.css)){
                            current.$$route.css = [current.$$route.css];
                        }
                        angular.forEach(current.$$route.css, function(sheet){
                            delete scope.routeStyles[sheet];
                        });
                    }
                    if(next && next.$$route && next.$$route.css){
                        if(!angular.isArray(next.$$route.css)){
                            next.$$route.css = [next.$$route.css];
                        }
                        angular.forEach(next.$$route.css, function(sheet){
                            scope.routeStyles[sheet] = sheet;
                        });
                    }
                });
            }
        };
    }
]);

app.config(function($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl : "index.html",
            controller  : "mainController"
        })
        .when("/Home-Search", {
            templateUrl : "Home-Search.html",
            controller  : "Home-SearchController",
            css: "Home-Search.css"
        })
        .when("/Results", {
            templateUrl : "Results.html",
            controller  : "ResultsController",
            css : "Results.css"
        })
        .when("/Reserve", {
            templateUrl : "Reserve.html",
            controller  : "ReserveController",
            css: "Reserve.css"
        })
        .when("/Tickets", {
            templateUrl : "Tickets.html",
            controller  : "TicketsController",
            css: "Tickets.css"
        });
});


app.controller('mainController', function($scope, $http){
    $scope.message = 'main!';
});

app.controller('Home-SearchController', function($scope, $rootScope, $http, $location){
    $scope.message = 'search!';
    $rootScope.flightList = [];

    this.searchFlights = function(){
        $rootScope.passengerInfo.adultCount = $rootScope.searchRequest.adultCount;
        $rootScope.passengerInfo.childCount = $rootScope.searchRequest.childCount;
        $rootScope.passengerInfo.infantCount = $rootScope.searchRequest.infantCount;

        $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/searchService/getFlights',$rootScope.searchRequest).then(
            function (response) {
                $rootScope.flightList = response.data;
                $location.path('/Results');
            });

/*
        //just for testing
        $rootScope.flightList = [{
          "mapSeatClassCapacities": [{
              "seatClass": {
                  "name": "Y",
                  "infantPrice": 1000,
                  "adultPrice": 3000,
                  "childPrice": 2000,
                  "airlineCode": "IR",
                  "originCode": "THR",
                  "destinationCode": "MHD"
              },
              "capacity": 8
          }, {
              "seatClass": {
                  "name": "M",
                  "infantPrice": 2000,
                  "adultPrice": 4000,
                  "childPrice": 3000,
                  "airlineCode": "IR",
                  "originCode": "THR",
                  "destinationCode": "MHD"
              },
              "capacity": 9
          }],
          "departureTime": "1740",
          "arrivalTime": "1850",
          "airplaneModel": "M80",
          "flightNumber": "452",
          "destCode": "MHD",
          "srcCode": "THR",
          "airlineCode": "IR",
          "flightId": 0,
          "date": "05Feb"
      }, {
          "mapSeatClassCapacities": [{
              "seatClass": {
                  "name": "F",
                  "infantPrice": 1000,
                  "adultPrice": 3000,
                  "childPrice": 2000,
                  "airlineCode": "IR",
                  "originCode": "THR",
                  "destinationCode": "MHD"
              },
              "capacity": 9
          }],
          "departureTime": "0730",
          "arrivalTime": "0840",
          "airplaneModel": "351",
          "flightNumber": "822",
          "destCode": "MHD",
          "srcCode": "THR",
          "airlineCode": "IR",
          "flightId": 1,
          "date": "05Feb"
      }];


        $location.path('/Results');
 */


    };
    $rootScope.searchRequest = {
        srcCode: 'THR',
        destCode: 'MHD',
        date: '05Feb',
        adultCount: 1,
        childCount: 1,
        infantCount: 1
    };

    $rootScope.passengerInfo = {
        adultCount: 1,
        childCount: 1,
        infantCount: 1
    }
});

app.controller('ResultsController', function($scope, $rootScope, $http, $location){
    $scope.message = 'results!';
    $scope.flightSeatClassList = [];
    $scope.myValueFunction = function(flightSeatClass) {
        return 0;
    };

    this.loadReservePage = function(flight, seatClass){

        $rootScope.chosenFlight = flight;
        $rootScope.chosenSeatClass = seatClass;

        $location.path('/Reserve');
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
            return flightSeatClass.mapSeatClassCapacity.seatClass.adultPrice * $rootScope.passengerInfo.adultCount
                + flightSeatClass.mapSeatClassCapacity.seatClass.childPrice * $rootScope.passengerInfo.childCount
                + flightSeatClass.mapSeatClassCapacity.seatClass.infantPrice * $rootScope.passengerInfo.infantCount;
        };
    };

    this.descendingSortBasedOnPrice = function(){
        $scope.myValueFunction = function(flightSeatClass) {
            return -(flightSeatClass.mapSeatClassCapacity.seatClass.adultPrice * $rootScope.passengerInfo.adultCount
            + flightSeatClass.mapSeatClassCapacity.seatClass.childPrice * $rootScope.passengerInfo.childCount
            + flightSeatClass.mapSeatClassCapacity.seatClass.infantPrice * $rootScope.passengerInfo.infantCount);
        };
    };

});

app.controller('ReserveController', function($scope, $http, $rootScope, $location){
    localRef = this;

    $scope.message = 'reserve!';
    $scope.flight = $rootScope.chosenFlight;
    $scope.seatClass = $rootScope.chosenSeatClass;

    $scope.adultCount = $rootScope.passengerInfo.adultCount;
    $scope.childCount = $rootScope.passengerInfo.childCount;
    $scope.infantCount = $rootScope.passengerInfo.infantCount;

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


        // /*, $scope.seatClass, $scope.flight*/, $scope.adultPassengerList/*, $scope.childPassengerList, $scope.infantPassengerList*/


        $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/ticketService/getTickets',$scope.ticketRequest).then(
            function (response) {
                $rootScope.ticketList = response.data;
                $location.path('/Tickets');
            });
    };



    for ( let index=0; index<$scope.adultCount; index++ ) {
        $scope.adultPassengerList.push(
            {
                firstName: "",
                lastName: "",
                gender: "",
                nationalID: ""
        });
    }

    for ( let index=0; index<$scope.childCount; index++ ) {
        $scope.childPassengerList.push(
            {
                firstName: "",
                lastName: "",
                gender: "",
                nationalID: ""
            });
    }

    for ( let index=0; index<$scope.infantCount; index++ ) {
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
        if($scope.adultPassengerList.length > $scope.adultCount) {
            $scope.adultPassengerList.pop();
            localRef.refreshAdultPassenger();
        }
        else if($scope.adultPassengerList.length < $scope.adultCount) {
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
        if($scope.childPassengerList.length > $scope.childCount) {
            $scope.childPassengerList.pop();
            localRef.refreshChildPassenger();
        }
        else if($scope.childPassengerList.length < $scope.childCount) {
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
        if($scope.infantPassengerList.length > $scope.infantCount) {
            $scope.infantPassengerList.pop();
            localRef.refreshInfantPassenger();
        }
        else if($scope.infantPassengerList.length < $scope.infantCount) {
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

app.controller('TicketsController', function($scope, $rootScope){

    $scope.message = 'tickets!';

    $scope.tickets = $rootScope.ticketList;

});







