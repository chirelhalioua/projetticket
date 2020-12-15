/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Entity.Etat;
import Entity.Ticket;
import Entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jbuffeteau
 */
public class FonctionsMetier implements IMetier
{

    private String idTicket;
    @Override
    public User GetUnUser(String login, String mdp)
    {
        
         User user = null;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idUser, nomUser, prenomUser, statutUser from users where loginUser = ? and pwdUser = ?");
            ps.setString(1, login);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            rs.next();
            user = new User(rs.getInt("idUser"), rs.getString("nomUser"), rs.getString("prenomUser"),rs.getString("statutUser"));
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @Override
    public ArrayList<Ticket> GetAllTickets()
    {
    ArrayList<Ticket> lesTickets = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idTicket, nomTicket, dateTicket, nomEtat  from tickets");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Ticket tckt = new Ticket(rs.getInt("idTicket"), rs.getString("nomTicket"), rs.getString(" dateTicket"),rs.getString(" nomEtat"));
                lesTickets.add(tckt);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesTickets;
        
       
    }

    @Override
    public ArrayList<Ticket> GetAllTicketsByIdUser(int idUser)
    {
        
        ArrayList<Ticket> lesTickets = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("SELECT tckt.idTicket, tckt.nomTicket, tckt.dateTicket, E.nomEtat from tickets tckt, etats E where numUser = ? and tckt.numEtat = E.idEtat");
           
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
               Ticket tckt = new Ticket(rs.getInt("idTicket"), rs.getString("nomTicket"), rs.getString(" dateTicket"),rs.getString(" nomEtat"));
                lesTickets.add(tckt);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesTickets;    
    }

    @Override
    public void InsererTicket(int idTicket, String nomTicket, String dateTicket, int idUser, int idEtat) 
    {
         try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("insert into tickets values('"+idTicket+"','"+idUser+"','"+nomTicket+"','"+dateTicket+"'0)");
             ps.setInt(1, idUser);
             ResultSet rs = ps.executeQuery();
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void ModifierEtatTicket(int idTicket, int idEtat) 
    
    {
    
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("update tickets set numEtat = ? where idTicket = ?");
            ps.setInt(1, idEtat);
            ps.setInt(2, idTicket);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
   

    @Override
    public ArrayList<User> GetAllUsers()
    {
         ArrayList<User> lesUsers= new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idUser,nomUser,prenomUser, statutUser from users ");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
               User usr = new User(rs.getInt("idUser"), rs.getString("nomUser"),rs.getString("prenomUser"),rs.getString("statutUser"));
                lesUsers.add(usr);
                
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesUsers;   
       
    }

    @Override
    public int GetLastIdTicket()
    {
        
        int lastId = 0;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select max(idTicket) as num from tickets");
            ResultSet rs = ps.executeQuery();
            rs.next();
            lastId = rs.getInt("num") + 1;
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastId;
    }
    

    @Override
    public int GetIdEtat(String nomEtat)
    {
        
        int IdEtat = 0;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idEtat as num from etats");
            ResultSet rs = ps.executeQuery();
            rs.next();
            IdEtat = rs.getInt("num") + 1;
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return IdEtat;
    }
    

    @Override
    public ArrayList<Etat> GetAllEtats()
    {
       {
    ArrayList<Etat> lesEtats = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idEtat , nomEtat from etats");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Etat E= new Etat(rs.getInt("idEtat"), rs.getString("nomEtat"));
                lesEtats.add(E);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesEtats;     
    }
}
    
}
