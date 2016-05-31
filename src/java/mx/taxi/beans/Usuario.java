/*
 * Autor: Miguel Angel Cedeno Garciduenas
 * Email: miguecg@gmail.com 
 */

package mx.taxi.beans;

/**
 *
 * @author miguel
 */
import java.io.Serializable;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.mig.dbc.Dao;


public class Usuario implements Serializable {
    
    private Long usuaUsuaid;
    private String usuaUsuario;
    private String usuaNombre;
    private String usuaApepat;
    private String usuaApemat;
    private String usuaEmail;
    private Long usuaTelefono;
    private String usuaPassword;
    private String usuaEstatus;
    
   
    private Dao dao;
    private boolean dbExterno = false;
    
    private Usuario usua;
    private List<Usuario> lista = new ArrayList();
   
    public Usuario() {
        this.dbExterno = false;
    }
    
    public Usuario(Dao dao) {
        this.dao = dao;
        this.dbExterno = true;
    }

    
    public String getUsuaApemat() {
        return usuaApemat;
    }

    public String getUsuaApepat() {
        return usuaApepat;
    }

    public String getUsuaEmail() {
        return usuaEmail;
    }

    public String getUsuaEstatus() {
        return usuaEstatus;
    }

    public String getUsuaNombre() {
        return usuaNombre;
    }

    public String getUsuaPassword() {
        return usuaPassword;
    }

    public Long getUsuaTelefono() {
        return usuaTelefono;
    }

    public Long getUsuaUsuaid() {
        return usuaUsuaid;
    }

    public String getUsuaUsuario() {
        return usuaUsuario;
    }

    public void setUsuaApemat(String usuaApemat) {
        this.usuaApemat = usuaApemat;
    }

    public void setUsuaApepat(String usuaApepat) {
        this.usuaApepat = usuaApepat;
    }

    public void setUsuaEmail(String usuaEmail) {
        this.usuaEmail = usuaEmail;
    }

    public void setUsuaEstatus(String usuaEstatus) {
        this.usuaEstatus = usuaEstatus;
    }

    public void setUsuaNombre(String usuaNombre) {
        this.usuaNombre = usuaNombre;
    }

    public void setUsuaPassword(String usuaPassword) {
        this.usuaPassword = usuaPassword;
    }

    public void setUsuaTelefono(Long usuaTelefono) {
        this.usuaTelefono = usuaTelefono;
    }

    public void setUsuaUsuaid(Long usuaUsuaid) {
        this.usuaUsuaid = usuaUsuaid;
    }

    public void setUsuaUsuario(String usuaUsuario) {
        this.usuaUsuario = usuaUsuario;
    }

    
    
    public void setId(Long idUsua) throws TaxiException {
        
        try {
            
            dao = dbExterno ? dao : new Dao();
            
            
            ResultSet rst =
                    dao.consultar("Select usua_id,"
                            + "usua_usuario,"
                            + "usua_nombre,"
                            + "usua_apepat,"
                            + "usua_apemat,"
                            + "usua_email,"
                            + "usua_telefono,"
                            + "usua_password,"
                            + "usua_estatus "
                            + "FROM USUARIO Where usua_id = '"+idUsua+"'"
                    );
            
            while (rst != null && rst.next()) {
                
                usua = new Usuario();
                usua.setUsuaUsuaid(idUsua);
                usua.setUsuaUsuario(rst.getString("USUA_USUARIO"));
                usua.setUsuaNombre(rst.getString("USUA_NOMBRE"));
                usua.setUsuaApepat(rst.getString("USUA_APEPAT"));
                usua.setUsuaApemat(rst.getString("USUA_APEMAT"));
                usua.setUsuaEstatus(rst.getString("USUA_ESTATUS"));
                usua.setUsuaEmail(rst.getString("USUA_EMAIL"));
                usua.setUsuaPassword(rst.getString("USUA_PASSWORD"));
                usua.setUsuaTelefono(rst.getLong("USUA_TELEFONO"));                
                
            }
            
            if (!dbExterno) {
                dao.desconectar();
            }
            
        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }
                
    }
    
