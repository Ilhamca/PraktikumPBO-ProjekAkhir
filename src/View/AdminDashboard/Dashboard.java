/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.AdminDashboard;

import Controller.ControllerHistory;
import Controller.ControllerPatients;
import Controller.ControllerUsers;
import Model.History.ModelHistory;
import Model.Patients.ModelPatients;
import Model.Users.ModelUsers;
import View.Login.LoginForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Iam
 */
public class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     * @param user
     */
    public Dashboard(ModelUsers user) {
        initComponents();
        table.setModel(ControllerPatients.getTableModel());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        idInputField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState();
                updateButtonStaffState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState();
                updateButtonStaffState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState();
                updateButtonStaffState();
            }
        });

        idInputStaffField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonStaffState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonStaffState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonStaffState();
            }
        });
        setAlignLeftTable();
        applyDefaultSorter();
    }

    public void clearAddFields() {
        idInputField.setText("0");
        nameInputField.setText("");
        phoneInputField.setText("");
        dobDateChooser.setDate(null);
    }

    public void clearStaffFields() {
        idInputStaffField.setText("0");
        nameInputStaffField.setText("");
        passwordInputStaffField.setText("");
        buttonGroup1.clearSelection(); // This deselects both Admin and Staff radio buttons
    }

    private void setAlignLeftTable() {
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
    }

    private void applyDefaultSorter() {
        if (table.getModel().getColumnCount() == 0) {
            System.out.println("Debug: Skipping cause no columns");
            table.setRowSorter(null);
            return;
        }
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        setAlignLeftTable();
    }

    public void refreshTable() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
        if (selectedTitle.equals("Patient")) {
            System.out.println("Loading patients data now...");
            table.setModel(ControllerPatients.getTableModel());
            table.getTableHeader().setReorderingAllowed(false);
            tableLable.setText("Patients Table");
        }
        if (selectedTitle.equals("User")) {
            System.out.println("Loading user data now...");
            table.setModel(ControllerUsers.getTableModel());
            table.getTableHeader().setReorderingAllowed(false);
            tableLable.setText("User Table");
        }
        if (selectedTitle.equals("Queue History")) {
            System.out.println("Loading queue history data now...");
            table.setModel(ControllerHistory.getTableModel());
            table.getTableHeader().setReorderingAllowed(false);
            tableLable.setText("Queue History Table");
        }
        applyDefaultSorter();
    }

    private void updateButtonState() {
        try {
            int id = Integer.parseInt(idInputField.getText());
            if (id != 0) {
                addPatientButton.setText("Edit");
            } else {
                addPatientButton.setText("Add");
            }
        } catch (NumberFormatException e) {
            // If the field is empty or contains invalid text, default to "Add"
            addPatientButton.setText("Add");
        }
    }

    private void updateButtonStaffState() {
        try {
            int id = Integer.parseInt(idInputStaffField.getText());
            if (id != 0) {
                addStaffButton.setText("Edit");
            } else {
                addStaffButton.setText("Add");
            }
        } catch (NumberFormatException e) {
            // If the field is empty or contains invalid text, default to "Add"
            addStaffButton.setText("Add");
        }
    }

    public String getPatientSearchName() {
        return namePatientSearch.getText();
    }

    public int getPatientIdInput() {
        try {
            return Integer.parseInt(idInputField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private ModelUsers.Role getSelectedRole() {
        if (adminRadioButton.isSelected()) {
            return ModelUsers.Role.ADMIN;
        } else if (staffRadioButton.isSelected()) {
            return ModelUsers.Role.STAFF;
        } else {
            return null; // No role was selected
        }
    }

    private void setStaffRoleInput(ModelUsers.Role role) {
        // Note: Your ButtonGroup in the designer is likely named buttonGroup1
        if (role == null) {
            buttonGroup1.clearSelection();
            return;
        }

        switch (role) {
            case ADMIN:
                adminRadioButton.setSelected(true);
                break;
            case STAFF:
                staffRadioButton.setSelected(true);
                break;
            default:
                buttonGroup1.clearSelection();
                break;
        }
    }

    private void setStaffRoleSelection(ModelUsers.Role role) {
        if (role == null) {
            buttonGroup1.clearSelection(); // Make sure your ButtonGroup is named buttonGroup1
            return;
        }

        switch (role) {
            case ADMIN:
                adminRadioButton.setSelected(true);
                break;
            case STAFF:
                staffRadioButton.setSelected(true);
                break;
            default:
                buttonGroup1.clearSelection();
                break;
        }
    }

    public String getPatientNameInput() {
        return nameInputField.getText();
    }

    public String getPatientPhoneInput() {
        return phoneInputField.getText();
    }

    public java.util.Date getPatientDobInput() {
        return dobDateChooser.getDate();
    }

    // --- Setters for Input Values ---
    public void setPatientIdInput(int id) {
        idInputField.setText(String.valueOf(id));
    }

    public void setPatientNameInput(String name) {
        nameInputField.setText(name);
    }

    public void setPatientPhoneInput(String phone) {
        phoneInputField.setText(phone);
    }

    public void setPatientDobInput(java.util.Date date) {
        dobDateChooser.setDate(date);
    }

    // --- Getters for Components ---
    public javax.swing.JButton getPatientSearchButton() {
        return searchPatientButton; // Note: Your search button is named 'addButton'
    }

    public javax.swing.JButton getPatientAddEditButton() {
        return addPatientButton; // Note: Your Add/Edit button is named 'addButton6'
    }

    public javax.swing.JButton getPatientDeleteButton() {
        return deletePatientButton;
    }

    public javax.swing.JButton getPatientRefreshButton() {
        return refreshAddButton;
    }

    public javax.swing.JButton getPatientResetButton() {
        return resetSearchPatientButton;
    }

    //==========================================================================
    //  USER / STAFF TAB
    //==========================================================================
    // --- Getters for Input Values ---
    public String getStaffSearchName() {
        return nameSearchStaffField.getText();
    }

    public int getStaffIdInput() {
        try {
            return Integer.parseInt(idInputStaffField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getStaffNameInput() {
        return nameInputStaffField.getText();
    }

    public String getStaffPasswordInput() {
        return passwordInputStaffField.getText();
    }

    // --- Setters for Input Values ---
    public void setStaffIdInput(int id) {
        idInputStaffField.setText(String.valueOf(id));
    }

    public void setStaffNameInput(String name) {
        nameInputStaffField.setText(name);
    }

    public void setStaffPasswordInput(String password) {
        passwordInputStaffField.setText(password);
    }

    // --- Getters for Components ---
    public javax.swing.JButton getStaffSearchButton() {
        return searchStaffButton;
    }

    public javax.swing.JButton getStaffClearSearchButton() {
        return resetStaffButton;
    }

    public javax.swing.JButton getStaffAddButton() {
        return addStaffButton;
    }

    public javax.swing.JButton getStaffDeleteButton() {
        return deleteStaffButton;
    }

    public javax.swing.JButton getStaffResetIdButton() {
        return resetIdStaffButton;
    }

    //==========================================================================
    //  QUEUE HISTORY TAB
    //==========================================================================
    // --- Getters for Input Values ---
    public int getHistorySearchId() {
        return (Integer) idSearchHistoryQueueSpinner.getValue();
    }

    public int getHistoryRemoveId() {
        try {
            return Integer.parseInt(idRemoveQueueHistoryField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }



    // --- Setters for Input Values ---
    public void setHistorySearchId(int id) {
        idSearchHistoryQueueSpinner.setValue(id);
    }

    public void setHistoryRemoveId(int id) {
        idRemoveQueueHistoryField.setText(String.valueOf(id));
    }


    // --- Getters for Components ---
    public javax.swing.JButton getHistorySearchButton() {
        return searchHistoryQueueButton;
    }

    public javax.swing.JButton getHistoryClearButton() {
        return clearHistoryQueueButton;
    }

    public javax.swing.JButton getHistoryRemoveButton() {
        return removeHistoryQueueButton;
    }

    public javax.swing.JButton getHistoryResetIdButton() {
        return idResetHistoryQueueButton;
    }

    public javax.swing.JSpinner getHistorySearchSpinner() {
        return idSearchHistoryQueueSpinner;
    }

    //==========================================================================
    //  MAIN COMPONENTS
    //==========================================================================
    public javax.swing.JTable getTable() {
        return table;
    }

    public javax.swing.JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public javax.swing.JMenuItem getLogoutMenuItem() {
        return logoutMenuItem;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        searchPatientButton = new javax.swing.JButton();
        namePatientSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        idInputField = new javax.swing.JTextField();
        refreshAddButton = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        nameInputField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        phoneInputField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        dobDateChooser = new com.toedter.calendar.JDateChooser();
        addPatientButton = new javax.swing.JButton();
        deletePatientButton = new javax.swing.JButton();
        resetSearchPatientButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nameInputStaffField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        passwordInputStaffField = new javax.swing.JTextField();
        addStaffButton = new javax.swing.JButton();
        deleteStaffButton = new javax.swing.JButton();
        idInputStaffField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        resetIdStaffButton = new javax.swing.JButton();
        nameSearchStaffField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        searchStaffButton = new javax.swing.JButton();
        resetStaffButton = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        staffRadioButton = new javax.swing.JRadioButton();
        adminRadioButton = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        removeHistoryQueueButton = new javax.swing.JButton();
        idRemoveQueueHistoryField = new javax.swing.JTextField();
        idResetHistoryQueueButton = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        searchHistoryQueueButton = new javax.swing.JButton();
        clearHistoryQueueButton = new javax.swing.JButton();
        idSearchHistoryQueueSpinner = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        tableLable = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        logoutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(1360, 768));
        setResizable(false);

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        searchPatientButton.setText("Search");
        searchPatientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPatientButtonActionPerformed(evt);
            }
        });

        namePatientSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namePatientSearchActionPerformed(evt);
            }
        });

        jLabel11.setText("Name:");

        jLabel14.setText("Search Patient");

        jLabel10.setText("Add/Edit/Remove Patient");

        jLabel13.setText("ID:");

        idInputField.setEditable(false);
        idInputField.setText("0");

        refreshAddButton.setText("⟳");
        refreshAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshAddButtonActionPerformed(evt);
            }
        });

        jLabel17.setText("Name:");

        jLabel18.setText("Phone Number:");

        jLabel19.setText("Date of Birth:");

        addPatientButton.setText("Add");
        addPatientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPatientButtonActionPerformed(evt);
            }
        });

        deletePatientButton.setText("Delete");
        deletePatientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePatientButtonActionPerformed(evt);
            }
        });

        resetSearchPatientButton.setText("Reset");
        resetSearchPatientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSearchPatientButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel11)
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(searchPatientButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resetSearchPatientButton))
                            .addComponent(jLabel10)
                            .addComponent(namePatientSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(83, 83, 83)
                                    .addComponent(idInputField)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(refreshAddButton))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addGap(62, 62, 62)
                                    .addComponent(nameInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel19))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(phoneInputField, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(dobDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addGap(57, 57, 57)
                                    .addComponent(addPatientButton)
                                    .addGap(76, 76, 76)
                                    .addComponent(deletePatientButton))))))
                .addContainerGap(73, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(211, Short.MAX_VALUE)
                    .addComponent(jLabel14)
                    .addGap(194, 194, 194)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namePatientSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchPatientButton)
                    .addComponent(resetSearchPatientButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(refreshAddButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(nameInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(phoneInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(dobDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deletePatientButton)
                    .addComponent(addPatientButton))
                .addGap(58, 58, 58))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jLabel14)
                    .addContainerGap(655, Short.MAX_VALUE)))
        );

        tabbedPane.addTab("Patient", jPanel2);

        jLabel5.setText("Add / Edit / Remove Staff");

        jLabel6.setText("Name:");

        nameInputStaffField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameInputStaffFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Password:");

        addStaffButton.setText("Add");
        addStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStaffButtonActionPerformed(evt);
            }
        });

        deleteStaffButton.setText("Delete");
        deleteStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteStaffButtonActionPerformed(evt);
            }
        });

        idInputStaffField.setEditable(false);
        idInputStaffField.setText("0");
        idInputStaffField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idInputStaffFieldActionPerformed(evt);
            }
        });

        jLabel15.setText("ID:");

        resetIdStaffButton.setText("⟳");
        resetIdStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetIdStaffButtonActionPerformed(evt);
            }
        });

        nameSearchStaffField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameSearchStaffFieldActionPerformed(evt);
            }
        });

        jLabel12.setText("Name:");

        jLabel20.setText("Search Staff");

        searchStaffButton.setText("Search");
        searchStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStaffButtonActionPerformed(evt);
            }
        });

        resetStaffButton.setText("Reset");
        resetStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetStaffButtonActionPerformed(evt);
            }
        });

        jLabel23.setText("Role:");

        buttonGroup1.add(staffRadioButton);
        staffRadioButton.setText("Staff");

        buttonGroup1.add(adminRadioButton);
        adminRadioButton.setText("Admin");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(203, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(143, 143, 143))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(jLabel20))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel15)
                            .addComponent(jLabel12)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(searchStaffButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resetStaffButton))
                            .addComponent(nameInputStaffField)
                            .addComponent(passwordInputStaffField)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(idInputStaffField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetIdStaffButton))
                            .addComponent(nameSearchStaffField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(addStaffButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(deleteStaffButton))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(adminRadioButton)
                                .addGap(53, 53, 53)
                                .addComponent(staffRadioButton)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameSearchStaffField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchStaffButton)
                    .addComponent(resetStaffButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idInputStaffField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(resetIdStaffButton))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameInputStaffField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordInputStaffField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(adminRadioButton)
                    .addComponent(staffRadioButton))
                .addGap(91, 91, 91)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addStaffButton)
                    .addComponent(deleteStaffButton))
                .addGap(106, 106, 106))
        );

        tabbedPane.addTab("User", jPanel3);

        jLabel8.setText("Remove History Queue");

        removeHistoryQueueButton.setText("Remove");
        removeHistoryQueueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeHistoryQueueButtonActionPerformed(evt);
            }
        });

        idRemoveQueueHistoryField.setEditable(false);
        idRemoveQueueHistoryField.setText("0");
        idRemoveQueueHistoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idRemoveQueueHistoryFieldActionPerformed(evt);
            }
        });

        idResetHistoryQueueButton.setText("⟳");
        idResetHistoryQueueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idResetHistoryQueueButtonActionPerformed(evt);
            }
        });

        jLabel16.setText("ID:");

        jLabel21.setText("Search History Queue");

        jLabel22.setText("ID:");

        searchHistoryQueueButton.setText("Search");
        searchHistoryQueueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchHistoryQueueButtonActionPerformed(evt);
            }
        });

        clearHistoryQueueButton.setText("Clear");
        clearHistoryQueueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearHistoryQueueButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(idRemoveQueueHistoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idResetHistoryQueueButton)
                        .addGap(128, 128, 128))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(jLabel8))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(removeHistoryQueueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(39, 39, 39)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(searchHistoryQueueButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(clearHistoryQueueButton))
                                    .addComponent(idSearchHistoryQueueSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 49, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idRemoveQueueHistoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(idResetHistoryQueueButton))
                .addGap(18, 18, 18)
                .addComponent(removeHistoryQueueButton)
                .addGap(101, 101, 101)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(idSearchHistoryQueueSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchHistoryQueueButton)
                    .addComponent(clearHistoryQueueButton))
                .addContainerGap(334, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 1, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 713, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tabbedPane.addTab("Queue History", jPanel1);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        tableLable.setText("XXXXXXXXXXXXXX");

        jMenu3.setText("Setting");

        logoutMenuItem.setText("Logout");
        logoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(logoutMenuItem);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(tableLable)
                        .addGap(402, 402, 402))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMenuItemActionPerformed
        LoginForm loginForm = new LoginForm();
        loginForm.setLocationRelativeTo(null);
        loginForm.setVisible(true);
        dispose();
    }//GEN-LAST:event_logoutMenuItemActionPerformed

    private void searchPatientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPatientButtonActionPerformed
        String nameToSearch = this.getPatientSearchName();

        if (nameToSearch.trim().isEmpty()) {
            // If the search box is empty, just refresh the whole table
            refreshTable();
            return;
        }

        ControllerPatients controller = new ControllerPatients();
        TableModel searchResults = controller.searchByName(nameToSearch);

        table.setModel(searchResults);
        table.setAutoCreateRowSorter(true);
        setAlignLeftTable();
        System.out.println("Search completed. Displaying " + searchResults.getRowCount() + " results.");
        tableLable.setText("Search Result: Patients");
    }//GEN-LAST:event_searchPatientButtonActionPerformed

    private void nameInputStaffFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameInputStaffFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameInputStaffFieldActionPerformed

    private void addStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStaffButtonActionPerformed
        try {
            int id = this.getStaffIdInput();
            String name = this.getStaffNameInput();
            String password = this.getStaffPasswordInput();
            ModelUsers.Role role = this.getSelectedRole(); // Use our new helper method

            // --- Validation ---
            if (name.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (role == null) {
                JOptionPane.showMessageDialog(this, "A Role (Admin or Staff) must be selected!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // --------------------

            ModelUsers userData = new ModelUsers(id, name, password, role);
            ControllerUsers controller = new ControllerUsers();

            // This logic uses the unified controller pattern we established
            try {
                if (id == 0) { // Add new user
                    controller.insert(userData);
                } else { // Update existing user
                    controller.update(userData);
                }
                JOptionPane.showMessageDialog(this, "User operation successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                // You might want a clearStaffFields() method here

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Operation failed: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            clearStaffFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID Format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addStaffButtonActionPerformed

    private void deleteStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteStaffButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteStaffButtonActionPerformed

    private void removeHistoryQueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeHistoryQueueButtonActionPerformed
        int historyIdToRemove = this.getHistoryRemoveId();
        if (historyIdToRemove == 0) {
            JOptionPane.showMessageDialog(this, "Please select a record from the history table to remove.", "No Record Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to permanently delete this history record (ID: " + historyIdToRemove + ")?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            ControllerHistory controller = new ControllerHistory();
            ModelHistory historyToDelete = new ModelHistory();
            historyToDelete.setId(historyIdToRemove);
            controller.delete(historyToDelete);
            JOptionPane.showMessageDialog(this, "History record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            setHistoryRemoveId(0);
            refreshTable();
        }
        refreshTable();
    }//GEN-LAST:event_removeHistoryQueueButtonActionPerformed

    private void namePatientSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namePatientSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namePatientSearchActionPerformed

    private void idInputStaffFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idInputStaffFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idInputStaffFieldActionPerformed

    private void resetIdStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetIdStaffButtonActionPerformed
        clearStaffFields();
        table.clearSelection();
    }//GEN-LAST:event_resetIdStaffButtonActionPerformed

    private void idRemoveQueueHistoryFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idRemoveQueueHistoryFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idRemoveQueueHistoryFieldActionPerformed

    private void idResetHistoryQueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idResetHistoryQueueButtonActionPerformed
        idRemoveQueueHistoryField.setText("");
        table.clearSelection();
    }//GEN-LAST:event_idResetHistoryQueueButtonActionPerformed

    private void nameSearchStaffFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameSearchStaffFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameSearchStaffFieldActionPerformed

    private void searchStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStaffButtonActionPerformed
        String nameToSearch = this.getStaffSearchName();

        if (nameToSearch.trim().isEmpty()) {
            // If the search box is empty, just refresh the whole table
            refreshTable();
            return;
        }

        ControllerUsers controller = new ControllerUsers();
        TableModel searchResults = controller.searchByName(nameToSearch);

        table.setModel(searchResults);
        table.setAutoCreateRowSorter(true);
        setAlignLeftTable();
        System.out.println("Search completed. Displaying " + searchResults.getRowCount() + " results.");
        tableLable.setText("Search Result: Patients");
    }//GEN-LAST:event_searchStaffButtonActionPerformed

    private void resetStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetStaffButtonActionPerformed
        nameSearchStaffField.setText("0");
        refreshTable();
    }//GEN-LAST:event_resetStaffButtonActionPerformed

    private void searchHistoryQueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchHistoryQueueButtonActionPerformed
        int patientIdToSearch = this.getHistorySearchId();
        if (patientIdToSearch <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Patient ID to search.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }


        ControllerHistory controller = new ControllerHistory();


        TableModel searchResult = controller.searchByPatientId(patientIdToSearch);
        table.setModel(searchResult);
        applyDefaultSorter();
        tableLable.setText("Search Result: History for Patient ID " + patientIdToSearch);
    }//GEN-LAST:event_searchHistoryQueueButtonActionPerformed

    private void clearHistoryQueueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearHistoryQueueButtonActionPerformed
        idSearchHistoryQueueSpinner.setValue(0);
        refreshTable();
    }//GEN-LAST:event_clearHistoryQueueButtonActionPerformed

    private void refreshAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshAddButtonActionPerformed
        clearAddFields();
        table.clearSelection();
    }//GEN-LAST:event_refreshAddButtonActionPerformed

    private void addPatientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPatientButtonActionPerformed
        try {

            int id = this.getPatientIdInput();
            String name = this.getPatientNameInput();
            String phone = this.getPatientPhoneInput();
            Date dob = this.getPatientDobInput();

            if (name.trim().isEmpty() || phone.trim().isEmpty() || dob == null) {
                JOptionPane.showMessageDialog(this, "All field must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop the process if validation fails
            }

            ModelPatients patientData = new ModelPatients(id, name, phone, dob);
            ControllerPatients controller = new ControllerPatients();

            try {
                if (id > 0) {
                    controller.update(patientData);
                } else {
                    controller.insert(patientData);
                }
                JOptionPane.showMessageDialog(this, "Operation successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearAddFields();
                refreshTable();
            } catch (Exception e) {
                // This code ONLY runs if insert() or update() FAILED and threw an exception.
                JOptionPane.showMessageDialog(this, "Operation failed. Database error.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); // Print the detailed error to the console for debugging
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID Format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addPatientButtonActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // First, check if the "Patient" tab is the one currently active
        int selectedTabIndex = tabbedPane.getSelectedIndex();
        String currentTabTitle = tabbedPane.getTitleAt(selectedTabIndex);
        int selectedViewRow = table.getSelectedRow();
        if ("Patient".equals(currentTabTitle)) {

            // This gets the row index as it appears on the screen (the "view" index)
            // Check if a row was actually selected
            if (selectedViewRow != -1) {

                // This is a crucial step for when the table is sorted:
                // Convert the visual row index to the actual data model index.
                int modelRow = table.convertRowIndexToModel(selectedViewRow);

                try {
                    // Get data from the MODEL using the corrected modelRow index
                    Integer id = (Integer) table.getModel().getValueAt(modelRow, 0);
                    String name = (String) table.getModel().getValueAt(modelRow, 1);
                    String phone = (String) table.getModel().getValueAt(modelRow, 2);
                    Date dob = (Date) table.getModel().getValueAt(modelRow, 3);

                    // Use your own setter methods to populate the form fields
                    this.setPatientIdInput(id);
                    this.setPatientNameInput(name);
                    this.setPatientPhoneInput(phone);
                    this.setPatientDobInput(dob);

                } catch (Exception e) {
                    // This will catch any errors if the data in the table is not in the expected format
                    JOptionPane.showMessageDialog(this, "Error reading data from table: " + e.getMessage(), "Data Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if ("User".equals(currentTabTitle)) {
            if (selectedViewRow != -1) {
                // Convert view row index to model row index to be safe with sorting
                int modelRow = table.convertRowIndexToModel(selectedViewRow);

                try {
                    // 1. Get all data from the correct columns for the User
                    Integer id = (Integer) table.getModel().getValueAt(modelRow, 0);
                    String name = (String) table.getModel().getValueAt(modelRow, 1);
                    ModelUsers.Role role = (ModelUsers.Role) table.getModel().getValueAt(modelRow, 2);

                    // 2. Populate the correct User/Staff form fields using their setters
                    this.setStaffIdInput(id);
                    this.setStaffNameInput(name);
                    this.setStaffPasswordInput("");
                    this.setStaffRoleSelection(role);          // This correctly sets the Role radio button

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error reading user data from table: " + e.getMessage(), "Data Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace(); // Very useful for debugging
                }
            }
        } else if ("Queue History".equals(currentTabTitle)) {
            try {
                int modelRow = table.convertRowIndexToModel(selectedViewRow);
                Integer historyId = (Integer) table.getModel().getValueAt(modelRow, 0);

                // Populate the "Remove History Queue" form
                this.setHistoryRemoveId(historyId);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error reading history data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tableMouseClicked

    private void deletePatientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePatientButtonActionPerformed
        // 1. Get the ID from the input field.
        int id = this.getPatientIdInput();

        // 2. Validate that a patient is actually selected.
        if (id == 0) {
            JOptionPane.showMessageDialog(this, "Please select a patient from the table to delete.", "No Patient Selected", JOptionPane.WARNING_MESSAGE);
            return; // Stop the method here
        }

        // 3. Ask for confirmation before deleting.
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete patient with ID " + id + "?\nThis action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        // 4. Proceed only if the user clicks "Yes".
        if (response == JOptionPane.YES_OPTION) {
            // Create an instance of the controller to use its methods.
            ControllerPatients controller = new ControllerPatients();

            // Even though we only need the ID, the controller method requires a full object.
            ModelPatients patientToDelete = new ModelPatients(id, "", "", null);

            // 5. Call the controller's delete method.
            controller.delete(patientToDelete);

            // 6. Refresh the UI.
            clearAddFields();
            refreshTable();
        }
    }//GEN-LAST:event_deletePatientButtonActionPerformed

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        refreshTable();
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void resetSearchPatientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSearchPatientButtonActionPerformed
        namePatientSearch.setText("");
        refreshTable();
    }//GEN-LAST:event_resetSearchPatientButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ModelUsers user = null;
                Dashboard dashboard = new Dashboard(user);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPatientButton;
    private javax.swing.JButton addStaffButton;
    private javax.swing.JRadioButton adminRadioButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearHistoryQueueButton;
    private javax.swing.JButton deletePatientButton;
    private javax.swing.JButton deleteStaffButton;
    private com.toedter.calendar.JDateChooser dobDateChooser;
    private javax.swing.JTextField idInputField;
    private javax.swing.JTextField idInputStaffField;
    private javax.swing.JTextField idRemoveQueueHistoryField;
    private javax.swing.JButton idResetHistoryQueueButton;
    private javax.swing.JSpinner idSearchHistoryQueueSpinner;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JTextField nameInputField;
    private javax.swing.JTextField nameInputStaffField;
    private javax.swing.JTextField namePatientSearch;
    private javax.swing.JTextField nameSearchStaffField;
    private javax.swing.JTextField passwordInputStaffField;
    private javax.swing.JTextField phoneInputField;
    private javax.swing.JButton refreshAddButton;
    private javax.swing.JButton removeHistoryQueueButton;
    private javax.swing.JButton resetIdStaffButton;
    private javax.swing.JButton resetSearchPatientButton;
    private javax.swing.JButton resetStaffButton;
    private javax.swing.JButton searchHistoryQueueButton;
    private javax.swing.JButton searchPatientButton;
    private javax.swing.JButton searchStaffButton;
    private javax.swing.JRadioButton staffRadioButton;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable table;
    private javax.swing.JLabel tableLable;
    // End of variables declaration//GEN-END:variables
}
