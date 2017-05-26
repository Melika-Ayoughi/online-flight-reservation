/**
 * Created by melikaayoughi on 5/26/17.
 */

app.controller('ViewTicketsByIdController', function($scope, $rootScope, $http, $routeParams,$location){
    $scope.message = 'ViewTicketsByIdController!';
    $scope.mytoken = $rootScope.loginResponse.token;
    $scope.ticketId = $routeParams.ticket_id;

    alert($scope.message);
    alert($scope.ticketId);
    alert($scope.mytoken);

    $rootScope.ViewTicketRequest = {
        userToken: $scope.mytoken,
        ticketNumber: $scope.ticketId
    };

    $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/ticketService/ticketsById',$rootScope.ViewTicketRequest)
        .then(function successCallback(response) {
            document.body.innerHTML = JSON.stringify(response.data);

        }, function errorCallback(response) {
            console.log(response);
            alert("Unauthorized access!");
            $location.path('/Home-Search');
        });
});
