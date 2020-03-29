package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {

	//fornire metodo per recuperare tutti i corsi di un determinato periodo didattico	
	//Creo la classe Corso nel model.
	
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
	
	
	
}
