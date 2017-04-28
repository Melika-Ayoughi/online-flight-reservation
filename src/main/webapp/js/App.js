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








