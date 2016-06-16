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

//import com.sun.webkit.WebPage;

import co.edu.udea.iw.dto.Empleado;
import co.edu.udea.iw.dto.Pqr;
import co.edu.udea.iw.service.PqrService;
import co.edu.udea.iw.util.encode.Cifrar;
import co.edu.udea.iw.util.exception.DaoException;
import co.edu.udea.iw.util.exception.ServiceException;
import co.edu.udea.iw.util.validations.Validaciones;
import co.edu.udea.iw.webDto.WebPqr2;
import co.edu.udea.iw.webDto.webPqr;
import co.edu.udea.iw.service.UsuarioService;
import co.edu.udea.iw.dao.hibernate.UsuarioDAOHibernate;
import co.edu.udea.iw.dto.Usuario;
import co.edu.udea.iw.service.EmpleadoService;
import co.edu.udea.iw.dao.EmpleadoDAO;
import co.edu.udea.iw.dao.UsuarioDAO;
import co.edu.udea.iw.dao.hibernate.EmpleadoDAOHibernate;


/**
 * 
 * Esta clase expone los servicios web necesarios para un Pqr
 * @author LUIS FERNANDO OROZCO
 * @author JONATHAN TORRES VELEZ
 * @author GILBERTO RENDON
 *
 */
@Component
@Path("Pqr")
public class PqrWs {
	@Autowired
	PqrService pqrService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	EmpleadoService empleadoService;
	
	private static Logger logger=Logger.getLogger(PqrWs.class);
	
	/**
	 * Metodo para obtener una peticion dada su id
	 * @param id identificacion de la peticion
	 * @return String en formato json
	 **/
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("Obtener")
	public String buscarPqr(@QueryParam("id")int id){
		boolean retorno=false;
		boolean validar=true;
		List<String> errores= new ArrayList<String>();
		try {
			if(validar){
				retorno=pqrService.Buscar(id);
			}
		} catch (ServiceException e) {
			//e.printStackTrace();
			logger.error("error al exponer el servicio web buscarPqr para un pqr",e);
			errores.add("{\"error\":\"id no existe\"}");
		} catch (DaoException e) {
			//e.printStackTrace();
			logger.error("error al exponer el servicio web buscarPqr para un pqr",e);
			errores.add("{\"error\":\"id no existe\"}");
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
	 * Metodo servicio web para crear pqrs
	 * @param cedula cedula del usuario que diligencia el pqr
	 * @param password contraseï¿½a del usuario que diligencia el pqr
	 * @param tipo tipo de solicitud (pregunta, queja, reclamo)
	 * @param descripcion descripcion y por quï¿½ de la solicitud
	 * @return
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	@Path("Realizar")
	public String realizarPqr(@QueryParam("cedula")String cedula,@QueryParam("tipo")String tipo,
			@QueryParam("descripcion")String descripcion){

		boolean retorno=false;
		boolean ejecutar=true;
	
		List<String> errores= new ArrayList<String>();
		if(Validaciones.isTextoVacio(cedula)){
			ejecutar=false;
			errores.add("{\"error\":\"la cedula no puede ser nula\"}");
		}
		
		if(Validaciones.isTextoVacio(tipo)){
			ejecutar=false;
			errores.add("{\"error\":\"el tipo no puede ser nula\"}");
		}
		
		if(Validaciones.isTextoVacio(descripcion)){
			ejecutar=false;
			errores.add("{\"error\":\"La descripcion no puede ser nula\"}");
		}
		
		try {
			if(ejecutar){
				Usuario usuario = usuarioService.obtener(cedula);
				if(usuario!=null){
					retorno = pqrService.realiarPqr(usuario, tipo, descripcion);
				}else{
					errores.add("{\"error\":\"Usuario no valido\"}");
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			errores.add("{\"error\":\""+e.getMessage()+"\"}");
			logger.error("error al crear el pqr",e);
		} catch (DaoException e) {
			errores.add("{\"error\":\"Ha ocurrido un problema al guardar el pqr\"}");
			e.printStackTrace();
			logger.error("error al crear el pqr",e);
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
		System.out.println("");
		return cadena;
	}
	
	/**
	 * Metodo servicio web para modificar o responder un pqr
	 * @param idPqr id del pqr que se va a modificar, es conocido por el empleado
	 * @param cedula cedula del empleado que responde el pqr
	 * @param password contraseï¿½a del empleado que responde el pqr
	 * @param respuesta respuesta que da el empleado
	 * @return
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	@Path("Modificar")
	public String modificarPqr(@QueryParam("idPqr")Integer idPqr,@QueryParam("cedula")String cedula,@QueryParam("respuesta")String respuesta){
		boolean retorno=false;
		boolean ejecutar=true;
		System.out.println("idpqr::: "+idPqr);
	
		List<String> errores= new ArrayList<String>();
		if(Validaciones.isTextoVacio(cedula)){
			ejecutar=false;
			errores.add("{\"error\":\"la cedula no puede ser nula\"}");
		}
		
		if(Validaciones.isTextoVacio(respuesta)){
			ejecutar=false;
			errores.add("{\"error\":\"la respuesta no puede ser nula\"}");
		}
		
		try {
			if(ejecutar){
				Empleado empleado = empleadoService.obtener(cedula);
				if(empleado!=null){
					retorno = pqrService.modificarPqr(idPqr, empleado, respuesta);
				}else{
					errores.add("{\"error\":\"empleado no valido\"}");
				}
				
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			errores.add("{\"error\":\""+e.getMessage()+"\"}");
			logger.error("error al modificar el pqr",e);
		} catch (DaoException e) {
			errores.add("{\"error\":\"Ha ocurrido un problema al modificar el pqr\"}");
			e.printStackTrace();
			logger.error("error al modificar el pqr",e);
		}
		String cadena = "{\"modificado\":\""+retorno+"\"";
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
	 * Metodo para obtener pqrs sin responder
	 * @return json con la información
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerSinRespuesta")
	public List<webPqr> obtenerSinRespuesta(){
		List<webPqr> lista = new ArrayList<webPqr>();
		try {
			for(Pqr pqr:pqrService.obtenerNoResueltos()){
				webPqr wPqr = new webPqr();
				wPqr.setDescripcion(pqr.getDescripcion());
				wPqr.setFechaSolicitud(pqr.getFechaSolicitud());
				wPqr.setId(pqr.getId());
				wPqr.setTipo(pqr.getTipo());
				wPqr.setUsuario(pqr.getUsuario().getCedula());
				lista.add(wPqr);
			}
		} catch (DaoException e) {
			
		}
		return lista;
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerUsuario")
	public List<WebPqr2> obtenerUsuario(@QueryParam("usuario")String usuario){
		System.out.println(usuario);
		List<WebPqr2> lista = new ArrayList<WebPqr2>();
		try {
			for(Pqr pqr:pqrService.obtenerUsuario(usuario)){
				WebPqr2 wPqr = new WebPqr2();
				wPqr.setDescripcion(pqr.getDescripcion());
				wPqr.setFechaSolicitud(pqr.getFechaSolicitud());
				wPqr.setId(pqr.getId());
				wPqr.setTipo(pqr.getTipo());
				wPqr.setEmpleado(pqr.getEstado());
				wPqr.setEstado(pqr.getEstado());
				wPqr.setFechaRespuesta(pqr.getFechaRespuesta());
				wPqr.setRespuesta(pqr.getRespuesta());
				lista.add(wPqr);
			}
		} catch (DaoException e) {
			
		}
		System.out.println(lista.size());
		return lista;
	}
	
}
