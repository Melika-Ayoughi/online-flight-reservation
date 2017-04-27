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
    // localThis = this;

    this.searchFlights = function(){
        $rootScope.passengerInfo.adultCount = $scope.searchRequest.adultCount;
        $rootScope.passengerInfo.childCount = $scope.searchRequest.childCount;
        $rootScope.passengerInfo.infantCount = $scope.searchRequest.infantCount;

        // $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/searchService/getFlights',$scope.searchRequest).then(
        //     function (response) {
        //         $scope.flightList = response.data;
        //         $location.path('/Results');
        //     });


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


    };

    $scope.searchRequest = {
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


    this.loadReservePage = function(flight, seatClass){

        $rootScope.chosenFlight = flight;
        $rootScope.chosenSeatClass = seatClass;

        $location.path('/Reserve');
    };


    var completeResult;
    var done = false;


    this.setup = function(){
        document.getElementsByTagName("body")[0].innerHTML = document.getElementsByTagName("body")[0].innerHTML;
        if (!done) {
            done = true;
            completeResult = document.getElementById("results");
            document.getElementsByTagName("body")[0].innerHTML = document.getElementsByTagName("body")[0].innerHTML;
        }
    };


    this.filterSeatClass = function(){
        document.getElementById("results").innerHTML = completeResult.innerHTML;

        var seatClassName = document.getElementById("seatClassFilter").value;
        var airlineName = document.getElementById("airlineFilter").value;

        var seatClassFilter = seatClassName.length == 1;
        var airlineFilter = airlineName.length != 0;

        if (seatClassFilter || airlineFilter) {
            var finalResultsHtml = "";
            var elements = document.getElementsByClassName("results-form");

            for(var i = 0; i < elements.length; i++) {
                var nodes = elements[i].getElementsByClassName("col-md-6 place-middle font-small lbl-color-light-gray pull-right");
                var node = nodes[0];
                var tempSeatClass = node.getElementsByTagName("span")[3].innerHTML.replace(/\s/g,'');
                nodes = elements[i].getElementsByClassName("col-md-3 place-middle font-large lbl-color-black pull-right");
                node = nodes[0];
                var tempAirline = (node.innerHTML.match(/\S+/g) || [])[0];

                if((!seatClassFilter || (seatClassName == tempSeatClass)) && (!airlineFilter || (airlineName == tempAirline)))
                    finalResultsHtml += elements[i].outerHTML;
            }

            document.getElementById("results").innerHTML = finalResultsHtml;
        }
    };


    this.ascendingCompare = function(a, b){
        var nodes = a.getElementsByClassName("col-md-12 place-middle");
        var node = nodes[0];
        var priceA = node.getElementsByTagName("span")[0].innerHTML;

        nodes = b.getElementsByClassName("col-md-12 place-middle");
        node = nodes[0];
        var priceB = node.getElementsByTagName("span")[0].innerHTML;

        return priceA - priceB;
    };


    this.ascendingSortBasedOnPrice = function(){
        var elements = document.getElementsByClassName("results-form");
        elements = Array.prototype.slice.call(elements, 0);
        elements.sort(ascendingCompare);
        var finalResultsHtml = "";
        for(var i = 0; i < elements.length; i++)
            finalResultsHtml += elements[i].outerHTML;
        document.getElementById("results").innerHTML = finalResultsHtml;
    };


    this.descendingCompare = function(a, b){
        var nodes = a.getElementsByClassName("col-md-12 place-middle");
        var node = nodes[0];
        var priceA = node.getElementsByTagName("span")[0].innerHTML;

        nodes = b.getElementsByClassName("col-md-12 place-middle");
        node = nodes[0];
        var priceB = node.getElementsByTagName("span")[0].innerHTML;

        return priceB - priceA;
    };


    this.descendingSortBasedOnPrice = function(){
        var elements = document.getElementsByClassName("results-form");
        elements = Array.prototype.slice.call(elements, 0);
        elements.sort(descendingCompare);
        var finalResultsHtml = "";
        for(var i = 0; i < elements.length; i++)
            finalResultsHtml += elements[i].outerHTML;
        document.getElementById("results").innerHTML = finalResultsHtml;
    };
});





app.controller('ReserveController', function($scope, $http, $rootScope){
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





app.controller('TicketsController', function($scope, $http){
    $scope.message = 'tickets!';
});