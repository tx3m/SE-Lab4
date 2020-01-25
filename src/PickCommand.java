public class PickCommand extends Command{

    public PickCommand(){

    }

    @Override
    public boolean execute(Player player) {
        if(hasSecondWord()) {
            String itemToPick = getSecondWord();
         //   player.pickUp(itemToPick);
        }
        else {
            // if there is no second word, we don't know where to go...
            player.println("Pick up what?");
        }
        return false;
    }
}
