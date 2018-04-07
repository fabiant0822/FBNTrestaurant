package restaurant;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fabian Tamas
 * @version 1.0
 * @since 2018.04.01.
 */
public class DB {

    final String user = "admin";
    final String pass = "admin";
    String dbUrl;
        
    public DB() {
        url_be();
    }
    
    /**
     * Beolvassa a config.properties fileból az adatbázis IP címét
     * és beállítja a dbUrl-t, ha nincs fájl, hibaüzenetet küld és
     * a localhost-ot használja.
     */
    private void url_be() {
        Properties beallitasok = new Properties();
        try (FileInputStream be = new FileInputStream("config.properties")) {
            beallitasok.load(be);
            String ip = beallitasok.getProperty("ip");
            dbUrl = "jdbc:mysql://" + ip + ":3306/etterem"
                    + "?useUnicode=true&characterEncoding=UTF-8";
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            dbUrl = "jdbc:mysql://localhost:3306/etterem"
                   + "?useUnicode=true&characterEncoding=UTF-8";
        }
    }

    /**
     * Beolvassa az asztalok tábla rekordjait, asztalszám szerinti sorrendben.
     * Az adatokat betölti a táblába.
     * @param tbl betölti ebbe a táblába a terméket
     * @param cb
     * @param cb4
     */
    public void asztal_be(JTable tbl, JComboBox cb, JComboBox cb4) {
        final DefaultTableModel tm = (DefaultTableModel)tbl.getModel();
        String s = "SELECT * FROM asztalok ORDER BY asztal;";

        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()) {
            tm.setRowCount(0);
            cb.removeAllItems();
            cb4.removeAllItems();
            while (eredmeny.next()) {
                Object sor[] = {
                    eredmeny.getInt("asztal"),
                    eredmeny.getInt("szek"),
                    eredmeny.getString("helyseg")
                };
                tm.addRow(sor);
                cb.addItem(eredmeny.getInt("asztal"));
                cb4.addItem(eredmeny.getInt("asztal"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Levágja a bevinni kívánt adatokat az adatbázisban megadott méretre
     * @param s a bevitt adat változója
     * @param n az adattípus megadott karakterszáma
     * @return 
     */
    private String levag(String s, int n) {
        return s.length() > n ? s.substring(0, n) : s;
    }
    
    /**
     * Hozzáadja az adatbázishoz az asztalszámot, a székek darabszámát
     * és a helységet.
     * @param tbl
     * @param chr
     * @param hly
     * @return 
     */
    public int asztal_hozzaad(int tbl, int chr, String hly) {
        if (tbl==0)
            return 0;
        String s = "INSERT INTO asztalok (asztal, szek, helyseg) VALUES(?,?,?);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setInt(0, tbl);
            parancs.setInt(1, chr);
            if (!hly.isEmpty())
                parancs.setString(2,levag(hly.trim(), 20));
            else
                parancs.setNull(2,java.sql.Types.VARCHAR);
            parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        return 0;
    }
    
    /**
     * Módosítja az adatbázisban az asztalszámot, a székek darabszámát
     * és a helységet.
     * @param tbl
     * @param chr
     * @param hly
     * @return 
     */
    public int asztal_modosit(int tbl, int chr, String hly) {
        if (tbl==0)
            return 0;
        String s = "UPDATE asztalok SET asztal=?, szek=?, helyseg=? "
                 + "WHERE asztal=?";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setInt(0, tbl);
            parancs.setInt(1, chr);
            if (!hly.isEmpty())
                parancs.setString(2,levag(hly.trim(), 20));
            else
                parancs.setNull(2,java.sql.Types.VARCHAR);
            parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return 0;
    }
    
    /**
     * Törli az adatbázisban az asztalt.
     * @param tbl 
     */
    public void asztal_torol(int tbl) {
        String s = "DELETE FROM asztalok WHERE asztal=?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setInt(1, tbl);
            parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }                
    }
      
    /**
     * Beolvassa a tetelek tábla rekordjait a név szerinti sorrendben.
     * Az adatokat betölti a táblába.
     * @param tbl betölti ebbe a táblába a terméket
     * @param cb
     */
    public void tetelek_be(JTable tbl, JComboBox cb) {
        final DefaultTableModel tm = (DefaultTableModel)tbl.getModel();
        String s = "SELECT * FROM tetelek ORDER BY tetel ASC;";

        try (Connection kapcs = DriverManager.getConnection(dbUrl,user,pass);
             PreparedStatement parancs = kapcs.prepareStatement(s);
             ResultSet eredmeny = parancs.executeQuery()) {
            tm.setRowCount(0);
            cb.removeAllItems();
            while (eredmeny.next()) {
                Object sor[] = {
                    eredmeny.getInt("tetelID"),
                    eredmeny.getString("tetel"),
                    eredmeny.getInt("egysegar"),
                    eredmeny.getString("egyseg")
                };
                tm.addRow(sor);
                cb.addItem(eredmeny.getString("tetel"));
            }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(2);
        }
    }
    
   /**
    * Hozzáadja az adatbázishoz az új tételt, annak árát és egységét.
    * @param tetel
    * @param egysegar
    * @param egyseg
    * @return 
    */
    public int tetel_hozzaad(String tetel, int egysegar, String egyseg) {
        if (tetel.isEmpty())
            return 0;
        String s = "INSERT INTO tetelek (tetel,egysegar,egyseg) VALUES(?,?,?);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
            PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setString(1, levag(tetel.trim(), 50));
            parancs.setInt(2, egysegar);
            parancs.setString(3, levag(egyseg.trim(), 5));
            parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return 0;
    }
    
    /**
     * Módosítja az adatbázisban a tételt, annak árát és egységét.
     * @param tetel
     * @param egysegar
     * @param egyseg
     * @return 
     */
    public int tetel_modosit(String tetel, int egysegar, String egyseg) {
        if (tetel.isEmpty())
            return 0;
        String s = "UPDATE tetelek SET tetel=?, egysegar=?, egyseg=? "
                 + "WHERE tetelID=?";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setString(1, levag(tetel.trim(), 50));
            parancs.setInt(2, egysegar);
            parancs.setString(3, levag(egyseg.trim(), 5));
            return parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return 0;
        }        
    }
    
    /**
     * Törli az adatbázisból a tételt.
     * @param tetelID 
     */
    public void tetel_torol(int tetelID) {
        String s = "DELETE FROM tetelek WHERE tetelID=?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setInt(1, tetelID);
            parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }                
    }
    
    /**
     * Beolvassa a rendelések tábla rekordjait az ID szerinti sorrendben.
     * Az adatokat betölti a táblába.
     * @param tbl 
     */
    public void rendeles_be(JTable tbl) {
        final DefaultTableModel tm = (DefaultTableModel)tbl.getModel();
        String s = "SELECT rendelesID, "
                 + "asztalok.asztal AS tbl, "
                 + "tetelek.tetel AS prd, "
                 + "mennyiseg, "
                 + "mennyiseg * tetelek.egysegar AS osszeg "
                 + "FROM rendelesek "
                 + "JOIN asztalok ON rendelesek.asztal=asztalok.asztal "
                 + "JOIN tetelek ON rendelesek.tetelID=tetelek.tetelID;";

        try (Connection kapcs = DriverManager.getConnection(dbUrl,user,pass);
             PreparedStatement parancs = kapcs.prepareStatement(s);
             ResultSet eredmeny = parancs.executeQuery()) {
            tm.setRowCount(0);
            while (eredmeny.next()) {
                Object sor[] = {
                    eredmeny.getInt("rendelesID"),
                    eredmeny.getInt("tbl"),
                    eredmeny.getString("prd"),
                    eredmeny.getInt("mennyiseg"),
                    eredmeny.getInt("osszeg")
                };
                tm.addRow(sor);
            }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(3);
        }
    }
    
    /**
     * Hozzáadja az adatbázishoz a rendeléseket az asztal a tétel és a mennyiség adataival.
     * @param asztal
     * @param tetel
     * @param mennyiseg
     * @return 
     */
    public int rendeles_hozzaad(int asztal, int tetel, int mennyiseg) {
        String s = "INSERT INTO rendelesek (asztal,tetelID,mennyiseg) VALUES(?,?,?);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setInt(1, asztal);
            parancs.setInt(2, tetel);
            parancs.setInt(3, mennyiseg);
            return parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return 0;
        }
    }

