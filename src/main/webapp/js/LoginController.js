/**
 * Created by Ali_Iman on 5/25/17.
 */
app.controller('LoginController', function($scope, $rootScope, $http, $location){
    $scope.message = 'login!';

    this.login = function(){
        $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/loginService/login',$rootScope.loginRequest).then(
            function (response) {
                $rootScope.loginResponse = response.data;
                $location.path('/Home-Search');
            });
    };

    $rootScope.loginRequest = {
        username: 'AliIman',
        password: '1235813'
    };

});
