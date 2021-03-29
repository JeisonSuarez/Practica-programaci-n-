/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.uni.edu.programacion.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ni.edu.uni.programacion.backend.dao.implementation.JsonVehicleDaoImpl;
import ni.edu.uni.programacion.backend.pojo.Vehicle;
import ni.edu.uni.programacion.backend.pojo.VehicleSubModel;
import ni.uni.edu.programacion.views.panels.PnlViewVehicles;

/**
 *
 * @author Javier Ramirez
 */
public class pnlViewVehicleController {
    private PnlViewVehicles pnlViewVehicle;
    private JsonVehicleDaoImpl jvdao;
    private List<VehicleSubModel> vehicleSubModels;
    private DefaultButtonModel btnBusqueda;
    private TableRowSorter tbr;
        public DefaultComboBoxModel <String> cmbBusquedaModel;
    private final String TIPOBUSQUEDA[]=new String[] {"Year","Make", "Model","Style","VIN"};
    int columna=0;
    
    public pnlViewVehicleController(PnlViewVehicles pnlViewVehicle) throws IOException {
        this.pnlViewVehicle = pnlViewVehicle;
        initComponent();
        initComponetTable();
    }
    
    private void initComponent(){
       cmbBusquedaModel= new DefaultComboBoxModel<>(TIPOBUSQUEDA);
        pnlViewVehicle.getCmbBusqueda().setModel(cmbBusquedaModel);
        
        
         pnlViewVehicle.getBtnBusqueda().addActionListener((e) -> {
             btnBusquedaActionListener(e);
          
         });
         //QUITA ESTOS COMENTARIOS DEL INIT COMPONENT Y MIRA , QUE LOS DATOS DE LA TABLA DESAPARECEN, DESPUES PONELOS Y APARECERAN TODOS LOS DATOS
         //añadir keylistener al txt de busqueda
       
         
        
     }
     
      private void initComponetTable () throws FileNotFoundException, IOException{
          jvdao= new JsonVehicleDaoImpl();
          List<Vehicle> listvehicle=(List<Vehicle>)jvdao.getAll();
          String [][] tableVehicles= new String[listvehicle.size()][14];
          
          MatrixTable(tableVehicles, listvehicle);
          
          setTable(tableVehicles);
       
          
      }
     
      private void setTable(String [][] tableVehicle){
          pnlViewVehicle.getTblTabla().setModel(new DefaultTableModel(
                  tableVehicle, 
                  new String[]{ 
                   "Stock Number","Year","Make","Model","Style","VIN",
                    "Exterior Color","Interior Color","Miles","Price","Transmision","Engine","Status","Image"
                  }
                          
          ));
      }
      
      private void MatrixTable(String [][] tableVehicle,List<Vehicle> listvehicle) throws IOException{
          for (int i = 0; i < jvdao.getAll().size(); i++) {
              
              tableVehicle [i][0]=Integer.toString(listvehicle.get(i).getStockNumber());
              tableVehicle [i][1]=Integer.toString(listvehicle.get(i).getYear());
              tableVehicle [i][2]=(listvehicle.get(i).getMake());
              tableVehicle [i][3]=(listvehicle.get(i).getModel());
              tableVehicle [i][4]=(listvehicle.get(i).getStyle());
              tableVehicle [i][5]=(listvehicle.get(i).getVin());
              tableVehicle [i][6]=(listvehicle.get(i).getExteriorColor());
              tableVehicle [i][7]=(listvehicle.get(i).getInteriorColor());
              tableVehicle [i][8]=(listvehicle.get(i).getMiles());
              tableVehicle [i][9]=Float.toString(listvehicle.get(i).getPrice());
              tableVehicle [i][10]=(listvehicle.get(i).getTransmission().toString());
              tableVehicle [i][11]=(listvehicle.get(i).getEngine());
              tableVehicle [i][12]=(listvehicle.get(i).getStatus());
              tableVehicle [i][13]=(listvehicle.get(i).getImage());
              
              

//              

          }
      }
       
    
    
    
    
    private void btnBusquedaActionListener(ActionEvent e){
         String txtContent;
         txtContent= pnlViewVehicle.getTxtBusqueda().getText();
          if (txtContent.equals("")){
           
        JOptionPane.showMessageDialog(null, "If the field is empty, all vehicle type objects are shown.",
                "Information message", JOptionPane.INFORMATION_MESSAGE);
         }
             String cadena=pnlViewVehicle.getTxtBusqueda().getText();
                filtro();
          tbr= new  TableRowSorter(pnlViewVehicle.getTblTabla().getModel());
        pnlViewVehicle.getTblTabla().setRowSorter(tbr);
//       else{
//              if (txtContent.equals(txtContent.trim() )) {
//                  JOptionPane.showMessageDialog(null, "Son Numeros los que ingreso.",
//                "Information message", JOptionPane.INFORMATION_MESSAGE);
//                  
//                  
//              }
         
         
        
     }
    private void filtro(){
       
     int indexBusqueda= pnlViewVehicle.getCmbBusqueda().getSelectedIndex();  //el numero es igual al index que este seleccionado en cmb de busqueda.
              switch(indexBusqueda){
                  case 0:
                      //indexBsuqeda marca year
                      columna=1;
                      return;
                      
                  case 1:
                     //indexBsuqeda marca Make
                      columna=2;
                      return;
                  case 2:
                      //indexBsuqeda marca Model
                      columna=3;
                      return;
                  case 3:
                      //indexBsuqeda marca Style
                      columna=4;
                      return;
                    case 4:
                      //indexBsuqeda marca VIN
                        columna=5;
                        return;
                          
              }
     tbr.setRowFilter(RowFilter.regexFilter(pnlViewVehicle.getTxtBusqueda().getText(), indexBusqueda));  //aqui se va a filtrar por lo que este escrito en  txt de busqueda , y la columna que sera el index seleccionado
   
}
//    private void TxrBusquedaKeyTyped(KeyEvent evt){
//         pnlViewVehicle.getTxtBusqueda().addKeyListener(new KeyAdapter(){ 
//            
//            @Override
//            public void keyReleased(KeyEvent e) {
//                int indexBusqueda= pnlViewVehicle.getCmbBusqueda().getSelectedIndex();
//                tbr.setRowFilter(RowFilter.regexFilter("(?i)"+pnlViewVehicle.getTxtBusqueda().getText(), indexBusqueda));
//            }
//        });
//        tbr= new  TableRowSorter();
//        pnlViewVehicle.getTblTabla().setRowSorter(tbr);
//    }
}
