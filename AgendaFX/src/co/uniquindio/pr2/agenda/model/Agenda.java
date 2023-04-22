package co.uniquindio.pr2.agenda.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import co.uniquindio.pr2.agenda.exceptions.ContactoException;

public class Agenda implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private Contacto[] listaContactos;
	private Grupo[] listaGrupos;
	private Reunion[] listaReuniones;


	public Agenda(String nombre, int numeroContactos, int numeroGrupos, int numeroReuniones) {
		super();
		this.nombre = nombre;
		this.listaContactos = new Contacto[numeroContactos];
		this.listaGrupos = new Grupo[numeroGrupos];
		this.listaReuniones = new Reunion[numeroReuniones];
	}

	public Agenda() {
		super();

//		Contacto contacto = new Contacto("San","Ovalle","BR 7 de Agosto","3133131313","san@correo.com", 0, 0);
//		listaContactos[0] = contacto;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Contacto[] getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(Contacto[] listaContactos) {
		this.listaContactos = listaContactos;
	}

	public Grupo[] getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(Grupo[] listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public Reunion[] getListaReuniones() {
		return listaReuniones;
	}

	public void setListaReuniones(Reunion[] listaReuniones) {
		this.listaReuniones = listaReuniones;
	}

	@Override
	public String toString() {
		return "Agenda [nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agenda other = (Agenda) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	/**
	 * Metodo para añadir contacto
	 * Se busca el contacto y se verifica si existe ya en la agenda o no existe
	 * tambien se verifica si hay una posicion para el contacto o no
	 * si existe, exception(ya existe) - si no existe, verifica posicion y si hay
	 * posicion disponible, se añade en esa posicion al contacto
	 *
	 * @param newContacto
	 * @throws ContactoException
	 * @return true si se añadio y false si no se pudo añadir
	 */

	public boolean aniadirContacto(Contacto newContacto) throws ContactoException {

		boolean aniadido = false;
		Contacto contacto = obtenerContacto(newContacto);
		int posDisponible = obtenerPosicion();

		if (!existeContacto(newContacto)) {
			if(contacto == null && posDisponible != -1) {
				listaContactos[posDisponible] = newContacto;
				aniadido = true;
				return aniadido;
			}
		}else{
			return aniadido;
		}
		return aniadido;

	}


	/**
	 * Metodo que recibe el arreglo y determina si la posicion está libre o no
	 * si no existe = -1, si existe = retorna posicion
	 * @return posicion libre
	 */

	private int obtenerPosicion() {
		for (int i = 0; i < listaContactos.length; i++) {
			if (listaContactos[i] == null) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Metodo para buscar un contacto a la lista
	 */

//	private Contacto buscarContacto(Contacto newContacto) {
//
//		List<Contacto> asList = Arrays.asList(listaContactos);
//
//		Optional<Contacto> findFirst = asList.stream().filter(c -> c.equals(newContacto)).findFirst();
//
//		return findFirst.get();
//	}

	/**
	 * Metodo para obtener el contacto de la lista de contactos
	 * Si existe y cumple con la comparativa se asigna como el contacto, de lo contrario queda vacío
	 * @param newContacto
	 * @return contactoHallad si se encuentra, en caso de no hacerlo -> null.
	 */

	public Contacto obtenerContacto(Contacto newContacto) {
		Contacto contactoEncontrado = null;
		for(Contacto contacto : listaContactos) {
			if(contacto != null && contacto.getNombre().equals(newContacto.getNombre()) && contacto.getTelefono().equals(newContacto.getTelefono())) {
				contactoEncontrado = contacto;
			}
		}
		return contactoEncontrado;
	}

	/**
	 * Metodo para buscar un Contacto por su nombre y así hallar su telefono
	 * Si el contacto existe y el nombre es igual al nombre del parametro se obtiene el numero
	 *
	 * @param nombre = nombre del contacto a buscar
	 * @return telefono = telefono del contacto, si no, mensaje de no se ha hallado.
	 */

	public String buscarTelefonoContacto (String nombre) {
		String telefono = null;
		List<Contacto> asList = Arrays.asList(this.listaContactos);
		for (Contacto contacto : asList) {
			if(contacto != null){
				if(contacto.getNombre().equals(nombre)){
					telefono = "El telefono del contacto: "+nombre+" \nes: "+contacto.getTelefono();
					return telefono;
				}
			}
		}
		return telefono;
	}

	/**
	 * Metodo que verifica si el contacto existe en la lista o no
	 * P1. se pasa a lista la lista de contactos
	 * P2. se recorre cada uno
	 * P3. se verifica que no sea nulo
	 * P4. se verifica que tengan mismo nombre y telefono
	 *
	 * @param newContacto
	 * @return encontrado Si son iguales = true, si no encontrado == false.
	 */

	private boolean existeContacto(Contacto newContacto) {

		boolean encontrado = false;
		List<Contacto> asList = Arrays.asList(this.listaContactos);
		for (Contacto contacto : asList) {
			if(contacto != null){
				if(contacto.getNombre().equals(newContacto.getNombre()) && contacto.getTelefono().equals(newContacto.getTelefono())){
					encontrado = true;
					return encontrado;
				}

			}
		}
		return encontrado;
	}

	/**
	 * Metodo que devuelve la cantidad de huecos libres en la agenda.
	 * si es null, significa que está vacia, por lo tanto aumenta el num de huecos.
	 * @return mensaje + cantHuecos
	 */

	public String huecosLibre() {
		int huecos = -1;
		for (Contacto contacto : listaContactos) {
			if(contacto == null){
				huecos++;
			}
		}
		String huecosTotales = ""+huecos;
		return huecosTotales;
	}

	/**
	 * Metodo que elimina contacto buscándolo en la lista y si existe lo borra
	 * @param contactoSeleccion
	 * @return true si se pudo borrar, false si no se pudo.
	 * @throws ContactoException
	 */

	public boolean eliminarContacto(Contacto contactoSeleccion) throws ContactoException {

		boolean eliminado= false;
		boolean existeContacto= existeContacto(contactoSeleccion);

		if(!existeContacto){
			eliminado= false;
			throw new ContactoException("El contacto no existe, por lo tanto no se puede eliminar");
		}

		for (int i = 0; i < listaContactos.length; i++) {
			if(listaContactos[i] == contactoSeleccion){
				listaContactos[i]= null;
				eliminado= true;
				break;
			}
		}

		return eliminado;
	}

	/**
	 * Metodo que verifica si todas las posiciones de la agenda están llenas
	 * @return false si hay alguna vacía, true si todas están llenas
	 */

	public boolean agendaLlena() {
		boolean agendaLlena= true;;
		for (int i = 0; i < listaContactos.length; i++) {
			if(listaContactos[i] ==null){
				agendaLlena=false;
			}
		}
		return agendaLlena;
	}
//------------------------------ METODOS DE LAS REUNIONES -------------------------------------------//

}
