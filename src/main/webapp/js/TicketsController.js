/**
 * Created by melikaayoughi on 4/28/17.
 */
app.controller('TicketsController', function($scope, $rootScope){

    $scope.message = 'tickets!';

    $scope.tickets = $rootScope.ticketList;

});