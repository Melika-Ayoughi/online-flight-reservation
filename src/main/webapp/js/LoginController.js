/**
 * Created by Ali_Iman on 5/25/17.
 */
app.controller('LoginController', function($scope, $rootScope, $http, $location){
    $scope.message = 'login!';

    this.login = function(){
        $http.post('http://localhost:8080/online_flight_reservation/onlinereservation/loginService/login',$rootScope.loginRequest)
            .then(function successCallback(response) {
                $rootScope.loginResponse = response.data;
                $location.path('/Home-Search');

            }, function errorCallback(response) {
                console.log(response);
                alert("Username or Password Was Incorrect! Please Try Again");
                $location.path('/Login');
                // alert(response.data.message);
            });
    };
    $rootScope.loginRequest = {
        username: 'AliIman',
        password: '1235813'
    };

});
