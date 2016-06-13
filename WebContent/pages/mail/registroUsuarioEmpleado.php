<?php
defined("DS") ? NULL : define("DS", DIRECTORY_SEPARATOR);

//Vamos a ver si da sin contraseña o si es que el nombre root va en la segunda posición

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$mysqli = new mysqli("localhost", "dbaEncuestas", "root", "");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ")" . $mysqli->connect_error;
}	
	
	//Variables en el html
	//Empleado
	$empid = $_POST['empid'];
	//y los otros
	//Usuario
	$cedulaU = $_POST['cedulaU'];
	$nombreU = $_POST['nombreU'];
	$passU = $_POST['passu'];
	$emailU = $_POST['emailU'];
	
	$query13junio =INSERT INTO usuario VALUES ('$cedulaU','$passU','$nombreU','emailU');

	
	$query1 = "INSERT INTO movilidadinterno VALUES('$fsolicitud','$nombre','$apellidos','$lnacimiento','$fnacimiento','$sexo','$gsanguineo','$factorrh',
	'$tipodocumento','$identificacion','$email','$direccionres','$ciudadres','$tel','$cel','$facultadorigen','$progacademico',
	'$sede','$nivel','$proacumulado','$jefedepto','$teljefe','$emailjefe','$nombredestino','$sededestino','$facultaddestino','$progacademico1','$semestreAcademico','$udeac1','$udeac2',
	'$udeac3','$udeac4','$udeac5','$udeac6','$udean1','$udean2','$udean3','$udean4','$udean5','$udean6','$anfitrionac1','$anfitrionac2','$anfitrionac3','$anfitrionac4',
	'$anfitrionac5','$anfitrionac6','$anfitrionan1','$anfitrionan2','$anfitrionan3','$anfitrionan4','$anfitrionan5','$anfitrionan6')";
	echo $query13junio;
	

if (!$mysqli->query($query13junio)){
    echo "Table creation failed: (". $mysqli->errno.")".$mysqli->error;
	}

//try {
//    echo inverse(5)."\n";
//}catch(Exception $e){
//    echo 'Caught exception: ',  $e->getMessage(), "\n";
//}
//verificacion de existencia del directorio
//if ($error) {
  //  echo '<script>alert("Ocurrio un error al subir los archivos")</script>';
    //echo '<script>location.href="index.php";</script>';
//} else {
  //  echo '<script>alert("Los archivos se alojaron correctamente")</script>';
    //echo '<script>location.href="index.php";</script>';
//}
?>


