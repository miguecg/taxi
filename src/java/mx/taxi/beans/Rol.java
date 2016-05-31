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

public class Rol implements Serializable {

    private Long roleRol;
    private String roleDescrip;

    private Dao dao;
    private boolean dbExterno = false;

    private List<Rol> lista = new ArrayList();
    private Rol rol;

    public Rol() {
        this.dbExterno = false;
    }

    public Rol(Dao dao) {
        this.dao = dao;
        this.dbExterno = true;
    }

    public String getRoleDescrip() {
        return roleDescrip;
    }

    public Long getRoleRol() {
        return roleRol;
    }

    public void setRoleDescrip(String roleDescrip) {
        this.roleDescrip = roleDescrip;
    }

    public void setRoleRol(Long roleRol) {
        this.roleRol = roleRol;
    }

    public void setId(Long roleRol) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();
            ResultSet rst
                    = dao.consultar("Select role_role,role_descrip From ROL Where role_role = '" + roleRol + "'");

            while (rst != null && rst.next()) {
                rol = new Rol();
                rol.setRoleRol(rst.getLong("ROLE_ROLE"));
                rol.setRoleDescrip(rst.getString("ROLE_DESCRIP"));
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
                    = dao.consultar("Select role_role,role_descrip From ROL " + condicion);

            while (rst != null && rst.next()) {
                rol = new Rol();
                rol.setRoleRol(rst.getLong("ROLE_ROLE"));
                rol.setRoleDescrip(rst.getString("ROLE_DESCRIP"));
                lista.add(rol);
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

    public List<Rol> getRoles() {
        return lista;
    }

    public Rol getRol() {
        return rol;
    }

    public Rol agrRol(Rol rol) throws TaxiException {

        try {

            dao = dbExterno ? dao : new Dao();

            dao.insertar("Insert into ROL (ROLE_ROLE,ROLE_DESCRIP) VALUES('" + rol.getRoleRol() + "','" + rol.getRoleDescrip() + "')");

            ResultSet rst = dao.consultar("Select LAST_INSERT_ID()");
            if (rst != null && rst.next()) {
                rol.setRoleRol(rst.getLong(1));
            }

            if (!dbExterno) {
                dao.desconectar();
            }

        } catch (IOException e) {
            throw new TaxiException(e);
        } catch (SQLException e) {
            throw new TaxiException(e);
        }

        return rol;
    }

    public void actRol(Rol rol) throws TaxiException {
        try {

            dao = dbExterno ? dao : new Dao();

            dao.actualizar("Update ROL set ROLE_DESCRIP = '" + rol.getRoleDescrip() + "' Where role_role = '" + rol.getRoleRol() + "'");

            ResultSet rst = dao.consultar("Select LAST_INSERT_ID();");
            if (rst != null && rst.next()) {
                rol.setRoleRol(rst.getLong(1));
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

    public boolean isRol(Long rol, List<Rol> lr) throws TaxiException {
        boolean loTiene = false;

        for (Rol r : lr) {
            if (r.getRoleRol().equals(rol)) {
                loTiene = true;
                break;
            }
        }

        return loTiene;
    }

}
