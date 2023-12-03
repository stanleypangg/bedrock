package app;

import data.CardDAO;
import data.TradingCardGameDAO;
import entities.*;
import interface_adapters.*;
//import interface_adapters.add_to_collection.AddToCollectionViewModel;
//import interface_adapters.build_card.BuildCardViewModel;
//import interface_adapters.build_deck.BuildDeckViewModel;
import interface_adapters.wonder_trade.WonderTradeController;
import interface_adapters.wonder_trade.WonderTradeViewModel;
import use_cases.wonder_trade.WonderTradeInputBoundary;
import interface_adapters.build_deck.BuildDeckViewModel;
import interface_adapters.delete_deck.DeleteDeckViewModel;
import view.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static Player player;
    public static void main(String[] args) {
        TradingCardGameDAO.fetch_and_write_data();

        player = createExamplePlayer();

        JFrame application = new JFrame("Pokémon");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        MainViewModel mainViewModel = new MainViewModel();
        HubViewModel hubViewModel = new HubViewModel();
        PlayViewModel playViewModel = new PlayViewModel();
        CollectionViewModel collectionViewModel = new CollectionViewModel();
        DecksViewModel decksViewModel = new DecksViewModel();

        DeleteDeckViewModel deleteDeckViewModel = new DeleteDeckViewModel();
        BuildDeckViewModel buildDeckViewModel = new BuildDeckViewModel();
      
        CardDAO userDataAccessObject;
        try {
            userDataAccessObject = new CardDAO("./cards.csv", new CommonCardFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainView mainView = new MainView(mainViewModel, viewManagerModel);
        views.add(mainView, mainView.viewName);

        HubView hubView = new HubView(hubViewModel, viewManagerModel);
        views.add(hubView, hubView.viewName);

        PlayView playView = new PlayView(playViewModel, viewManagerModel);
        views.add(playView, playView.viewName);

        CollectionView collectionView = new CollectionView(collectionViewModel, viewManagerModel, buildDeckViewModel);
        views.add(collectionView, collectionView.viewName);

        DecksView decksView = DeckUseCaseFactory.create(viewManagerModel, decksViewModel, deleteDeckViewModel);
        views.add(decksView, decksView.viewName);

        BuildDeckView buildDeckView = new BuildDeckView(buildDeckViewModel, viewManagerModel);
        views.add(buildDeckView, buildDeckView.viewName);

        viewManagerModel.setActiveView(mainView.viewName);
        viewManagerModel.firePropertyChanged();

        // Title Bar Icon
        try {
            Image pokeballIcon = ImageIO.read(new File("images/pokeball_icon.png"));
            application.setIconImage(pokeballIcon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        application.pack();
        application.setSize(1280, 720);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }

    public static Player createExamplePlayer() {
        CommonCardFactory commonCardFactory = new CommonCardFactory();
        Card one = commonCardFactory.create("sv3pt5-202", "Zapdos ex");
        Card two = commonCardFactory.create("sv3pt5-179", "Mr. Mime");
        Card three = commonCardFactory.create("sv3pt5-176", "Poliwhirl");
        Card four = commonCardFactory.create("sv3pt5-193", "Mew ex");
        Card five = commonCardFactory.create("sv3pt5-131", "Lapras");
        Card six = commonCardFactory.create("sv3pt5-143", "Snorlax");

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(one);
        cards.add(two);
        cards.add(three);
        cards.add(four);
        cards.add(five);
        cards.add(six);

        Collection collection = new CommonCollection();
        collection.initializeCollection(true);
        Deck deck = new CommonDeck("Awesome Deck", one, two, three, four, five, six);

        HashMap<String, Deck> decks = new HashMap<String, Deck>();

        Player player = new CommonPlayer("Tester", deck, collection, decks);
        player.addDeck("test deck", deck);

        return player;
    }
}