/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Department;
import model.Skill; 
import model.Student;
/**
 *
 * @author Ngo Tung Son
 */
public class StudentDBContext extends DBContext<Student> {

    @Override
    public void insert(Student model) {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [Student]\n"
                    + "           ([sname]\n"
                    + "           ,[gender]\n"
                    + "           ,[dob]\n"
                    + "           ,[created_time]\n"
                    + "           ,[did])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,GETDATE()\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setBoolean(2, model.isGender());
            stm.setDate(3, model.getDob());
            stm.setInt(4, model.getDept().getId());
            stm.executeUpdate();
            // get student id of new student
            String sql_get_sid = "SELECT @@IDENTITY as [sid]";
            PreparedStatement stm_get_sid = connection.prepareStatement(sql_get_sid);
            ResultSet rs = stm_get_sid.executeQuery();
            if (rs.next()) {
                model.setId(rs.getInt("sid"));
            }
            // register for skills
            for (Skill skill : model.getSkills()) {
                String sql_register_skill = "INSERT INTO [Student_Skill]\n"
                        + "           ([sid]\n"
                        + "           ,[skid])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?)";
                PreparedStatement stm_register_skill = connection.prepareStatement(sql_register_skill);
                stm_register_skill.setInt(1, model.getId());
                stm_register_skill.setInt(2, skill.getId());
                stm_register_skill.executeUpdate();
            }

            //success
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void update(Student model) {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE [Student]\n"
                    + "   SET [sname] = ?\n"
                    + "      ,[gender] = ?\n"
                    + "      ,[dob] = ?\n"
                    + "      ,[did] = ?\n"
                    + " WHERE [sid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setBoolean(2, model.isGender());
            stm.setDate(3, model.getDob());
            stm.setInt(4, model.getDept().getId());
            stm.setInt(5, model.getId());
            stm.executeUpdate();
            
            //remove registered skills
            sql = "DELETE Student_Skill WHERE [sid] = ?";
            PreparedStatement stm_remove_skill = connection.prepareStatement(sql);
            stm_remove_skill.setInt(1, model.getId());
            stm_remove_skill.executeUpdate();
            
            // register for skills
            for (Skill skill : model.getSkills()) {
                String sql_register_skill = "INSERT INTO [Student_Skill]\n"
                        + "           ([sid]\n"
                        + "           ,[skid])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?)";
                PreparedStatement stm_register_skill = connection.prepareStatement(sql_register_skill);
                stm_register_skill.setInt(1, model.getId());
                stm_register_skill.setInt(2, skill.getId());
                stm_register_skill.executeUpdate();
            }
            
            //success
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Student model) {
        try {
            String sql = "DELETE Student\n"
                    + " WHERE [sid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Student get(int id) {
        try {
            String sql = "  SELECT s.sid,s.sname,s.gender,s.dob,d.did,d.dname,s.created_time\n"
                    + "  ,ISNULL(sk.skid,-1) skid , sk.skname\n"
                    + "  FROM Student s INNER JOIN Department d ON s.did = d.did\n"
                    + "  LEFT JOIN Student_Skill ss ON ss.sid = s.sid\n"
                    + "  LEFT JOIN Skill sk ON sk.skid = ss.skid\n"
                    + "WHERE s.sid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            Student s = null;
            while (rs.next()) {
                if (s == null) {
                    s = new Student();
                    int sid = rs.getInt("sid");
                    String name = rs.getString("sname");
                    boolean gender = rs.getBoolean("gender");
                    Date dob = rs.getDate("dob");
                    Timestamp timestamp = rs.getTimestamp("created_time");
                    java.util.Date created_time
                            = new java.util.Date(timestamp.getTime());
                    s.setId(sid);
                    s.setName(name);
                    s.setGender(gender);
                    s.setDob(dob);
                    s.setCreated_time(created_time);
                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    d.setName(rs.getString("dname"));
                    s.setDept(d);
                }
                int skid = rs.getInt("skid");
                if(skid !=-1)
                {
                    Skill sk = new Skill();
                    sk.setId(rs.getInt("skid"));
                    sk.setName(rs.getString("skname"));
                    s.getSkills().add(sk);
                }
            }
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Student> list() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT [sid],sname,gender,dob,created_time FROM Student";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                int sid = rs.getInt("sid");
                String name = rs.getString("sname");
                boolean gender = rs.getBoolean("gender");
                Date dob = rs.getDate("dob");
                Timestamp timestamp = rs.getTimestamp("created_time");
                java.util.Date created_time
                        = new java.util.Date(timestamp.getTime());
                s.setId(sid);
                s.setName(name);
                s.setGender(gender);
                s.setDob(dob);
                s.setCreated_time(created_time);
                students.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return students;
    }

}
