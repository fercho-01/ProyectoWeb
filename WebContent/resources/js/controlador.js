/**
 * Controlador angular de la pagina web
 */
var app = angular.module('proyecto',['ngRoute','ngCookies']);
var servicioLoginEmpleado = "http://localhost:8080/ProyectoWeb/rest/Empleado/Login";
var servicioLoginUsuario = "http://localhost:8080/ProyectoWeb/rest/Usuario/Login";
var servicioRegistrarUsuario = "http://localhost:8080/ProyectoWeb/rest/Usuario/Crear";

app.factory('auth', function($cookies){
    return{
    	 login : function(empleado)
         {
             //creamos la cookie con el nombre que nos han pasado
             $cookies.nombreUsuario = empleado,
             //mandamos a la lista de clientes
             window.location="./empleado.html";
         },
        
        validarEstado : function(){
            if(typeof($cookies.nombreUsuario) == 'undefined'){
                window.location"./index.html";
            }
            //en el caso de que intente acceder al login y ya haya iniciado sesi�n lo mandamos a 
            //la lista de clientes
            if(typeof($cookies.nombreUsuario) != 'undefined' && $location.url() == '/'){
                $location.url('/listaClientes');
            }
        }
    };
});

app.controller('login',function($scope,ServiceValidarEmpleado,ServiceValidarUsuario){
	$scope.cedula="";
	$scope.pass="";
	$scope.cedulaU="";
	$scope.passU="";
	
	$scope.validarEmpleado = function(){
		//alert("validar");
		ServiceValidarEmpleado.validar($scope.cedula,$scope.pass).success(function($data){
			if($data.valido=="true"){
				
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