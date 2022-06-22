package mx.com.gm.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		
		 
		// Seteamos el ID para que sea autoincrementable
		int id = (int) (json.getJSONObject(json.length()-1).opt("idPersona"))+1; //Obteniendo el id del ultimo elemento con .opt
		persona.setIdPersona(id); //Se asigna Id 
		
		//Pequeña transformacion de un objeto java a Gson y por ultimo a JSONObject 
		Gson gson = new Gson();
		String personaJson = gson.toJson(persona);
		JSONObject personaJson2 = new JSONObject(personaJson);
		json.put(personaJson2); //Se añade el nuevo objeto al array de jsons

		// Escribimos el nuevo objeto sobre el archivo Json

		try {
			String test = json.toString();
			File file = new File(ruta);

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(test);
			bw.close();

			System.out.println("D	one");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Regresamos el objeto persona para la respuesta en el controlador
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
		
		
		// Sobreescribir el archivo JSON
		try {
			String test = json.toString();
			File file = new File(ruta);

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(test);
			bw.close();

			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

		


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
