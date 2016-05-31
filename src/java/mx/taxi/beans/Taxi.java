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

public class Taxi implements Serializable {

    private Long taxiTaxi;
    private Long taxiModelo;
    private String taxiPlaca;
    private Long taxiOcupantes;
    private Long taxiTipotaxi;
    private String taxiDescrip;
    private String taxiEstatus;
    private Long taxiUsuario;
    private String taxiMarca;

    private Dao dao;
    private boolean dbExterno = false;

    private Taxi taxi;
    private List<Taxi> lista = new ArrayList();

    public Taxi() {
        this.dbExterno = false;
    }

    public Taxi(Dao dao) {
        this.dao = dao;
        this.dbExterno = true;
    }

    public String getTaxiMarca() {
        return taxiMarca;
    }

    public void setTaxiMarca(String taxiMarca) {
        this.taxiMarca = taxiMarca;
    }

    public String getTaxiDescrip() {
        return taxiDescrip;
    }

    public String getTaxiEstatus() {
        return taxiEstatus;
    }

    public Long getTaxiModelo() {
        return taxiModelo;
    }

    public Long getTaxiOcupantes() {
        return taxiOcupantes;
    }

    public String getTaxiPlaca() {
        return taxiPlaca;
    }

    public Long getTaxiTaxi() {
        return taxiTaxi;
    }

    public Long getTaxiTipotaxi() {
        return taxiTipotaxi;
    }

    public Long getTaxiUsuario() {
        return taxiUsuario;
    }

    public void setTaxiDescrip(String taxiDescrip) {
        this.taxiDescrip = taxiDescrip;
    }

    public void setTaxiEstatus(String taxiEstatus) {
        this.taxiEstatus = taxiEstatus;
    }

    public void setTaxiModelo(Long taxiModelo) {
        this.taxiModelo = taxiModelo;
    }

    public void setTaxiOcupantes(Long taxiOcupantes) {
        this.taxiOcupantes = taxiOcupantes;
    }

    public void setTaxiPlaca(String taxiPlaca) {
        this.taxiPlaca = taxiPlaca;
    }

    public void setTaxiTaxi(Long taxiTaxi) {
        this.taxiTaxi = taxiTaxi;
    }

    public void setTaxiTipotaxi(Long taxiTipotaxi) {
        this.taxiTipotaxi = taxiTipotaxi;
    }

    public void setTaxiUsuario(Long taxiUsuario) {
        this.taxiUsuario = taxiUsuario;
    }

    public void setId(Long taxiTaxi) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();
            ResultSet rst
                    = dao.consultar("Select "
                            + "TAXI_TAXI,"
                            + "TAXI_MODELO,"
                            + "TAXI_PLACA,"
                            + "TAXI_OCUPANTES,"
                            + "TAXI_TIPOTAXI,"
                            + "TAXI_DESCRIP,"
                            + "TAXI_ESTATUS,TAXI_MARCA, "
                            + "TAXI_USUARIO FROM TAXI Where TAXI_TAXI = '" + taxiTaxi + "'"
                    );

            while (rst != null && rst.next()) {
                taxi = new Taxi();
                taxi.setTaxiTaxi(taxiTaxi);
                taxi.setTaxiModelo(rst.getLong("TAXI_MODELO"));
                taxi.setTaxiEstatus(rst.getString("TAXI_ESTATUS"));
                taxi.setTaxiPlaca(rst.getString("TAXI_PLACA"));
                taxi.setTaxiTipotaxi(rst.getLong("TAXI_TIPOTAXI"));
                taxi.setTaxiDescrip(rst.getString("TAXI_DESCRIP"));
                taxi.setTaxiOcupantes(rst.getLong("TAXI_OCUPANTES"));
                taxi.setTaxiMarca(rst.getString("TAXI_MARCA"));
                taxi.setTaxiUsuario(rst.getLong("TAXI_USUARIO"));
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
                            + "TAXI_TAXI,"
                            + "TAXI_MODELO,"
                            + "TAXI_PLACA,"
                            + "TAXI_OCUPANTES,"
                            + "TAXI_TIPOTAXI,"
                            + "TAXI_DESCRIP,"
                            + "TAXI_ESTATUS,TAXI_MARCA,"
                            + "TAXI_USUARIO FROM TAXI " + condicion
                    );

