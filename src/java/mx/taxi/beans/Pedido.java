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

public class Pedido implements Serializable {

    private Long pediPedido;
    private String pediEstatus;
    private Date pediFecha;
    private Double pediLatitud;
    private Double pediLongitud;
    private Float pediMonto;
    private Date pediFechaPago;
    private Long pediTaxi;
    private Long pediUsuario;

    private Pedido pedido;
    private List<Pedido> lista = new ArrayList();

    private Dao dao;
    private boolean dbExterno = false;

    public Pedido() {
        dbExterno = false;
    }

    public Pedido(Dao dao) {
        this.dao = dao;
        dbExterno = true;
    }

    public Long getPediUsuario() {
        return pediUsuario;
    }

    public void setPediUsuario(Long pediUsuario) {
        this.pediUsuario = pediUsuario;
    }

    public String getPediEstatus() {
        return pediEstatus;
    }

    public Date getPediFecha() {
        return pediFecha;
    }

    public Date getPediFechaPago() {
        return pediFechaPago;
    }

    public Double getPediLatitud() {
        return pediLatitud;
    }

    public Double getPediLongitud() {
        return pediLongitud;
    }

    public Float getPediMonto() {
        return pediMonto;
    }

    public Long getPediPedido() {
        return pediPedido;
    }

    public Long getPediTaxi() {
        return pediTaxi;
    }

    public void setPediEstatus(String pediEstatus) {
        this.pediEstatus = pediEstatus;
    }

    public void setPediFecha(Date pediFecha) {
        this.pediFecha = pediFecha;
    }

    public void setPediFechaPago(Date pediFechaPago) {
        this.pediFechaPago = pediFechaPago;
    }

    public void setPediLatitud(Double pediLatitud) {
        this.pediLatitud = pediLatitud;
    }

    public void setPediLongitud(Double pediLongitud) {
        this.pediLongitud = pediLongitud;
    }

    public void setPediMonto(Float pediMonto) {
        this.pediMonto = pediMonto;
    }

    public void setPediPedido(Long pediPedido) {
        this.pediPedido = pediPedido;
    }

    public void setPediTaxi(Long pediTaxi) {
        this.pediTaxi = pediTaxi;
    }

    public Pedido agrPedido(Pedido pedido) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();

            if (pedido.getPediTaxi() != null) {
                dao.insertar(
                        "Insert into PEDIDO(PEDI_LATITUD,PEDI_LONGITUD,PEDI_USUARIO,PEDI_TAXI) "
                        + " VALUES ('" + pedido.getPediLatitud() + "','" + pedido.getPediLongitud() + "','" + pedido.getPediUsuario() + "','" + (pedido.getPediTaxi() != null ? pedido.getPediTaxi() : "") + "') "
                );
            } else {
                dao.insertar(
                        "Insert into PEDIDO(PEDI_LATITUD,PEDI_LONGITUD,PEDI_USUARIO) "
                        + " VALUES ('" + pedido.getPediLatitud() + "','" + pedido.getPediLongitud() + "','" + pedido.getPediUsuario() + "') "
                );
            }

            ResultSet rst = dao.consultar("Select LAST_INSERT_ID() ");
            if (rst != null && rst.next()) {
                pedido.setPediPedido(rst.getLong(1));
            }

            if (!dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

        return pedido;
    }

    public void actEstatus(Long pediPedido, String estatus) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();