    public void setCondicion(String condicion) throws TaxiException {
        
        try {
            
            dao = dbExterno ? dao : new Dao();
            
            
            ResultSet rst =
                    dao.consultar("Select usua_id,"
                            + "usua_usuario,"
                            + "usua_nombre,"
                            + "usua_apepat,"
                            + "usua_apemat,"
                            + "usua_email,"
                            + "usua_telefono,"
                            + "usua_password,"
                            + "usua_estatus "
                            + "FROM USUARIO "+condicion
                    );
            
            while (rst != null && rst.next()) {                
                usua = new Usuario();
                usua.setUsuaUsuaid(rst.getLong("USUA_ID"));
                usua.setUsuaUsuario(rst.getString("USUA_USUARIO"));
                usua.setUsuaNombre(rst.getString("USUA_NOMBRE"));
                usua.setUsuaApepat(rst.getString("USUA_APEPAT"));
                usua.setUsuaApemat(rst.getString("USUA_APEMAT"));
                usua.setUsuaEstatus(rst.getString("USUA_ESTATUS"));
                usua.setUsuaEmail(rst.getString("USUA_EMAIL"));
                usua.setUsuaPassword(rst.getString("USUA_PASSWORD"));
                usua.setUsuaTelefono(rst.getLong("USUA_TELEFONO"));   
                lista.add(usua);                
            }
            
            if (!dbExterno) {
                dao.desconectar();
            }
            
        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }
                
    }
    
    
    public List<Usuario> getUsuarios() {
        return lista;
    }
    
    public Usuario getUsuario() {
        return usua;
    }
    
    
    public Usuario agrUsuario(Usuario usua) throws TaxiException {
        
        try {
            
            dao = dbExterno ? dao : new Dao();
            
            dao.insertar("Insert into USUARIO (USUA_USUARIO,USUA_NOMBRE,USUA_APEPAT,USUA_APEMAT,USUA_EMAIL,USUA_TELEFONO,USUA_ESTATUS,USUA_PASSWORD)"
                    + "VALUES ('"+usua.getUsuaUsuario()+"','"+usua.getUsuaNombre()+"','"+usua.getUsuaApepat()+"','"+usua.getUsuaApemat()+"','"+usua.getUsuaEmail()+"','"+usua.getUsuaTelefono()+"','A','"+usua.getUsuaPassword()+"' ) "
            );
            
            ResultSet rst = dao.consultar("Select LAST_INSERT_ID()");
            if (rst != null && rst.next()) {
                usua.setUsuaUsuaid(rst.getLong(1));
            }
            
            if (!dbExterno) {
                dao.desconectar();
            }
            
        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }
                
      return usua;  
    }
    
    
    public void actDatosUsuario(Usuario usua) throws TaxiException {
        
        try {
            
            dao = dbExterno ? dao : new Dao();
            
            dao.actualizar("Update USUARIO Set "
                    + "usua_nombre = '"+usua.getUsuaNombre()+"', "
                    + "usua_apepat = '"+usua.getUsuaApepat()+"',"
                    + "usua_apemat = '"+usua.getUsuaApemat()+"',"
                    + "usua_email = '"+usua.getUsuaEmail()+"',"
                    + "usua_telefono = '"+usua.getUsuaTelefono()+"' "
                    + " Where usua_id = '"+usua.getUsuaUsuaid()+"' "
            );            
            
            if (!dbExterno) {
                dao.desconectar();
            }
            
        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }                
    }
    
    public void actPassword(Long idUsua, String passwd) throws TaxiException {
        
        try {
            dao = dbExterno ? dao : new Dao();            
            dao.actualizar("Update USUARIO Set usua_password = '"+passwd+"' Where usua_id = '"+idUsua+"' ");            
            if (!dbExterno) {
                dao.desconectar();
            }
        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        } 
        
    }
    
    public void actEstatus(Long idUsua, String estatus) throws TaxiException {
        
        try {
            dao = dbExterno ? dao : new Dao();            
            dao.actualizar("Update USUARIO Set usua_estatus = '"+estatus+"' Where usua_id = '"+idUsua+"' ");            
            if (!dbExterno) {
                dao.desconectar();
            }
        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        } 
        
    }
    
    public void agrRol(Long idUsua, Long rol) throws TaxiException {
        
        try {
            dao = dbExterno ? dao : new Dao();            
                        
            dao.insertar("Insert into USROL (USRO_ROL,USRO_USUID) VALUES ('"+idUsua+"','"+rol+"') ");
            
            if (!dbExterno) {
                dao.desconectar();
            }
        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        } 
        
    }
     
    
    
    
}
