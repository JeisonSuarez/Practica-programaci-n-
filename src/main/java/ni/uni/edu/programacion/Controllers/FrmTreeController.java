/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.uni.edu.programacion.Controllers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import ni.edu.uni.programacion.backend.dao.implementation.JsonVehicleDaoImpl;
import ni.edu.uni.programacion.backend.pojo.Vehicle;
import ni.uni.edu.programacion.views.FrmTreeDemo;

/**
 *
 * @author Sistemas-05
 */
public class FrmTreeController {
    private FrmTreeDemo frmTreeDemo;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode root;
    private Vehicle vehicle;
    private Gson gson;
    private JsonVehicleDaoImpl  jvdao;
    public FrmTreeController(FrmTreeDemo frmTreeDemo) throws FileNotFoundException, IOException {
        this.frmTreeDemo = frmTreeDemo;
        initComponent();
    }
    
    public void initComponent() throws FileNotFoundException, IOException{
         gson = new Gson();
        jvdao = new JsonVehicleDaoImpl();

        JsonReader jreader = new JsonReader(new BufferedReader(
                new InputStreamReader(getClass()
                        .getResourceAsStream("/jsons/vehicleData.json"))
        ));
        
        root = new DefaultMutableTreeNode("Vehicles", true);
        treeModel = new DefaultTreeModel(root);
        
        frmTreeDemo.getTreeAccount().setModel(treeModel);
        frmTreeDemo.getTreeAccount().setExpandsSelectedPaths(true);
        frmTreeDemo.getBtnAdd().addActionListener((e) -> {
            btnAddActionListener(e);
        });
        
        frmTreeDemo.getBtnRemove().addActionListener((e) -> {
            btnRemoveActionListener(e);
        });
        
        frmTreeDemo.getTreeAccount().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                treeAccountMouseListener(e);
            }
        });
        
        frmTreeDemo.getMniAdd().addActionListener((e) -> {
            btnAddActionListener(e);
        });
        
        frmTreeDemo.getMniRemove().addActionListener((e) -> {
            btnRemoveActionListener(e);
        });
        frmTreeDemo.getBtnRemoveAll().addActionListener((e)->{
            btnRemoveAllActionListener(e);
        });
        vehicleRootChild();
    }
        
    public void btnAddActionListener(ActionEvent e){
        DefaultMutableTreeNode node = getSelectedNode();
        if(node == null){
            return;
        }
        String accountName = JOptionPane.showInputDialog(null, "Account name", "Input Dialog", 
                JOptionPane.INFORMATION_MESSAGE);
        int childCount = node.getChildCount();
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(accountName);
        treeModel.insertNodeInto(child, node, childCount);
    }
    
    public void btnRemoveActionListener(ActionEvent e){
        DefaultMutableTreeNode node = getSelectedNode();
        if(node == null || node.isRoot()){
            return;
        }
        
        treeModel.removeNodeFromParent(node);
    }
    public void btnRemoveAllActionListener(ActionEvent e){
        root.removeAllChildren();
        treeModel.reload();
    }
    
    
    public void treeAccountMouseListener(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON3 ){
             
            TreePath c =  frmTreeDemo.getTreeAccount().getPathForLocation(e.getX(), e.getY());
            if(c == null){
                return;
            }
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) c.getLastPathComponent();         
            frmTreeDemo.getPmnTree().show(frmTreeDemo.getTreeAccount(), e.getX(), e.getY());
        }
    }
    
    private DefaultMutableTreeNode getSelectedNode(){
        TreePath treePath = frmTreeDemo.getTreeAccount().getSelectionPath();
        if(treePath == null){
            return null;
        }
        return (DefaultMutableTreeNode) treePath.getLastPathComponent();
    }
    
    public void vehicleRootChild() throws IOException{
        
        List<Vehicle> vehiclesList= jvdao.getAll().stream().collect(Collectors.toList());
 
        Map< String, List<Vehicle>> marcaVehicle
                = vehiclesList.stream()
                .collect(Collectors.groupingBy(Vehicle::getMake));
//      for (Map.entry(String  , List <Vehicle>)entry :marcaVehicle.entrySet()){
//        int childCount= root.getChildCount();
//        DefaultMutableTreeNode child = new DefaultMutableTreeNode(marcaVehicle);
//        treeModel.insertNodeInto(child, root, childCount);
//    }
marcaVehicle.entrySet().stream().map(_item -> root.getChildCount()).forEachOrdered(childCount -> {
    DefaultMutableTreeNode child = new DefaultMutableTreeNode(marcaVehicle);
    treeModel.insertNodeInto(child, root, childCount);
        });
    
 
}
    }
