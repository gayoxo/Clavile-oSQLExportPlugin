/**
 * 
 */
package fdi.ucm.server.exportparser.sql;

import java.util.ArrayList;

import fdi.ucm.server.modelComplete.ImportExportDataEnum;
import fdi.ucm.server.modelComplete.ImportExportPair;
import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.SaveCollection;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.CompleteLogAndUpdates;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class SaveRemoteCollectionSQL extends SaveCollection {

	
	private ArrayList<ImportExportPair> Parametros;


	public SaveRemoteCollectionSQL() {
		super();
	}
	
	
	/* (non-Javadoc)
	 * @see fdi.ucm.server.SaveCollection#processCollecccion(fdi.ucm.shared.model.collection.Collection)
	 */
	@Override
	public CompleteLogAndUpdates processCollecccion(CompleteCollection Salvar,
			String PathTemporalFiles) throws CompleteImportRuntimeException {

		CompleteLogAndUpdates CL=new CompleteLogAndUpdates();
		SaveProcessMainSQL sql;
		sql = new SaveProcessMainSQL(Salvar);
		sql.preocess();	
		return CL;

	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.SaveCollection#getConfiguracion()
	 */
	@Override
	public ArrayList<ImportExportPair> getConfiguracion() {
		if (Parametros==null)
		{
			ArrayList<ImportExportPair> ListaCampos=new ArrayList<ImportExportPair>();
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "MySQL Server Direction"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "MySQL Database"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Number, "MySQL Port"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "MySQL User"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.EncriptedText, "MySQL Password"));
			Parametros=ListaCampos;
			return ListaCampos;
		}
		else return Parametros;
		
		
	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.SaveCollection#setConfiguracion(java.util.ArrayList)
	 */
	@Override
	public void setConfiguracion(ArrayList<String> DateEntrada) {
		if (DateEntrada!=null)	
		{
			String Database = RemoveSpecialCharacters(DateEntrada.get(1));
			MySQLConnectionMySQL.getInstance(DateEntrada.get(0),Database,Integer.parseInt(DateEntrada.get(2)),DateEntrada.get(3),DateEntrada.get(4));
		}
	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.SaveCollection#getName()
	 */
	@Override
	public String getName() {
		return "MySQL";
	}
	
	
	/**
	 * QUitar caracteres especiales.
	 * @param str texto de entrada.
	 * @return texto sin caracteres especiales.
	 */
	public String RemoveSpecialCharacters(String str) {
		   StringBuilder sb = new StringBuilder();
		   for (int i = 0; i < str.length(); i++) {
			   char c = str.charAt(i);
			   if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_') {
			         sb.append(c);
			      }
		}
		   return sb.toString();
		}

	@Override
	public boolean isFileOutput() {
		return false;
	}


	@Override
	public String FileOutput() {
		return "";
	}


	@Override
	public void SetlocalTemporalFolder(String TemporalPath) {
		
	}



}
