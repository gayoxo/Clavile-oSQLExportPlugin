/**
 * 
 */
package fdi.ucm.server.exportparser.sql;

import java.util.ArrayList;

import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalView;
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
		
		ArrayList<CompleteOperationalView> Shows = hastype.getViews();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().equals(NameConstantsSQL.SQL))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType showValues : ShowValue) {
					if (showValues.getName().equals(NameConstantsSQL.SQLType))
							if (showValues.getDefault().equals(NameConstantsSQL.TABLA)) 
										return true;
				}
			}
		}
		return false;
	}

	public static boolean isColumna(CompleteElementType hastype) {
		ArrayList<CompleteOperationalView> Shows = hastype.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().equals(NameConstantsSQL.SQL))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType showValues : ShowValue) {
					if (showValues.getName().equals(NameConstantsSQL.SQLType))
							if (showValues.getDefault().equals(NameConstantsSQL.COLUMNA)) 
										return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isKey(CompleteElementType hastype) {
		ArrayList<CompleteOperationalView> Shows = hastype.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().equals(NameConstantsSQL.SQL))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType showValues : ShowValue) {
					if (showValues.getName().equals(NameConstantsSQL.KEY))
						if (showValues.getDefault().equals(Boolean.toString(true))) 
							return true;
						else if (showValues.getDefault().equals(Boolean.toString(false))) 
								return false;

				}
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
		ArrayList<CompleteOperationalView> Shows = hastype.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().equals(NameConstantsSQL.METATYPE))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType showValues : ShowValue) {
					if (showValues.getName().equals(NameConstantsSQL.METATYPETYPE))
							if (showValues.getDefault().equals(NameConstantsSQL.NUMERIC)) 
										return true;
				}
			}
		}
		return false;
	}

	public static boolean isDate(CompleteElementType hastype) {
		ArrayList<CompleteOperationalView> Shows = hastype.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().equals(NameConstantsSQL.METATYPE))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType showValues : ShowValue) {
					if (showValues.getName().equals(NameConstantsSQL.METATYPETYPE))
							if (showValues.getDefault().equals(NameConstantsSQL.DATE)) 
										return true;
				}
			}
		}
		return false;
	}

	public static boolean isBoolean(CompleteElementType completeElementType) {
		ArrayList<CompleteOperationalView> Shows = completeElementType.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().equals(NameConstantsSQL.METATYPE))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType showValues : ShowValue) {
					if (showValues.getName().equals(NameConstantsSQL.METATYPETYPE))
							if (showValues.getDefault().equals(NameConstantsSQL.BOOLEAN)) 
										return true;
				}
			}
		}
		return false;
	}
	
	public static ArrayList<String> getVocabulary(CompleteTextElementType attribute) {
		ArrayList<String> Salida=new ArrayList<String>();
		ArrayList<CompleteOperationalView> Shows = attribute.getShows();
		for (CompleteOperationalView show : Shows) {	
			if (show.getName().equals(NameConstantsSQL.VOCABULARY))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType showValues : ShowValue) {
					if (showValues.getName().equals(NameConstantsSQL.TERM))
						Salida.add(showValues.getDefault());
				}
			}
		}
		return Salida;
	}

	public static boolean isControled(CompleteElementType completeElementType) {
		ArrayList<CompleteOperationalView> Shows = completeElementType.getShows();
		for (CompleteOperationalView show : Shows) {
			
			if (show.getName().equals(NameConstantsSQL.METATYPE))
			{
				ArrayList<CompleteOperationalValueType> ShowValue = show.getValues();
				for (CompleteOperationalValueType showValues : ShowValue) {
					if (showValues.getName().equals(NameConstantsSQL.METATYPETYPE))
							if (showValues.getDefault().equals(NameConstantsSQL.CONTROLED)) 
										return true;
				}
			}
		}
		return false;
	}

}
