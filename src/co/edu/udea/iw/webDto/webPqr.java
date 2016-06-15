package co.edu.udea.iw.webDto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import co.edu.udea.iw.dto.Empleado;
import co.edu.udea.iw.dto.Usuario;

/**
 * Clase encargada de mapear objetos de la capa de service a los servicios web
 * @author LUIS
 *
 */
@XmlRootElement
public class webPqr {
	private Integer id;
	private String usuario;
	private String tipo;
	private String descripcion;
	private Date fechaSolicitud;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	
}
