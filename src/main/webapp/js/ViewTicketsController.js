/**
 * Created by melikaayoughi on 5/26/17.
 */


app.controller('ViewTicketsController', function($scope, $rootScope, $http, $location){
    $scope.message = 'viewTicketsController!';
    $scope.token = $rootScope.loginResponse.token;

    alert($scope.message);
    $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/ticketService/tickets',$scope.token)
        .then(function successCallback(response) {
            document.body.innerHTML = JSON.stringify(response.data);

        }, function errorCallback(response) {
            console.log(response);
            alert("Unauthorized access!");
            $location.path('/Home-Search');
        });
});
