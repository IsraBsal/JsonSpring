package mx.com.gm.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import mx.com.gm.dao.PersonaDao;
import mx.com.gm.domain.Persona;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaDao personaDao;

	@Override
	@Transactional
	public Persona guardar(Persona persona) throws IOException, JSONException {

		String ruta = "\\Users\\Default.DESKTOP-SMFCBP8\\Desktop\\employees.json";

		// Leemos el archivo en un string
		BufferedReader reader = new BufferedReader(new FileReader(ruta));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		String content = stringBuilder.toString();
		// convert to json array
		JSONArray json = new JSONArray(content);
		
		//Convertimos el objeto
		Persona personaResponse = new Persona();
		personaResponse.setApellido(persona.getApellido());
		personaResponse.setEmail(persona.getEmail());
		personaResponse.setNombre(persona.getNombre());
		Long id = (long) json.length()+1;
		System.out.println(id);
		personaResponse.setIdPersona(id);
		Gson gson = new Gson();
	    String personaJson = gson.toJson(personaResponse);
	    JSONObject personaJson2 = new JSONObject(personaJson);
		System.out.println(personaJson2);
		json.put(personaJson2);
		
		System.out.println("Json del servicio: "+json);
		
		//Escribimos el nuevo objeto sobre el archivo Json 
		///////
		
		
		return persona;
	}

	@Override
	@Transactional
	public Boolean eliminar(int idPersona) throws IOException, JSONException {
		String ruta = "\\Users\\Default.DESKTOP-SMFCBP8\\Desktop\\employees.json";

		// Leemos el archivo en un string
		BufferedReader reader = new BufferedReader(new FileReader(ruta));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		String content = stringBuilder.toString();
		// convert to json array
		JSONArray json = new JSONArray(content);
		
		json.remove(idPersona);
		System.out.println(json.get(idPersona));
		System.out.println(idPersona);
		System.out.println(json);
		return true;
		
		//Sobreescribir el archivo JSON
		/////////

	}

	@Override
	@Transactional(readOnly = true)
	public Persona encontrarPersona(Persona persona) {

		return personaDao.findById(persona.getIdPersona()).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public JSONArray listaPersonas1() throws IOException, JSONException {
		// TODO Auto-generated method stub
		String ruta = "\\Users\\Default.DESKTOP-SMFCBP8\\Desktop\\employees.json";

		// Leemos el archivo en un string
		BufferedReader reader = new BufferedReader(new FileReader(ruta));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		String content = stringBuilder.toString();
		// convert to json array
		JSONArray json = new JSONArray(content);

		for (int i = 0; i < json.length(); i++) {
			JSONObject obj = json.getJSONObject(i);
			// do something
			System.out.println(obj);
		}

		return json;

	}

}
