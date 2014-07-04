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
import fdi.ucm.server.modelComplete.collection.CompleteCollectionLog;

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
	public CompleteCollectionLog processCollecccion(CompleteCollection Salvar)
			throws CompleteImportRuntimeException {

		CompleteCollectionLog CL=new CompleteCollectionLog();
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
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Server"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Database"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Number, "Port"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "User"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.EncriptedText, "Password"));
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

}
