package co.edu.udea.iw.ws;
/**
 * Esta clase expone los servicios requeridos por un usuario
 * @author LUIS FERNANDO OROZCO
 * @author GILBERTO RENDON
 * @author JONATHAN TORRES
 **/

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
	 * @return true si el usuario es valido, false de lo contrario
	 */
	@Produces(MediaType.TEXT_HTML)
	@GET
	@Path("Login")
	public String login(@QueryParam("cedula") String cedula, @QueryParam("pass")String pass){
		boolean retorno = false;
		try {
			retorno = usuarioService.login(cedula,pass);
		} catch (DaoException e) {
			e.printStackTrace();
			logger.error("error en el servicio web",e);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("error en el servicio web",e);
		}
		return String.valueOf(retorno);
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
	public String ModificarUsuario(@FormParam("cedula")String cedula,@FormParam("pass")String pass,@FormParam("nombre")String nombre,@FormParam("email")String email){
		return "false";
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
	public String crear(@FormParam("cedula")String cedula,@FormParam("pass")String pass,@FormParam("nombre")String nombre,@FormParam("email")String email){
		boolean retorno=false;
		try {
			retorno = usuarioService.crearUsuario(cedula, pass, nombre, email);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("error al crear un usuario",e);
		} catch (DaoException e) {
			e.printStackTrace();
			logger.error("error al crear un usuario",e);
		}
		return String.valueOf(retorno);
	}
	
}
