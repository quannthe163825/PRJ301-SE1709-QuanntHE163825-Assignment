/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;
import model.Skill;

/**
 *
 * @author Ngo Tung Son
 */
public class SkillDBContext extends DBContext<Skill> {

    @Override
    public void insert(Skill model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Skill model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Skill model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Skill get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Skill> list() {
        
        ArrayList<Skill> skills = new ArrayList<>();
        try {
            String sql = "SELECT skid,skname FROM Skill";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                Skill d = new Skill();
                d.setId(rs.getInt("skid"));
                d.setName(rs.getString("skname"));
                skills.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SkillDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return skills;
        
    }
    
}
