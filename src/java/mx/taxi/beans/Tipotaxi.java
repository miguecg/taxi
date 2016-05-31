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

public class Tipotaxi implements Serializable {

    private Long tiptTipotaxi;
    private Float tiptPrecio;
    private String tiptDescrip;
    private String tiptTexto;

    private Dao dao;
    private boolean dbExterno = false;

    private Tipotaxi ttaxi;
    private List<Tipotaxi> lista = new ArrayList();

    public Tipotaxi() {
        this.dbExterno = false;
    }

    public Tipotaxi(Dao dao) {
        this.dbExterno = true;
        this.dao = dao;
    }

    public String getTiptDescrip() {
        return tiptDescrip;
    }

    public Float getTiptPrecio() {
        return tiptPrecio;
    }

    public String getTiptTexto() {
        return tiptTexto;
    }

    public Long getTiptTipotaxi() {
        return tiptTipotaxi;
    }

    public void setTiptDescrip(String tiptDescrip) {
        this.tiptDescrip = tiptDescrip;
    }

    public void setTiptPrecio(Float tiptPrecio) {
        this.tiptPrecio = tiptPrecio;
    }

    public void setTiptTexto(String tiptTexto) {
        this.tiptTexto = tiptTexto;
    }

    public void setTiptTipotaxi(Long tiptTipotaxi) {
        this.tiptTipotaxi = tiptTipotaxi;
    }

    public void setId(Long tiptTipotaxi) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();
            ResultSet rst
                    = dao.consultar("Select tipt_tipotaxi,tipt_precio,tipt_descrip,tipt_texto From TIPOTAXI Where tipt_tipotaxi = '" + tiptTipotaxi + "'");

            while (rst != null && rst.next()) {
                ttaxi = new Tipotaxi();
                ttaxi.setTiptTipotaxi(rst.getLong("TIPT_TIPOTAXI"));
                ttaxi.setTiptPrecio(rst.getFloat("TIPT_PRECIO"));
                ttaxi.setTiptDescrip(rst.getString("TIPT_DESCRIP"));
                ttaxi.setTiptTexto(rst.getString("TIPT_TEXTO"));
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
            ResultSet rst
                    = dao.consultar("Select tipt_tipotaxi,tipt_precio,tipt_descrip,tipt_texto "
                            + "From TIPOTAXI " + condicion);

            while (rst != null && rst.next()) {
                ttaxi = new Tipotaxi();
                ttaxi.setTiptTipotaxi(rst.getLong("TIPT_TIPOTAXI"));
                ttaxi.setTiptPrecio(rst.getFloat("TIPT_PRECIO"));
                ttaxi.setTiptDescrip(rst.getString("TIPT_DESCRIP"));
                ttaxi.setTiptTexto(rst.getString("TIPT_TEXTO"));
                lista.add(ttaxi);
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

    public List<Tipotaxi> getTiposTaxi() {
        return lista;
    }

    public Tipotaxi getTipoTaxi() {
        return ttaxi;
    }

    public Tipotaxi agrTipotaxi(Tipotaxi ttaxi) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();
            
            dao.insertar("Insert into TIPOTAXI (TIPT_PRECIO,TIPT_DESCRIP,TIPT_TEXTO) VALUES('"+ttaxi.getTiptPrecio()+"','"+ttaxi.getTiptDescrip()+"','"+ttaxi.getTiptTexto()+"') ");
            
            ResultSet rst = dao.consultar("Select LAST_INSERT_ID()");
            if (rst != null && rst.next()) {
                ttaxi.setTiptTipotaxi(rst.getLong(1));
            }
            
            if (!dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

        return ttaxi;
    }
    
    public void actTipotaxi(Tipotaxi ttaxi) throws TaxiException {
        
        try {

            dao = dbExterno ? dao : new Dao();
            
            dao.actualizar("UPDATE TIPOTAXI SET "
                    + " TIPT_DESCRIP = '"+ttaxi.getTiptDescrip()+"',TIPT_TEXTO = '"+ttaxi.getTiptTexto()+"' "
                    + "Where tipt_tipotaxi = '"+ttaxi.getTiptTipotaxi()+"'"
            );
            
            ResultSet rst = dao.consultar("Select LAST_INSERT_ID();");
            if (rst != null && rst.next()) {
                ttaxi.setTiptTipotaxi(rst.getLong(1));
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
    
    public void actPrecio(Long tipoTaxi,Float precio) throws TaxiException {
        
        try {

            dao = dbExterno ? dao : new Dao();
            
            dao.actualizar("UPDATE TIPOTAXI SET "
                    + " TIPT_PRECIO = '"+precio+"' "
                    + "Where tipt_tipotaxi = '"+tipoTaxi+"'"
            );
            
            ResultSet rst = dao.consultar("Select LAST_INSERT_ID();");
            if (rst != null && rst.next()) {
                ttaxi.setTiptTipotaxi(rst.getLong(1));
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
    

}