            dao.actualizar(
                    "Update PEDIDO "
                    + "Set pedi_estatus =  '" + estatus + "' "
                    + "Where pedi_pedido = '" + pediPedido + "'"
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

    public void actMonto(Long pediPedido, Float monto) throws TaxiException {

        try {
            dao = dbExterno ? dao : new Dao();
            dao.actualizar(
                    "Update PEDIDO "
                    + "Set pedi_monto = '" + monto + "' "
                    + "Where pedi_pedido = '" + pediPedido + "' "
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

    public void asigTaxiPedido(Long pediPedido, Long taxi) throws TaxiException {

        try {
            dao = dbExterno ? dao : new Dao();

            dao.actualizar(
                    "Update PEDIDO "
                    + "Set pedi_taxi = '" + taxi + "' "
                    + "Where pedi_pedido = '" + pediPedido + "' "
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

    public Float calcularPago(Long pediPedido) throws TaxiException {

        Float monto = 0.0f;

        try {

            dao = dbExterno ? dao : new Dao();

            Recorrido reco = new Recorrido(dao);
            reco.setCondicion("Where reco_pedido = '" + pediPedido.toString() + "'");
            List<Recorrido> lr = reco.getRecorridos();

            Double lat1 = 0.0d;
            Double lat2 = 0.0d;
            Double lon1 = 0.0d;
            Double lon2 = 0.0d;
            for (Recorrido r : lr) {

                if (r.getRecoTrecorrido().equals("I")) {
                    lat1 = r.getRecoLatitud();
                    lon1 = r.getRecoLongitud();
                } else if (r.getRecoTrecorrido().equals("F")) {
                    lat2 = r.getRecoLatitud();
                    lon2 = r.getRecoLongitud();
                }
            }

            Double dist = this.distanciaPuntos(lat1, lat2, lon1, lon2);

            this.setId(pediPedido);

            Taxi tx = new Taxi(dao);
            tx.setId(this.getPedido().getPediTaxi());
            Tipotaxi tt = new Tipotaxi(dao);
            tt.setId(tx.getTaxiTipotaxi());

            monto = tt.getTiptPrecio() * dist.floatValue();

            if (!dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

        return monto;
    }

    public void setId(Long pediPedido) throws TaxiException {

        try {
            dao = dbExterno ? dao : new Dao();
            ResultSet rst
                    = dao.consultar("SELECT "
                            + "PEDI_PEDIDO, "
                            + "PEDI_ESTATUS, "
                            + "PEDI_FECHA, "
                            + "PEDI_LATITUD, "
                            + "PEDI_LONGITUD, "
                            + "PEDI_MONTO, "
                            + "PEDI_FECHAPAGO, "
                            + "PEDI_TAXI, PEDI_USUARIO "
                            + "FROM PEDIDO Where PEDI_PEDIDO = '" + pediPedido + "' ");

            while (rst != null && rst.next()) {
                pedido = new Pedido();
                pedido.setPediPedido(pediPedido);
                pedido.setPediEstatus(rst.getString("PEDI_ESTATUS"));
                pedido.setPediFecha(rst.getDate("PEDI_FECHA"));
                pedido.setPediLatitud(rst.getDouble("PEDI_LATITUD"));
                pedido.setPediLongitud(rst.getDouble("PEDI_LONGITUD"));
                pedido.setPediMonto(rst.getFloat("PEDI_MONTO"));
                pedido.setPediFechaPago(rst.getDate("PEDI_FECHAPAGO"));
                pedido.setPediTaxi(rst.getLong("PEDI_TAXI"));
                pedido.setPediUsuario(rst.getLong("PEDI_USUARIO"));
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
                    = dao.consultar("SELECT "
                            + "PEDI_PEDIDO, "
                            + "PEDI_ESTATUS, "
                            + "PEDI_FECHA, "
                            + "PEDI_LATITUD, "
                            + "PEDI_LONGITUD, "
                            + "PEDI_MONTO, "
                            + "PEDI_FECHAPAGO, "
                            + "PEDI_TAXI,PEDI_USUARIO "
                            + "FROM PEDIDO " + condicion);

            while (rst != null && rst.next()) {
                pedido = new Pedido();
                pedido.setPediPedido(rst.getLong("PEDI_PEDIDO"));
                pedido.setPediEstatus(rst.getString("PEDI_ESTATUS"));
                pedido.setPediFecha(rst.getDate("PEDI_FECHA"));
                pedido.setPediLatitud(rst.getDouble("PEDI_LATITUD"));
                pedido.setPediLongitud(rst.getDouble("PEDI_LONGITUD"));
                pedido.setPediMonto(rst.getFloat("PEDI_MONTO"));
                pedido.setPediFechaPago(rst.getDate("PEDI_FECHAPAGO"));
                pedido.setPediTaxi(rst.getLong("PEDI_TAXI"));
                pedido.setPediUsuario(rst.getLong("PEDI_USUARIO"));
                lista.add(pedido);
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

    public List<Pedido> getPedidos() {
        return lista;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Double distanciaPuntos(Double lat1, Double lat2, Double lon1, Double lon2) {
        Double dist = 0.0;

        Double radio = 6378.137;
        Double dLat = rad(lat2 - lat1);
        Double dLong = rad(lon2 - lon1);

        Double a = Math.sin(dLat / 2)
                * Math.sin(dLat / 2)
                + Math.cos(rad(lat1))
                * Math.cos(rad(lat2))
                * Math.sin(dLong / 2)
                * Math.sin(dLong / 2);

        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        dist = radio * c;

        return dist;
    }

    private Double rad(Double num) {
        return (num * Math.PI / 180);
    }

}
