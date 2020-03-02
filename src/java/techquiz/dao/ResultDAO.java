
package techquiz.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import techquiz.dbutil.DBConnection;
import techquiz.dto.rankDTO;
import techquiz.dto.resultDTO;


public class ResultDAO {
    private static PreparedStatement ps1,ps2,ps3,ps4;
    static{
        try{   
            ps3 = DBConnection.getConnection().prepareStatement("select email,percentage from results where examid = ? order by percentage desc");
            ps1 = DBConnection.getConnection().prepareStatement("insert into results values (?,?,?,?,?,?,?,?,?)");
            ps2 = DBConnection.getConnection().prepareStatement("select * from results where email = ? and examid = ?");
            ps4 = DBConnection.getConnection().prepareStatement("delete from results where examid = ?");
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static boolean saveResult(resultDTO obj)throws SQLException{
       ps1.setString(1, obj.getEmailid());
       ps1.setString(2, obj.getExamid());
       ps1.setInt(3,obj.getTotalque());
       ps1.setInt(4, obj.getTotalattempt());
       ps1.setInt(5, obj.getRightans());
       ps1.setInt(6, obj.getWrongans());
       ps1.setInt(7, obj.getUnattempt());
       ps1.setInt(8, obj.getTotalmarks());
       ps1.setDouble(9, obj.getPercentage());
       return ps1.executeUpdate()!=0;
    }
    
    public static resultDTO getResultByid(String email,String examid)throws SQLException{
        ps2.setString(1, email);
        ps2.setString(2, examid);
        ResultSet rs = ps2.executeQuery();
        rs.next();
        resultDTO robj = new resultDTO();
        robj.setEmailid(email);
        robj.setExamid(examid);
        robj.setRightans(rs.getInt("rightans"));
        robj.setTotalattempt(rs.getInt("totalattempt"));
        robj.setTotalmarks(rs.getInt("totalmarks"));
        robj.setTotalque(rs.getInt("totalques"));
        robj.setUnattempt(rs.getInt("unattempt"));
        robj.setWrongans(rs.getInt("wrongans"));
        robj.setPercentage(rs.getDouble("percentage"));
        return robj;
    }
    
    public static ArrayList<rankDTO> getAllResultbyexamid(String eid) throws SQLException{
        ps3.setString(1, eid);
        ResultSet rs = ps3.executeQuery();
        ArrayList<rankDTO> al = new ArrayList<>();
        
        while(rs.next()){
            rankDTO obj = new rankDTO();
            obj.setEmail(rs.getString(1));
            obj.setPer(rs.getDouble(2));
            al.add(obj);
        }
        return al;
    }
    
    public static boolean deleteAllResultbyexamid(String eid) throws SQLException{
        ps4.setString(1, eid);
        return ps4.executeUpdate()!=0;
    }
}
