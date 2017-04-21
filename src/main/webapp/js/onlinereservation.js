/**
 * Created by Ali_Iman on 4/20/17.
 */
(function () {
    var app = angular.module('onlinereservation', [ ]);

    app.controller("searchController", function ($scope, $http) {
        localRef = this;
        this.flights =[];

        this.search = function() {
            $http.get('http://localhost:8080/online_flight_reservation/onlinereservation/test/search').then(
                function (response) {
                    localRef.flights = response.data;
                    window.location.href = 'http://localhost:8080/online_flight_reservation/Home-Search.html';
                    // window.innerHTML = response.data;
                });
            // return{
            //     src:"THR",
            //     dest:"MHD",
            //     date:"05Feb",
            //     adultcnt: 1,
            //     childcnt: 1,
            //     infant: 1
            // };
        };
        this.search();
    });


})();