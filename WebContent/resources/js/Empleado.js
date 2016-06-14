var app = angular.module('empleado',['ngRoute']);

app.config(['$routeProvider',function($routeProvider){
	$routeProvider.when('/',{
		templateUrl:'listaPeticiones',
		controller:'lista'
	});
}]);

app.controller('ctrlEmpleado',function($scope,$location));