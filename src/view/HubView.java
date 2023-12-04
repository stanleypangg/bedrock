package view;

import interface_adapters.HubViewModel;
import interface_adapters.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HubView extends JPanel {
    public final String viewName = "Hub Menu";
    private final HubViewModel hubViewModel;
    private ViewManagerModel viewManagerModel;
    final JButton play;
    final JButton collection;
    final JButton decks;
    final Dimension BUTTON_SIZE = new Dimension(300, 200);
    final Font BUTTON_FONT = new Font("Roboto", Font.BOLD, 16);

    public HubView(HubViewModel hubViewModel, ViewManagerModel viewManagerModel) {
        this.hubViewModel = hubViewModel;
        this.viewManagerModel = viewManagerModel;

        JPanel buttons = new JPanel(new FlowLayout()); // Use FlowLayout for buttons

        play = new JButton(hubViewModel.PLAY_BUTTON_LABEL);
        play.setPreferredSize(BUTTON_SIZE);
        play.setFont(BUTTON_FONT);
        buttons.add(play);

        collection = new JButton(hubViewModel.COLLECTION_BUTTON_LABEL);
        collection.setPreferredSize(BUTTON_SIZE);
        collection.setFont(BUTTON_FONT);
        buttons.add(collection);

        decks = new JButton(hubViewModel.DECKS_BUTTON_LABEL);
        decks.setPreferredSize(BUTTON_SIZE);
        decks.setFont(BUTTON_FONT);
        buttons.add(decks);

        // Set layout for the HubView to center the buttons vertically and horizontally
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(buttons, gbc);

        play.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(play)) {
                            viewManagerModel.setActiveView("Play Menu");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );
        collection.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(collection)) {
                            viewManagerModel.setActiveView("Collection Menu");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );
        decks.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(decks)) {
                            viewManagerModel.setActiveView("Decks Menu");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );
    }
}