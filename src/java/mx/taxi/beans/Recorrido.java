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
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.mig.dbc.Dao;

public class Recorrido implements Serializable {

    private Long recoRecorrido;
    private Date recoFecha;
    private Double recoLatitud;
    private Double recoLongitud;
    private Long recoPedido;
    private String recoTrecorrido;

    private Dao dao;
    private boolean dbExterno = false;

    private Recorrido reco;
    private List<Recorrido> lista = new ArrayList();

    public Recorrido() {
        this.dbExterno = false;
    }

    public Recorrido(Dao dao) {
        this.dao = dao;
        this.dbExterno = true;
    }

    public String getRecoTrecorrido() {
        return recoTrecorrido;
    }

    public void setRecoTrecorrido(String recoTrecorrido) {
        this.recoTrecorrido = recoTrecorrido;
    }

    public Long getRecoPedido() {
        return recoPedido;
    }

    public void setRecoPedido(Long recoPedido) {
        this.recoPedido = recoPedido;
    }

    public Date getRecoFecha() {
        return recoFecha;
    }

    public Double getRecoLatitud() {
        return recoLatitud;
    }

    public Double getRecoLongitud() {
        return recoLongitud;
    }

    public Long getRecoRecorrido() {
        return recoRecorrido;
    }

    public void setRecoFecha(Date recoFecha) {
        this.recoFecha = recoFecha;
    }

    public void setRecoLatitud(Double recoLatitud) {
        this.recoLatitud = recoLatitud;
    }

    public void setRecoLongitud(Double recoLongitud) {
        this.recoLongitud = recoLongitud;
    }

    public void setRecoRecorrido(Long recoRecorrido) {
        this.recoRecorrido = recoRecorrido;
    }

    public void setId(Long recoRecorrido) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();

            ResultSet rst
                    = dao.consultar("Select "
                            + "RECO_RECORRIDO,"
                            + "RECO_FECHA,"
                            + "RECO_LATITUD,"
                            + "RECO_LONGITUD,"
                            + "RECO_PEDIDO "
                            + "FROM RECORRIDO Where RECO_RECORRIDO = '" + recoRecorrido + "'"
                    );

            while (rst != null && rst.next()) {
                reco = new Recorrido();
                reco.setRecoRecorrido(recoRecorrido);
                reco.setRecoFecha(rst.getDate("RECO_FECHA"));
                reco.setRecoLatitud(rst.getDouble("RECO_LATITUD"));
                reco.setRecoLongitud(rst.getDouble("RECO_LONGITUD"));
                reco.setRecoPedido(rst.getLong("RECO_PEDIDO"));
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
                    = dao.consultar("Select "
                            + "RECO_RECORRIDO,"
                            + "RECO_FECHA,"
                            + "RECO_LATITUD,"
                            + "RECO_LONGITUD,"
                            + "RECO_PEDIDO "
                            + "FROM RECORRIDO " + condicion
                    );

            while (rst != null && rst.next()) {
                reco = new Recorrido();
                reco.setRecoRecorrido(rst.getLong("RECO_RECORRIDO"));
                reco.setRecoFecha(rst.getDate("RECO_FECHA"));
                reco.setRecoLatitud(rst.getDouble("RECO_LATITUD"));
                reco.setRecoLongitud(rst.getDouble("RECO_LONGITUD"));
                reco.setRecoPedido(rst.getLong("RECO_PEDIDO"));
                lista.add(reco);
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

    public void agrInicioRecorrido(Long recoRecorrido, Double latitud, Double longitud) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();

            dao.insertar(
                    "Insert into RECORRIDO (RECO_LATITUD,RECO_LONGITUD,RECO_PEDIDO,RECO_TRECORRIDO) "
                    + "VALUES ('" + latitud + "','" + longitud + "','" + recoRecorrido + "','I') "
            );

            if (dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

    }

    public void agrFinRecorrido(Long recoRecorrido, Double latitud, Double longitud) throws TaxiException {
        try {

            dao = dbExterno ? dao : new Dao();

            dao.insertar(
                    "Insert into RECORRIDO (RECO_LATITUD,RECO_LONGITUD,RECO_PEDIDO,RECO_TRECORRIDO) "
                    + "VALUES ('" + latitud + "','" + longitud + "','" + recoRecorrido + "','F') "
            );

            if (dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }
    }

    public boolean isRecorridoIniciado(Long pedido) throws TaxiException {
        boolean iniciado = false;

        try {
            dao = dbExterno ? dao : new Dao();

            ResultSet rst =
            dao.consultar("Select count(*) From RECORRIDO WHERE RECO_TIPO = 'I' AND RECO_PEDIDO = '"+pedido+"'");
            
            if (rst != null && rst.next()) {
                iniciado = rst.getInt(1) > 0;
            }
            
            if (dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

        return iniciado;
    }
    
    public boolean isRecorridoTerminado(Long pedido) throws TaxiException {
        boolean termino = false;

        try {
            dao = dbExterno ? dao : new Dao();

            ResultSet rst =
            dao.consultar("Select count(*) From RECORRIDO WHERE RECO_TIPO = 'F' AND RECO_PEDIDO = '"+pedido+"'");
            
            if (rst != null && rst.next()) {
                termino = rst.getInt(1) > 0;
            }
            
            if (dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

        return termino;
    }

    public List<Recorrido> getRecorridos() {
        return lista;
    }

    public Recorrido getRecorrido() {
        return reco;
    }

}
