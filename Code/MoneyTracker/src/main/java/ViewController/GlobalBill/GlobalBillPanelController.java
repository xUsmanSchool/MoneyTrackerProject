package ViewController.GlobalBill;

import DatabaseController.PersonsDBController;
import DatabaseController.TicketsDBController;
import HelperClass.CalculateBill;
import Model.modelxd;
import View.others.CustomColors;
import View.panels.GlobalBill.GlobalBillPanel;
import ViewController.ViewController;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class GlobalBillPanelController extends ViewController {
    private final TicketsDBController ticketsDBController;
    private final PersonsDBController personsDBController;
    private final GlobalBillPanel globalBillPanel;

    public GlobalBillPanelController(PersonsDBController personsDBController, TicketsDBController ticketsDBController, GlobalBillPanel globalBillPanel) {
        this.ticketsDBController = ticketsDBController;
        this.personsDBController = personsDBController;
        this.globalBillPanel = globalBillPanel;
    }

    @Override
    public void init() {
        // general panel styling
        globalBillPanel.setBorder(BorderFactory.createEmptyBorder(80, 20, 15, 15));
        globalBillPanel.getJList().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        globalBillPanel.getJList().setBackground(CustomColors.getDarkGrey());
        globalBillPanel.getTextButtonContainer().setBackground(CustomColors.getMidGrey());
        globalBillPanel.setBackground(CustomColors.getMidGrey());

        CalculateBill calculateBill = new CalculateBill(personsDBController, ticketsDBController);
        addModelxdToListToListModel(calculateBill.calculate());

        // title
        globalBillPanel.getTextButtonContainer().setBackground(CustomColors.getMidGrey());
        globalBillPanel.getTitleLabel().setForeground(Color.WHITE);
        globalBillPanel.setTitle("Global bill");
        globalBillPanel.getTitleLabel().setFont(new Font("", Font.PLAIN, 16));
    }

    @Override
    public void activateActionListeners() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private void addModelxdToListToListModel(ArrayList<modelxd> modelxdList) {
        for (modelxd m : modelxdList) addModelxd(m);
    }

    private void addModelxd(modelxd modelxd) {
        this.globalBillPanel.getListModel().addElement(modelxd);
    }
}
