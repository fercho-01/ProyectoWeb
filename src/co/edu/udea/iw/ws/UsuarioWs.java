package co.edu.udea.iw.ws;
/**
 * Esta clase expone los servicios requeridos por un usuario
 * @author LUIS FERNANDO OROZCO
 * @author GILBERTO RENDON
 * @author JONATHAN TORRES
 **/

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
import co.edu.udea.iw.service.UsuarioService;
import co.edu.udea.iw.service.UsuarioServiceTest;
import co.edu.udea.iw.util.exception.DaoException;
import co.edu.udea.iw.util.exception.ServiceException;
import co.edu.udea.iw.util.validations.Validaciones;
@Component
@Path("Usuario")
public class UsuarioWs {
	@Autowired
	UsuarioService usuarioService;
	
	private static Logger logger=Logger.getLogger(UsuarioWs.class);
	
	/**
	 * Metodo que expone un servicio web para verificar si un usuario es valido
	 * @param cedula Cedula del usuario
	 * @param pass Contraseña del usuario
	 * @return json con resultado y posibles errores
	 */
	
	//retornar json
	@Produces(MediaType.TEXT_HTML)
	@GET
	@Path("Login")
	public String login(@QueryParam("cedula") String cedula, @QueryParam("pass")String pass){
		boolean retorno = false;
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
				retorno = usuarioService.login(cedula,pass);
			}
		} catch (DaoException e) {
			e.printStackTrace();
			logger.error("error en el servicio web",e);
			errores.add("{\"error\":\"usuario o contraseña no validos\"}");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("error en el servicio web",e);
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
	 * Metodo que expone un servicio web para modificar un usuario
	 * @param cedula Cedula del usuario
	 * @param pass Contraseña del usuario
	 * @param nombre nombre del usuario
	 * @param email correo electronico del usuario
	 * @return true si la modificacion se dio con exito, de lo contrario false
	 */
	@Produces(MediaType.TEXT_HTML)
	@POST
	@Path("Modificar")
	//retornar json con confirmacion
	public String ModificarUsuario(@FormParam("cedula")String cedula,@FormParam("pass")String pass,@FormParam("nombre")String nombre,@FormParam("email")String email){
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
		try {
			if(ejecutar){
				retorno = usuarioService.modificarUsuario(cedula, pass, nombre, email);
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error al modificar un usuario",e);
			errores.add("{\"error\":\""+e.getMessage()+"\"}");
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error al modificar un usuario",e);
			errores.add("{\"error\":\"Ha ocurrido un problema al guardar el usuario\"}");
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
	 * Metodo que expone un servicio web para crear un usuario nuevo
	 * @param cedula Cedula del usuario
	 * @param pass Contraseña del usuario
	 * @param nombre Nombre del usuario
	 * @param email correo electronico del usuario
	 * @return true si la creacion del usuario es correcta, false de lo contrario
	 */
	@Produces(MediaType.TEXT_HTML)
	@PUT
	@Path("Crear")
	//retornar json con usuario creado
	public String crear(@FormParam("cedula")String cedula,@FormParam("pass")String pass,@FormParam("nombre")String nombre,@FormParam("email")String email){
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
		try {
			if(ejecutar){
				retorno = usuarioService.crearUsuario(cedula, pass, nombre, email);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("error al crear un usuario",e);
			errores.add("{\"error\":\""+e.getMessage()+"\"}");
		} catch (DaoException e) {
			e.printStackTrace();
			logger.error("error al crear un usuario",e);
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
