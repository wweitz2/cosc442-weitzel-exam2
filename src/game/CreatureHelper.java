package game;

import java.util.ArrayList;
import java.util.List;

public class CreatureHelper {
	
	private Creature creature;
	private World world;
	
	public CreatureHelper(World world, Creature creature) {
		this.world = world;
		this.creature = creature;
	}
	
	public void doAction(String message, Object ... params){
		for (Creature other : getCreaturesWhoSeeMe()){
			if (other == this.creature){
				other.notify("You " + message + ".", params);
			} else {
				other.notify(String.format("The %s %s.", creature.name(), makeSecondPerson(message)), params);
			}
		}
	}
	
	public void doAction(Item item, String message, Object ... params){
		if (creature.hp() < 1)
			return;
		
		for (Creature other : getCreaturesWhoSeeMe()){
			if (other == this.creature){
				other.notify("You " + message + ".", params);
			} else {
				other.notify(String.format("The %s %s.", creature.name(), makeSecondPerson(message)), params);
			}
			other.learnName(item);
		}
	}
	
	private List<Creature> getCreaturesWhoSeeMe(){
		List<Creature> others = new ArrayList<Creature>();
		int r = 9;
		for (int ox = -r; ox < r+1; ox++){
			for (int oy = -r; oy < r+1; oy++){
				if (ox*ox + oy*oy > r*r)
					continue;
				
				Creature other = world.creature(creature.x+ox, creature.y+oy, creature.z);
				
				if (other == null)
					continue;
				
				others.add(other);
			}
		}
		return others;
	}
	
	private String makeSecondPerson(String text){
		String[] words = text.split(" ");
		words[0] = words[0] + "s";
		
		StringBuilder builder = new StringBuilder();
		for (String word : words){
			builder.append(" ");
			builder.append(word);
		}
		
		return builder.toString().trim();
	}
	
}
