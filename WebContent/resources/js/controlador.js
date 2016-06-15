/**
 * Controlador angular de la pagina web
 */
var app = angular.module('proyecto',['ngCookies']);
var servicioLoginEmpleado = "http://localhost:8080/ProyectoWeb/rest/Empleado/Login";
var servicioLoginUsuario = "http://localhost:8080/ProyectoWeb/rest/Usuario/Login";
var servicioRegistrarUsuario = "http://localhost:8080/ProyectoWeb/rest/Usuario/Crear";

app.controller('login',function($scope,ServiceValidarEmpleado,ServiceValidarUsuario,$cookies){
	$scope.cedula="";
	$scope.pass="";
	$scope.cedulaU="";
	$scope.passU="";
	
	$scope.validarEmpleado = function(){
		//alert("validar");
		ServiceValidarEmpleado.validar($scope.cedula,$scope.pass).success(function($data){
			if($data.valido=="true"){
				$cookies.put('usuario',$scope.cedula);
				window.location="./empleado.html";
			}else{
				alert("empleado no valido");
			}
		});
	}
	
	$scope.validarUsuario = function(){
		ServiceValidarUsuario.validar($scope.cedulaU,$scope.passU).success(function($data){
			if($data.valido=="true"){
				window.location="./main.html";
			}else{
				alert("usuario no valido");
			}
		});
	};
	
});

app.controller('registroUsuarios',function($scope,ServiceCrearUsuario){
	$scope.cedula="";
	$scope.pass = "";
	$scope.pass2="";
	$scope.nombre="";
	$scope.email="";
	
	
	$scope.registrarUsuario = function(){
		if($scope.pass==$scope.pass2){
		ServiceCrearUsuario.crear($scope.cedula,$scope.pass,$scope.nombre,$scope.email).success(function($data){
			if($data.realizado=="true"){
				alert("usuario creado");
			}else{
				alert("usuario no creado");
				$scope.errores = $data.errores;
			}
		});
		}else{
			alert("las contraseñas no coinciden");
		}
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

app.service('ServiceValidarUsuario',function($http){
	this.validar = function(login,pws){
		return $http({
			method:'GET',
			url:servicioLoginUsuario,
			params:{
				cedula:login,
				pass:pws
			}
		});
	}
});

app.service('ServiceCrearUsuario',function($http){
	this.crear = function(cedulaU,passU,nombreU,emailU){
		alert(cedulaU + emailU);
		return $http({
			method : 'POST',
			url : servicioRegistrarUsuario,
			params : {
				cedula : cedulaU,
				pass : passU,
				nombre : nombreU,
				email : emailU
			}
		});
	};
});