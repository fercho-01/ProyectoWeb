
var app = angular.module('empleado',['ngRoute','ngCookies']);
var servicioObtenerPeticiones = "http://localhost:8080/ProyectoWeb/rest/Pqr/ObtenerSinRespuesta";
var servicioResponder = "http://localhost:8080/ProyectoWeb/rest/Pqr/Modificar";
var servicioCrearEmpleado = "http://localhost:8080/ProyectoWeb/rest/Empleado/Crear";
app.config(['$routeProvider',function($routeProvider){
	$routeProvider.when('/',{
		templateUrl:'listaPeticiones.html',
		controller:'lista'
	});
	
	$routeProvider.when('/crear',{
		templateUrl:'crear.html',
		controller:'crear'
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
			if($data.modificado=="true"){
				location.reload(true);
			}
		});
		}
	}
});

app.controller('crear',function($scope,$location,ServiceCrearEmpleado){
	$scope.cedula="";
	$scope.pass="";
	$scope.pass2="";
	$scope.nombre="";
	$scope.rol="";
	$scope.email="";
	
	$scope.crearEmpleado = function(){
		
		ServiceCrearEmpleado.crear($scope.cedula,$scope.pass,$scope.nombre,$scope.email,$scope.rol).success(function(data){
			if(data.realizado=="true"){
				$location.url('/');
			}else{
				//mostrar errores
			}
			
		});
	};
	
	
});
app.service('serviceObtenerPeticiones',function($http){
	this.obtener = function(){
		return $http({
			method : 'GET',
			url : servicioObtenerPeticiones
		});
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

app.service('ServiceCrearEmpleado',function($http){
	this.crear = function(cedulaE,passE,nombreE,emailE,cargoE){
		console.log(cedulaE+" "+passE+" "+nombreE+" "+emailE+" "+cargoE);
		return $http({
			method:'PUT',
			url:servicioCrearEmpleado,
			params:{
				cedula:cedulaE,
				pass:passE,
				nombre:nombreE,
				email:emailE,
				cargo:cargoE
			}
		});
	}
});

