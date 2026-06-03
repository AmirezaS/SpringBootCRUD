var app = angular.module('crudSystem',['ngRoute']);
app.config(function($routeProvider){
    $routeProvider.
        when('/person-list',{
        templateUrl : "persons.html",
        controller : "personsController"
    }).when('/insert-person',{
        templateUrl : "insert.html",
        controller : "insertController"
    }).when('/update-person/:id',{
        templateUrl : "update.html",
        controller : "updateController"
    }).otherwise({
        templateUrl : "home.html"
    })
});