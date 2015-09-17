/**
 * 
 */
package fdi.ucm.server.exportparser.sql;

import java.util.ArrayList;

import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Funcion que implementa las funciones estaticas de la exportacion
 * @author Joaquin Gayoso-Cabada
 *
 */
public class StaticFuctionsSQL {
	
	
	/**
	 * Revisa si un elemento es VirtualObject
	 * @param hastype
	 * @return
	 */
	public static boolean isTablas(CompleteGrammar hastype) {
		
		ArrayList<CompleteOperationalValueType> Shows = hastype.getViews();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.SQL))
			{
					if (show.getName().equals(NameConstantsSQL.SQLType))
							if (show.getDefault().equals(NameConstantsSQL.TABLA)) 
										return true;
			}
		}
		return false;
	}

	public static boolean isColumna(CompleteElementType hastype) {
		ArrayList<CompleteOperationalValueType> Shows = hastype.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.SQL))
			{
					if (show.getName().equals(NameConstantsSQL.SQLType))
							if (show.getDefault().equals(NameConstantsSQL.COLUMNA)) 
										return true;
			}
		}
		return false;
	}
	
	public static boolean isKey(CompleteElementType hastype) {
		ArrayList<CompleteOperationalValueType> Shows = hastype.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.SQL))
			{
					if (show.getName().equals(NameConstantsSQL.KEY))
						if (show.getDefault().equals(Boolean.toString(true))) 
							return true;
						else if (show.getDefault().equals(Boolean.toString(false))) 
								return false;

			}
		}
		return false;
	}

	/**
	 * Teste si es un numero
	 * @param hastype
	 * @return
	 */
	public static boolean isNumeric(CompleteElementType hastype) {
		ArrayList<CompleteOperationalValueType> Shows = hastype.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.METATYPE))
			{
					if (show.getName().equals(NameConstantsSQL.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsSQL.NUMERIC)) 
										return true;
			}
		}
		return false;
	}

	public static boolean isDate(CompleteElementType hastype) {
		ArrayList<CompleteOperationalValueType> Shows = hastype.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.METATYPE))
			{
					if (show.getName().equals(NameConstantsSQL.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsSQL.DATE)) 
										return true;
			}
		}
		return false;
	}

	public static boolean isBoolean(CompleteElementType completeElementType) {
		ArrayList<CompleteOperationalValueType> Shows = completeElementType.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.METATYPE))
			{
					if (show.getName().equals(NameConstantsSQL.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsSQL.BOOLEAN)) 
										return true;
			}
		}
		return false;
	}
	
	public static ArrayList<String> getVocabulary(CompleteTextElementType attribute) {
		ArrayList<String> Salida=new ArrayList<String>();
		ArrayList<CompleteOperationalValueType> Shows = attribute.getShows();
		for (CompleteOperationalValueType show : Shows) {	
			if (show.getView().equals(NameConstantsSQL.VOCABULARY))
			{
					if (show.getName().equals(NameConstantsSQL.TERM))
						Salida.add(show.getDefault());
			}
		}
		return Salida;
	}

	public static boolean isControled(CompleteElementType completeElementType) {
		ArrayList<CompleteOperationalValueType> Shows = completeElementType.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.METATYPE))
			{

					if (show.getName().equals(NameConstantsSQL.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsSQL.CONTROLED)) 
										return true;
			}
		}
		return false;
	}

	public static String getType(CompleteElementType completeElementType) {
		ArrayList<CompleteOperationalValueType> Shows = completeElementType.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.SQL))
			{
					if (show.getName().equals(NameConstantsSQL.TYPECOLUMN))
									return show.getDefault();

			}
		}
		return null;
	}

	public static String getValor(CompleteElementType completeElementType) {
		ArrayList<CompleteOperationalValueType> Shows = completeElementType.getShows();
		for (CompleteOperationalValueType show : Shows) {
			
			if (show.getView().equals(NameConstantsSQL.SQL))
			{
					if (show.getName().equals(NameConstantsSQL.TYPECOLUMN2))
									return show.getDefault();
			}
		}
		return null;
	}

}
