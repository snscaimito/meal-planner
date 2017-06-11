package net.caimito.recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
	private final Logger logger = LoggerFactory.getLogger(RecipesController.class) ;

	private Map<String, Recipe> recipes = new HashMap<>() ;
	
	@RequestMapping(method=RequestMethod.PUT)
	public Recipe addRecipe(@RequestBody Recipe recipe) {
		logger.info(recipe.toString());
		recipes.put(recipe.getName(), recipe) ;
		return recipe ;
		/*
		 * TODO: Store incoming recipes as JSON in Solr
		 */
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Collection<Recipe> listAll() {
		logger.info("listing all recipes");
		return recipes.values() ;
	}

	@RequestMapping(method=RequestMethod.DELETE)
	public void deleteAll() {
		logger.info("clearing recipes database");
		recipes.clear();
	}

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public List<Recipe> searchRecipes(@RequestParam(name="term", required = true) String searchTerm) {
		logger.info(searchTerm);
		List<Recipe> recipesFound = new ArrayList<>() ;
		
		/*
		 * TODO: Query Solr and obtain recipe data
		 */
		
		if (searchTerm.isEmpty())
			return recipesFound ;
		
		for (Recipe recipe : recipes.values()) {
				if (recipe.getName().contains(searchTerm))
					recipesFound.add(recipe) ;
				else if (recipe.getDescription().contains(searchTerm))
					recipesFound.add(recipe) ;
		}
		
		return recipesFound ;
	}
}