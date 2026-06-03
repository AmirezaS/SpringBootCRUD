app.controller('personsController',function ($scope,$http,$location,$route) {
    $http.get('http://localhost:8081/person/findAll.do').then(function (response) {
        $scope.persons = response.data;
    });
    $scope.editPerson = function (a) {
        $location.path('/update-person/' + a);
    };
    $scope.removePerson = function (b) {
        $http({
            method : 'POST',
            url : 'http://localhost:8081/person/remove.do?id=' + b
        }).then(function (response) {
            $location.path('person-list');
            $route.reload();
        });
    };
});
app.controller('insertController',function ($scope,$http,$location,$route) {
$scope.submitForm = function () {
    $http({
        method : 'GET',
        url : 'http://localhost:8081/person/save.do',
        params : $scope.person
    }).then(function (response) {
        $location.path('/person-list');
        $route.reload();
    })
}
});
app.controller('updateController',function ($scope,$http,$location,$route,$routeParams) {
    $scope.personID = $routeParams.id;
    $http({
        method : 'GET',
        url : 'http://localhost:8081/person/findOne.do?id=' +  $scope.personID,
    }).then(function (response) {
        $scope.person = response.data;
    });
    $scope.updateForm = function () {
        $http({
            method : 'POST',
            url : 'http://localhost:8081/person/update.do',
            params : $scope.person
        }).then(function (response) {
            $location.path('/person-list');
            $route.reload();
        });
    };
});