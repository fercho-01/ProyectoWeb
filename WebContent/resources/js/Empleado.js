
var app = angular.module('empleado',['ngRoute','ngCookies']);
var servicioObtenerPeticiones = "http://localhost:8080/ProyectoWeb/rest/Pqr/ObtenerSinRespuesta";
var servicioResponder = "http://localhost:8080/ProyectoWeb/rest/Pqr/Modificar";
app.config(['$routeProvider',function($routeProvider){
	$routeProvider.when('/',{
		templateUrl:'listaPeticiones.html',
		controller:'lista'
	});
}]);


app.controller('lista',function($scope,$cookies,serviceObtenerPeticiones,ServiceModificar){
	if($cookies.get('usuario')!=""){
		document.getElementById("user").innerHTML=$cookies.get('usuario');
	}
	
	serviceObtenerPeticiones.obtener().success(function(data){
		var json = data.webPqr;
		var check=false;
		$scope.pqrs = [];
		angular.forEach(json, function(item) {
			if(item.descripcion!=undefined){
				$scope.pqrs.push(item);
				check=true;
			}else{
				check=false;
			}
		});
		if(!check){
			$scope.pqrs.push(data.webPqr);
		}
	});
	
	$scope.enviar=function(data){
		var respuesta = document.getElementById(data).value;
		if(respuesta!=""){
		ServiceModificar.modificar(data,$cookies.get('usuario'),respuesta).success(function($data){
			//alert($data.modificado);
			if($data.modificado=="true"){
				//alert("modificado");
				location.reload(true);
			}
		});
		}
	}
});

app.service('serviceObtenerPeticiones',function($http){
	this.obtener = function(){
		return $http({
			method : 'GET',
			url : servicioObtenerPeticiones
		})
	}
});

app.service('ServiceModificar',function($http){
	this.modificar=function(idPqrS,cedulaS,respuestaS){
		return $http({
			method:'POST',
			url:servicioResponder,
			params:{
				idPqr:idPqrS,
				cedula:cedulaS,
				respuesta:respuestaS
			}
		});
	}
});
