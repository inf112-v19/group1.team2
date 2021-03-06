package inf112.skeleton.app.Objects;

import inf112.skeleton.app.card.MoveCard;
import inf112.skeleton.app.game.PlayerMovements;
import inf112.skeleton.app.grid.TileGrid;

import java.util.Iterator;
import java.util.LinkedList;

public class Dfs {
    private MoveCard[] listOfNine;
    private boolean[] selected;
    private LinkedList<Integer> queue;
    private int cardsToChoose;
    private Position currentPosition;
    private Position originalPosition;
    private TileGrid grid;
    public boolean foundAllCards;

    //Only created so we can use the checkIfMoveIsOutOfBounds method
    PlayerMovements movements;
    Graph graph;

    public Dfs(TileGrid grid, int cardsToChoose, Position position, MoveCard[] listOfNine) {
        this.grid = grid;
        this.cardsToChoose = cardsToChoose;
        this.listOfNine = listOfNine;
        currentPosition = position;
        originalPosition = position;
        selected = new boolean[listOfNine.length];

        queue = new LinkedList<>();
        movements = new PlayerMovements(new Player(),position.yPosition, position.xPosition, position.direction);
    }

    public void dfs() {
        this.graph = new Graph(listOfNine, cardsToChoose);

        for (int i = 0; i < listOfNine.length; i++) {
            initialize();
            if (isLegalMove(listOfNine[i], currentPosition))
                dfs(i, currentPosition, 0);

            if (foundAllCards)
                break;
        }
    }


    private void initialize() {
        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }
        foundAllCards = false;
        currentPosition = originalPosition.copy();
    }


    private void dfs(int i, Position position, int selectedCards) {
            selected[i] = true;
            selectedCards++;
            queue.add(i);

            if (queue.size() == cardsToChoose) {
                foundAllCards = true;
                return;
            }


            Iterator<Integer> iter = graph.getAdj()[i].listIterator();
            while (iter.hasNext() && !foundAllCards) {
                int n = iter.next();
                MoveCard.Type type = listOfNine[n].getType();
                Position newPosition = position.copy();
                if (!selected[n] && isLegalMove(listOfNine[n], newPosition))
                    dfs(n, newPosition, selectedCards);
            }
    }

    //This also updates the position
    private boolean isLegalMove(MoveCard moveCard, Position position) {
        return position.movePosition(moveCard.getType(), grid);
    }

    public LinkedList<Integer> getSelectedCards() {
        return queue;
    }
}
