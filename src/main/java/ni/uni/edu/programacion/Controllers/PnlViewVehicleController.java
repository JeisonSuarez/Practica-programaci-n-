/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.uni.edu.programacion.Controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ni.edu.uni.programacion.backend.dao.implementation.JsonVehicleDaoImpl;
import ni.edu.uni.programacion.backend.pojo.Vehicle;
import ni.uni.edu.programacion.component.model.VehicleTableModel;
import ni.uni.edu.programacion.views.InternalFrmViewVehicles;
import ni.uni.edu.programacion.views.panels.PnlViewVehicles;

/**
 *
 * @author Sistemas-05
 */
public class PnlViewVehicleController {

    private final PnlViewVehicles pnlViewVehicles;
    private JsonVehicleDaoImpl jsonVehicleDaoImpl;
    private VehicleTableModel tblViewModel;
    private List<Vehicle> vehicles;
    private final String[] HEADERS = new String[]{"StockNumber", "Year", "Make", "Model", "Style",
        "Vin", "Exterior color", "Interior color", "Miles", "Price", "Transmission", "Engine", "Image", "Status"};
    private TableRowSorter<VehicleTableModel> tblRowSorter;

    public PnlViewVehicleController(PnlViewVehicles pnlViewVehicles) {
        this.pnlViewVehicles = pnlViewVehicles;
        initComponent();
    }

    public VehicleTableModel getTblViewModel() {
        return tblViewModel;
    }

    private void initComponent() {
        try {
            jsonVehicleDaoImpl = new JsonVehicleDaoImpl();

            loadTable();

            pnlViewVehicles.getTxtFinder().addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    txtFinderKeyTyped(e);
                }

            });

           

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actionPerformed(ActionEvent e) throws IOException {
        if (e.getActionCommand().equalsIgnoreCase("Delete")) {
            tblViewModel.borrarFila(pnlViewVehicles.getTblViewVehicle().getSelectedRow());
            
        }
        
    }
    public boolean borrarDedata() throws IOException{
        
        Vehicle v = (Vehicle) tblViewModel.getValueAt(pnlViewVehicles.getTblViewVehicle().getSelectedRow(), 0);
      boolean  eliminado=jsonVehicleDaoImpl.delete(v);
      return eliminado;
    }
        
    private void txtFinderKeyTyped(KeyEvent e) {
        RowFilter<VehicleTableModel, Object> rf = null;
        rf = RowFilter.regexFilter(pnlViewVehicles.getTxtFinder().getText(), 0, 1, 2, 3, 4, 5, 6, 7, 8);
        tblRowSorter.setRowFilter(rf);
    }

    private void loadTable() throws IOException {
        vehicles = jsonVehicleDaoImpl.getAll().stream().collect(Collectors.toList());
        tblViewModel = new VehicleTableModel(vehicles, HEADERS);
        tblRowSorter = new TableRowSorter<>(tblViewModel);

        pnlViewVehicles.getTblViewVehicle().setModel(tblViewModel);
        pnlViewVehicles.getTblViewVehicle().setRowSorter(tblRowSorter);
    }

//    private Object[][] getData() throws IOException {
//        vehicles = jsonVehicleDaoImpl.getAll().stream().collect(Collectors.toList());
//        Object data[][] = new Object[vehicles.size()][vehicles.get(0).asArray().length];
//
//        IntStream.range(0, vehicles.size()).forEach(i -> {
//            data[i] = vehicles.get(i).asArray();
//        });
//
//        return data;
//    }
}
