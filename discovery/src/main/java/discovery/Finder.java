package discovery;

import java.util.Collection;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.CacheableSearchConfig;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.ClassHunter.SearchResult;
import org.burningwave.core.classes.SearchConfig;
import org.burningwave.core.io.PathHelper;
    
public class Finder {
	
	public static void main(String[] args) {
		System.out.println("Starting Finder");
		Finder myfinder = new Finder();
		Collection<Class<?>> result = myfinder.find();
		System.out.println("result=" + result.size());
	}
    
	
    public Collection<Class<?>> find() {
        ComponentSupplier componentSupplier = ComponentContainer.getInstance();
        PathHelper pathHelper = componentSupplier.getPathHelper();
        ClassHunter classHunter = componentSupplier.getClassHunter();
        
        CacheableSearchConfig searchConfig = SearchConfig.forPaths(
            //Here you can add all absolute path you want:
            //both folders, zip, jar, ear and war will be recursively scanned.
            //For example you can add: "C:\\Users\\user\\.m2", or a path of
            //an ear file that contains nested war with nested jar files
            //With the row below the search will be executed on runtime Classpaths
            pathHelper.getMainClassPaths()
            //If you want to scan only one jar or some certain jars you can use, for example,
            //this commented line of code instead "pathHelper.getMainClassPaths()":
            //pathHelper.getPaths(path -> path.contains("spring-core-4.3.4.RELEASE.jar"))
        ).by(
            ClassCriteria.create().allThat((cls) -> {
                return cls.getPackage().getName().matches("java.lang.Math");   // "java.lang.Math"  ".*springframework.*"
            })
        );
        //The loadInCache method loads all classes in the paths of the SearchConfig received as input
        //and then execute the queries of the ClassCriteria on the cached data. Once the data has been 
        //cached, it is possible to take advantage of faster searches for the loaded paths also through 
        //the findBy method. In addition to the loadCache method, loading data into the cache can also
        //take place via the findBy method if the latter receives a SearchConfig without ClassCriteria
        //as input. It is possible to clear the cache individually for every hunter (ClassHunter, 
        //ByteCodeHunter and ClassPathHunter) with clearCache method but to avoid inconsistencies 
        //it is recommended to perform this cleaning using the clearHuntersCache method of the ComponentSupplier.
        //To perform searches that do not use the cache you must intantiate the search configuration with 
        //SearchConfig.withoutUsingCache() method
        SearchResult searchResult = classHunter.loadInCache(searchConfig).find();
        
        return searchResult.getClasses();
    }
    
}
