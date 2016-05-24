package co.edu.udea.iw.ws;

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
	
	@Produces(MediaType.TEXT_HTML)
	@GET
	@Path("Login")
	public String login(@QueryParam("cedula")String cedula,@QueryParam("pass")String pass){
		boolean retorno=false;
		try {
			retorno=empleadoService.validar(cedula, pass);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("error al exponer el servicio web loging para un empleado",e);
		} catch (DaoException e) {
			e.printStackTrace();
			logger.error("error al exponer el servicio web loging para un empleado",e);
		}
		return String.valueOf(retorno);
	}
	
	/**
	 * Metodo que expone un servicio web para crear un empleado en el sistema
	 * @param cedula Cedula del empleado
	 * @param pass Contraseña del empleado
	 * @param nombre Nombre del empleado
 	 * @param email Correo electronico del empleado
	 * @param cargo Cargo del empleado
	 * @return true en caso de crear el empleado exitosamente, de lo contrario false
	 */
	@Produces(MediaType.TEXT_HTML)
	@PUT
	@Path("Crear")
	public String crearEmpleado(@FormParam("cedula")String cedula,@FormParam("pass")String pass,@FormParam("nombre")String nombre,@FormParam("email")String email,@FormParam("cargo")String cargo){
		boolean retorno=false;
		try {
			retorno = empleadoService.registrarEmpleado(cedula, email, nombre, cargo, pass);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("error al crear el empleado",e);
		} catch (DaoException e) {
			e.printStackTrace();
			logger.error("error al crear el empleado",e);
		}
		return String.valueOf(retorno);
	}
	/**
	 * Metodo que expone un servicio web para modificar los datos de un empleado
	 * @param cedula Cedula del empleado
	 * @param pass Contraseña del empleado
	 * @param nombre Nombre del empleado
	 * @param email Correo electronico del empleado
	 * @param cargo Cargo del empleado
	 * @return true si la operacion se realizo con exito de lo contrario false
	 */
	@Produces(MediaType.TEXT_HTML)
	@POST
	@Path("Modificar")
	public String modificarEmpleado(@FormParam("cedula")String cedula,@FormParam("pass")String pass,@FormParam("nombre")String nombre,@FormParam("email")String email,@FormParam("cargo")String cargo){
		boolean retorno=false;
		try {
			retorno = empleadoService.modificarEmpleado(cedula, pass, nombre, email, cargo);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error al modificar empleado",e);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error al modificar empleado",e);
		}
		return String.valueOf(retorno);
	}
}