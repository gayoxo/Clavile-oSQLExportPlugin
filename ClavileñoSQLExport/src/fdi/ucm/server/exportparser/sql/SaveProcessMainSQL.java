/**
 * 
 */
package fdi.ucm.server.exportparser.sql;

import java.util.ArrayList;
import java.util.Hashtable;

import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteElement;
import fdi.ucm.server.modelComplete.collection.document.CompleteResourceElementFile;
import fdi.ucm.server.modelComplete.collection.document.CompleteResourceElementURL;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteLinkElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteResourceElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteStructure;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Clase que define las funciones basicas para la exportacion a SQL
 * @author Joaquin Gayoso-Cabada
 *
 */
public class SaveProcessMainSQL {

	private Hashtable<CompleteGrammar, ArrayList<CompleteElementType>> ListaTablas;
	private CompleteCollection toOda;

	public SaveProcessMainSQL(CompleteCollection salvar) {
		toOda=salvar;
		ListaTablas=new Hashtable<CompleteGrammar, ArrayList<CompleteElementType>>();
	}

	public void preocess() {
		
		
		ProcessValidacion();
		for (CompleteGrammar iterable_element : toOda.getMetamodelGrammar()) {
			 if (StaticFuctionsSQL.isTablas(iterable_element))
			 {
				 String TableName=iterable_element.getNombre();
				 ArrayList<CompleteElementType> Lista = generateTabla(iterable_element);
				 generaTablaSQL(TableName,Lista);
				 ListaTablas.put(iterable_element,Lista);
			 }
		}
		processEstructuras();
		
	}

	
	private void processEstructuras() {
		for (CompleteDocuments iterable_element : toOda.getEstructuras()) {
			ArrayList<CompleteElementType> Campos=ListaTablas.get(iterable_element.getDocument());
			if (Campos!=null&&!Campos.isEmpty())
				{
				StringBuffer Cabecera= new StringBuffer();
				StringBuffer Cuerpo= new StringBuffer();
				Cabecera.append("INSERT INTO `");
				Cabecera.append(iterable_element.getDocument().getNombre());
				Cabecera.append("` (");
				boolean iniciado=false;
				for (int i = 0; i < Campos.size(); i++) {
					CompleteElementType completeElementType=Campos.get(i);
					CompleteElement M=null;
					for (CompleteElement meta2 : iterable_element.getDescription()) {
						if (meta2.getHastype()==completeElementType)
						{
							M=meta2;
							break;
						}
					}
					if (M!=null)
						{
						if (i!=0&&iniciado)
							{
							Cabecera.append(",");
							Cuerpo.append(",");
							}
						Cabecera.append("`");
						Cabecera.append(completeElementType.getName());
						Cabecera.append("`");
						Cuerpo.append(generacuerpo(M));
						iniciado=true;
						}
				}
				
				Cabecera.append(") Values (");
				Cabecera.append(Cuerpo.toString());
				Cabecera.append(");");
				
			//	System.out.println(Cabecera.toString());
				
				MySQLConnectionMySQL.RunQuerry(Cabecera.toString());
					
				
				}
		}
		
	}

	/**
	 * Metavalues generador
	 * @param m
	 * @return
	 */
	private String generacuerpo(CompleteElement m) {
		if (m instanceof CompleteTextElement)
			return "'"+((CompleteTextElement) m).getValue()+"'";
//		if (m instanceof MetaControlledValue)
//			return "'"+((MetaControlledValue) m).getValue()+"'";
		if (m instanceof CompleteResourceElementFile)
				return "'"+((((CompleteResourceElementFile) m).getValue())).getPath()+"'";

			if (m instanceof CompleteResourceElementURL)
				return "'"+(((CompleteResourceElementURL) m).getValue())+"'";
		return null;
	}

	private void generaTablaSQL(String tableName, ArrayList<CompleteElementType> lista) {
		StringBuffer SB=new StringBuffer();
		SB.append("CREATE TABLE `");
		SB.append(tableName);
		SB.append("` (");
		ArrayList<String> Keys=new ArrayList<String>();
		for (int i = 0; i < lista.size(); i++) {
			CompleteElementType completeElementType=lista.get(i);
			String S=generaCampo(completeElementType);
			if (S!=null)
				{
				if (StaticFuctionsSQL.isKey(completeElementType))
					Keys.add(completeElementType.getName());
				SB.append(S);
				if (i != lista.size()-1)
					SB.append(",");
				}
		}
		if (!Keys.isEmpty())
			{
			SB.append(",  primary key (");
			for (int i = 0; i < Keys.size(); i++) {
				SB.append("`"+Keys.get(i)+"`");
				if (i!=Keys.size()-1)
					SB.append(",");
				}
			SB.append(")");
			}
		SB.append(") ENGINE=MyISAM AUTO_INCREMENT=24150 DEFAULT CHARSET=utf8;");
		
		
		MySQLConnectionMySQL.RunQuerry(SB.toString());
		
	}