            while (rst != null && rst.next()) {
                taxi = new Taxi();
                taxi.setTaxiTaxi(rst.getLong("TAXI_TAXI"));
                taxi.setTaxiModelo(rst.getLong("TAXI_MODELO"));
                taxi.setTaxiEstatus(rst.getString("TAXI_ESTATUS"));
                taxi.setTaxiPlaca(rst.getString("TAXI_PLACA"));
                taxi.setTaxiTipotaxi(rst.getLong("TAXI_TIPOTAXI"));
                taxi.setTaxiDescrip(rst.getString("TAXI_DESCRIP"));
                taxi.setTaxiOcupantes(rst.getLong("TAXI_OCUPANTES"));
                taxi.setTaxiMarca(rst.getString("TAXI_MARCA"));
                taxi.setTaxiUsuario(rst.getLong("TAXI_USUARIO"));
                lista.add(taxi);
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

    public List<Taxi> getTaxis() {
        return lista;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public Taxi agrTaxi(Taxi taxi) throws TaxiException {

        try {

             dao = dbExterno ? dao : new Dao();

            dao.insertar("Insert into TAXI (TAXI_MODELO,TAXI_PLACA,TAXI_MARCA,TAXI_OCUPANTES,TAXI_TIPOTAXI,TAXI_DESCRIP,TAXI_USUARIO,TAXI_ESTATUS) "
                    + "VALUES ('" + taxi.getTaxiModelo() + "','" + taxi.getTaxiPlaca() + "','" + taxi.getTaxiMarca() + "','" + taxi.getTaxiOcupantes() + "','" + taxi.getTaxiTipotaxi() + "','" + taxi.getTaxiDescrip() + "','"+taxi.getTaxiUsuario()+"','" + taxi.getTaxiEstatus() + "') ");

            ResultSet rst = dao.consultar("Select LAST_INSERT_ID()");
            if (rst != null && rst.next()) {
                taxi.setTaxiTaxi(rst.getLong(1));
            }

            if (!dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

        return taxi;
    }

    public void actTaxi(Taxi taxi) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();

            dao.actualizar("Update TAXI Set "
                    + "taxi_placa = '" + taxi.getTaxiPlaca() + "',"
                    + "taxi_modelo = '" + taxi.getTaxiModelo() + "',"
                    + "taxi_marca = '" + taxi.getTaxiMarca() + "',"
                    + "taxi_ocupantes = '" + taxi.getTaxiOcupantes() + "',"
                    + "taxi_tipotaxi = '" + taxi.getTaxiTipotaxi() + "',"
                    + "taxi_descrip = '" + taxi.getTaxiDescrip() + "',"
                    + "taxi_usuario = '" + taxi.getTaxiUsuario() + "',"
                    + "taxi_estatus = '" + taxi.getTaxiEstatus() + "' Where taxi_taxi = '" + taxi.getTaxiTaxi() + "' "
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

    public void actEstatus(Long taxiTaxi, String estatus) throws TaxiException {

        try {
            dao = dbExterno ? dao : new Dao();

            dao.actualizar("Update TAXI Set taxi_estatus = '" + estatus + "' Where taxi_taxi = '" + taxiTaxi + "' ");

            if (!dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

    }

    public void asigTaxiCond(Long taxiTaxi, Long usuario) throws TaxiException {
        try {
            dao = dbExterno ? dao : new Dao();

            dao.actualizar("Update TAXI Set taxi_usuario = '" + usuario + "' Where taxi_taxi = '" + taxiTaxi + "' ");

            if (!dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }
    }

    public void deasigTaxiCond(Long taxiTaxi) throws TaxiException {
        try {
            dao = dbExterno ? dao : new Dao();

            dao.actualizar("Update TAXI Set taxi_usuario = 0 Where taxi_taxi = '" + taxiTaxi + "' ");

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
