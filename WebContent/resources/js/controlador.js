/**
 * Controlador angular de la pagina web
 */
var app = angular.module('proyecto',['ngRoute']);
var servicioLoginEmpleado = "http://localhost:8080/ProyectoWeb/rest/Empleado/Login";

app.config(['$routeProvider',function($routeProvider){
	$routeProvider.when('/',{
		templateUrl:'main.html',
		controller:'main'
	}).otherwise({redirectTo: '/'});
}]);

app.controller('login',function($scope,ServiceValidarEmpleado){
	$scope.cedula="";
	$scope.pass="";
	
	$scope.validarEmpleado = function(){
		//alert("validar");
		ServiceValidarEmpleado.validar($scope.cedula,$scope.pass).success(function($data){
			if($data.valido=="true"){
				window.location="./main.html";
			}else{
				alert("empleado no valido");
			}
		});
	}
});

app.service('ServiceValidarEmpleado',function($http){
	this.validar = function(login,pws){
		return $http({
			method:'GET',
			url:servicioLoginEmpleado,
			params:{
				cedula:login,
				pass:pws
			}
		});
	};
});