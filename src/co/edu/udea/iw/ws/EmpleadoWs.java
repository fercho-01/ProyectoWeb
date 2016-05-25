package co.edu.udea.iw.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.service.EmpleadoService;
import co.edu.udea.iw.util.exception.DaoException;
import co.edu.udea.iw.util.exception.ServiceException;
import co.edu.udea.iw.util.validations.Validaciones;

/**
 * Esta clase expone los servicios web necesarios para un empleado
 * @author LUIS FERNANDO OROZCO
 * @author GILBERTO RENDON
 * @author JONATHAN TORRES
 *
 */
@Component
@Path("Empleado")
public class EmpleadoWs {
	@Autowired
	EmpleadoService empleadoService;
	
	private static Logger logger=Logger.getLogger(UsuarioWs.class);
	
	/**
	 * Metodo que expone un servicio web para loggear empleados en el sistema
	 * @param cedula Cedula del empleado
	 * @param pass Contraseña del empleado
	 * @return json con la validacion
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("Login")
	public String login(@QueryParam("cedula")String cedula,@QueryParam("pass")String pass){
		boolean retorno=false;
		boolean validar=true;
		List<String> errores= new ArrayList<String>();
		if(Validaciones.isTextoVacio(cedula)){
			validar=false;
			errores.add("{\"error\":\"la cedula no puede ser nula\"}");
		}
		if(Validaciones.isTextoVacio(pass)){
			validar=false;
			errores.add("{\"error\":\"la contraseña no puede ser nula\"}");
		}
		try {
			if(validar){
				retorno=empleadoService.validar(cedula, pass);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("error al exponer el servicio web loging para un empleado",e);
			errores.add("{\"error\":\"usuario o contraseña no validos\"}");
		} catch (DaoException e) {
			e.printStackTrace();
			logger.error("error al exponer el servicio web loging para un empleado",e);
			errores.add("{\"error\":\"usuario o contraseña no validos\"}");
		}
		String cadena = "{\"valido\":\""+retorno+"\"";
		if(!errores.isEmpty()){
			cadena+=",\"errores\":[";
			for(String error:errores){
				cadena+=error+",";
			}
			cadena = cadena.substring(0,cadena.length()-1);
			cadena+="]";
		}
		cadena+="}";
		return cadena;
	}
	
	/**
	 * Metodo que expone un servicio web para crear un empleado en el sistema
	 * @param cedula Cedula del empleado
	 * @param pass Contraseña del empleado
	 * @param nombre Nombre del empleado
 	 * @param email Correo electronico del empleado
	 * @param cargo Cargo del empleado
	 * @return json con la validacion
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	@Path("Crear")
	public String crearEmpleado(@FormParam("cedula")String cedula,@FormParam("pass")String pass,@FormParam("nombre")String nombre,@FormParam("email")String email,@FormParam("cargo")String cargo){
		boolean retorno=false;
		boolean ejecutar=true;
		List<String> errores= new ArrayList<String>();
		if(Validaciones.isTextoVacio(cedula)){
			ejecutar=false;
			errores.add("{\"error\":\"la cedula no puede ser nula\"}");
		}
		if(Validaciones.isTextoVacio(pass)){
			ejecutar=false;
			errores.add("{\"error\":\"la contraseña no puede ser nula\"}");
		}
		if(Validaciones.isTextoVacio(nombre)){
			ejecutar=false;
			errores.add("{\"error\":\"El nombre no puede ser nulo\"}");
		}
		if(!Validaciones.isEmail(email)){
			ejecutar=false;
			errores.add("{\"error\":\"Email no valido\"}");
		}
		if(Validaciones.isTextoVacio(cargo)){
			ejecutar=false;
			errores.add("{\"error\":\"El cargo no puede ser nulo\"}");
		}
		try {
			if(ejecutar){
				retorno = empleadoService.registrarEmpleado(cedula, email, nombre, cargo, pass);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			errores.add("{\"error\":\""+e.getMessage()+"\"}");
			logger.error("error al crear el empleado",e);
		} catch (DaoException e) {
			errores.add("{\"error\":\"Ha ocurrido un problema al guardar el empleado\"}");
			e.printStackTrace();
			logger.error("error al crear el empleado",e);
		}
		String cadena = "{\"realizado\":\""+retorno+"\"";
		if(!errores.isEmpty()){
			cadena+=",\"errores\":[";
			for(String error:errores){
				cadena+=error+",";
			}
			cadena = cadena.substring(0,cadena.length()-1);
			cadena+="]";
		}
		cadena+="}";
		return cadena;
	}
	/**
	 * Metodo que expone un servicio web para modificar los datos de un empleado
	 * @param cedula Cedula del empleado
	 * @param pass Contraseña del empleado
	 * @param nombre Nombre del empleado
	 * @param email Correo electronico del empleado
	 * @param cargo Cargo del empleado
	 * @return json con confirmacion, y posibles errores
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	@Path("Modificar")
	public String modificarEmpleado(@FormParam("cedula")String cedula,@FormParam("pass")String pass,@FormParam("nombre")String nombre,@FormParam("email")String email,@FormParam("cargo")String cargo){
		boolean retorno=false;
		boolean ejecutar=true;
		List<String> errores= new ArrayList<String>();
		if(Validaciones.isTextoVacio(cedula)){
			ejecutar=false;
			errores.add("{\"error\":\"la cedula no puede ser nula\"}");
		}
		if(Validaciones.isTextoVacio(pass)){
			ejecutar=false;
			errores.add("{\"error\":\"la contraseña no puede ser nula\"}");
		}
		if(Validaciones.isTextoVacio(nombre)){
			ejecutar=false;
			errores.add("{\"error\":\"El nombre no puede ser nulo\"}");
		}
		if(!Validaciones.isEmail(email)){
			ejecutar=false;
			errores.add("{\"error\":\"Email no valido\"}");
		}
		if(Validaciones.isTextoVacio(cargo)){
			ejecutar=false;
			errores.add("{\"error\":\"El cargo no puede ser nulo\"}");
		}
		
		try {
			if(ejecutar){
				retorno = empleadoService.modificarEmpleado(cedula, pass, nombre, email, cargo);
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error al modificar empleado",e);
			errores.add("{\"error\":\""+e.getMessage()+"\"}");
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error al modificar empleado",e);
			errores.add("{\"error\":\"Ha ocurrido un problema al guardar el empleado\"}");
		}
		String cadena = "{\"realizado\":\""+retorno+"\"";
		if(!errores.isEmpty()){
			cadena+=",\"errores\":[";
			for(String error:errores){
				cadena+=error+",";
			}
			cadena = cadena.substring(0,cadena.length()-1);
			cadena+="]";
		}
		cadena+="}";
		return cadena;
	}
}