	private String generaCampo(CompleteElementType completeElementType) {
		if (completeElementType instanceof CompleteTextElementType)
				
			if (StaticFuctionsSQL.isNumeric(completeElementType)) 
				return "`"+completeElementType.getName()+"` int(100) DEFAULT NULL";
			else if (StaticFuctionsSQL.isDate(completeElementType))
				return "`"+completeElementType.getName()+"` DATETIME DEFAULT NULL";
			else if (StaticFuctionsSQL.isBoolean(completeElementType))
				return "`"+completeElementType.getName()+"` BOOL DEFAULT NULL";
			else if (StaticFuctionsSQL.isControled(completeElementType))
				{
				StringBuffer SB = new StringBuffer();
				SB.append("`"+completeElementType.getName()+"` SET(");
				
				ArrayList<String> Vocabulario=StaticFuctionsSQL.getVocabulary((CompleteTextElementType)completeElementType);
				for (int i = 0; i < Vocabulario.size(); i++) {
					String term=Vocabulario.get(i);
					if (!term.isEmpty())
						{
						SB.append(term);
						if (i != (Vocabulario.size()-1))
							SB.append(",");
						}
				}
				SB.append(")");
				return SB.toString();
				}
			else return "`"+completeElementType.getName()+"` varchar(255) DEFAULT NULL";
		if (completeElementType instanceof CompleteResourceElementType)
			return "`"+completeElementType.getName()+"` varchar(255) DEFAULT NULL";
		if (completeElementType instanceof CompleteLinkElementType)
			return "`"+completeElementType.getName()+"` varchar(255) DEFAULT NULL";
		
		return null;
	}

	private ArrayList<CompleteElementType> generateTabla(CompleteGrammar elementType) {
		
		ArrayList<CompleteElementType> Lista=new ArrayList<CompleteElementType>();
		for (CompleteStructure hijos : elementType.getSons()) {
			if (hijos instanceof CompleteElementType &&StaticFuctionsSQL.isColumna((CompleteElementType)hijos))
				Lista.add((CompleteElementType) hijos);
			ArrayList<CompleteElementType> ListaHijos=generateTabla((CompleteElementType)hijos);
			Lista.addAll(ListaHijos);
		}
		return Lista;
	}
	
	private ArrayList<CompleteElementType> generateTabla(CompleteElementType completeElementType) {
		
		ArrayList<CompleteElementType> Lista=new ArrayList<CompleteElementType>();
		for (CompleteStructure hijos : completeElementType.getSons()) {
			if (hijos instanceof CompleteElementType &&StaticFuctionsSQL.isColumna((CompleteElementType)hijos))
				Lista.add((CompleteElementType) hijos);
			ArrayList<CompleteElementType> ListaHijos=generateTabla((CompleteElementType)hijos);
			Lista.addAll(ListaHijos);
		}
		return Lista;
	}

	/**
	 * Funcion que realiza una validacion basica del sistema
	 */
	private void ProcessValidacion() {
		if (!findTablas())
			throw new CompleteImportRuntimeException("Error en la validacion del modelo al no tener tablas que validar. Añadir Vista de Tabla a las gramaticas para poder usarlas");
		

		if (!FindCamposEnTablas())
			throw new CompleteImportRuntimeException("Error en la validacion del modelo al no tener campos en las tablas. Añadir vista de Campo a los elementos dentro de las tablas para poder usarlas");
		
		
	}

	private boolean FindCamposEnTablas() {
		boolean NoError=false;
		for (CompleteGrammar iterable_element : toOda.getMetamodelGrammar()) {

				 if (StaticFuctionsSQL.isTablas((iterable_element)))
				 	 if (!findColumnas( iterable_element))
				 		 return false;
				 	 else NoError=true;

		}
		return NoError;
	}

	private boolean findTablas() {
		for (CompleteGrammar iterable_element : toOda.getMetamodelGrammar()) {
				 if (StaticFuctionsSQL.isTablas( iterable_element))
					 return true;

		}
		return false;
	}
	
	/**
	 * Busca una columna interna que da sentido al sistema
	 * @param padre
	 * @return
	 */
	private boolean findColumnas(CompleteGrammar padre) {
		for (CompleteStructure iterable_element2 : padre.getSons()) {
			if (iterable_element2 instanceof CompleteElementType)
			{
			 if (StaticFuctionsSQL.isColumna(((CompleteElementType) iterable_element2))||findColumnas((CompleteElementType) iterable_element2))
				 return true;
			}
		}
		return false;
	}
	
	/**
	 * Busca una columna interna que da sentido al sistema
	 * @param padre
	 * @return
	 */
	private boolean findColumnas(CompleteElementType padre) {
		for (CompleteStructure iterable_element2 : padre.getSons()) {
			if (iterable_element2 instanceof CompleteElementType)
			{
			 if (StaticFuctionsSQL.isColumna(((CompleteElementType) iterable_element2))||findColumnas((CompleteElementType) iterable_element2))
				 return true;
			}
		}
		return false;
	}

}
