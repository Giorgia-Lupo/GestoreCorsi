package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {

	//fornire metodo per recuperare tutti i corsi di un determinato periodo didattico	
	//Creo la classe Corso nel model.
	
	public boolean esisteCorso(String codins) {
		String sql="SELECT * from corso WHERE codins = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, codins);
			ResultSet r = st.executeQuery();
			
			if(r.next()) {
				conn.close();//!!!!!!!!!!!!!!!!!!!!!!!!
				return true;
			}else {
				conn.close();
				return false;
			}			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		
		String sql = "select * from corso where pd = ?"; //preparedStatement
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);
			}
			
			conn.close();//!!!!!!!!!!
			
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd){
		String sql = "select c.codins, c.nome, c.crediti, c.pd, COUNT(*) as tot " +
	                 "from corso as c, iscrizione i " +
				     "where c.codins = i.codins AND c.pd = ? "+
	                 "group by c.codins, c.nome, c.crediti, c.pd"; //preparedStatement
		
		Map<Corso, Integer> result = new HashMap<Corso, Integer>();		
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				result.put(c, n);
			}
			
			conn.close();//!!!!!!!!!!
			
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public List<Studente> getStudentiByCorso(Corso c){
		
		String sql = "SELECT * FROM studente AS s, iscrizione AS i " + 
				" WHERE s.matricola = i.matricola AND i.codins= ? ";
		List<Studente> studenti = new LinkedList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, c.getCodins());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Studente s = new Studente(res.getInt("matricola"), res.getString("nome"),
						res.getString("cognome"), res.getString("CDS"));
				studenti.add(s);						
			}
			
			conn.close();
			
		}catch(SQLException e ) {
			throw new RuntimeException(e);
		}
		
		return studenti;
	}
	
	public Map<String, Integer> getDivisioneCDS (Corso c){
		
		String sql = "SELECT s.CDS, COUNT(*) AS tot " + 
				"  from studente AS s, iscrizione AS i " + 
				"  WHERE s.matricola = i.matricola AND s.CDS<> \"\" and  i.codins = ? " + 
				"  GROUP BY s.CDS";
		Map<String, Integer> statistiche = new HashMap<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, c.getCodins());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				statistiche.put(res.getString("CDS"), res.getInt("tot"));					
			}
			
			conn.close();
			
		}catch(SQLException e ) {
			throw new RuntimeException(e);
		}
		
		return statistiche;
	}
		
}
