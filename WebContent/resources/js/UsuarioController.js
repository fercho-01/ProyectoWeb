var app = angular.module('usuario',['ngCookies']);
var servicioCrearPqr = "http://localhost:8080/ProyectoWeb/rest/Pqr/Realizar";
var servicioPeticiones = "http://localhost:8080/ProyectoWeb/rest/Pqr/ObtenerUsuario";
app.controller('usuario',function($scope,$cookies,ServiceCrearPqr){
	if ($cookies.get('usuario') != "") {
		document.getElementById("user").innerHTML = $cookies.get('usuario');
	}
	$scope.peticion="";
	$scope.queja="";
	$scope.reclamo="";
	$scope.realizarPeticion = function(){
		ServiceCrearPqr.crear($cookies.get('usuario'),'peticion',$scope.peticion).success(function(data){
			if(data.realizado=="true"){
				window.location = "./Usuario.html";
			}else{
				alert("ha ocurrido un error");
			}
		});
	};
	
	$scope.realizarQueja = function(){
			ServiceCrearPqr.crear($cookies.get('usuario'),'queja',$scope.queja).success(function(data){
				if(data.realizado=="true"){
					window.location = "./Usuario.html";
				}else{
					alert("ha ocurrido un error");
				}
			});
	};
	
	$scope.realizarReclamo = function(){
			ServiceCrearPqr.crear($cookies.get('usuario'),'reclamo',$scope.reclamo).success(function(data){
				if(data.realizado=="true"){
					window.location = "./Usuario.html";
				}else{
					alert("ha ocurrido un error");
				}
			});
		};
});

app.controller('view',function($scope,ServiceLista,$cookies){
	ServiceLista.obtener($cookies.get('usuario')).success(function(data){
		var json = data.webPqr2;
		var check = false;
		$scope.pqrs = [];
		angular.forEach(json, function(item) {
			if (item.descripcion != undefined) {
				$scope.pqrs.push(item);
				check = true;
			} else {
				check = false;
			}
		});
		if (!check) {
			$scope.pqrs.push(data.webPqr2);
		}
	});
});

app.service('ServiceCrearPqr',function($http){
	this.crear = function(cedulaU,tipoU,descripcionU){
		return $http({
			method:'POST',
			url:servicioCrearPqr,
			params:{
				cedula:cedulaU,
				tipo:tipoU,
				descripcion:descripcionU
			}
		});
	}
});

app.service('ServiceLista', function($http) {
	this.obtener = function(cedulaU) {
		return $http({
			method : 'GET',
			url : servicioPeticiones,
			params:{
				usuario:cedulaU
			}
		});
	};
});