    /**
     * Módosítja az adatbázisban a rendeléseket az asztal a tétel és a mennyiség adataival.
     * @param rendelesID
     * @param asztal
     * @param tetel
     * @param mennyiseg
     * @return
     */
    public int rendeles_modosit(int rendelesID, int asztal, int tetel, int mennyiseg) {
        String s = "UPDATE rendelesek SET asztal=?, tetelID=?, mennyiseg=? "
                 + "WHERE rendelesID=?";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setInt(1, asztal);
            parancs.setInt(2, tetel);
            parancs.setInt(3, mennyiseg);
            parancs.setInt(4, rendelesID);
            return parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return 0;
        }        
    }
    
    /**
     * Törli a rendelést
     * @param rendelesID 
     */
    public void rendeles_torol(int rendelesID) {
        String s = "DELETE FROM rendelesek WHERE rendelesID=?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.setInt(1, rendelesID);
            parancs.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }                
    }
    
    public void szamla_be(JTable tbl) {
        final DefaultTableModel tm = (DefaultTableModel)tbl.getModel();
        String s = "SELECT rendelesID, "
                 + "asztalok.asztal AS tbl, "
                 + "tetelek.tetel AS prd, "
                 + "mennyiseg, "
                 + "mennyiseg * tetelek.egysegar AS osszeg "
                 + "FROM rendelesek "
                 + "JOIN asztalok ON rendelesek.asztal=asztalok.asztal "
                 + "JOIN tetelek ON rendelesek.tetelID=tetelek.tetelID;";

        try (Connection kapcs = DriverManager.getConnection(dbUrl,user,pass);
             PreparedStatement parancs = kapcs.prepareStatement(s);
             ResultSet eredmeny = parancs.executeQuery()) {
            tm.setRowCount(0);
            while (eredmeny.next()) {
                Object sor[] = {
                    eredmeny.getInt("rendelesID"),
                    eredmeny.getInt("tbl"),
                    eredmeny.getString("prd"),
                    eredmeny.getInt("mennyiseg"),
                    eredmeny.getInt("osszeg")
                };
                tm.addRow(sor);
            }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(4);
        }
    }
            
    public void szamla_lekerdez(JTable tbl, int asz) {
        final DefaultTableModel tm = (DefaultTableModel)tbl.getModel();
        String s = "SELECT rendelesID, "
                 + "asztalok.asztal AS tbl, "
                 + "tetelek.tetel AS prd, "
                 + "mennyiseg, "
                 + "mennyiseg * tetelek.egysegar AS osszeg "
                 + "FROM rendelesek "
                 + "JOIN asztalok ON rendelesek.asztal=asztalok.asztal "
                 + "JOIN tetelek ON rendelesek.tetelID=tetelek.tetelID "
                 + "WHERE rendelesek.asztal=" + asz
                 + "ORDER BY rendelesID;";

        try (Connection kapcs = DriverManager.getConnection(dbUrl,user,pass);
             PreparedStatement parancs = kapcs.prepareStatement(s);
             ResultSet eredmeny = parancs.executeQuery()) {
            tm.setRowCount(0);
            while (eredmeny.next()) {
                Object sor[] = {
                    eredmeny.getInt("rendelesID"),
                    eredmeny.getInt("tbl"),
                    eredmeny.getString("prd"),
                    eredmeny.getInt("mennyiseg"),
                    eredmeny.getInt("osszeg")
                };
                tm.addRow(sor);
            }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.exit(5);
        }
    }
    
    
//    public int szamla_hozzaad(int asztal, int tetelID, int mennyiseg, int vegosszeg) {
//        String s = "INSERT INTO szamla (asztal,tetelID,mennyiseg,vegosszeg) VALUES(?,?,?,?);";
//        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
//                PreparedStatement parancs = kapcs.prepareStatement(s)) {
//            parancs.setInt(1, asztal);
//            parancs.setInt(2, tetelID);
//            parancs.setInt(3, mennyiseg);
//            parancs.setInt(4, vegosszeg);
//            return parancs.executeUpdate();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//            return 0;
//        }
//    }

}