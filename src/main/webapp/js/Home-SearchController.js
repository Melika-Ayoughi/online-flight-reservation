/**
 * Created by melikaayoughi on 4/28/17.
 */
app.controller('Home-SearchController', function($scope, $rootScope, $http, $location){
    $scope.message = 'search!';
    $rootScope.flightList = [];

    this.searchFlights = function(){

        $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/searchService/getFlights',$rootScope.searchRequest).then(
            function (response) {
                $rootScope.flightList = response.data;
                $location.path('/Results');
            });


        //   //just for testing
        //   $rootScope.flightList = [{
        //     "mapSeatClassCapacities": [{
        //         "seatClass": {
        //             "name": "Y",
        //             "infantPrice": 1000,
        //             "adultPrice": 3000,
        //             "childPrice": 2000,
        //             "airlineCode": "IR",
        //             "originCode": "THR",
        //             "destinationCode": "MHD"
        //         },
        //         "capacity": 8
        //     }, {
        //         "seatClass": {
        //             "name": "M",
        //             "infantPrice": 2000,
        //             "adultPrice": 4000,
        //             "childPrice": 3000,
        //             "airlineCode": "IR",
        //             "originCode": "THR",
        //             "destinationCode": "MHD"
        //         },
        //         "capacity": 9
        //     }],
        //     "departureTime": "1740",
        //     "arrivalTime": "1850",
        //     "airplaneModel": "M80",
        //     "flightNumber": "452",
        //     "destCode": "MHD",
        //     "srcCode": "THR",
        //     "airlineCode": "IR",
        //     "flightId": 0,
        //     "date": "05Feb"
        // }, {
        //     "mapSeatClassCapacities": [{
        //         "seatClass": {
        //             "name": "F",
        //             "infantPrice": 1000,
        //             "adultPrice": 3000,
        //             "childPrice": 2000,
        //             "airlineCode": "IR",
        //             "originCode": "THR",
        //             "destinationCode": "MHD"
        //         },
        //         "capacity": 9
        //     }],
        //     "departureTime": "0730",
        //     "arrivalTime": "0840",
        //     "airplaneModel": "351",
        //     "flightNumber": "822",
        //     "destCode": "MHD",
        //     "srcCode": "THR",
        //     "airlineCode": "IR",
        //     "flightId": 1,
        //     "date": "05Feb"
        // }];
        //
        //
        //   $location.path('/Results');



    };
    $rootScope.searchRequest = {
        srcCode: 'THR',
        destCode: 'MHD',
        date: '05Feb',
        adultCount: 1,
        childCount: 1,
        infantCount: 1
    };

});
