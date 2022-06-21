package mx.com.gm.web;

import lombok.extern.slf4j.Slf4j;
import mx.com.gm.domain.Persona;
import mx.com.gm.service.PersonaService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import aj.org.objectweb.asm.Type;

@Controller
@Slf4j
public class ControladorInicio {

	@Autowired
	private PersonaService personaService;

	@RequestMapping(value = "/Users", method = RequestMethod.GET)
	public  @ResponseBody ResponseEntity<String> inicio() throws FileNotFoundException, IOException, JSONException {
		JSONArray array = personaService.listaPersonas1();
		log.info("ejecutando el controlador Spring MVC");
		return new ResponseEntity<String>(array.toString(), HttpStatus.OK);

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)	
	public @ResponseBody ResponseEntity<Persona> guardar(@RequestBody @Valid Persona persona) throws FileNotFoundException, IOException, JSONException {
		//System.out.println("Como me llega en el controlador:"+ persona.toString());
		Persona response = personaService.guardar(persona);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	@PutMapping("/editar/{idPersona}")
	public void editar(Persona persona, Model model) {
		persona = personaService.encontrarPersona(persona);

	}

	@DeleteMapping("/eliminar/{idPersona}")
	public @ResponseBody ResponseEntity<?> eliminar(@PathVariable("idPersona") @Valid int idPersona) throws FileNotFoundException, IOException, JSONException {
		Boolean respuesta = personaService.eliminar(idPersona);
		return ResponseEntity.status(HttpStatus.OK).body("Persona Eliminada");

	}

}
